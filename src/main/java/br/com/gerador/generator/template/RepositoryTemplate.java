package br.com.gerador.generator.template;

import br.com.gerador.generator.util.NamingUtils;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.Field;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Template para geração de Repository Spring Data JPA.
 */
public class RepositoryTemplate {

    private final String basePackage;

    public RepositoryTemplate(String basePackage) {
        this.basePackage = basePackage;
    }

    public String generate(Entity entity) {
        StringBuilder sb = new StringBuilder();
        String entityName = entity.getName();
        String pkType = getPrimaryKeyType(entity);

        // Package
        sb.append("package ").append(basePackage).append(".repository;\n\n");

        // Imports
        sb.append("import ").append(basePackage).append(".entity.").append(entityName).append(";\n");
        sb.append("import org.springframework.data.domain.Page;\n");
        sb.append("import org.springframework.data.domain.Pageable;\n");
        sb.append("import org.springframework.data.jpa.repository.JpaRepository;\n");
        sb.append("import org.springframework.data.jpa.repository.JpaSpecificationExecutor;\n");
        sb.append("import org.springframework.stereotype.Repository;\n");

        // Import do tipo da PK se necessário
        String pkImport = getPrimaryKeyImport(entity);
        if (pkImport != null) {
            sb.append("import ").append(pkImport).append(";\n");
        }

        sb.append("\nimport java.util.List;\n");
        sb.append("import java.util.Optional;\n");
        sb.append("\n");

        // Javadoc
        sb.append("/**\n");
        sb.append(" * Repository para ").append(entityName).append(".\n");
        sb.append(" */\n");

        // Interface
        sb.append("@Repository\n");
        sb.append("public interface ").append(entityName).append("Repository ");
        sb.append("extends JpaRepository<").append(entityName).append(", ").append(pkType).append(">");
        sb.append(", JpaSpecificationExecutor<").append(entityName).append("> {\n\n");

        // Métodos de busca baseados em campos únicos e de pesquisa
        for (Field field : entity.getFields()) {
            if (field.isUnique() && !field.isPrimaryKey()) {
                sb.append(generateFindByUniqueField(entityName, field));
            }
            if (hasSearchEnabled(field)) {
                sb.append(generateSearchMethod(entityName, field));
            }
        }

        // Métodos de busca para FKs (exceto se FK é parte de PK composta)
        boolean hasCompositeKey = entity.getFields().stream()
            .filter(Field::isPrimaryKey)
            .count() > 1;

        for (Field field : entity.getFields()) {
            if (field.isForeignKey()) {
                // Se tem chave composta e o campo é parte da PK, usa caminho id.campo
                if (hasCompositeKey && field.isPrimaryKey()) {
                    sb.append(generateFindByCompositeKeyForeignKey(entityName, field));
                } else {
                    sb.append(generateFindByForeignKey(entityName, field));
                }
            }
        }

        // Método paginado para sessionFilter
        if (entity.hasSessionFilter()) {
            String filterField = entity.getSessionFilter().getField();
            String filterField2 = entity.getSessionFilter().hasField2() ? entity.getSessionFilter().getField2() : null;
            String filterFieldPascal = NamingUtils.toPascalCase(filterField);
            String filterField2Pascal = filterField2 != null ? NamingUtils.toPascalCase(filterField2) : null;

            // Determina o tipo Java do campo de filtro
            String filterFieldType = "Integer";
            for (Field field : entity.getFields()) {
                if (field.getName().equals(filterField)) {
                    filterFieldType = NamingUtils.toJavaType(field.getDataType());
                    break;
                }
            }

            String filterField2Type = "Integer";
            if (filterField2 != null) {
                for (Field field : entity.getFields()) {
                    if (field.getName().equals(filterField2)) {
                        filterField2Type = NamingUtils.toJavaType(field.getDataType());
                        break;
                    }
                }
            }

            // Método para filtro por field1
            sb.append("    /**\n");
            sb.append("     * Busca paginada filtrada por ").append(filterField).append(".\n");
            sb.append("     */\n");
            sb.append("    Page<").append(entityName).append("> findBy").append(filterFieldPascal);
            sb.append("(").append(filterFieldType).append(" ").append(filterField).append(", Pageable pageable);\n\n");

            if (filterField2 != null) {
                // Método para filtro por field2
                sb.append("    /**\n");
                sb.append("     * Busca paginada filtrada por ").append(filterField2).append(".\n");
                sb.append("     */\n");
                sb.append("    Page<").append(entityName).append("> findBy").append(filterField2Pascal);
                sb.append("(").append(filterField2Type).append(" ").append(filterField2).append(", Pageable pageable);\n\n");

                // Método para filtro combinado
                sb.append("    /**\n");
                sb.append("     * Busca paginada filtrada por ").append(filterField).append(" e ").append(filterField2).append(".\n");
                sb.append("     */\n");
                sb.append("    Page<").append(entityName).append("> findBy").append(filterFieldPascal).append("And").append(filterField2Pascal);
                sb.append("(").append(filterFieldType).append(" ").append(filterField).append(", ").append(filterField2Type).append(" ").append(filterField2).append(", Pageable pageable);\n\n");
            }
        }

        sb.append("}\n");

        return sb.toString();
    }

