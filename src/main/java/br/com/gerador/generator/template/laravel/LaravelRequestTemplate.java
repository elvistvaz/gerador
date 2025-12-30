package br.com.gerador.generator.template.laravel;

import br.com.gerador.generator.config.ProjectConfig;
import br.com.gerador.metamodel.model.DataType;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.Field;
import br.com.gerador.metamodel.model.MetaModel;

/**
 * Template para geração de Form Requests do Laravel.
 */
public class LaravelRequestTemplate {

    private ProjectConfig projectConfig;

    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }

    public String generate(Entity entity, MetaModel metaModel) {
        return generateRequest(entity, "Store", true);
    }

    public String generateUpdateRequest(Entity entity, MetaModel metaModel) {
        return generateRequest(entity, "Update", false);
    }

    private String generateRequest(Entity entity, String prefix, boolean isCreate) {
        StringBuilder sb = new StringBuilder();
        String entityName = entity.getName();

        // PHP opening tag
        sb.append("<?php\n\n");

        // Namespace
        sb.append("namespace App\\Http\\Requests;\n\n");

        // Imports
        sb.append("use Illuminate\\Foundation\\Http\\FormRequest;\n\n");

        // Class declaration
        sb.append("class ").append(prefix).append(entityName).append("Request extends FormRequest\n");
        sb.append("{\n");

        // Authorize method
        sb.append("    /**\n");
        sb.append("     * Determine if the user is authorized to make this request.\n");
        sb.append("     */\n");
        sb.append("    public function authorize(): bool\n");
        sb.append("    {\n");
        sb.append("        return true;\n");
        sb.append("    }\n\n");

        // Rules method
        sb.append("    /**\n");
        sb.append("     * Get the validation rules that apply to the request.\n");
        sb.append("     */\n");
        sb.append("    public function rules(): array\n");
        sb.append("    {\n");
        sb.append("        return [\n");

        // Generate validation rules
        for (Field field : entity.getFields()) {
            if (field.isPrimaryKey() ||
                field.getName().equals("created_at") ||
                field.getName().equals("updated_at") ||
                field.getName().equals("deleted_at")) {
                continue;
            }

            String fieldName = toSnakeCase(field.getName());
            String rules = getValidationRules(field, entityName, isCreate);

            if (rules != null && !rules.isEmpty()) {
                sb.append("            '").append(fieldName).append("' => '").append(rules).append("',\n");
            }
        }

        sb.append("        ];\n");
        sb.append("    }\n");

        sb.append("}\n");

        return sb.toString();
    }

    private String getValidationRules(Field field, String entityName, boolean isCreate) {
        StringBuilder rules = new StringBuilder();

        // Required
        if (field.isRequired()) {
            rules.append("required");
        } else {
            rules.append("nullable");
        }

        // Type validation
        String typeRule = getTypeValidation(field);
        if (typeRule != null) {
            if (rules.length() > 0) rules.append("|");
            rules.append(typeRule);
        }

        // Unique
        if (field.isUnique()) {
            if (rules.length() > 0) rules.append("|");
            String tableName = toSnakeCase(entityName);
            String fieldName = toSnakeCase(field.getName());

            if (isCreate) {
                rules.append("unique:").append(tableName).append(",").append(fieldName);
            } else {
                rules.append("unique:").append(tableName).append(",").append(fieldName).append(",{id}");
            }
        }

        // Max length
        if (field.getLength() != null && field.getLength() > 0 &&
            (field.getDataType() == DataType.STRING || field.getDataType() == DataType.TEXT)) {
            if (rules.length() > 0) rules.append("|");
            rules.append("max:").append(field.getLength());
        }

        // Foreign key
        if (field.isForeignKey()) {
            if (rules.length() > 0) rules.append("|");
            String referencedTable = toSnakeCase(field.getReference().getEntity());
            String referencedField = field.getReference().getField() != null
                ? toSnakeCase(field.getReference().getField())
                : "id";
            rules.append("exists:").append(referencedTable).append(",").append(referencedField);
        }

        return rules.toString();
    }

    private String getTypeValidation(Field field) {
        if (field.getDataType() == null) {
            return "string";
        }

        return switch (field.getDataType()) {
            case STRING, TEXT -> "string";
            case INTEGER, LONG -> "integer";
            case DECIMAL, MONEY -> "numeric";
            case BOOLEAN -> "boolean";
            case DATE, DATETIME, TIME -> "date";
            default -> null;
        };
    }

    private String toSnakeCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}
