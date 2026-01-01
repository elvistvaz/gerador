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

    /**
     * Obtém o nome da coluna para um campo.
     * Se o campo tem columnName definido, usa ele. Senão, converte para snake_case.
     */
    private String getFieldColumnName(Field field) {
        if (field.getColumnName() != null && !field.getColumnName().isEmpty()) {
            return field.getColumnName();
        }
        return toSnakeCase(field.getName());
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
            String fieldName = getFieldColumnName(field);

            // Pular campos automáticos
            if (fieldName.equals("created_at") || fieldName.equals("updated_at") || fieldName.equals("deleted_at")) {
                continue;
            }

            // Para PKs auto-increment, pular sempre
            if (field.isPrimaryKey() && field.isAutoIncrement()) {
                continue;
            }

            // Para PKs não auto-increment, incluir apenas no Store (create)
            if (field.isPrimaryKey() && !field.isAutoIncrement()) {
                if (!isCreate) {
                    continue; // Pular no Update
                }
            }

            String rules = getValidationRules(field, entity, isCreate);

            if (rules != null && !rules.isEmpty()) {
                sb.append("            '").append(fieldName).append("' => '").append(rules).append("',\n");
            }
        }

        sb.append("        ];\n");
        sb.append("    }\n");

        sb.append("}\n");

        return sb.toString();
    }

    private String getValidationRules(Field field, Entity entity, boolean isCreate) {
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

        // Unique (incluindo PKs não auto-increment)
        if (field.isUnique() || (field.isPrimaryKey() && !field.isAutoIncrement())) {
            if (rules.length() > 0) rules.append("|");
            String tableName = toSnakeCase(entity.getName());
            String fieldName = getFieldColumnName(field);

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
        if (field.isForeignKey() && field.getReference() != null) {
            if (rules.length() > 0) rules.append("|");
            String referencedTable = toSnakeCase(field.getReference().getEntity());

            // Se a referência especifica o campo, usar ele; senão, usar "id"
            String referencedField = "id";
            if (field.getReference().getField() != null && !field.getReference().getField().isEmpty()) {
                // Aqui devemos buscar o campo na entidade referenciada para pegar seu columnName
                // Por enquanto, vamos usar o nome do campo da referência
                referencedField = field.getReference().getField();
            }

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
