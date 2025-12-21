package br.com.gerador.metamodel;

import br.com.gerador.metamodel.loader.MetaModelLoader;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.EntityType;
import br.com.gerador.metamodel.model.Field;
import br.com.gerador.metamodel.model.MenuItem;
import br.com.gerador.metamodel.model.MetaModel;
import br.com.gerador.metamodel.model.Metadata;
import br.com.gerador.metamodel.model.Module;

import java.io.IOException;
import java.util.List;

/**
 * Classe principal para demonstrar o carregamento do meta-modelo.
 */
public class MetaModelMain {

    public static void main(String[] args) {
        try {
            // Caminho dos arquivos
            String baseModelPath = "metamodel/data/sociedade-model.json";
            String entitiesPath = "metamodel/data/sociedade-model-entities.json";

            System.out.println("===========================================");
            System.out.println("  Carregador de Meta-Modelo");
            System.out.println("===========================================\n");

            // Carrega o modelo base
            MetaModelLoader loader = new MetaModelLoader();
            MetaModel model = loader.load(baseModelPath);

            // Mescla as entidades adicionais
            loader.mergeEntities(model, entitiesPath);

            // Exibe informações do modelo
            printModelInfo(model);

            // Valida o modelo
            List<String> errors = model.validate();
            if (errors.isEmpty()) {
                System.out.println("\n[OK] Modelo validado com sucesso!");
            } else {
                System.out.println("\n[ERRO] Erros encontrados na validação:");
                for (String error : errors) {
                    System.out.println("  - " + error);
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao carregar o modelo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printModelInfo(MetaModel model) {
        Metadata metadata = model.getMetadata();

        System.out.println("METADADOS");
        System.out.println("-----------------------------------------");
        System.out.println("  Nome:        " + metadata.getName());
        System.out.println("  Versão:      " + metadata.getVersion());
        System.out.println("  Descrição:   " + metadata.getDescription());
        System.out.println("  Banco:       " + (metadata.getDatabase() != null ? metadata.getDatabase().getType() : "N/A"));

        System.out.println("\nMÓDULOS (" + model.getModules().size() + ")");
        System.out.println("-----------------------------------------");
        for (Module module : model.getModules()) {
            System.out.println("  [" + module.getOrder() + "] " + module.getName() + " (" + module.getItems().size() + " itens)");
            for (MenuItem item : module.getItems()) {
                System.out.println("      - " + item.getName() + " -> " + item.getEntityRef());
            }
        }

        System.out.println("\nENTIDADES (" + model.getEntities().size() + ")");
        System.out.println("-----------------------------------------");

        // Agrupa por tipo
        System.out.println("\n  MAIN (" + model.getMainEntities().size() + "):");
        for (Entity entity : model.getMainEntities()) {
            printEntitySummary(entity);
        }

        System.out.println("\n  LOOKUP (" + model.getLookupEntities().size() + "):");
        for (Entity entity : model.getLookupEntities()) {
            printEntitySummary(entity);
        }

        System.out.println("\n  CHILD/JUNCTION:");
        for (Entity entity : model.getEntities()) {
            if (entity.isChild()) {
                printEntitySummary(entity);
            }
        }

        System.out.println("\n  CONFIG:");
        for (Entity entity : model.getEntitiesByType(EntityType.CONFIG)) {
            printEntitySummary(entity);
        }
    }

    private static void printEntitySummary(Entity entity) {
        StringBuilder sb = new StringBuilder();
        sb.append("    - ").append(entity.getName());
        sb.append(" (").append(entity.getTableName()).append(")");
        sb.append(" [").append(entity.getFields().size()).append(" campos");

        List<Field> pks = entity.getPrimaryKeyFields();
        if (!pks.isEmpty()) {
            sb.append(", PK: ");
            for (int i = 0; i < pks.size(); i++) {
                if (i > 0) sb.append("+");
                sb.append(pks.get(i).getName());
            }
        }

        List<Field> fks = entity.getForeignKeyFields();
        if (!fks.isEmpty()) {
            sb.append(", ").append(fks.size()).append(" FKs");
        }

        sb.append("]");

        if (entity.getParentEntity() != null) {
            sb.append(" -> parent: ").append(entity.getParentEntity());
        }

        System.out.println(sb);
    }
}
