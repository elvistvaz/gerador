package br.com.gerador.generator;

import br.com.gerador.generator.config.ProjectConfig;
import br.com.gerador.generator.template.laravel.*;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.Field;
import br.com.gerador.metamodel.model.MetaModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Gerador de código Laravel a partir do MetaModel.
 */
public class LaravelGenerator {

    private final Path outputDir;
    private ProjectConfig projectConfig;

    private final LaravelModelTemplate modelTemplate;
    private final LaravelControllerTemplate controllerTemplate;
    private final LaravelWebControllerTemplate webControllerTemplate;
    private final LaravelViewTemplate viewTemplate;
    private final LaravelMigrationTemplate migrationTemplate;
    private final LaravelRouteTemplate routeTemplate;
    private final LaravelRequestTemplate requestTemplate;
    private final LaravelProjectTemplate projectTemplate;

    private final List<String> generatedFiles = new ArrayList<>();
    private final List<String> errors = new ArrayList<>();

    public LaravelGenerator(Path outputDir) {
        this.outputDir = outputDir;
        this.modelTemplate = new LaravelModelTemplate();
        this.controllerTemplate = new LaravelControllerTemplate();
        this.webControllerTemplate = new LaravelWebControllerTemplate();
        this.viewTemplate = new LaravelViewTemplate();
        this.migrationTemplate = new LaravelMigrationTemplate();
        this.routeTemplate = new LaravelRouteTemplate();
        this.requestTemplate = new LaravelRequestTemplate();
        this.projectTemplate = new LaravelProjectTemplate();
    }

    /**
     * Define as configurações do projeto (config.json).
     */
    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;

