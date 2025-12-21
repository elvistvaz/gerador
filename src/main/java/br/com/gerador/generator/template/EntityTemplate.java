package br.com.gerador.generator.template;

import br.com.gerador.generator.util.NamingUtils;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.Field;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Template para geração de Entity JPA.
 */
public class EntityTemplate {

    private final String basePackage;

    public EntityTemplate(String basePackage) {
        this.basePackage = basePackage;
    }

    /**
     * Verifica se a entidade tem chave primária composta.
     */
    public boolean hasCompositeKey(Entity entity) {
        long pkCount = entity.getFields().stream()
            .filter(Field::isPrimaryKey)
            .count();
        return pkCount > 1;
    }

    /**
     * Verifica se a entidade não tem nenhum campo marcado como PK.
     */
    public boolean hasNoPrimaryKey(Entity entity) {
        return entity.getFields().stream()
            .noneMatch(Field::isPrimaryKey);
    }

    public String generate(Entity entity) {
        StringBuilder sb = new StringBuilder();
        Set<String> imports = new TreeSet<>();

        // Imports básicos JPA
        imports.add("jakarta.persistence.*");

        // Coleta imports dos tipos de campos (apenas para campos não-PK em caso de chave composta)
        collectFieldImports(entity, imports);

        // Package
        sb.append("package ").append(basePackage).append(".entity;\n\n");

        // Imports
        for (String imp : imports) {
            sb.append("import ").append(imp).append(";\n");
        }
        sb.append("\n");

        // Javadoc
        sb.append("/**\n");
        sb.append(" * ").append(entity.getDescription() != null ? entity.getDescription() : entity.getName()).append("\n");
        sb.append(" */\n");

        // Annotations
        sb.append("@Entity\n");
        sb.append("@Table(name = \"").append(entity.getTableName()).append("\"");
        if (entity.getSchema() != null && !entity.getSchema().isEmpty()) {
            sb.append(", schema = \"").append(entity.getSchema()).append("\"");
        }
        sb.append(")\n");

        // Classe
        sb.append("public class ").append(entity.getName()).append(" {\n\n");

        // Campos
        List<Field> pkFields = entity.getFields().stream()
            .filter(Field::isPrimaryKey)
            .collect(Collectors.toList());

        List<Field> regularFields = entity.getFields().stream()
            .filter(f -> !f.isPrimaryKey())
            .collect(Collectors.toList());

        boolean hasCompositeKey = pkFields.size() > 1;
        boolean hasNoPk = pkFields.isEmpty();

        if (hasNoPk) {
            // Entidade sem PK definida - adiciona um ID artificial auto-gerado
            sb.append("    @Id\n");
            sb.append("    @GeneratedValue(strategy = GenerationType.IDENTITY)\n");
            sb.append("    @Column(name = \"id\")\n");
            sb.append("    private Long id;\n\n");
        } else if (hasCompositeKey) {
            // Usa @EmbeddedId para chave composta
            String idClassName = entity.getName() + "Id";
            sb.append("    @EmbeddedId\n");
            sb.append("    private ").append(idClassName).append(" id;\n\n");
        } else {
            // Gera campos PK normalmente
            for (Field field : pkFields) {
                sb.append(generateField(field, true));
                sb.append("\n");
            }
        }

        // Gera campos regulares
        for (Field field : regularFields) {
            sb.append(generateField(field, false));
            sb.append("\n");
        }

        // Construtor padrão
        sb.append("    public ").append(entity.getName()).append("() {\n");
        sb.append("    }\n\n");

        // Getters e Setters
        if (hasNoPk) {
            // Getter/Setter para o ID artificial
            sb.append("    public Long getId() {\n");
            sb.append("        return id;\n");
            sb.append("    }\n\n");
            sb.append("    public void setId(Long id) {\n");
            sb.append("        this.id = id;\n");
            sb.append("    }\n\n");
        } else if (hasCompositeKey) {
            // Getter/Setter para o EmbeddedId
            String idClassName = entity.getName() + "Id";
            sb.append("    public ").append(idClassName).append(" getId() {\n");
            sb.append("        return id;\n");
            sb.append("    }\n\n");
            sb.append("    public void setId(").append(idClassName).append(" id) {\n");
            sb.append("        this.id = id;\n");
            sb.append("    }\n\n");
        } else {
            // Getters/Setters para PKs normais
            for (Field field : pkFields) {
                sb.append(generateGetter(field));
                sb.append(generateSetter(field, entity.getName()));
            }
        }

        // Getters/Setters para campos regulares
        for (Field field : regularFields) {
            sb.append(generateGetter(field));
            sb.append(generateSetter(field, entity.getName()));
        }

        sb.append("}\n");

        return sb.toString();
    }

