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
 * Classe principal para geração de aplicações Laravel a partir do MetaModel.
 *
 * Uso:
 *   - Sem parâmetros: usa a pasta "xandel" como padrão
 *   - Com parâmetro: usa a pasta informada (ex: "xandel", "icep")
 *
 * O gerador varre a pasta especificada procurando arquivos JSON:
 *   - config-laravel.json: configurações específicas do Laravel
 *   - config.json: configurações gerais (fallback)
 *   - *-model.json: arquivo principal do modelo
 */
public class LaravelGeneratorMain {

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

            System.out.println("╔═══════════════════════════════════════════════════════════╗");
            System.out.println("║          GERADOR LARAVEL - MetaModel                      ║");
            System.out.println("╚═══════════════════════════════════════════════════════════╝");
            System.out.println("\nPasta selecionada: " + folderName);
            System.out.println("Caminho: " + folderPath);
            System.out.println();

            // Carrega as configurações do projeto
            ProjectConfig config = loadProjectConfig(folderPath);
            if (config != null) {
                System.out.println("Configurações carregadas:");
                System.out.println("  Projeto: " + config.getProjectName());
                System.out.println("  Tipo: " + config.getGenerationType());
                if (config.getBackendFramework() != null) {
                    System.out.println("  Framework: " + config.getBackendFramework());
                }
                if (config.getDatabaseType() != null) {
                    System.out.println("  Database: " + config.getDatabaseType());
                }
                System.out.println();
            }

            // Varre a pasta procurando arquivos JSON
            Path modelPath = null;
            Path entitiesPath = null;

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath, "*.json")) {
                for (Path file : stream) {
                    String fileName = file.getFileName().toString().toLowerCase();
                    if (fileName.equals("config.json") || fileName.equals("config-laravel.json")) {
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

            // Obtém o nome do projeto
            String projectName = config != null
                ? config.getProjectId()
                : metaModel.getMetadata().getName().toLowerCase();

            // Caminhos de saída
            String outputBaseDir = config != null
                ? config.getOutputBaseDir()
                : "generated/" + projectName + "-laravel";

            String backendDir = config != null
                ? config.getOutputBackendDir()
                : "backend";

            Path outputPath = Paths.get(BASE_PATH, outputBaseDir, backendDir);

            System.out.println("MetaModel carregado: " + metaModel.getMetadata().getName());
            System.out.println("Projeto: " + projectName);
            System.out.println("Total de entidades: " + metaModel.getEntities().size());
            System.out.println("Diretório de saída: " + outputPath.toAbsolutePath());
            System.out.println();

            // ===== BACKEND (Laravel) =====
            System.out.println("═".repeat(70));
            System.out.println("GERANDO BACKEND LARAVEL (PHP)");
            System.out.println("═".repeat(70));

            LaravelGenerator generator = new LaravelGenerator(outputPath);
            generator.setProjectConfig(config);
            LaravelGenerator.GenerationResult result = generator.generate(metaModel);

            // ===== RESUMO FINAL =====
            System.out.println("\n" + "═".repeat(70));
            System.out.println("RESUMO DA GERAÇÃO LARAVEL");
            System.out.println("═".repeat(70));
            System.out.println("Backend (Laravel):");
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
                System.out.println("\nPróximos passos:");
                System.out.println("1. Acesse o diretório: " + outputPath.toAbsolutePath());
                System.out.println("2. Execute: composer install");
                System.out.println("3. Configure o .env com suas credenciais de banco");
                System.out.println("4. Execute: php artisan key:generate");
                System.out.println("5. Execute: php artisan migrate");
                System.out.println("6. Execute: php artisan serve");
            }

        } catch (Exception e) {
            System.err.println("Erro fatal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carrega o arquivo config-laravel.json ou config.json do projeto.
     */
    private static ProjectConfig loadProjectConfig(Path folderPath) {
        try {
            // Tenta carregar config-laravel.json primeiro
            Path laravelConfigPath = folderPath.resolve("config-laravel.json");
            if (Files.exists(laravelConfigPath)) {
                return ProjectConfig.load(folderPath, "config-laravel.json");
            }

            // Fallback para config.json
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
