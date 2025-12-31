package br.com.gerador.generator;

import br.com.gerador.generator.config.ProjectConfig;
import br.com.gerador.metamodel.loader.MetaModelLoader;
import br.com.gerador.metamodel.model.MetaModel;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Gerador Unificado - Detecta automaticamente qual "cartucho" usar baseado na configuração.
 *
 * Uso:
 *   mvn exec:java -Dexec.mainClass="br.com.gerador.generator.UnifiedGeneratorMain" -Dexec.args="xandel"
 *
 * O gerador detecta automaticamente se deve usar:
 *   - Spring Boot + Angular (backend.framework = "spring-boot")
 *   - Laravel (backend.framework = "laravel")
 *   - Outros cartuchos futuros
 */
public class UnifiedGeneratorMain {

    private static final String BASE_PATH = "c:/java/workspace/Gerador";
    private static final String DATA_PATH = BASE_PATH + "/metamodel/data";
    private static final String DEFAULT_FOLDER = "xandel";

    public static void main(String[] args) {
        try {
            // Determina a pasta a ser usada
            String folderName = (args.length >= 1 && !args[0].isEmpty()) ? args[0] : DEFAULT_FOLDER;
            Path folderPath = Paths.get(DATA_PATH, folderName);

            if (!Files.exists(folderPath) || !Files.isDirectory(folderPath)) {
                System.err.println("Erro: Pasta não encontrada: " + folderPath);
                System.err.println("Pastas disponíveis em " + DATA_PATH + ":");
                listAvailableFolders();
                System.exit(1);
            }

            printBanner();
            System.out.println("Pasta selecionada: " + folderName);
            System.out.println("Caminho: " + folderPath);
            System.out.println();

            // Detecta qual arquivo de configuração usar
            ProjectConfig config = detectAndLoadConfig(folderPath);

            if (config == null) {
                System.err.println("Erro: Nenhum arquivo de configuração encontrado.");
                System.err.println("Esperado: config.json ou config-*.json");
                System.exit(1);
            }

            // Detecta o tipo de framework/cartucho
            String framework = detectFramework(config);

            System.out.println("╔═══════════════════════════════════════════════════════════╗");
            System.out.println("║  FRAMEWORK DETECTADO: " + framework.toUpperCase());
            System.out.println("╚═══════════════════════════════════════════════════════════╝");
            System.out.println();

            displayConfig(config, framework);

            // Varre a pasta procurando arquivos JSON do modelo
            Path modelPath = null;
            Path entitiesPath = null;

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath, "*.json")) {
                for (Path file : stream) {
                    String fileName = file.getFileName().toString().toLowerCase();
                    if (fileName.startsWith("config")) {
                        continue;
                    }
                    if (fileName.endsWith("-model.json") && !fileName.contains("-entities")) {
                        modelPath = file;
                        System.out.println("Arquivo de modelo encontrado: " + file.getFileName());
                    } else if (fileName.endsWith("-entities.json")) {
                        entitiesPath = file;
                        System.out.println("Arquivo de entidades encontrado: " + file.getFileName());
                    }
                }
            }

            if (modelPath == null) {
                System.err.println("Erro: Nenhum arquivo *-model.json encontrado na pasta: " + folderPath);
                System.exit(1);
            }

            System.out.println();
            System.out.println("Carregando MetaModel...");

            // Carrega o MetaModel
            MetaModelLoader loader = new MetaModelLoader();
            MetaModel metaModel = loader.load(modelPath);

            // Mescla as entidades adicionais se existirem
            if (entitiesPath != null && Files.exists(entitiesPath)) {
                loader.mergeEntities(metaModel, entitiesPath.toString());
                System.out.println("Entidades adicionais mescladas.");
            }

            System.out.println("MetaModel carregado: " + metaModel.getMetadata().getName());
            System.out.println("Total de entidades: " + metaModel.getEntities().size());
            System.out.println();

