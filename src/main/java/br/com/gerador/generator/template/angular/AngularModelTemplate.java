package br.com.gerador.generator.template.angular;

import br.com.gerador.generator.util.NamingUtils;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.Field;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Template para geração de Models TypeScript (interfaces) para Angular.
 */
public class AngularModelTemplate {

    /**
     * Gera interface TypeScript para a entidade.
     */
    public String generate(Entity entity) {
        StringBuilder sb = new StringBuilder();
        String entityName = entity.getName();

        // Comentário do arquivo
        sb.append("/**\n");
        sb.append(" * Model: ").append(entity.getDescription() != null ? entity.getDescription() : entityName).append("\n");
        sb.append(" * Auto-gerado pelo gerador de código\n");
        sb.append(" */\n\n");

        // Interface principal
        sb.append("export interface ").append(entityName).append(" {\n");

        // Campos da entidade
        List<Field> allFields = entity.getFields();

        // Se não tem PK, adiciona id artificial
        boolean hasNoPk = entity.getFields().stream().noneMatch(Field::isPrimaryKey);
        if (hasNoPk) {
            sb.append("  id?: number;\n");
        }

        for (int i = 0; i < allFields.size(); i++) {
            Field field = allFields.get(i);
            String tsType = toTypeScriptType(field);
            String optional = field.isRequired() ? "" : "?";

            sb.append("  ").append(field.getName()).append(optional).append(": ").append(tsType).append(";\n");
        }

        sb.append("}\n\n");

        // Interface para criação (Request - sem IDs auto-increment)
        sb.append("export interface ").append(entityName).append("Request {\n");

        for (Field field : allFields) {
            // Pula PKs auto-increment
            if (field.isPrimaryKey() && field.isAutoIncrement()) {
                continue;
            }

            String tsType = toTypeScriptType(field);
            String optional = field.isRequired() ? "" : "?";

            sb.append("  ").append(field.getName()).append(optional).append(": ").append(tsType).append(";\n");
        }

        sb.append("}\n\n");

        // Interface para resposta (Response - igual à principal)
        sb.append("export interface ").append(entityName).append("Response {\n");

        if (hasNoPk) {
            sb.append("  id?: number;\n");
        }

        for (int i = 0; i < allFields.size(); i++) {
            Field field = allFields.get(i);
            String tsType = toTypeScriptType(field);
            String optional = field.isRequired() ? "" : "?";

            sb.append("  ").append(field.getName()).append(optional).append(": ").append(tsType).append(";\n");
        }

        sb.append("}\n\n");

        // Interface para listagem (apenas campos visíveis no grid)
        sb.append("export interface ").append(entityName).append("List {\n");

        if (hasNoPk) {
            sb.append("  id: number;\n");
        }

        for (Field field : allFields) {
            if (isVisibleInGrid(field) || field.isPrimaryKey()) {
                String tsType = toTypeScriptType(field);
                sb.append("  ").append(field.getName()).append(": ").append(tsType).append(";\n");
            }
        }

        sb.append("}\n");

        // Interface para ID composto (se houver mais de um campo PK)
        List<Field> pkFields = allFields.stream()
            .filter(Field::isPrimaryKey)
            .collect(Collectors.toList());

        if (pkFields.size() > 1) {
            sb.append("\n");
            sb.append("export interface ").append(entityName).append("Id {\n");
            for (Field pkField : pkFields) {
                String tsType = toTypeScriptType(pkField);
                sb.append("  ").append(pkField.getName()).append(": ").append(tsType).append(";\n");
            }
            sb.append("}\n");
        }

        return sb.toString();
    }

    /**
     * Converte tipo Java/SQL para TypeScript.
     */
    private String toTypeScriptType(Field field) {
        String dataType = field.getDataType().name();

        switch (dataType.toUpperCase()) {
            case "STRING":
            case "TEXT":
            case "VARCHAR":
            case "CHAR":
            case "UUID":
                return "string";

            case "INTEGER":
            case "LONG":
            case "BIGINT":
            case "SMALLINT":
            case "TINYINT":
            case "DECIMAL":
            case "NUMERIC":
            case "FLOAT":
            case "DOUBLE":
            case "REAL":
                return "number";

            case "BOOLEAN":
            case "BIT":
                return "boolean";

            case "DATE":
            case "DATETIME":
            case "TIMESTAMP":
            case "TIME":
                return "Date | string";

            case "BINARY":
            case "VARBINARY":
            case "BLOB":
                return "Blob | string";

            default:
                return "any";
        }
    }

    /**
     * Verifica se o campo está visível no grid.
     */
    private boolean isVisibleInGrid(Field field) {
        return field.getUi() != null
            && field.getUi().getGrid() != null
            && field.getUi().getGrid().isVisible();
    }
}
