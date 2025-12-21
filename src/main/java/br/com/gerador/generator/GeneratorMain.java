package br.com.gerador.generator;

import br.com.gerador.metamodel.loader.MetaModelLoader;
import br.com.gerador.metamodel.model.MetaModel;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Classe principal para execução do gerador de código Spring Boot.
 */
public class GeneratorMain {

    public static void main(String[] args) {
        try {
            // Caminhos dos arquivos
            String basePath = "c:/java/workspace/Gerador";
            Path modelPath = Paths.get(basePath, "metamodel/data/sociedade-model.json");
            Path entitiesPath = Paths.get(basePath, "metamodel/data/sociedade-model-entities.json");
            Path outputPath = Paths.get(basePath, "generated/back/src/main/java");

            System.out.println("Carregando MetaModel...");

            // Carrega o MetaModel
            MetaModelLoader loader = new MetaModelLoader();
            MetaModel metaModel = loader.load(modelPath);

            // Mescla as entidades adicionais
            loader.mergeEntities(metaModel, entitiesPath.toString());

            System.out.println("MetaModel carregado: " + metaModel.getMetadata().getName());
            System.out.println("Total de entidades: " + metaModel.getEntities().size());

            // Configura o gerador
            GeneratorConfig config = new GeneratorConfig()
                .basePackage("br.com.sociedade")
                .outputDir(outputPath)
                .overwriteExisting(true);

            // Executa a geração
            SpringBootGenerator generator = new SpringBootGenerator(config);
            SpringBootGenerator.GenerationResult result = generator.generate(metaModel);

            // Exibe resultado
            System.out.println("\n" + "=".repeat(60));
            System.out.println("RESUMO DA GERAÇÃO");
            System.out.println("=".repeat(60));
            System.out.println("Total de arquivos gerados: " + result.getFileCount());

            if (result.hasErrors()) {
                System.out.println("\nERROS:");
                for (String error : result.getErrors()) {
                    System.out.println("  - " + error);
                }
            }

        } catch (Exception e) {
            System.err.println("Erro fatal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
