package br.com.gerador.generator.template.laravel;

import br.com.gerador.generator.config.ProjectConfig;
import br.com.gerador.metamodel.model.DataType;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.Field;
import br.com.gerador.metamodel.model.MetaModel;

import java.util.stream.Collectors;

/**
 * Template para geração de Models do Laravel.
 */
public class LaravelModelTemplate {

    private ProjectConfig projectConfig;

    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }

    public String generate(Entity entity, MetaModel metaModel) {
        StringBuilder sb = new StringBuilder();

        // PHP opening tag
        sb.append("<?php\n\n");

        // Namespace
        sb.append("namespace App\\Models;\n\n");

        // Imports
        generateImports(sb, entity);

        // Class declaration
        sb.append("class ").append(entity.getName());

        boolean useAuditing = projectConfig != null &&
            projectConfig.getBackendFeatures() != null &&
            projectConfig.getBackendFeatures().getOrDefault("audit", false);

        if (useAuditing) {
            sb.append(" extends BaseModel implements Auditable\n");
        } else {
            sb.append(" extends BaseModel\n");
        }

        sb.append("{\n");

        // Traits
        generateTraits(sb, entity, useAuditing);

        // Table name
        generateTableName(sb, entity);

        // Primary key (if not 'id')
        Field pkField = getPrimaryKeyField(entity);
        if (pkField != null && !pkField.getName().equals("id")) {
            String pkName = toSnakeCase(pkField.getName());
            sb.append("\n    protected $primaryKey = '").append(pkName).append("';\n");

            // Route key name (for Route Model Binding)
            sb.append("\n    /**\n");
            sb.append("     * Get the route key for the model.\n");
            sb.append("     */\n");
            sb.append("    public function getRouteKeyName()\n");
            sb.append("    {\n");
            sb.append("        return '").append(pkName).append("';\n");
            sb.append("    }\n");
        }

        // Fillable
        generateFillable(sb, entity);

        // Hidden (para campos sensíveis como senha)
        generateHidden(sb, entity);

        // Casts
        generateCasts(sb, entity);

        // Field options (constants and accessor for labels)
        generateFieldOptions(sb, entity);

        // Relationships
        generateRelationships(sb, entity, metaModel);

        // Custom methods
        generateCustomMethods(sb, entity);

        sb.append("}\n");

        return sb.toString();
    }

    private void generateImports(StringBuilder sb, Entity entity) {
        sb.append("use Illuminate\\Database\\Eloquent\\Factories\\HasFactory;\n");
        sb.append("use Illuminate\\Database\\Eloquent\\SoftDeletes;\n");

        boolean useAuditing = projectConfig != null &&
            projectConfig.getBackendFeatures() != null &&
            projectConfig.getBackendFeatures().getOrDefault("audit", false);

        if (useAuditing) {
            sb.append("use OwenIt\\Auditing\\Auditable as AuditableTrait;\n");
            sb.append("use OwenIt\\Auditing\\Contracts\\Auditable;\n");
        }

        sb.append("\n");
    }

    private void generateTraits(StringBuilder sb, Entity entity, boolean useAuditing) {
        sb.append("    use HasFactory, SoftDeletes");

        if (useAuditing) {
            sb.append(", AuditableTrait");
        }

        sb.append(";\n");
    }

    private void generateTableName(StringBuilder sb, Entity entity) {
        String tableName = toSnakeCase(entity.getTableName() != null
            ? entity.getTableName()
            : entity.getName());

        sb.append("\n    protected $table = '").append(tableName).append("';\n");
    }

    private void generateFillable(StringBuilder sb, Entity entity) {
        sb.append("\n    protected $fillable = [\n");

        entity.getFields().stream()
            .filter(field -> !field.isPrimaryKey() &&
                           !field.getName().equals("created_at") &&
                           !field.getName().equals("updated_at") &&
                           !field.getName().equals("deleted_at"))
            .forEach(field -> {
                String fieldName = toSnakeCase(field.getName());
                sb.append("        '").append(fieldName).append("',\n");
            });

        sb.append("    ];\n");
    }

    private void generateHidden(StringBuilder sb, Entity entity) {
        java.util.List<String> hiddenFields = entity.getFields().stream()
            .filter(field -> field.getName().toLowerCase().contains("password") ||
                           field.getName().toLowerCase().contains("senha") ||
                           field.getName().toLowerCase().contains("token"))
            .map(field -> toSnakeCase(field.getName()))
            .collect(Collectors.toList());

        if (!hiddenFields.isEmpty()) {
            sb.append("\n    protected $hidden = [\n");
            hiddenFields.forEach(fieldName ->
                sb.append("        '").append(fieldName).append("',\n")
            );
            sb.append("    ];\n");
        }
    }

    private void generateCasts(StringBuilder sb, Entity entity) {
        sb.append("\n    protected $casts = [\n");

        for (Field field : entity.getFields()) {
            String phpType = getPhpCastType(field);
            if (phpType != null) {
                String fieldName = toSnakeCase(field.getName());
                sb.append("        '").append(fieldName).append("' => '").append(phpType).append("',\n");
            }
        }

        sb.append("        'deleted_at' => 'datetime',\n");
        sb.append("    ];\n");
    }

    private void generateRelationships(StringBuilder sb, Entity entity, MetaModel metaModel) {
        sb.append("\n    // Relationships\n");

        // BelongsTo relationships (foreign keys in this entity)
        for (Field field : entity.getFields()) {
            if (field.isForeignKey()) {
                generateBelongsTo(sb, field);
            }
        }

        // HasMany relationships (this entity is referenced by others)
        for (Entity otherEntity : metaModel.getEntities()) {
            for (Field field : otherEntity.getFields()) {
                if (field.isForeignKey() &&
                    field.getReference().getEntity().equals(entity.getName())) {
                    generateHasMany(sb, otherEntity, field);
                }
            }
        }
    }

    private void generateBelongsTo(StringBuilder sb, Field field) {
        String relatedEntity = field.getReference().getEntity();
        String methodName = toCamelCase(relatedEntity);
        String foreignKey = toSnakeCase(field.getName());

        sb.append("\n    public function ").append(methodName).append("()\n");
        sb.append("    {\n");
        sb.append("        return $this->belongsTo(").append(relatedEntity).append("::class, '");
        sb.append(foreignKey).append("');\n");
        sb.append("    }\n");
    }

    private void generateHasMany(StringBuilder sb, Entity relatedEntity, Field foreignKeyField) {
        String foreignKey = toSnakeCase(foreignKeyField.getName());

        // Gera nome do método baseado na FK para evitar duplicações
        // Exemplo: id_naturalidade -> pessoasPorNaturalidade, id_cidade -> pessoas
        String methodName = generateUniqueRelationshipName(relatedEntity.getName(), foreignKeyField.getName());

        sb.append("\n    public function ").append(methodName).append("()\n");
        sb.append("    {\n");
        sb.append("        return $this->hasMany(").append(relatedEntity.getName()).append("::class, '");
        sb.append(foreignKey).append("');\n");
        sb.append("    }\n");
    }

    /**
     * Gera um nome único para o relacionamento baseado no nome da FK.
     * Se a FK for padrão (idEntidade), usa o nome da entidade no plural.
     * Se for diferente (ex: idNaturalidade), adiciona sufixo para evitar duplicação.
     */
    private String generateUniqueRelationshipName(String entityName, String foreignKeyName) {
        String baseName = toCamelCasePlural(entityName);

        // Nome esperado padrão da FK: idEntityName
        String expectedFkName = "id" + entityName;

        // Se a FK tem o nome padrão, usa o nome base
        if (foreignKeyName.equalsIgnoreCase(expectedFkName)) {
            return baseName;
        }

        // Se a FK tem nome diferente, adiciona sufixo baseado no nome da FK
        // Exemplo: idNaturalidade -> pessoasPorNaturalidade
        String suffix = extractSuffixFromFk(foreignKeyName);
        if (suffix != null && !suffix.isEmpty()) {
            return baseName + "Por" + capitalize(suffix);
        }

        return baseName;
    }

    /**
     * Extrai o sufixo do nome da FK.
     * Exemplo: idNaturalidade -> Naturalidade
     */
    private String extractSuffixFromFk(String fkName) {
        if (fkName.startsWith("id") && fkName.length() > 2) {
            return fkName.substring(2);
        }
        return fkName;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private void generateCustomMethods(StringBuilder sb, Entity entity) {
        String entityName = entity.getName();
        sb.append("\n    /**\n");
        sb.append("     * Get ").append(entityName).append(" by ID\n");
        sb.append("     */\n");
        sb.append("    public static function get").append(entityName).append("($id)\n");
        sb.append("    {\n");
        sb.append("        return self::findOrFail($id);\n");
        sb.append("    }\n");
    }

    // Utility methods

    private Field getPrimaryKeyField(Entity entity) {
        return entity.getFields().stream()
            .filter(Field::isPrimaryKey)
            .findFirst()
            .orElse(null);
    }

    private String getPhpCastType(Field field) {
        if (field.getDataType() == null) {
            return null;
        }

        return switch (field.getDataType()) {
            case INTEGER, LONG -> "integer";
            case DECIMAL, MONEY -> "decimal:2";
            case BOOLEAN -> "boolean";
            case DATE -> "date";
            case DATETIME, TIME -> "datetime";
            default -> null;
        };
    }

    /**
     * Gera constantes e métodos para campos com opções definidas (ui.options).
     */
    private void generateFieldOptions(StringBuilder sb, Entity entity) {
        if (entity.getFields() == null) return;

        for (Field field : entity.getFields()) {
            if (field.getUi() != null && field.getUi().hasOptions()) {
                String fieldName = field.getName();
                String fieldNameSnake = toSnakeCase(fieldName);
                String fieldNameUpper = fieldNameSnake.toUpperCase();

                sb.append("\n    // Opções do campo ").append(fieldNameSnake).append("\n");

                // Gerar constantes para cada opção
                for (br.com.gerador.metamodel.model.FieldOption option : field.getUi().getOptions()) {
                    String optionValue = option.getValue().toString();
                    String optionLabel = option.getLabel();

                    // Criar nome da constante: CAMPO_LABEL_NORMALIZADO
                    String constantName = fieldNameUpper + "_" + normalizeConstantName(optionLabel);

                    sb.append("    public const ").append(constantName).append(" = ").append(optionValue).append(";\n");
                }

                // Gerar método estático que retorna todas as opções
                String methodName = "get" + capitalize(toCamelCase(fieldName)) + "Options";
                sb.append("\n    /**\n");
                sb.append("     * Retorna todas as opções de ").append(fieldNameSnake).append("\n");
                sb.append("     */\n");
                sb.append("    public static function ").append(methodName).append("()\n");
                sb.append("    {\n");
                sb.append("        return [\n");

                for (br.com.gerador.metamodel.model.FieldOption option : field.getUi().getOptions()) {
                    String optionValue = option.getValue().toString();
                    String optionLabel = option.getLabel();
                    String constantName = fieldNameUpper + "_" + normalizeConstantName(optionLabel);

                    sb.append("            self::").append(constantName).append(" => '").append(optionLabel).append("',\n");
                }

                sb.append("        ];\n");
                sb.append("    }\n");

                // Gerar accessor para obter o label do valor atual
                String accessorName = fieldNameSnake + "_label";
                sb.append("\n    /**\n");
                sb.append("     * Retorna o label do ").append(fieldNameSnake).append("\n");
                sb.append("     */\n");
                sb.append("    public function get").append(capitalize(toCamelCase(fieldName))).append("LabelAttribute()\n");
                sb.append("    {\n");
                sb.append("        $options = self::").append(methodName).append("();\n");
                sb.append("        return $options[$this->").append(fieldNameSnake).append("] ?? '-';\n");
                sb.append("    }\n");
            }
        }
    }

    /**
     * Normaliza o nome de uma label para usar como nome de constante.
     */
    private String normalizeConstantName(String label) {
        return label.toUpperCase()
                .replaceAll("[ÀÁÂÃÄÅ]", "A")
                .replaceAll("[ÈÉÊË]", "E")
                .replaceAll("[ÌÍÎÏ]", "I")
                .replaceAll("[ÒÓÔÕÖ]", "O")
                .replaceAll("[ÙÚÛÜ]", "U")
                .replaceAll("[Ç]", "C")
                .replaceAll("[^A-Z0-9]+", "_")
                .replaceAll("^_+|_+$", ""); // Remove underscores no início e fim
    }

    private String toSnakeCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    private String toCamelCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    private String toCamelCasePlural(String str) {
        String camelCase = toCamelCase(str);
        if (camelCase.endsWith("y")) {
            return camelCase.substring(0, camelCase.length() - 1) + "ies";
        } else if (camelCase.endsWith("s")) {
            return camelCase + "es";
        } else {
            return camelCase + "s";
        }
    }
}