    private String getPrimaryKeyType(Entity entity) {
        List<Field> pkFields = entity.getFields().stream()
            .filter(Field::isPrimaryKey)
            .collect(Collectors.toList());

        if (pkFields.isEmpty()) {
            return "Long";
        }

        if (pkFields.size() == 1) {
            return NamingUtils.toJavaType(pkFields.get(0).getDataType());
        }

        // PK composta - deveria usar classe Id
        return entity.getName() + "Id";
    }

    private String getPrimaryKeyImport(Entity entity) {
        List<Field> pkFields = entity.getFields().stream()
            .filter(Field::isPrimaryKey)
            .collect(Collectors.toList());

        if (pkFields.size() == 1) {
            return NamingUtils.getImportForType(pkFields.get(0).getDataType());
        }
        // Para chave composta, importa a classe Id do pacote entity
        if (pkFields.size() > 1) {
            return basePackage + ".entity." + entity.getName() + "Id";
        }
        return null;
    }

    private boolean hasSearchEnabled(Field field) {
        return field.getUi() != null
            && field.getUi().getSearch() != null
            && field.getUi().getSearch().isEnabled();
    }

    private String generateFindByUniqueField(String entityName, Field field) {
        StringBuilder sb = new StringBuilder();
        String fieldName = NamingUtils.toPascalCase(field.getName());
        String javaType = NamingUtils.toJavaType(field.getDataType());

        sb.append("    /**\n");
        sb.append("     * Busca por ").append(field.getLabel() != null ? field.getLabel() : field.getName()).append(".\n");
        sb.append("     */\n");
        sb.append("    Optional<").append(entityName).append("> findBy").append(fieldName);
        sb.append("(").append(javaType).append(" ").append(field.getName()).append(");\n\n");

        return sb.toString();
    }

    private String generateSearchMethod(String entityName, Field field) {
        StringBuilder sb = new StringBuilder();
        String fieldName = NamingUtils.toPascalCase(field.getName());

        // Se é string, gera método com LIKE
        if (NamingUtils.isStringType(field.getDataType())) {
            sb.append("    /**\n");
            sb.append("     * Busca por ").append(field.getLabel() != null ? field.getLabel() : field.getName()).append(" (contém).\n");
            sb.append("     */\n");
            sb.append("    List<").append(entityName).append("> findBy").append(fieldName);
            sb.append("ContainingIgnoreCase(String ").append(field.getName()).append(");\n\n");
        }

        return sb.toString();
    }

    private String generateFindByForeignKey(String entityName, Field field) {
        StringBuilder sb = new StringBuilder();
        String fieldName = NamingUtils.toPascalCase(field.getName());
        String javaType = NamingUtils.toJavaType(field.getDataType());

        sb.append("    /**\n");
        sb.append("     * Busca por ").append(field.getLabel() != null ? field.getLabel() : field.getName()).append(".\n");
        sb.append("     */\n");
        sb.append("    List<").append(entityName).append("> findBy").append(fieldName);
        sb.append("(").append(javaType).append(" ").append(field.getName()).append(");\n\n");

        return sb.toString();
    }

    /**
     * Gera método findBy para FK que faz parte de chave composta.
     * Usa o caminho id.campo para acessar o campo dentro do EmbeddedId.
     */
    private String generateFindByCompositeKeyForeignKey(String entityName, Field field) {
        StringBuilder sb = new StringBuilder();
        String fieldName = NamingUtils.toPascalCase(field.getName());
        String javaType = NamingUtils.toJavaType(field.getDataType());

        sb.append("    /**\n");
        sb.append("     * Busca por ").append(field.getLabel() != null ? field.getLabel() : field.getName()).append(".\n");
        sb.append("     */\n");
        // Para chave composta, usa id_Campo para acessar via EmbeddedId
        sb.append("    List<").append(entityName).append("> findById_").append(fieldName);
        sb.append("(").append(javaType).append(" ").append(field.getName()).append(");\n\n");

        return sb.toString();
    }
}
