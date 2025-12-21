package br.com.gerador.generator.template;

import br.com.gerador.generator.util.NamingUtils;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.Field;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Template para geração de classes @Embeddable para chaves primárias compostas.
 */
public class EmbeddableIdTemplate {

    private final String basePackage;

    public EmbeddableIdTemplate(String basePackage) {
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
     * Retorna o nome da classe Id para a entidade.
     */
    public String getIdClassName(Entity entity) {
        return entity.getName() + "Id";
    }

    /**
     * Gera a classe @Embeddable para chave composta.
     */
    public String generate(Entity entity) {
        if (!hasCompositeKey(entity)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        Set<String> imports = new TreeSet<>();
        String idClassName = getIdClassName(entity);

        List<Field> pkFields = entity.getFields().stream()
            .filter(Field::isPrimaryKey)
            .collect(Collectors.toList());

        // Coleta imports
        imports.add("jakarta.persistence.Embeddable");
        imports.add("jakarta.persistence.Column");
        imports.add("java.io.Serializable");
        imports.add("java.util.Objects");

        for (Field field : pkFields) {
            String importClass = NamingUtils.getImportForType(field.getDataType());
            if (importClass != null) {
                imports.add(importClass);
            }
        }

        // Package
        sb.append("package ").append(basePackage).append(".entity;\n\n");

        // Imports
        for (String imp : imports) {
            sb.append("import ").append(imp).append(";\n");
        }
        sb.append("\n");

        // Javadoc
        sb.append("/**\n");
        sb.append(" * Chave primária composta para ").append(entity.getName()).append(".\n");
        sb.append(" */\n");

        // Classe
        sb.append("@Embeddable\n");
        sb.append("public class ").append(idClassName).append(" implements Serializable {\n\n");

        sb.append("    private static final long serialVersionUID = 1L;\n\n");

        // Campos
        for (Field field : pkFields) {
            String javaType = NamingUtils.toJavaType(field.getDataType());
            sb.append("    @Column(name = \"").append(field.getColumnName()).append("\"");
            if (field.getLength() != null && field.getLength() > 0 && NamingUtils.isStringType(field.getDataType())) {
                sb.append(", length = ").append(field.getLength());
            }
            sb.append(")\n");
            sb.append("    private ").append(javaType).append(" ").append(field.getName()).append(";\n\n");
        }

        // Construtor padrão
        sb.append("    public ").append(idClassName).append("() {\n");
        sb.append("    }\n\n");

        // Construtor com parâmetros
        sb.append("    public ").append(idClassName).append("(");
        boolean first = true;
        for (Field field : pkFields) {
            if (!first) sb.append(", ");
            sb.append(NamingUtils.toJavaType(field.getDataType())).append(" ").append(field.getName());
            first = false;
        }
        sb.append(") {\n");
        for (Field field : pkFields) {
            sb.append("        this.").append(field.getName()).append(" = ").append(field.getName()).append(";\n");
        }
        sb.append("    }\n\n");

        // Getters e Setters
        for (Field field : pkFields) {
            String javaType = NamingUtils.toJavaType(field.getDataType());
            String methodName = NamingUtils.toPascalCase(field.getName());

            // Getter
            sb.append("    public ").append(javaType).append(" get").append(methodName).append("() {\n");
            sb.append("        return ").append(field.getName()).append(";\n");
            sb.append("    }\n\n");

            // Setter
            sb.append("    public void set").append(methodName).append("(").append(javaType).append(" ").append(field.getName()).append(") {\n");
            sb.append("        this.").append(field.getName()).append(" = ").append(field.getName()).append(";\n");
            sb.append("    }\n\n");
        }

        // equals()
        sb.append("    @Override\n");
        sb.append("    public boolean equals(Object o) {\n");
        sb.append("        if (this == o) return true;\n");
        sb.append("        if (o == null || getClass() != o.getClass()) return false;\n");
        sb.append("        ").append(idClassName).append(" that = (").append(idClassName).append(") o;\n");
        sb.append("        return ");
        first = true;
        for (Field field : pkFields) {
            if (!first) sb.append("\n            && ");
            sb.append("Objects.equals(").append(field.getName()).append(", that.").append(field.getName()).append(")");
            first = false;
        }
        sb.append(";\n");
        sb.append("    }\n\n");

        // hashCode()
        sb.append("    @Override\n");
        sb.append("    public int hashCode() {\n");
        sb.append("        return Objects.hash(");
        first = true;
        for (Field field : pkFields) {
            if (!first) sb.append(", ");
            sb.append(field.getName());
            first = false;
        }
        sb.append(");\n");
        sb.append("    }\n\n");

        // toString()
        sb.append("    @Override\n");
        sb.append("    public String toString() {\n");
        sb.append("        return \"").append(idClassName).append("{\" +\n");
        first = true;
        for (Field field : pkFields) {
            if (first) {
                sb.append("            \"").append(field.getName()).append("=\" + ").append(field.getName());
            } else {
                sb.append(" +\n            \", ").append(field.getName()).append("=\" + ").append(field.getName());
            }
            first = false;
        }
        sb.append(" +\n            '}';\n");
        sb.append("    }\n");

        sb.append("}\n");

        return sb.toString();
    }
}