    private void collectFieldImports(Entity entity, Set<String> imports) {
        boolean hasCompositeKey = hasCompositeKey(entity);
        for (Field field : entity.getFields()) {
            // Se tem chave composta, não precisa importar tipos dos campos PK (estão na classe Id)
            if (hasCompositeKey && field.isPrimaryKey()) {
                continue;
            }
            String importClass = NamingUtils.getImportForType(field.getDataType());
            if (importClass != null) {
                imports.add(importClass);
            }
        }
    }

    private String generateField(Field field, boolean isPk) {
        StringBuilder sb = new StringBuilder();
        String javaType = NamingUtils.toJavaType(field.getDataType());

        // Annotations
        if (isPk) {
            sb.append("    @Id\n");
            if (field.isAutoIncrement()) {
                sb.append("    @GeneratedValue(strategy = GenerationType.IDENTITY)\n");
            }
        }

        // Para campos TEXT, usar @Lob
        boolean isTextField = field.getDataType() != null &&
            field.getDataType().name().equalsIgnoreCase("TEXT");
        if (isTextField) {
            sb.append("    @Lob\n");
        }

        // @Column
        sb.append("    @Column(name = \"").append(field.getColumnName()).append("\"");
        if (field.isRequired() && !isPk) {
            sb.append(", nullable = false");
        }
        if (field.getLength() != null && field.getLength() > 0 && NamingUtils.isStringType(field.getDataType())) {
            sb.append(", length = ").append(field.getLength());
        }
        if (field.getPrecision() != null && field.getScale() != null) {
            sb.append(", precision = ").append(field.getPrecision());
            sb.append(", scale = ").append(field.getScale());
        }
        if (field.isUnique()) {
            sb.append(", unique = true");
        }
        // Para campos TEXT com columnDefinition (útil para H2)
        if (isTextField) {
            sb.append(", columnDefinition = \"CLOB\"");
        }
        sb.append(")\n");

        // Campo
        sb.append("    private ").append(javaType).append(" ").append(field.getName()).append(";\n");

        return sb.toString();
    }

    private String generateGetter(Field field) {
        StringBuilder sb = new StringBuilder();
        String javaType = NamingUtils.toJavaType(field.getDataType());
        String methodName = "get" + NamingUtils.toPascalCase(field.getName());

        sb.append("    public ").append(javaType).append(" ").append(methodName).append("() {\n");
        sb.append("        return ").append(field.getName()).append(";\n");
        sb.append("    }\n\n");

        return sb.toString();
    }

    private String generateSetter(Field field, String entityName) {
        StringBuilder sb = new StringBuilder();
        String javaType = NamingUtils.toJavaType(field.getDataType());
        String methodName = "set" + NamingUtils.toPascalCase(field.getName());

        sb.append("    public void ").append(methodName).append("(").append(javaType).append(" ").append(field.getName()).append(") {\n");
        sb.append("        this.").append(field.getName()).append(" = ").append(field.getName()).append(";\n");
        sb.append("    }\n\n");

        return sb.toString();
    }
}
