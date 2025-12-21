package br.com.gerador.generator;

import br.com.gerador.generator.config.ProjectConfig;
import br.com.gerador.metamodel.AccessControlConfig;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.Field;
import br.com.gerador.metamodel.model.MetaModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Gerador de carga inicial a partir de arquivos CSV.
 *
 * Lê arquivos CSV da pasta "carga-inicial" e gera:
 * - Script SQL para SQL Server (carga-inicial.sql)
 * - Arquivo data.sql do Spring Boot para carga automática
 */
public class InitialDataGenerator {

    private final Path csvFolder;
    private final Path outputPath;
    private final MetaModel metaModel;
    private final String schema;
    private ProjectConfig projectConfig;

    public InitialDataGenerator(Path csvFolder, Path outputPath, MetaModel metaModel) {
        this.csvFolder = csvFolder;
        this.outputPath = outputPath;
        this.metaModel = metaModel;
        this.schema = metaModel.getMetadata().getDatabase() != null
            ? metaModel.getMetadata().getDatabase().getSchema()
            : "dbo";
    }

    /**
     * Define as configurações do projeto (config.json).
     */
    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }

    /**
     * Obtém as configurações do projeto.
     */
    public ProjectConfig getProjectConfig() {
        return projectConfig;
    }

    /**
     * Gera os scripts de carga inicial.
     */
    public GenerationResult generate() throws IOException {
        GenerationResult result = new GenerationResult();

        if (!Files.exists(csvFolder)) {
            System.out.println("Pasta de carga inicial não encontrada: " + csvFolder);
            return result;
        }

        System.out.println("============================================================");
        System.out.println("Gerando scripts de carga inicial");
        System.out.println("Pasta CSV: " + csvFolder);
        System.out.println("============================================================");

        // Cria diretório de saída
        Path sqlOutputPath = outputPath.resolve("sql");
        Files.createDirectories(sqlOutputPath);

        // StringBuilder para SQL Server (carga-inicial.sql)
        StringBuilder sqlServerInserts = new StringBuilder();
        sqlServerInserts.append("-- ============================================================\n");
        sqlServerInserts.append("-- CARGA INICIAL - ").append(metaModel.getMetadata().getName().toUpperCase()).append("\n");
        sqlServerInserts.append("-- Gerado automaticamente a partir dos arquivos CSV\n");
        sqlServerInserts.append("-- Para SQL Server\n");
        sqlServerInserts.append("-- ============================================================\n\n");

        // StringBuilder para H2/Spring Boot (data.sql)
        StringBuilder h2Inserts = new StringBuilder();
        h2Inserts.append("-- ============================================================\n");
        h2Inserts.append("-- CARGA INICIAL - ").append(metaModel.getMetadata().getName().toUpperCase()).append("\n");
        h2Inserts.append("-- Gerado automaticamente a partir dos arquivos CSV\n");
        h2Inserts.append("-- Para H2 Database (Spring Boot Dev)\n");
        h2Inserts.append("-- ============================================================\n\n");

        // Lista de entidades na ordem correta (respeitando dependências)
        List<Entity> orderedEntities = getEntitiesInDependencyOrder();

        // Processa cada CSV
        for (Entity entity : orderedEntities) {
            String csvFileName = toSnakeCase(entity.getName()) + ".csv";
            Path csvFile = csvFolder.resolve(csvFileName);

            if (Files.exists(csvFile)) {
                System.out.println("\nProcessando: " + csvFileName);

                // Gera INSERTs para SQL Server (com schema)
                String sqlServerScript = generateInsertsFromCsv(entity, csvFile, true);
                if (!sqlServerScript.isEmpty()) {
                    // Adiciona SET IDENTITY_INSERT para SQL Server
                    String tableName = entity.getTableName() != null ? entity.getTableName() : entity.getName();
                    sqlServerInserts.append("SET IDENTITY_INSERT ").append(schema).append(".").append(tableName).append(" ON;\n");
                    sqlServerInserts.append(sqlServerScript);
                    sqlServerInserts.append("SET IDENTITY_INSERT ").append(schema).append(".").append(tableName).append(" OFF;\n\n");
                }

                // Gera INSERTs para H2 (sem schema, sem N'')
                String h2Script = generateInsertsFromCsv(entity, csvFile, false);
                if (!h2Script.isEmpty()) {
                    h2Inserts.append(h2Script);
                    result.incrementFileCount();
                }
            }
        }

        // Gera INSERTs para perfis e usuários (se configurado)
        if (metaModel.getMetadata() != null && metaModel.getMetadata().hasAccessControl()) {
            System.out.println("\nGerando carga inicial de perfis e usuários...");
            AccessControlConfig accessControl = metaModel.getMetadata().getAccessControl();

            // SQL Server
            sqlServerInserts.append("\n-- ============================================================\n");
            sqlServerInserts.append("-- PERFIS E USUARIOS\n");
            sqlServerInserts.append("-- ============================================================\n");
            sqlServerInserts.append(generatePerfisSqlServer(accessControl));
            sqlServerInserts.append(generateUsuariosSqlServer(accessControl));

            // H2
            h2Inserts.append("\n-- ============================================================\n");
            h2Inserts.append("-- PERFIS E USUARIOS\n");
            h2Inserts.append("-- ============================================================\n");
            h2Inserts.append(generatePerfisH2(accessControl));
            h2Inserts.append(generateUsuariosH2(accessControl));

            System.out.println("  [OK] Perfis e usuários gerados");
        }

        // Salva o script SQL Server
        Path sqlFile = sqlOutputPath.resolve("carga-inicial.sql");
        Files.writeString(sqlFile, sqlServerInserts.toString(), StandardCharsets.UTF_8);
        System.out.println("\n[OK] " + sqlFile.getFileName() + " (SQL Server)");

        // Adiciona comandos de reset de auto-increment para H2
        // Apenas para entidades com PK numérica (auto-increment)
        h2Inserts.append("\n-- ============================================================\n");
        h2Inserts.append("-- RESET AUTO-INCREMENT SEQUENCES\n");
        h2Inserts.append("-- ============================================================\n");
        Map<String, Integer> maxIds = calculateMaxIds(orderedEntities);
        for (Entity entity : orderedEntities) {
            // Apenas gera RESTART WITH para PKs numéricas
            if (!isNumericPrimaryKey(entity)) {
                continue;
            }
            String tableName = entity.getTableName() != null ? entity.getTableName() : entity.getName();
            String h2TableName = toHibernateTableName(tableName);
            String pkColumn = getPrimaryKeyColumn(entity);
            if (pkColumn != null && maxIds.containsKey(entity.getName())) {
                int nextId = maxIds.get(entity.getName()) + 1;
                // Para H2, converte o nome da coluna para o padrão do Hibernate
                String h2PkColumn = toHibernateColumnName(pkColumn);
                h2Inserts.append("ALTER TABLE ").append(schema).append(".").append(h2TableName)
                    .append(" ALTER COLUMN ").append(h2PkColumn).append(" RESTART WITH ").append(nextId).append(";\n");
            }
        }

        // Gera o data.sql para Spring Boot (H2)
        Path dataSqlFile = outputPath.resolve("src/main/resources/data.sql");
        Files.createDirectories(dataSqlFile.getParent());
        Files.writeString(dataSqlFile, h2Inserts.toString(), StandardCharsets.UTF_8);
        System.out.println("[OK] data.sql (H2/Spring Boot)");

        result.incrementFileCount();
        result.incrementFileCount();

        System.out.println("\n============================================================");
        System.out.println("Carga inicial gerada com sucesso!");
        System.out.println("============================================================");

        return result;
    }

    /**
     * Gera INSERTs a partir de um arquivo CSV.
     * @param entity Entidade
     * @param csvFile Arquivo CSV
     * @param sqlServer true para SQL Server (com schema e N''), false para H2
     */
    private String generateInsertsFromCsv(Entity entity, Path csvFile, boolean sqlServer) throws IOException {
        StringBuilder sb = new StringBuilder();
        String tableName = entity.getTableName() != null ? entity.getTableName() : entity.getName();

        // Para H2, o Hibernate/Spring Boot usa a estratégia SpringPhysicalNamingStrategy
        // que adiciona underscore apenas quando há lowercase→uppercase (não uppercase→uppercase)
        // Ex: AmbitoGestao -> ambito_gestao, AvaliacaoSDA -> avaliacaosda
        if (!sqlServer) {
            tableName = toHibernateTableName(tableName);
        }

        sb.append("-- ").append(entity.getName()).append("\n");
        sb.append("-- ").append(entity.getDescription() != null ? entity.getDescription() : "").append("\n");

        try (BufferedReader reader = Files.newBufferedReader(csvFile, StandardCharsets.UTF_8)) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return "";
            }

            // Remove BOM se presente
            headerLine = headerLine.replace("\uFEFF", "");

            // Parse header (colunas)
            String[] columns = headerLine.split(";");
            List<String> columnNames = Arrays.stream(columns)
                .map(String::trim)
                .collect(Collectors.toList());

            // Para H2, converte os nomes das colunas para o padrão do Hibernate
            List<String> outputColumnNames;
            if (!sqlServer) {
                outputColumnNames = columnNames.stream()
                    .map(this::toHibernateColumnName)
                    .collect(Collectors.toList());
            } else {
                outputColumnNames = columnNames;
            }

            // Mapeia colunas para campos da entidade
            Map<String, Field> fieldMap = new HashMap<>();
            for (Field field : entity.getFields()) {
                String columnName = field.getColumnName() != null ? field.getColumnName() : field.getName();
                fieldMap.put(columnName.toLowerCase(), field);
                fieldMap.put(toSnakeCase(field.getName()).toLowerCase(), field);
            }

            // Lê cada linha e gera INSERT
            String line;
            int rowCount = 0;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] values = line.split(";", -1); // -1 para manter campos vazios

                sb.append("INSERT INTO ");
                // Ambos usam schema (H2 também precisa do schema dbo)
                sb.append(schema).append(".");
                sb.append(tableName);
                sb.append(" (");
                sb.append(String.join(", ", outputColumnNames));
                sb.append(") VALUES (");

                List<String> formattedValues = new ArrayList<>();
                for (int i = 0; i < values.length && i < columnNames.size(); i++) {
                    String value = values[i].trim();
                    String columnName = columnNames.get(i).toLowerCase();
                    Field field = fieldMap.get(columnName);

                    formattedValues.add(formatValue(value, field, sqlServer));
                }

                sb.append(String.join(", ", formattedValues));
                sb.append(");\n");
                rowCount++;
            }

            if (sqlServer) {
                System.out.println("  [OK] " + rowCount + " registros");
            }
        }

        sb.append("\n");
        return sb.toString();
    }

    /**
     * Formata um valor para SQL baseado no tipo do campo.
     * @param value Valor a formatar
     * @param field Campo da entidade
     * @param sqlServer true para SQL Server (N''), false para H2 ('')
     */
    private String formatValue(String value, Field field, boolean sqlServer) {
        if (value == null || value.isEmpty()) {
            return "NULL";
        }

        if (field == null) {
            // Se não encontrou o campo, tenta inferir pelo valor
            if (value.matches("-?\\d+")) {
                return value;
            } else if (value.matches("-?\\d+\\.\\d+")) {
                return value;
            } else {
                return formatString(value, sqlServer);
            }
        }

        String dataType = field.getDataType() != null ? field.getDataType().name() : "STRING";

        switch (dataType.toUpperCase()) {
            case "INTEGER":
            case "LONG":
            case "INT":
                return value.isEmpty() ? "NULL" : value;
            case "DECIMAL":
            case "DOUBLE":
            case "FLOAT":
            case "NUMERIC":
                return value.isEmpty() ? "NULL" : value.replace(",", ".");
            case "BOOLEAN":
                return parseBooleanValue(value, sqlServer);
            case "DATE":
            case "DATETIME":
            case "TIMESTAMP":
                return "'" + value + "'";
            default:
                // STRING, TEXT, VARCHAR, etc.
                return formatString(value, sqlServer);
        }
    }

    /**
     * Formata string para SQL.
     * SQL Server usa N'valor', H2 usa 'valor'
     */
    private String formatString(String value, boolean sqlServer) {
        if (sqlServer) {
            return "N'" + escapeString(value) + "'";
        } else {
            return "'" + escapeString(value) + "'";
        }
    }

    /**
     * Escapa caracteres especiais para SQL.
     */
    private String escapeString(String value) {
        return value.replace("'", "''");
    }

    /**
     * Converte valor booleano para SQL.
     * SQL Server/H2 usam 1/0, mas H2 também aceita TRUE/FALSE
     */
    private String parseBooleanValue(String value, boolean sqlServer) {
        if (value == null || value.isEmpty()) {
            return "NULL";
        }
        String lower = value.toLowerCase();
        boolean isTrue = lower.equals("true") || lower.equals("1") || lower.equals("sim") || lower.equals("s");

        if (sqlServer) {
            return isTrue ? "1" : "0";
        } else {
            // H2 aceita TRUE/FALSE
            return isTrue ? "TRUE" : "FALSE";
        }
    }

    /**
     * Converte CamelCase para snake_case.
     * Ex: AmbitoGestao -> ambito_gestao
     * Usado para nomes de arquivos CSV.
     */
    private String toSnakeCase(String name) {
        return name.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    /**
     * Converte nome de coluna para o padrão do Hibernate/Spring Boot.
     * A SpringPhysicalNamingStrategy (CamelCaseToUnderscoresNamingStrategy) converte
     * CamelCase para snake_case, usando a mesma lógica de nomes de tabela.
     * Ex: NomeEmpresa -> nome_empresa, NomeCBO -> nomecbo, id_Empresa -> id_empresa
     */
    private String toHibernateColumnName(String name) {
        // Usa a mesma lógica de conversão de nomes de tabela
        return toHibernateTableName(name);
    }

    /**
     * Converte nome de entidade para nome de tabela H2 usando a estratégia do Hibernate/Spring Boot.
     * A SpringPhysicalNamingStrategy (CamelCaseToUnderscoresNamingStrategy) adiciona underscore
     * quando uma maiúscula é precedida de minúscula E seguida de minúscula (início de nova palavra).
     * Siglas em maiúsculas no final (como SDA) são tratadas como uma unidade sem underscore.
     * Ex: AmbitoGestao -> ambito_gestao, AvaliacaoSDA -> avaliacaosda
     */
    private String toHibernateTableName(String name) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (i > 0 && Character.isUpperCase(c)) {
                char prev = name.charAt(i - 1);
                // Verifica se a letra seguinte (se existir) é minúscula
                boolean nextIsLower = (i + 1 < name.length()) && Character.isLowerCase(name.charAt(i + 1));
                // Adiciona underscore se:
                // - A letra anterior for minúscula E
                // - A letra seguinte for minúscula (indica início de nova palavra camelCase)
                if (Character.isLowerCase(prev) && nextIsLower) {
                    result.append('_');
                }
            }
            result.append(Character.toLowerCase(c));
        }
        return result.toString();
    }

    /**
     * Calcula o maior ID de cada entidade a partir dos arquivos CSV.
     */
    private Map<String, Integer> calculateMaxIds(List<Entity> entities) throws IOException {
        Map<String, Integer> maxIds = new HashMap<>();

        for (Entity entity : entities) {
            String csvFileName = toSnakeCase(entity.getName()) + ".csv";
            Path csvFile = csvFolder.resolve(csvFileName);

            if (Files.exists(csvFile)) {
                String pkColumn = getPrimaryKeyColumn(entity);
                if (pkColumn != null) {
                    int maxId = getMaxIdFromCsv(csvFile, pkColumn);
                    if (maxId > 0) {
                        maxIds.put(entity.getName(), maxId);
                    }
                }
            }
        }

        return maxIds;
    }

    /**
     * Obtém o maior ID de um arquivo CSV.
     */
    private int getMaxIdFromCsv(Path csvFile, String pkColumn) throws IOException {
        int maxId = 0;

        try (BufferedReader reader = Files.newBufferedReader(csvFile, StandardCharsets.UTF_8)) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return 0;
            }

            // Remove BOM se presente
            headerLine = headerLine.replace("\uFEFF", "");

            String[] columns = headerLine.split(";");
            int pkIndex = -1;
            for (int i = 0; i < columns.length; i++) {
                if (columns[i].trim().equalsIgnoreCase(pkColumn)) {
                    pkIndex = i;
                    break;
                }
            }

            if (pkIndex == -1) {
                return 0;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] values = line.split(";", -1);
                if (pkIndex < values.length) {
                    try {
                        int id = Integer.parseInt(values[pkIndex].trim());
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException e) {
                        // Ignora valores não numéricos
                    }
                }
            }
        }

        return maxId;
    }

    /**
     * Obtém o nome da coluna PK da entidade.
     */
    private String getPrimaryKeyColumn(Entity entity) {
        for (Field field : entity.getFields()) {
            if (field.isPrimaryKey()) {
                return field.getColumnName() != null ? field.getColumnName() : field.getName();
            }
        }
        return null;
    }

    /**
     * Verifica se a PK da entidade é numérica (INTEGER ou LONG).
     * Apenas PKs numéricas podem ter RESTART WITH.
     */
    private boolean isNumericPrimaryKey(Entity entity) {
        for (Field field : entity.getFields()) {
            if (field.isPrimaryKey()) {
                String dataType = field.getDataType() != null ? field.getDataType().name() : "STRING";
                return dataType.equalsIgnoreCase("INTEGER") || dataType.equalsIgnoreCase("LONG");
            }
        }
        return false;
    }

    /**
     * Ordena as entidades respeitando dependências (FK).
     * Entidades sem dependências vêm primeiro.
     */
    private List<Entity> getEntitiesInDependencyOrder() {
        List<Entity> entities = new ArrayList<>(metaModel.getEntities());
        List<Entity> ordered = new ArrayList<>();
        Set<String> processed = new HashSet<>();

        while (ordered.size() < entities.size()) {
            for (Entity entity : entities) {
                if (processed.contains(entity.getName())) {
                    continue;
                }

                // Verifica se todas as dependências já foram processadas
                boolean allDependenciesProcessed = true;
                for (Field field : entity.getFields()) {
                    if (field.getReference() != null) {
                        String refEntity = field.getReference().getEntity();
                        if (!processed.contains(refEntity) && !refEntity.equals(entity.getName())) {
                            allDependenciesProcessed = false;
                            break;
                        }
                    }
                }

                if (allDependenciesProcessed) {
                    ordered.add(entity);
                    processed.add(entity.getName());
                }
            }

            // Evita loop infinito em caso de dependência circular
            if (ordered.size() == processed.size() && ordered.size() < entities.size()) {
                for (Entity entity : entities) {
                    if (!processed.contains(entity.getName())) {
                        ordered.add(entity);
                        processed.add(entity.getName());
                    }
                }
            }
        }

        return ordered;
    }

    /**
     * Resultado da geração.
     */
    public static class GenerationResult {
        private int fileCount = 0;
        private final List<String> errors = new ArrayList<>();

        public void incrementFileCount() {
            fileCount++;
        }

        public int getFileCount() {
            return fileCount;
        }

        public void addError(String error) {
            errors.add(error);
        }

        public List<String> getErrors() {
            return errors;
        }

        public boolean hasErrors() {
            return !errors.isEmpty();
        }
    }

    // ============================================================
    // Métodos para geração de Perfis e Usuários
    // ============================================================

    /**
     * Gera INSERTs de perfis para SQL Server.
     */
    private String generatePerfisSqlServer(AccessControlConfig accessControl) {
        StringBuilder sb = new StringBuilder();
        sb.append("-- Perfis de acesso\n");

        if (accessControl.getRoles() != null) {
            for (AccessControlConfig.Role role : accessControl.getRoles()) {
                sb.append("INSERT INTO ").append(schema).append(".perfil (id, nome, descricao, ativo) VALUES (N'");
                sb.append(role.getId()).append("', N'");
                sb.append(role.getName()).append("', N'");
                sb.append(role.getDescription() != null ? role.getDescription() : "").append("', 1);\n");

                // Inserir permissões
                if (role.getPermissions() != null) {
                    for (AccessControlConfig.Permission perm : role.getPermissions()) {
                        String permStr = perm.getModule() + ":" + perm.getAccess();
                        sb.append("INSERT INTO ").append(schema).append(".perfil_permissao (perfil_id, permissao) VALUES (N'");
                        sb.append(role.getId()).append("', N'").append(permStr).append("');\n");
                    }
                }
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Gera INSERTs de usuários para SQL Server.
     */
    private String generateUsuariosSqlServer(AccessControlConfig accessControl) {
        StringBuilder sb = new StringBuilder();
        sb.append("-- Usuários de exemplo\n");
        sb.append("SET IDENTITY_INSERT ").append(schema).append(".usuario ON;\n");

        // BCrypt hash for password "123456" - generated by Spring BCryptPasswordEncoder
        // Note: BCrypt generates a different hash each time, but they all verify against "123456"
        String senhaHash = "$2a$10$crXXmwWc3SxYMZU1Fg3wjuyMia4qh2LIMHWwg8BG6uV48gICpLiiC";

        if (accessControl.getRoles() != null) {
            int id = 1;
            for (AccessControlConfig.Role role : accessControl.getRoles()) {
                String username = role.getId();
                String nome = "Usuário " + role.getName();
                String email = role.getId() + "@sistema.com";

                sb.append("INSERT INTO ").append(schema).append(".usuario (id, username, senha, nome, email, ativo, perfil_id, data_criacao) VALUES (");
                sb.append(id++).append(", N'").append(username).append("', N'").append(senhaHash).append("', N'");
                sb.append(nome).append("', N'").append(email).append("', 1, N'").append(role.getId()).append("', GETDATE());\n");
            }
        }

        sb.append("SET IDENTITY_INSERT ").append(schema).append(".usuario OFF;\n\n");
        return sb.toString();
    }

    /**
     * Gera INSERTs de perfis para H2.
     */
    private String generatePerfisH2(AccessControlConfig accessControl) {
        StringBuilder sb = new StringBuilder();
        sb.append("-- Perfis de acesso\n");

        if (accessControl.getRoles() != null) {
            for (AccessControlConfig.Role role : accessControl.getRoles()) {
                sb.append("INSERT INTO ").append(schema).append(".perfil (id, nome, descricao, ativo) VALUES ('");
                sb.append(role.getId()).append("', '");
                sb.append(role.getName()).append("', '");
                sb.append(role.getDescription() != null ? role.getDescription() : "").append("', true);\n");

                // Inserir permissões
                if (role.getPermissions() != null) {
                    for (AccessControlConfig.Permission perm : role.getPermissions()) {
                        String permStr = perm.getModule() + ":" + perm.getAccess();
                        sb.append("INSERT INTO ").append(schema).append(".perfil_permissao (perfil_id, permissao) VALUES ('");
                        sb.append(role.getId()).append("', '").append(permStr).append("');\n");
                    }
                }
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Gera INSERTs de usuários para H2.
     */
    private String generateUsuariosH2(AccessControlConfig accessControl) {
        StringBuilder sb = new StringBuilder();
        sb.append("-- Usuários de exemplo\n");

        // BCrypt hash for password "123456" - generated by Spring BCryptPasswordEncoder
        // Note: BCrypt generates a different hash each time, but they all verify against "123456"
        String senhaHash = "$2a$10$crXXmwWc3SxYMZU1Fg3wjuyMia4qh2LIMHWwg8BG6uV48gICpLiiC";

        if (accessControl.getRoles() != null) {
            int id = 1;
            for (AccessControlConfig.Role role : accessControl.getRoles()) {
                String username = role.getId();
                String nome = "Usuário " + role.getName();
                String email = role.getId() + "@sistema.com";

                sb.append("INSERT INTO ").append(schema).append(".usuario (id, username, senha, nome, email, ativo, perfil_id, data_criacao) VALUES (");
                sb.append(id++).append(", '").append(username).append("', '").append(senhaHash).append("', '");
                sb.append(nome).append("', '").append(email).append("', true, '").append(role.getId()).append("', CURRENT_TIMESTAMP);\n");
            }

            // Reset auto-increment
            sb.append("ALTER TABLE ").append(schema).append(".usuario ALTER COLUMN id RESTART WITH ").append(id).append(";\n");
        }

        sb.append("\n");
        return sb.toString();
    }
}
