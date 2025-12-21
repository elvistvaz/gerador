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
 * Classe principal para geração Full Stack (Spring Boot + Angular).
 *
 * Uso:
 *   - Sem parâmetros: usa a pasta "icep" como padrão
 *   - Com parâmetro: usa a pasta informada (ex: "xandel", "icep")
 *
 * O gerador varre a pasta especificada procurando arquivos JSON:
 *   - config.json: configurações do projeto (portas, senhas, paths, etc.)
 *   - *-model.json: arquivo principal do modelo
 *   - *-model-entities.json: arquivo adicional de entidades (opcional)
 */
public class FullStackGeneratorMain {

    private static final String BASE_PATH = "c:/java/workspace/Gerador";
    private static final String DATA_PATH = BASE_PATH + "/metamodel/data";
    private static final String DEFAULT_FOLDER = "icep";

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

            System.out.println("Pasta selecionada: " + folderName);
            System.out.println("Caminho: " + folderPath);
            System.out.println();

            // Carrega as configurações do projeto
            ProjectConfig config = loadProjectConfig(folderPath);
            if (config != null) {
                System.out.println("Configurações carregadas: config.json");
                System.out.println("  Projeto: " + config.getProjectName());
                System.out.println("  Backend: porta " + config.getBackendPort() + ", pacote " + config.getBasePackage());
                System.out.println("  Frontend: porta " + config.getFrontendPort());
                System.out.println("  Database: " + config.getDatabaseType() + " (" + config.getDatabaseName() + ")");
                System.out.println();
            }

            // Varre a pasta procurando arquivos JSON
            Path modelPath = null;
            Path entitiesPath = null;

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath, "*.json")) {
                for (Path file : stream) {
                    String fileName = file.getFileName().toString().toLowerCase();
                    if (fileName.equals("config.json")) {
                        // Já processado acima
                        continue;
                    }
                    if (fileName.endsWith("-model.json") && !fileName.contains("-entities")) {
                        modelPath = file;
                        System.out.println("Arquivo de modelo encontrado: " + file.getFileName());
                    } else if (fileName.endsWith("-model-entities.json")) {
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

            // Obtém o nome do projeto (do config.json ou do metamodel)
            String projectName = config != null
                ? config.getProjectId()
                : metaModel.getMetadata().getName().toLowerCase();

            // Obtém o pacote base (do config.json ou padrão)
            String basePackage = config != null
                ? config.getBasePackage()
                : "br.com." + projectName;

            // Caminhos de saída (do config.json ou padrão)
            String outputBaseDir = config != null
                ? config.getOutputBaseDir()
                : "generated/" + projectName;
            String backendDir = config != null
                ? config.getOutputBackendDir()
                : "back";
            String frontendDir = config != null
                ? config.getOutputFrontendDir()
                : "front";

            Path backOutputPath = Paths.get(BASE_PATH, outputBaseDir, backendDir, "src/main/java");
            Path frontOutputPath = Paths.get(BASE_PATH, outputBaseDir, frontendDir);

            System.out.println("MetaModel carregado: " + metaModel.getMetadata().getName());
            System.out.println("Projeto: " + projectName);
            System.out.println("Total de entidades: " + metaModel.getEntities().size());
            System.out.println("Diretório de saída: " + outputBaseDir + "/");
            System.out.println();

            // ===== BACKEND (Spring Boot) =====
            System.out.println("═".repeat(70));
            System.out.println("GERANDO BACKEND (Spring Boot)");
            System.out.println("═".repeat(70));

            GeneratorConfig backConfig = new GeneratorConfig()
                .basePackage(basePackage)
                .outputDir(backOutputPath)
                .overwriteExisting(true);

            SpringBootGenerator backGenerator = new SpringBootGenerator(backConfig);
            backGenerator.setProjectConfig(config); // Passa as configurações do projeto
            SpringBootGenerator.GenerationResult backResult = backGenerator.generate(metaModel);

            // ===== FRONTEND (Angular) =====
            System.out.println("\n" + "═".repeat(70));
            System.out.println("GERANDO FRONTEND (Angular)");
            System.out.println("═".repeat(70));

            AngularGenerator frontGenerator = new AngularGenerator(frontOutputPath);
            frontGenerator.setProjectConfig(config); // Passa as configurações do projeto
            AngularGenerator.GenerationResult frontResult = frontGenerator.generate(metaModel);

            // ===== CARGA INICIAL =====
            Path cargaInicialPath = folderPath.resolve("carga-inicial");
            InitialDataGenerator.GenerationResult dataResult = null;
            if (Files.exists(cargaInicialPath)) {
                System.out.println("\n" + "═".repeat(70));
                System.out.println("GERANDO CARGA INICIAL");
                System.out.println("═".repeat(70));

                Path backBasePath = Paths.get(BASE_PATH, outputBaseDir, backendDir);
                InitialDataGenerator dataGenerator = new InitialDataGenerator(cargaInicialPath, backBasePath, metaModel);
                dataGenerator.setProjectConfig(config); // Passa as configurações do projeto
                dataResult = dataGenerator.generate();
            }

            // ===== RESUMO FINAL =====
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

            // Exibe erros se houver
            if (backResult.hasErrors() || frontResult.hasErrors()) {
                System.out.println("\nERROS:");
                for (String error : backResult.getErrors()) {
                    System.out.println("  [BACK] " + error);
                }
                for (String error : frontResult.getErrors()) {
                    System.out.println("  [FRONT] " + error);
                }
            }

        } catch (Exception e) {
            System.err.println("Erro fatal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carrega o arquivo config.json do projeto, se existir.
     */
    private static ProjectConfig loadProjectConfig(Path folderPath) {
        try {
            if (ProjectConfig.exists(folderPath)) {
                return ProjectConfig.load(folderPath);
            }
        } catch (IOException e) {
            System.out.println("Aviso: Erro ao carregar config.json: " + e.getMessage());
            System.out.println("Usando configurações padrão.");
        }
        return null;
    }

    /**
     * Lista as pastas disponíveis no diretório de dados.
     */
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
