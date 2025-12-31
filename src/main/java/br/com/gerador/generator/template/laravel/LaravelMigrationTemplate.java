package br.com.gerador.generator.template.laravel;

import br.com.gerador.generator.config.ProjectConfig;
import br.com.gerador.metamodel.model.DataType;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.Field;
import br.com.gerador.metamodel.model.MetaModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Template para geração de Migrations do Laravel.
 */
public class LaravelMigrationTemplate {

    private ProjectConfig projectConfig;

    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }

    public String generate(Entity entity, MetaModel metaModel) {
        StringBuilder sb = new StringBuilder();
        String tableName = toSnakeCase(entity.getTableName() != null ? entity.getTableName() : entity.getName());

        // PHP opening tag
        sb.append("<?php\n\n");

        // Imports
        sb.append("use Illuminate\\Database\\Migrations\\Migration;\n");
        sb.append("use Illuminate\\Database\\Schema\\Blueprint;\n");
        sb.append("use Illuminate\\Support\\Facades\\Schema;\n\n");

        // Class declaration
        sb.append("return new class extends Migration\n");
        sb.append("{\n");

        // Up method
        sb.append("    /**\n");
        sb.append("     * Run the migrations.\n");
        sb.append("     */\n");
        sb.append("    public function up(): void\n");
        sb.append("    {\n");
        sb.append("        Schema::create('").append(tableName).append("', function (Blueprint $table) {\n");

        // Primary key (simple or composite)
        boolean hasCompositePK = entity.hasCompositePrimaryKey();
        Field pkField = getPrimaryKeyField(entity);

        if (!hasCompositePK && pkField != null && pkField.getName().equals("id")) {
            sb.append("            $table->id();\n");
        }

        // Fields
        for (Field field : entity.getFields()) {
            // Para chave composta, gerar todos os campos normalmente
            // Para chave simples "id", já foi gerado acima com $table->id()
            if (!hasCompositePK && field.isPrimaryKey() && field.getName().equals("id")) continue;
            if (field.getName().equals("created_at") || field.getName().equals("updated_at")) continue;
            if (field.getName().equals("deleted_at")) continue;

            generateField(sb, field, field.isPrimaryKey());
        }

        // Timestamps
        sb.append("            $table->timestamps();\n");

        // Soft deletes
        sb.append("            $table->softDeletes();\n");

        // Composite primary key definition
        if (hasCompositePK) {
            List<Field> pkFields = entity.getPrimaryKeyFields();
            sb.append("\n");
            sb.append("            // Chave primária composta\n");
            sb.append("            $table->primary([");
            for (int i = 0; i < pkFields.size(); i++) {
                sb.append("'").append(toSnakeCase(pkFields.get(i).getName())).append("'");
                if (i < pkFields.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]);\n");
        }

        // Foreign keys
        sb.append("\n");
        sb.append("            // Foreign keys\n");
        for (Field field : entity.getFields()) {
            if (field.isForeignKey()) {
                generateForeignKey(sb, field);
            }
        }

        sb.append("        });\n");
        sb.append("    }\n\n");

        // Down method
        sb.append("    /**\n");
        sb.append("     * Reverse the migrations.\n");
        sb.append("     */\n");
        sb.append("    public function down(): void\n");
        sb.append("    {\n");
        sb.append("        Schema::dropIfExists('").append(tableName).append("');\n");
        sb.append("    }\n");

        sb.append("};\n");

        return sb.toString();
    }

    private void generateField(StringBuilder sb, Field field, boolean isPrimaryKey) {
        String fieldName = toSnakeCase(field.getName());
        String fieldDefinition = getFieldDefinition(field);

        sb.append("            $table->").append(fieldDefinition);

        // Add unsigned for integer primary/foreign keys
        if (isPrimaryKey || field.isForeignKey()) {
            if (field.getDataType() == DataType.INTEGER ||
                field.getDataType() == DataType.LONG) {
                sb.append("->unsigned()");
            }
        }

        // AutoIncrement for single primary keys (not composite)
        // Quando tem autoIncrement, o primary já é implícito no Laravel
        if (isPrimaryKey && field.isAutoIncrement()) {
            sb.append("->autoIncrement()");
        }
        // Primary key constraint para campos SEM autoIncrement
        else if (isPrimaryKey && !field.getName().equals("id")) {
            sb.append("->primary()");
        }

        // Nullable
        if (!field.isRequired() && !isPrimaryKey) {
            sb.append("->nullable()");
        }

        // Default value
        if (field.getDefaultValue() != null) {
            String defaultValue = formatDefaultValue(field);
            sb.append("->default(").append(defaultValue).append(")");
        }

        // Unique
        if (field.isUnique()) {
            sb.append("->unique()");
        }

        sb.append(";\n");
    }

    private String getFieldDefinition(Field field) {
        String fieldName = toSnakeCase(field.getName());
        DataType dataType = field.getDataType();
        String databaseType = field.getDatabaseType();

        // Se tem databaseType específico, usar mapeamento direto
        if (databaseType != null && !databaseType.isEmpty()) {
            return switch (databaseType.toLowerCase()) {
                case "smallint" -> "smallInteger('" + fieldName + "')";
                case "tinyint" -> "tinyInteger('" + fieldName + "')";
                case "bigint" -> "bigInteger('" + fieldName + "')";
                case "int", "integer" -> "integer('" + fieldName + "')";
                case "varchar" -> {
                    int length = field.getLength() != null && field.getLength() > 0 ? field.getLength() : 255;
                    yield "string('" + fieldName + "', " + length + ")";
                }
                case "decimal", "numeric" -> {
                    int precision = field.getPrecision() != null && field.getPrecision() > 0 ? field.getPrecision() : 10;
                    int scale = field.getScale() != null && field.getScale() > 0 ? field.getScale() : 2;
                    yield "decimal('" + fieldName + "', " + precision + ", " + scale + ")";
                }
                case "text" -> "text('" + fieldName + "')";
                case "date" -> "date('" + fieldName + "')";
                case "datetime", "datetime2" -> "dateTime('" + fieldName + "')";
                case "time" -> "time('" + fieldName + "')";
                case "bit" -> "boolean('" + fieldName + "')";
                default -> getFieldDefinitionFromDataType(field, fieldName, dataType);
            };
        }

        return getFieldDefinitionFromDataType(field, fieldName, dataType);
    }

    private String getFieldDefinitionFromDataType(Field field, String fieldName, DataType dataType) {
        if (dataType == null) {
            return "string('" + fieldName + "')";
        }

        return switch (dataType) {
            case STRING -> {
                int length = field.getLength() != null && field.getLength() > 0 ? field.getLength() : 255;
                yield "string('" + fieldName + "', " + length + ")";
            }
            case TEXT -> "text('" + fieldName + "')";
            case INTEGER -> "integer('" + fieldName + "')";
            case LONG -> "bigInteger('" + fieldName + "')";
            case DECIMAL, MONEY -> {
                int precision = field.getPrecision() != null && field.getPrecision() > 0 ? field.getPrecision() : 10;
                int scale = field.getScale() != null && field.getScale() > 0 ? field.getScale() : 2;
                yield "decimal('" + fieldName + "', " + precision + ", " + scale + ")";
            }
            case BOOLEAN -> "boolean('" + fieldName + "')";
            case DATE -> "date('" + fieldName + "')";
            case DATETIME -> "dateTime('" + fieldName + "')";
            case TIME -> "time('" + fieldName + "')";
            case BINARY -> "binary('" + fieldName + "')";
            default -> "string('" + fieldName + "')";
        };
    }

    private void generateForeignKey(StringBuilder sb, Field field) {
        String foreignKey = toSnakeCase(field.getName());
        String referencedTable = toSnakeCase(field.getReference().getEntity());
        String referencedKey = field.getReference().getField() != null
            ? toSnakeCase(field.getReference().getField())
            : "id";

        sb.append("            $table->foreign('").append(foreignKey).append("')\n");
        sb.append("                  ->references('").append(referencedKey).append("')\n");
        sb.append("                  ->on('").append(referencedTable).append("')\n");
        sb.append("                  ->onDelete('cascade');\n");
    }

    private String formatDefaultValue(Field field) {
        String defaultValue = field.getDefaultValue();

        if (field.getDataType() == DataType.STRING || field.getDataType() == DataType.TEXT) {
            return "'" + defaultValue + "'";
        } else if (field.getDataType() == DataType.BOOLEAN) {
            return Boolean.parseBoolean(defaultValue) ? "true" : "false";
        }

        return defaultValue;
    }

    private Field getPrimaryKeyField(Entity entity) {
        return entity.getFields().stream()
            .filter(Field::isPrimaryKey)
            .findFirst()
            .orElse(null);
    }

    public String getMigrationFileName(Entity entity) {
        String tableName = toSnakeCase(entity.getTableName() != null ? entity.getTableName() : entity.getName());
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmss"));
        return timestamp + "_create_" + tableName + "_table.php";
    }

    private String toSnakeCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}