            // Delega para o gerador apropriado baseado no framework
            switch (framework.toLowerCase()) {
                case "spring-boot":
                    generateSpringBootAngular(metaModel, config, folderPath);
                    break;

                case "laravel":
                    generateLaravel(metaModel, config, folderPath);
                    break;

                default:
                    System.err.println("Erro: Framework não suportado: " + framework);
                    System.err.println("Frameworks suportados: spring-boot, laravel");
                    System.exit(1);
            }

        } catch (Exception e) {
            System.err.println("Erro fatal: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Detecta e carrega o arquivo de configuração apropriado.
     * Prioridade:
     *   1. config-laravel.json (se existir, assume Laravel)
     *   2. config.json (padrão)
     */
    private static ProjectConfig detectAndLoadConfig(Path folderPath) throws IOException {
        // Tenta config-laravel.json primeiro
        Path laravelConfig = folderPath.resolve("config-laravel.json");
        if (Files.exists(laravelConfig)) {
            System.out.println("Configuração detectada: config-laravel.json");
            return ProjectConfig.load(folderPath, "config-laravel.json");
        }

        // Tenta config.json
        Path defaultConfig = folderPath.resolve("config.json");
        if (Files.exists(defaultConfig)) {
            System.out.println("Configuração detectada: config.json");
            return ProjectConfig.load(folderPath);
        }

        // Tenta qualquer config-*.json
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath, "config-*.json")) {
            for (Path configFile : stream) {
                String fileName = configFile.getFileName().toString();
                System.out.println("Configuração detectada: " + fileName);
                return ProjectConfig.load(folderPath, fileName);
            }
        }

        return null;
    }

    /**
     * Detecta o framework baseado na configuração.
     */
    private static String detectFramework(ProjectConfig config) {
        String framework = config.getBackendFramework();

        if (framework == null || framework.isEmpty()) {
            // Se não especificado, usa spring-boot como padrão
            return "spring-boot";
        }

        return framework;
    }

    /**
     * Exibe as configurações carregadas.
     */
    private static void displayConfig(ProjectConfig config, String framework) {
        System.out.println("Configurações do Projeto:");
        System.out.println("  Nome: " + config.getProjectName());
        System.out.println("  Versão: " + config.getProjectVersion());
        System.out.println("  Framework Backend: " + framework);

        if ("spring-boot".equalsIgnoreCase(framework)) {
            System.out.println("  Linguagem: Java " + config.getJavaVersion());
            System.out.println("  Spring Boot: " + config.getSpringBootVersion());
            System.out.println("  Pacote Base: " + config.getBasePackage());
            System.out.println("  Porta Backend: " + config.getBackendPort());
            System.out.println("  Frontend: Angular " + config.getAngularVersion());
            System.out.println("  Porta Frontend: " + config.getFrontendPort());
        } else if ("laravel".equalsIgnoreCase(framework)) {
            System.out.println("  Linguagem: PHP");
            System.out.println("  Porta Backend: " + config.getBackendPort());
        }

        System.out.println("  Banco de Dados: " + config.getDatabaseType());
        System.out.println("  Diretório de Saída: " + config.getOutputBaseDir());
        System.out.println();
    }

    /**
     * Gera aplicação Spring Boot + Angular.
     */
    private static void generateSpringBootAngular(MetaModel metaModel, ProjectConfig config, Path folderPath)
            throws IOException {

        String projectName = config.getProjectId();
        String basePackage = config.getBasePackage();
        String outputBaseDir = config.getOutputBaseDir();
        String backendDir = config.getOutputBackendDir();
        String frontendDir = config.getOutputFrontendDir();

        Path backOutputPath = Paths.get(BASE_PATH, outputBaseDir, backendDir, "src/main/java");
        Path frontOutputPath = Paths.get(BASE_PATH, outputBaseDir, frontendDir);

        System.out.println("═".repeat(70));
        System.out.println("GERANDO BACKEND (Spring Boot)");
        System.out.println("═".repeat(70));

        GeneratorConfig backConfig = new GeneratorConfig()
            .basePackage(basePackage)
            .outputDir(backOutputPath)
            .overwriteExisting(true);

        SpringBootGenerator backGenerator = new SpringBootGenerator(backConfig);
        backGenerator.setProjectConfig(config);
        SpringBootGenerator.GenerationResult backResult = backGenerator.generate(metaModel);

        System.out.println("\n" + "═".repeat(70));
        System.out.println("GERANDO FRONTEND (Angular)");
        System.out.println("═".repeat(70));

        AngularGenerator frontGenerator = new AngularGenerator(frontOutputPath);
        frontGenerator.setProjectConfig(config);
        AngularGenerator.GenerationResult frontResult = frontGenerator.generate(metaModel);

        // Carga inicial (se existir)
        Path cargaInicialPath = folderPath.resolve("carga-inicial");
        InitialDataGenerator.GenerationResult dataResult = null;
        if (Files.exists(cargaInicialPath)) {
            System.out.println("\n" + "═".repeat(70));
            System.out.println("GERANDO CARGA INICIAL");
            System.out.println("═".repeat(70));

            Path backBasePath = Paths.get(BASE_PATH, outputBaseDir, backendDir);
            InitialDataGenerator dataGenerator = new InitialDataGenerator(cargaInicialPath, backBasePath, metaModel);
            dataGenerator.setProjectConfig(config);
            dataResult = dataGenerator.generate();
        }

        printSummary(backResult, frontResult, dataResult);
    }

    /**
     * Gera aplicação Laravel (monolítica - backend e views integrados).
     */
    private static void generateLaravel(MetaModel metaModel, ProjectConfig config, Path folderPath)
            throws IOException {

        String projectName = config.getProjectId();
        String outputBaseDir = config.getOutputBaseDir();

        // Laravel é monolítico - não há separação backend/frontend
        Path outputPath = Paths.get(BASE_PATH, outputBaseDir);

        System.out.println("═".repeat(70));
        System.out.println("GERANDO APLICAÇÃO LARAVEL (Monolítica - API + Views)");
        System.out.println("═".repeat(70));

        LaravelGenerator generator = new LaravelGenerator(outputPath);
        generator.setProjectConfig(config);
        LaravelGenerator.GenerationResult result = generator.generate(metaModel);

        System.out.println("\n" + "═".repeat(70));
        System.out.println("RESUMO DA GERAÇÃO LARAVEL");
        System.out.println("═".repeat(70));
        System.out.println("Aplicação (Laravel - Monolítica):");
        System.out.println("  - Arquivos gerados: " + result.getFileCount());

        if (result.hasErrors()) {
            System.out.println("  - Erros: " + result.getErrors().size());
            System.out.println("\nERROS:");
            for (String error : result.getErrors()) {
                System.out.println("  [ERRO] " + error);
            }
        }

        System.out.println("\nTotal de arquivos: " + result.getFileCount());
        System.out.println("═".repeat(70));

        if (!result.hasErrors()) {
            System.out.println("\n✓ Geração concluída com sucesso!");

            // Executa php artisan key:generate automaticamente
            System.out.println("\n" + "═".repeat(70));
            System.out.println("CONFIGURANDO APLICAÇÃO LARAVEL");
            System.out.println("═".repeat(70));

            executeArtisanKeyGenerate(outputPath);
            createConsoleRoutesFile(outputPath);
            createSqliteDatabase(outputPath);
            executeComposerInstall(outputPath);

            System.out.println("\nPróximos passos:");
            System.out.println("1. Acesse o diretório: " + outputPath.toAbsolutePath());
            System.out.println("2. Configure o .env com suas credenciais de banco (se necessário)");
            System.out.println("3. Execute: php artisan migrate");
            System.out.println("4. Execute: php artisan db:seed --class=InitialDataSeeder");
            System.out.println("5. Execute: php artisan serve");
        }
    }

    /**
     * Executa php artisan key:generate automaticamente.
     */
    private static void executeArtisanKeyGenerate(Path laravelPath) {
        try {
            System.out.println("\nGerando chave de aplicação Laravel...");

            ProcessBuilder pb = new ProcessBuilder();
            pb.directory(laravelPath.toFile());
            pb.command("C:\\php82\\php.exe", "artisan", "key:generate");
            pb.redirectErrorStream(true);

            Process process = pb.start();

            // Aguarda a conclusão
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("✓ Chave de aplicação gerada com sucesso!");
            } else {
                System.out.println("⚠ Aviso: Não foi possível gerar a chave automaticamente.");
                System.out.println("  Execute manualmente: php artisan key:generate");
            }

        } catch (Exception e) {
            System.out.println("⚠ Aviso: Não foi possível gerar a chave automaticamente.");
            System.out.println("  Execute manualmente: php artisan key:generate");
            System.out.println("  Erro: " + e.getMessage());
        }
    }

    /**
     * Cria o arquivo routes/console.php (obrigatório no Laravel 12).
     */
    private static void createConsoleRoutesFile(Path laravelPath) {
        try {
            Path consoleRoutesPath = laravelPath.resolve("routes/console.php");

            if (!Files.exists(consoleRoutesPath)) {
                System.out.println("\nCriando arquivo routes/console.php...");

                String content = "<?php\n\n" +
                    "use Illuminate\\Foundation\\Inspiring;\n" +
                    "use Illuminate\\Support\\Facades\\Artisan;\n\n" +
                    "Artisan::command('inspire', function () {\n" +
                    "    $this->comment(Inspiring::quote());\n" +
                    "})->purpose('Display an inspiring quote')->hourly();\n";

                Files.writeString(consoleRoutesPath, content, java.nio.charset.StandardCharsets.UTF_8);
                System.out.println("✓ Arquivo routes/console.php criado com sucesso!");
            }

        } catch (Exception e) {
            System.out.println("⚠ Aviso: Não foi possível criar routes/console.php");
            System.out.println("  Erro: " + e.getMessage());
        }
    }

    /**
     * Cria o arquivo de banco de dados SQLite vazio para desenvolvimento.
     */
    private static void createSqliteDatabase(Path laravelPath) {
        try {
            Path databaseDir = laravelPath.resolve("database");
            Path sqlitePath = databaseDir.resolve("database.sqlite");

            if (!Files.exists(sqlitePath)) {
                System.out.println("\nCriando banco de dados SQLite...");

                // Cria diretório se não existir
                Files.createDirectories(databaseDir);

                // Cria arquivo vazio do SQLite
                Files.createFile(sqlitePath);

                System.out.println("✓ Banco de dados SQLite criado com sucesso!");
                System.out.println("  Localização: database/database.sqlite");
            }

        } catch (Exception e) {
            System.out.println("⚠ Aviso: Não foi possível criar database.sqlite");
            System.out.println("  Erro: " + e.getMessage());
        }
    }

    /**
     * Executa composer install com flags otimizadas para usar cache.
     *
     * Flags utilizadas:
     * - --prefer-dist: Baixa pacotes como arquivos ZIP (mais rápido e usa cache)
     * - --no-dev: Não instala dependências de desenvolvimento (reduz pacotes)
     * - --optimize-autoloader: Gera autoloader otimizado (usa classmap em vez de PSR-4 quando possível)
     * - --no-interaction: Não pede confirmações (modo não-interativo)
     *
     * O Composer automaticamente usa seu cache global em:
     * Windows: C:\Users\<usuario>\AppData\Local\Composer\
     * Linux/Mac: ~/.composer/cache/
     */
    private static void executeComposerInstall(Path laravelPath) {
        try {
            System.out.println("\n" + "═".repeat(70));
            System.out.println("INSTALANDO DEPENDÊNCIAS DO COMPOSER");
            System.out.println("═".repeat(70));
            System.out.println("\nEsta etapa pode demorar na primeira execução.");
            System.out.println("Nas próximas execuções será muito mais rápido devido ao cache local.\n");

            ProcessBuilder pb = new ProcessBuilder();
            pb.directory(laravelPath.toFile());

            // Composer install com flags otimizadas para cache
            pb.command(
                "C:\\php82\\php.exe",
                "C:\\php82\\composer.phar",
                "install",
                "--prefer-dist",           // Usa cache de distribuição (mais rápido)
                "--no-dev",                // Sem dependências de desenvolvimento
                "--optimize-autoloader",   // Autoloader otimizado
                "--no-interaction"         // Não-interativo
            );

            pb.redirectErrorStream(true);
            pb.inheritIO(); // Mostra output em tempo real

            long startTime = System.currentTimeMillis();
            Process process = pb.start();

            // Aguarda a conclusão
            int exitCode = process.waitFor();
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime) / 1000; // em segundos

            System.out.println("\n" + "═".repeat(70));
            if (exitCode == 0) {
                System.out.println("✓ Dependências instaladas com sucesso!");
                System.out.println("  Tempo decorrido: " + duration + " segundos");
                System.out.println("\nDica: Na próxima execução será mais rápido (cache do Composer)");
            } else {
                System.out.println("⚠ Aviso: composer install falhou (código: " + exitCode + ")");
                System.out.println("  Execute manualmente: composer install");
            }
            System.out.println("═".repeat(70));

        } catch (Exception e) {
            System.out.println("\n⚠ Aviso: Não foi possível executar composer install automaticamente.");
            System.out.println("  Execute manualmente no diretório do projeto:");
            System.out.println("  composer install --prefer-dist --no-dev --optimize-autoloader");
            System.out.println("  Erro: " + e.getMessage());
        }
    }

    /**
     * Exibe resumo da geração Spring Boot + Angular.
     */
    private static void printSummary(SpringBootGenerator.GenerationResult backResult,
                                    AngularGenerator.GenerationResult frontResult,
                                    InitialDataGenerator.GenerationResult dataResult) {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("RESUMO DA GERAÇÃO FULL STACK");
        System.out.println("═".repeat(70));
        System.out.println("Backend (Spring Boot):");
        System.out.println("  - Arquivos gerados: " + backResult.getFileCount());
        if (backResult.hasErrors()) {
            System.out.println("  - Erros: " + backResult.getErrors().size());
        }

        System.out.println("\nFrontend (Angular):");
        System.out.println("  - Arquivos gerados: " + frontResult.getFileCount());
        if (frontResult.hasErrors()) {
            System.out.println("  - Erros: " + frontResult.getErrors().size());
        }

        int totalFiles = backResult.getFileCount() + frontResult.getFileCount();
        if (dataResult != null) {
            System.out.println("\nCarga Inicial:");
            System.out.println("  - Arquivos gerados: " + dataResult.getFileCount());
            totalFiles += dataResult.getFileCount();
        }

        System.out.println("\nTotal de arquivos: " + totalFiles);
        System.out.println("═".repeat(70));

        if (backResult.hasErrors() || frontResult.hasErrors()) {
            System.out.println("\nERROS:");
            for (String error : backResult.getErrors()) {
                System.out.println("  [BACK] " + error);
            }
            for (String error : frontResult.getErrors()) {
                System.out.println("  [FRONT] " + error);
            }
        }
    }

    private static void printBanner() {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║        GERADOR UNIFICADO - MetaModel Framework            ║");
        System.out.println("║                                                           ║");
        System.out.println("║  Cartuchos Disponíveis:                                   ║");
        System.out.println("║    • Spring Boot + Angular                                ║");
        System.out.println("║    • Laravel (PHP)                                        ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private static void listAvailableFolders() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(DATA_PATH))) {
            for (Path path : stream) {
                if (Files.isDirectory(path)) {
                    System.out.println("  - " + path.getFileName());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao listar pastas: " + e.getMessage());
        }
    }
}