        // Passa as configurações para os templates
        this.modelTemplate.setProjectConfig(projectConfig);
        this.controllerTemplate.setProjectConfig(projectConfig);
        this.webControllerTemplate.setProjectConfig(projectConfig);
        this.viewTemplate.setProjectConfig(projectConfig);
        this.migrationTemplate.setProjectConfig(projectConfig);
        this.routeTemplate.setProjectConfig(projectConfig);
        this.requestTemplate.setProjectConfig(projectConfig);
        this.projectTemplate.setProjectConfig(projectConfig);
    }

    /**
     * Gera todo o código Laravel para o MetaModel.
     */
    public GenerationResult generate(MetaModel metaModel) {
        generatedFiles.clear();
        errors.clear();

        String projectName = metaModel.getMetadata() != null
            ? metaModel.getMetadata().getName()
            : "Application";

        System.out.println("=".repeat(60));
        System.out.println("Iniciando geração de código Laravel");
        System.out.println("Projeto: " + projectName);
        System.out.println("Diretório de saída: " + outputDir.toAbsolutePath());
        System.out.println("=".repeat(60));

        // Cria estrutura de diretórios Laravel
        createDirectories();

        // Copia vendor pré-compilado se disponível (acelera muito o processo!)
        copyVendorTemplate();

        // Gera arquivos de infraestrutura do projeto
        generateProjectFiles(metaModel);

        // Gera código para cada entidade
        for (Entity entity : metaModel.getEntities()) {
            generateForEntity(entity, metaModel);
        }

        // Gera rotas consolidadas
        generateRoutes(metaModel);

        // Gera rotas web com CRUDs
        generateWebRoutes(metaModel);

        // Gera seeders a partir dos CSVs
        generateSeedersFromCSV(metaModel);

        System.out.println("\nResumo da geração:");
        System.out.println("  Total de arquivos gerados: " + generatedFiles.size());
        if (!errors.isEmpty()) {
            System.out.println("  Total de erros: " + errors.size());
        }

        return new GenerationResult(generatedFiles, errors);
    }

    /**
     * Copia o vendor template pré-compilado se disponível.
     * Isso acelera MUITO o processo de geração, evitando ter que rodar composer install.
     */
    private void copyVendorTemplate() {
        Path vendorTemplatePath = Path.of("c:/java/workspace/Gerador/templates/laravel-vendor/vendor.tar.gz");

        if (!Files.exists(vendorTemplatePath)) {
            System.out.println("\n⚠ Vendor template não encontrado em: " + vendorTemplatePath);
            System.out.println("  Execute 'composer install' manualmente após a geração.");
            return;
        }

        System.out.println("\n📦 Vendor template encontrado! Descompactando...");

        try {
            Path vendorOutputPath = outputDir.resolve("vendor");

            // Remove vendor existente se houver
            if (Files.exists(vendorOutputPath)) {
                System.out.println("  Removendo vendor existente...");
                deleteDirectory(vendorOutputPath);
            }

            // Descompacta o vendor.tar.gz
            ProcessBuilder pb = new ProcessBuilder(
                "tar", "-xzf",
                vendorTemplatePath.toAbsolutePath().toString(),
                "-C", outputDir.toAbsolutePath().toString()
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("  ✓ Vendor descompactado com sucesso!");
                System.out.println("  ⚡ Deploy acelerado - composer install não será necessário!");
            } else {
                System.err.println("  ✗ Erro ao descompactar vendor (exit code: " + exitCode + ")");
                System.err.println("  Execute 'composer install' manualmente.");
            }

        } catch (Exception e) {
            System.err.println("  ✗ Erro ao copiar vendor template: " + e.getMessage());
            System.err.println("  Execute 'composer install' manualmente.");
        }
    }

    /**
     * Remove um diretório recursivamente.
     */
    private void deleteDirectory(Path directory) throws IOException {
        if (Files.exists(directory)) {
            Files.walk(directory)
                .sorted((a, b) -> b.compareTo(a)) // Reverse order for deletion
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        // Ignore
                    }
                });
        }
    }

    /**
     * Cria a estrutura de diretórios do Laravel.
     */
    private void createDirectories() {
        try {
            // App
            Files.createDirectories(outputDir.resolve("app/Models"));
            Files.createDirectories(outputDir.resolve("app/Http/Controllers"));
            Files.createDirectories(outputDir.resolve("app/Http/Controllers/Web"));
            Files.createDirectories(outputDir.resolve("app/Http/Requests"));
            Files.createDirectories(outputDir.resolve("app/Http/Middleware"));
            Files.createDirectories(outputDir.resolve("app/Providers"));
            Files.createDirectories(outputDir.resolve("app/Traits"));
            Files.createDirectories(outputDir.resolve("app/Helpers"));

            // Bootstrap (Laravel 12 requirement)
            Files.createDirectories(outputDir.resolve("bootstrap/cache"));

            // Database
            Files.createDirectories(outputDir.resolve("database/migrations"));
            Files.createDirectories(outputDir.resolve("database/seeders"));
            Files.createDirectories(outputDir.resolve("database/factories"));

            // Config
            Files.createDirectories(outputDir.resolve("config"));

            // Routes
            Files.createDirectories(outputDir.resolve("routes/api"));

            // Resources
            Files.createDirectories(outputDir.resolve("resources/views"));

            // Public
            Files.createDirectories(outputDir.resolve("public"));

            // Storage
            Files.createDirectories(outputDir.resolve("storage/app"));
            Files.createDirectories(outputDir.resolve("storage/framework/cache"));
            Files.createDirectories(outputDir.resolve("storage/framework/sessions"));
            Files.createDirectories(outputDir.resolve("storage/framework/views"));
            Files.createDirectories(outputDir.resolve("storage/logs"));

            // Tests
            Files.createDirectories(outputDir.resolve("tests/Feature"));
            Files.createDirectories(outputDir.resolve("tests/Unit"));

            System.out.println("Estrutura de diretórios criada com sucesso.");
        } catch (IOException e) {
            errors.add("Erro ao criar diretórios: " + e.getMessage());
            System.err.println("Erro ao criar diretórios: " + e.getMessage());
        }
    }

    /**
     * Gera arquivos de infraestrutura do projeto Laravel.
     */
    private void generateProjectFiles(MetaModel metaModel) {
        System.out.println("\nGerando arquivos de infraestrutura...");

        // composer.json
        generateFile("composer.json", projectTemplate.generateComposerJson(metaModel));

        // .env
        generateFile(".env", projectTemplate.generateEnvFile(metaModel));

        // .env.example
        generateFile(".env.example", projectTemplate.generateEnvExample(metaModel));

        // artisan
        generateFile("artisan", projectTemplate.generateArtisan());

        // bootstrap/app.php (Laravel 12 requirement)
        generateFile("bootstrap/app.php", projectTemplate.generateBootstrapApp(metaModel));

        // public/index.php (Laravel entry point)
        generateFile("public/index.php", projectTemplate.generatePublicIndexPhp());

        // BaseModel
        generateFile("app/Models/BaseModel.php", projectTemplate.generateBaseModel());

        // Controller base
        generateFile("app/Http/Controllers/Controller.php", projectTemplate.generateBaseController());

        // AppServiceProvider
        generateFile("app/Providers/AppServiceProvider.php", projectTemplate.generateAppServiceProvider());

        // routes/api.php
        generateFile("routes/api.php", projectTemplate.generateApiRoutes());

        // database.php config
        generateFile("config/database.php", projectTemplate.generateDatabaseConfig());

        // cors.php config
        generateFile("config/cors.php", projectTemplate.generateCorsConfig());

        // session.php config
        generateFile("config/session.php", projectTemplate.generateSessionConfig());

        // auth.php config
        generateFile("config/auth.php", projectTemplate.generateAuthConfig());

        // resources/views/welcome.blade.php
        generateFile("resources/views/welcome.blade.php", projectTemplate.generateWelcomeView(metaModel));

        // Layout e views de autenticação
        generateFile("resources/views/layouts/app.blade.php", projectTemplate.generateLayoutBlade(metaModel));
        generateFile("resources/views/auth/login.blade.php", projectTemplate.generateLoginView());
        generateFile("resources/views/auth/register.blade.php", projectTemplate.generateRegisterView());
        generateFile("resources/views/dashboard.blade.php", projectTemplate.generateDashboardView());

        // Traduções em português (pt_BR)
        generateFile("lang/pt_BR/pagination.php", projectTemplate.generatePaginationTranslation());
        generateFile("lang/pt_BR/validation.php", projectTemplate.generateValidationTranslation());

        // Template customizado de paginação
        generateFile("resources/views/vendor/pagination/bootstrap-5.blade.php", projectTemplate.generateBootstrap5PaginationView());

        // AuthController
        generateFile("app/Http/Controllers/AuthController.php", projectTemplate.generateAuthController(metaModel));

        // Session Context (se configurado)
        String sessionController = projectTemplate.generateSessionController(metaModel);
        if (!sessionController.isEmpty()) {
            generateFile("app/Http/Controllers/SessionController.php", sessionController);

            String sessionMiddleware = projectTemplate.generateSessionMiddleware(metaModel);
            generateFile("app/Http/Middleware/EnsureSessionContextSelected.php", sessionMiddleware);

            String sessionView = projectTemplate.generateSessionSelectView(metaModel);
            generateFile("resources/views/session/select.blade.php", sessionView);

            System.out.println("  ✓ Sistema de contexto de sessão gerado");
        }

        // User Model
        generateFile("app/Models/User.php", projectTemplate.generateUserModel());

        // Users migration
        generateFile("database/migrations/0001_01_01_000000_create_users_table.php", projectTemplate.generateUsersMigration());

        // README.md
        generateFile("README.md", projectTemplate.generateReadme(metaModel));

        // Database Seeder
        generateFile("database/seeders/DatabaseSeeder.php", projectTemplate.generateDatabaseSeeder(metaModel));

        System.out.println("Arquivos de infraestrutura gerados.");
    }

    /**
     * Gera seeders a partir de arquivos CSV.
     */
    private void generateSeedersFromCSV(MetaModel metaModel) {
        String projectName = metaModel.getMetadata().getName().toLowerCase();
        Path csvDir = Path.of("c:/java/workspace/Gerador/metamodel/data", projectName, "carga-inicial");

        if (!Files.exists(csvDir)) {
            System.out.println("Diretório de carga inicial não encontrado: " + csvDir);
            return;
        }

        System.out.println("\n════════════════════════════════════════════════════════════════");
        System.out.println("GERANDO SEEDERS A PARTIR DOS CSVs");
        System.out.println("════════════════════════════════════════════════════════════════");

        StringBuilder allInserts = new StringBuilder();
        int totalTables = 0;
        int totalRecords = 0;

        try {
            List<Path> csvFiles = Files.list(csvDir)
                    .filter(p -> p.toString().endsWith(".csv"))
                    .toList();

            // Ordena os CSVs por dependência de FK (ordenação topológica)
            List<Path> orderedCsvFiles = sortByForeignKeyDependencies(csvFiles, metaModel);

            System.out.println("  Ordem de carga (respeitando FKs):");
            for (int i = 0; i < orderedCsvFiles.size(); i++) {
                System.out.println("    " + (i + 1) + ". " + orderedCsvFiles.get(i).getFileName());
            }
            System.out.println();

            for (Path csvFile : orderedCsvFiles) {
                String fileName = csvFile.getFileName().toString();
                String tableName = fileName.replace(".csv", "");

                System.out.println("  Processando: " + fileName);

                // Encontra a entidade correspondente no MetaModel
                Entity entity = findEntityByTableName(metaModel, tableName);

                List<java.util.Map<String, String>> data = readCSV(csvFile);

                if (!data.isEmpty()) {
                    for (java.util.Map<String, String> row : data) {
                        allInserts.append("        DB::table('").append(tableName).append("')->insert([\n");

                        for (java.util.Map.Entry<String, String> entry : row.entrySet()) {
                            // Mapeia o header do CSV para o columnName do JSON
                            String column = mapCsvHeaderToColumnName(entry.getKey(), entity);
                            String value = entry.getValue();

                            if (value == null || value.trim().isEmpty()) {
                                allInserts.append("            '").append(column).append("' => null,\n");
                            } else if (column.toLowerCase().matches(".*(id|quantidade|ordem|peso|carga_horaria)$") && value.matches("\\d+(\\.\\d+)?")) {
                                // Numeric values
                                allInserts.append("            '").append(column).append("' => ").append(value).append(",\n");
                            } else {
                                // String values - escape single quotes
                                String escapedValue = value.replace("\\", "\\\\").replace("'", "\\'");
                                allInserts.append("            '").append(column).append("' => '").append(escapedValue).append("',\n");
                            }
                        }

                        allInserts.append("        ]);\n\n");
                        totalRecords++;
                    }
                    totalTables++;
                }
            }

            // Gera o seeder consolidado
            String seederContent = """
<?php

namespace Database\\Seeders;

use Illuminate\\Database\\Seeder;
use Illuminate\\Support\\Facades\\DB;

class InitialDataSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        // Desabilita foreign key checks temporariamente (compatível com SQLite e MySQL)
        $driver = DB::connection()->getDriverName();

        if ($driver === 'sqlite') {
            DB::statement('PRAGMA foreign_keys = OFF;');
        } else {
            DB::statement('SET FOREIGN_KEY_CHECKS=0;');
        }

        // Carga inicial de dados
%s
        // Reabilita foreign key checks
        if ($driver === 'sqlite') {
            DB::statement('PRAGMA foreign_keys = ON;');
        } else {
            DB::statement('SET FOREIGN_KEY_CHECKS=1;');
        }
    }
}
""".formatted(allInserts.toString());

            generateFile("database/seeders/InitialDataSeeder.php", seederContent);

            System.out.println("\n✓ Seeder gerado com sucesso!");
            System.out.println("  Total de tabelas: " + totalTables);
            System.out.println("  Total de registros: " + totalRecords);

        } catch (IOException e) {
            System.err.println("Erro ao processar arquivos CSV: " + e.getMessage());
        }

        System.out.println("════════════════════════════════════════════════════════════════\n");
    }

    /**
     * Lê um arquivo CSV e retorna uma lista de mapas (linha -> coluna:valor).
     */
    private List<java.util.Map<String, String>> readCSV(Path csvFile) {
        List<java.util.Map<String, String>> result = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(csvFile, StandardCharsets.UTF_8);

            if (lines.isEmpty()) {
                return result;
            }

            // Primeira linha contém os cabeçalhos
            String headerLine = lines.get(0);
            String[] headers = headerLine.split(";");

            // Remove BOM se presente
            if (headers.length > 0 && headers[0].startsWith("\uFEFF")) {
                headers[0] = headers[0].substring(1);
            }

            // Processa as demais linhas
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] values = line.split(";", -1); // -1 para preservar campos vazios

                java.util.Map<String, String> row = new java.util.LinkedHashMap<>();

                for (int j = 0; j < headers.length && j < values.length; j++) {
                    String header = headers[j].trim();
                    String value = values[j].trim();
                    row.put(header, value);
                }

                result.add(row);
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler CSV " + csvFile.getFileName() + ": " + e.getMessage());
        }

        return result;
    }

    /**
     * Gera código para uma entidade específica.
     */
    private void generateForEntity(Entity entity, MetaModel metaModel) {
        String entityName = entity.getName();
        System.out.println("\nProcessando entidade: " + entityName);

        try {
            // Model
            String modelCode = modelTemplate.generate(entity, metaModel);
            generateFile("app/Models/" + entityName + ".php", modelCode);

            // Controller
            String controllerCode = controllerTemplate.generate(entity, metaModel);
            generateFile("app/Http/Controllers/" + entityName + "Controller.php", controllerCode);

            // Migration
            String migrationCode = migrationTemplate.generate(entity, metaModel);
            String migrationFileName = migrationTemplate.getMigrationFileName(entity);
            generateFile("database/migrations/" + migrationFileName, migrationCode);

            // Form Request (validação)
            String requestCode = requestTemplate.generate(entity, metaModel);
            generateFile("app/Http/Requests/Store" + entityName + "Request.php", requestCode);

            String updateRequestCode = requestTemplate.generateUpdateRequest(entity, metaModel);
            generateFile("app/Http/Requests/Update" + entityName + "Request.php", updateRequestCode);

            // Web Controller e Views (exceto para JUNCTION e CHILD que são gerenciados em cascata)
            br.com.gerador.metamodel.model.EntityType entityType = entity.getType();
            if (entityType != br.com.gerador.metamodel.model.EntityType.JUNCTION &&
                entityType != br.com.gerador.metamodel.model.EntityType.CHILD) {

                String webControllerCode = webControllerTemplate.generate(entity, metaModel);
                generateFile("app/Http/Controllers/Web/" + entityName + "Controller.php", webControllerCode);

                // Views (index e form) - usar camelCase para diretórios
                String entityNameLower = toLowerCamelCase(entityName);

                String indexViewCode = viewTemplate.generateIndexView(entity, metaModel);
                generateFile("resources/views/" + entityNameLower + "/index.blade.php", indexViewCode);

                String formViewCode = viewTemplate.generateFormView(entity, metaModel);
                generateFile("resources/views/" + entityNameLower + "/form.blade.php", formViewCode);

                System.out.println("  ✓ Model, Controllers (API + Web), Migration, Requests e Views gerados");
            } else {
                System.out.println("  ✓ Model, Controller (API), Migration e Requests gerados (tipo: " + entityType + ")");
            }

        } catch (Exception e) {
            String error = "Erro ao gerar código para " + entityName + ": " + e.getMessage();
            errors.add(error);
            System.err.println("  ✗ " + error);
            e.printStackTrace();
        }
    }

    /**
     * Gera arquivo de rotas API consolidado.
     */
    private void generateRoutes(MetaModel metaModel) {
        System.out.println("\nGerando rotas API consolidadas...");

        String routesCode = routeTemplate.generate(metaModel);
        generateFile("routes/api/v1.php", routesCode);

        System.out.println("Rotas API geradas.");
    }

    /**
     * Gera arquivo de rotas Web com CRUDs.
     */
    private void generateWebRoutes(MetaModel metaModel) {
        System.out.println("\nGerando rotas Web...");

        // Verificar se há sessionContext configurado
        var metadata = metaModel.getMetadata();
        boolean hasSessionContext = metadata.hasSessionContext();

        // Coletar entidades que terão rotas web (exceto JUNCTION e CHILD)
        Set<String> webEntities = new LinkedHashSet<>();
        for (Entity entity : metaModel.getEntities()) {
            br.com.gerador.metamodel.model.EntityType entityType = entity.getType();
            if (entityType != br.com.gerador.metamodel.model.EntityType.JUNCTION &&
                entityType != br.com.gerador.metamodel.model.EntityType.CHILD) {
                webEntities.add(entity.getName());
            }
        }

        StringBuilder routes = new StringBuilder();
        routes.append("<?php\n\n");
        routes.append("use Illuminate\\Support\\Facades\\Route;\n");
        routes.append("use App\\Http\\Controllers\\AuthController;\n");

        // Adicionar import do SessionController se necessário
        if (hasSessionContext) {
            routes.append("use App\\Http\\Controllers\\SessionController;\n");
        }

        routes.append("\n");

        // Imports dos controllers (apenas MAIN e CONFIG)
        for (String entityName : webEntities) {
            routes.append("use App\\Http\\Controllers\\Web\\").append(entityName).append("Controller;\n");
        }

        routes.append("\n// Página inicial redireciona conforme autenticação\n");
        routes.append("Route::get('/', function () {\n");
        routes.append("    return auth()->check() ? redirect('/dashboard') : redirect('/login');\n");
        routes.append("});\n\n");

        routes.append("// Rotas de autenticação\n");
        routes.append("Route::get('/login', [AuthController::class, 'showLogin'])->name('login');\n");
        routes.append("Route::post('/login', [AuthController::class, 'login']);\n");
        routes.append("Route::get('/register', [AuthController::class, 'showRegister'])->name('register');\n");
        routes.append("Route::post('/register', [AuthController::class, 'register']);\n");
        routes.append("Route::post('/logout', [AuthController::class, 'logout'])->name('logout');\n\n");

        routes.append("// Rotas protegidas\n");
        routes.append("Route::middleware(['auth'])->group(function () {\n");

        // Adicionar rotas de sessão se configurado
        if (hasSessionContext) {
            routes.append("    // Seleção de contexto de sessão\n");
            routes.append("    Route::get('/session/select', [SessionController::class, 'select'])->name('session.select');\n");
            routes.append("    Route::post('/session/store', [SessionController::class, 'store'])->name('session.store');\n");
            routes.append("    Route::post('/session/update', [SessionController::class, 'update'])->name('session.update');\n\n");

            routes.append("    Route::get('/dashboard', [AuthController::class, 'dashboard'])->name('dashboard');\n");
            routes.append("});\n\n");

            // Rotas que requerem contexto de sessão
            routes.append("// Rotas que requerem contexto de sessão selecionado\n");
            routes.append("Route::middleware(['auth', 'session.context'])->group(function () {\n");
            routes.append("    // Rotas de CRUD\n");
            for (String entityName : webEntities) {
                String entityNameLower = toLowerCamelCase(entityName);
                routes.append("    Route::resource('").append(entityNameLower).append("', ").append(entityName).append("Controller::class);\n");
            }
            routes.append("});\n");
        } else {
            routes.append("    Route::get('/dashboard', [AuthController::class, 'dashboard'])->name('dashboard');\n\n");

            routes.append("    // Rotas de CRUD\n");
            for (String entityName : webEntities) {
                String entityNameLower = toLowerCamelCase(entityName);
                routes.append("    Route::resource('").append(entityNameLower).append("', ").append(entityName).append("Controller::class);\n");
            }
            routes.append("});\n");
        }

        generateFile("routes/web.php", routes.toString());

        System.out.println("Rotas Web geradas.");
    }

    /**
     * Gera um arquivo com o conteúdo especificado.
     */
    private void generateFile(String relativePath, String content) {
        try {
            Path filePath = outputDir.resolve(relativePath);
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, content, StandardCharsets.UTF_8);
            generatedFiles.add(relativePath);
        } catch (IOException e) {
            errors.add("Erro ao gerar arquivo " + relativePath + ": " + e.getMessage());
            System.err.println("Erro ao gerar arquivo " + relativePath + ": " + e.getMessage());
        }
    }

    private String toLowerCamelCase(String name) {
        if (name == null || name.isEmpty()) return name;
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }

    private String toPlural(String name) {
        if (name.endsWith("s")) return name + "es";
        if (name.endsWith("y")) return name.substring(0, name.length() - 1) + "ies";
        return name + "s";
    }

    /**
     * Converte uma string para snake_case.
     */
    private String toSnakeCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        // Remove underscore antes de números/maiúsculas (ex: id_Empresa -> idEmpresa)
        String cleaned = str.replaceAll("_([A-Z])", "$1");
        // Converte para snake_case
        return cleaned.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    /**
     * Ordena os arquivos CSV por dependências de Foreign Keys (ordenação topológica).
     * Garante que tabelas referenciadas sejam populadas antes das tabelas que as referenciam.
     */
    private List<Path> sortByForeignKeyDependencies(List<Path> csvFiles, MetaModel metaModel) {
        // Mapa: tableName -> Entity
        java.util.Map<String, Entity> tableToEntity = new java.util.HashMap<>();
        for (Entity entity : metaModel.getEntities()) {
            if (entity.getTableName() != null) {
                tableToEntity.put(entity.getTableName().toLowerCase(), entity);
            }
        }

        // Mapa: tableName -> lista de tabelas que dependem dela
        java.util.Map<String, java.util.Set<String>> dependencies = new java.util.HashMap<>();
        java.util.Map<String, Integer> inDegree = new java.util.HashMap<>();

        // Inicializa estruturas
        for (Path csvFile : csvFiles) {
            String tableName = csvFile.getFileName().toString().replace(".csv", "").toLowerCase();
            dependencies.putIfAbsent(tableName, new java.util.HashSet<>());
            inDegree.putIfAbsent(tableName, 0);
        }

        // Analisa dependências de FK
        for (Path csvFile : csvFiles) {
            String tableName = csvFile.getFileName().toString().replace(".csv", "").toLowerCase();
            Entity entity = findEntityByTableName(metaModel, tableName);

            if (entity != null && entity.getFields() != null) {
                for (Field field : entity.getFields()) {
                    // Verifica se o campo é uma FK
                    if (field.getReference() != null && field.getReference().getEntity() != null) {
                        String referencedEntityName = field.getReference().getEntity();

                        // Encontra a tabela referenciada
                        Entity referencedEntity = metaModel.getEntities().stream()
                            .filter(e -> e.getName().equals(referencedEntityName))
                            .findFirst()
                            .orElse(null);

                        if (referencedEntity != null && referencedEntity.getTableName() != null) {
                            String referencedTableName = referencedEntity.getTableName().toLowerCase();

                            // Ignora auto-referências
                            if (!referencedTableName.equals(tableName)) {
                                // tableName depende de referencedTableName
                                if (dependencies.containsKey(referencedTableName)) {
                                    dependencies.get(referencedTableName).add(tableName);
                                    inDegree.put(tableName, inDegree.get(tableName) + 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Ordenação topológica (algoritmo de Kahn)
        java.util.Queue<String> queue = new java.util.LinkedList<>();
        List<String> sortedTableNames = new ArrayList<>();

        // Adiciona tabelas sem dependências
        for (String tableName : inDegree.keySet()) {
            if (inDegree.get(tableName) == 0) {
                queue.offer(tableName);
            }
        }

        while (!queue.isEmpty()) {
            String current = queue.poll();
            sortedTableNames.add(current);

            // Remove arestas
            for (String dependent : dependencies.get(current)) {
                inDegree.put(dependent, inDegree.get(dependent) - 1);
                if (inDegree.get(dependent) == 0) {
                    queue.offer(dependent);
                }
            }
        }

        // Se houver ciclos, adiciona as tabelas restantes ao final
        for (String tableName : inDegree.keySet()) {
            if (inDegree.get(tableName) > 0) {
                sortedTableNames.add(tableName);
                System.out.println("  ⚠️ Aviso: Dependência circular detectada na tabela: " + tableName);
            }
        }

        // Reconstrói a lista de Paths na ordem correta
        List<Path> sortedCsvFiles = new ArrayList<>();
        for (String tableName : sortedTableNames) {
            csvFiles.stream()
                .filter(p -> p.getFileName().toString().replace(".csv", "").equalsIgnoreCase(tableName))
                .findFirst()
                .ifPresent(sortedCsvFiles::add);
        }

        return sortedCsvFiles;
    }

    /**
     * Encontra uma entidade pelo nome da tabela.
     */
    private Entity findEntityByTableName(MetaModel metaModel, String tableName) {
        if (metaModel == null || metaModel.getEntities() == null) {
            return null;
        }

        // Normaliza o nome da tabela removendo underscores para comparação
        String normalizedTableName = tableName.replace("_", "").toLowerCase();

        for (Entity entity : metaModel.getEntities()) {
            String entityTableName = entity.getTableName();
            if (entityTableName != null) {
                String normalizedEntityTable = entityTableName.replace("_", "").toLowerCase();
                if (normalizedEntityTable.equals(normalizedTableName)) {
                    return entity;
                }
            }
        }

        return null;
    }

    /**
     * Mapeia o header do CSV para o columnName do JSON.
     * Procura o campo na entidade que tenha o columnName correspondente ao header do CSV.
     */
    private String mapCsvHeaderToColumnName(String csvHeader, Entity entity) {
        if (entity == null || entity.getFields() == null) {
            // Fallback: retorna o header do CSV sem alteração
            return csvHeader;
        }

        // Normaliza o header do CSV removendo underscores para comparação
        String normalizedCsvHeader = csvHeader.replace("_", "").toLowerCase();

        for (Field field : entity.getFields()) {
            String columnName = field.getColumnName();
            if (columnName != null) {
                String normalizedColumnName = columnName.replace("_", "").toLowerCase();
                if (normalizedColumnName.equals(normalizedCsvHeader)) {
                    // Retorna o columnName EXATAMENTE como está no JSON
                    return columnName;
                }
            }
        }

        // Fallback: retorna o header do CSV sem alteração
        return csvHeader;
    }

    /**
     * Classe para armazenar o resultado da geração.
     */
    public static class GenerationResult {
        private final List<String> generatedFiles;
        private final List<String> errors;

        public GenerationResult(List<String> generatedFiles, List<String> errors) {
            this.generatedFiles = new ArrayList<>(generatedFiles);
            this.errors = new ArrayList<>(errors);
        }

        public int getFileCount() {
            return generatedFiles.size();
        }

        public List<String> getGeneratedFiles() {
            return generatedFiles;
        }

        public List<String> getErrors() {
            return errors;
        }

        public boolean hasErrors() {
            return !errors.isEmpty();
        }
    }
}
