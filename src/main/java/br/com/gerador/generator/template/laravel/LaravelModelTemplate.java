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

        // PHPDoc com metadata
        generateClassDocBlock(sb, entity, metaModel);

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
            String pkName = getFieldColumnName(pkField);
            sb.append("\n    protected $primaryKey = '").append(pkName).append("';\n");

            // Se não é auto-increment, adiciona as propriedades necessárias
            if (!pkField.isAutoIncrement()) {
                sb.append("\n    /**\n");
                sb.append("     * Indicates if the IDs are auto-incrementing.\n");
                sb.append("     */\n");
                sb.append("    public $incrementing = false;\n");

                // Se a PK não é INTEGER ou LONG, define o tipo
                if (pkField.getDataType() != DataType.INTEGER &&
                    pkField.getDataType() != DataType.LONG) {
                    sb.append("\n    /**\n");
                    sb.append("     * The data type of the primary key.\n");
                    sb.append("     */\n");
                    sb.append("    protected $keyType = '");
                    sb.append(getPhpKeyType(pkField.getDataType()));
                    sb.append("';\n");
                }
            }

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

    private void generateClassDocBlock(StringBuilder sb, Entity entity, MetaModel metaModel) {
        sb.append("/**\n");
        sb.append(" * ").append(entity.getName());

        if (entity.getDisplayName() != null && !entity.getDisplayName().isEmpty()) {
            sb.append(" - ").append(entity.getDisplayName());
        }

        sb.append("\n");

        if (entity.getDescription() != null && !entity.getDescription().isEmpty()) {
            sb.append(" *\n");
            sb.append(" * ").append(entity.getDescription()).append("\n");
        }

        // Adicionar informações de metadata se disponível
        if (metaModel != null && metaModel.getMetadata() != null) {
            var metadata = metaModel.getMetadata();

            if (metadata.getAuthor() != null && !metadata.getAuthor().isEmpty()) {
                sb.append(" *\n");
                sb.append(" * @author ").append(metadata.getAuthor()).append("\n");
            }

            if (metadata.getVersion() != null && !metadata.getVersion().isEmpty()) {
                sb.append(" * @version ").append(metadata.getVersion()).append("\n");
            }
        }

        sb.append(" */\n");
    }

    private void generateTraits(StringBuilder sb, Entity entity, boolean useAuditing) {
        sb.append("    use HasFactory, SoftDeletes");

        if (useAuditing) {
            sb.append(", AuditableTrait");
        }

        sb.append(";\n");
    }

    private void generateTableName(StringBuilder sb, Entity entity) {
        // Usa exatamente o tableName configurado no JSON, sem conversão
        String tableName = entity.getTableName() != null
            ? entity.getTableName()
            : entity.getName();

        sb.append("\n    protected $table = '").append(tableName).append("';\n");
    }

    private void generateFillable(StringBuilder sb, Entity entity) {
        sb.append("\n    protected $fillable = [\n");

        entity.getFields().stream()
            .filter(field -> {
                // Excluir PKs auto-increment
                if (field.isPrimaryKey() && field.isAutoIncrement()) {
                    return false;
                }
                // Excluir campos de timestamp
                if (field.getName().equals("created_at") ||
                    field.getName().equals("updated_at") ||
                    field.getName().equals("deleted_at")) {
                    return false;
                }
                // Incluir PKs não auto-increment e todos os outros campos
                return true;
            })
            .forEach(field -> {
                String fieldName = getFieldColumnName(field);
                sb.append("        '").append(fieldName).append("',\n");
            });

        sb.append("    ];\n");
    }

    private void generateHidden(StringBuilder sb, Entity entity) {
        java.util.List<String> hiddenFields = entity.getFields().stream()
            .filter(field -> field.getName().toLowerCase().contains("password") ||
                           field.getName().toLowerCase().contains("senha") ||
                           field.getName().toLowerCase().contains("token"))
            .map(this::getFieldColumnName)
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
                String fieldName = getFieldColumnName(field);
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
                generateBelongsTo(sb, field, metaModel);
            }
        }

        // HasMany relationships explícitos de childEntities
        if (entity.getChildEntities() != null && !entity.getChildEntities().isEmpty()) {
            for (br.com.gerador.metamodel.model.ChildEntity child : entity.getChildEntities()) {
                generateHasManyFromChildEntity(sb, child);
            }
        }

        // HasMany relationships automáticos (entidades que referenciam esta)
        for (Entity otherEntity : metaModel.getEntities()) {
            for (Field field : otherEntity.getFields()) {
                if (field.isForeignKey() &&
                    field.getReference().getEntity().equals(entity.getName())) {
                    // Verificar se não é um childEntity explícito (evitar duplicação)
                    if (!isExplicitChildEntity(entity, otherEntity.getName())) {
                        generateHasMany(sb, otherEntity, field);
                    }
                }
            }
        }
    }

    private void generateBelongsTo(StringBuilder sb, Field field, MetaModel metaModel) {
        String relatedEntity = field.getReference().getEntity();
        String foreignKey = getFieldColumnName(field);

        // Gera nome de método único para evitar duplicação quando há múltiplas FKs para a mesma entidade
        // Exemplo: id_cidade -> cidade(), id_naturalidade -> naturalidade()
        String methodName = generateUniqueBelongsToName(field.getName(), relatedEntity);

        // Busca a PK da entidade relacionada no metamodel
        String relatedPk = getRelatedEntityPrimaryKeyColumnName(field, metaModel);

        sb.append("\n    public function ").append(methodName).append("()\n");
        sb.append("    {\n");
        sb.append("        return $this->belongsTo(").append(relatedEntity).append("::class, '");
        sb.append(foreignKey).append("'");
        if (relatedPk != null) {
            sb.append(", '").append(relatedPk).append("'");
        }
        sb.append(");\n");
        sb.append("    }\n");
    }

    private void generateHasMany(StringBuilder sb, Entity relatedEntity, Field foreignKeyField) {
        String foreignKey = getFieldColumnName(foreignKeyField);

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
     * Gera hasMany a partir de childEntity explícito do metamodel.
     */
    private void generateHasManyFromChildEntity(StringBuilder sb, br.com.gerador.metamodel.model.ChildEntity child) {
        String childEntity = child.getEntity();
        String foreignKey = toSnakeCase(child.getForeignKey());

        // Usar label como nome do método se disponível, senão usar nome da entidade no plural
        String methodName = child.getLabel() != null && !child.getLabel().isEmpty()
            ? toCamelCase(child.getLabel().toLowerCase().replace(" ", ""))
            : toCamelCasePlural(childEntity);

        sb.append("\n    /**\n");
        sb.append("     * ").append(child.getLabel() != null ? child.getLabel() : childEntity).append("\n");
        sb.append("     */\n");
        sb.append("    public function ").append(methodName).append("()\n");
        sb.append("    {\n");
        sb.append("        return $this->hasMany(").append(childEntity).append("::class, '");
        sb.append(foreignKey).append("');\n");
        sb.append("    }\n");
    }

    /**
     * Verifica se uma entidade está na lista de childEntities explícitos.
     */
    private boolean isExplicitChildEntity(Entity parent, String entityName) {
        if (parent.getChildEntities() == null) {
            return false;
        }

        for (br.com.gerador.metamodel.model.ChildEntity child : parent.getChildEntities()) {
            if (child.getEntity().equals(entityName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna o nome da coluna da chave primária da entidade relacionada.
     * Busca a entidade no metamodel e retorna o columnName da PK.
     */
    private String getRelatedEntityPrimaryKeyColumnName(Field field, MetaModel metaModel) {
        if (field.getReference() == null || field.getReference().getEntity() == null) {
            return null;
        }

        String relatedEntityName = field.getReference().getEntity();

        // Busca a entidade relacionada no metamodel
        for (Entity entity : metaModel.getEntities()) {
            if (entity.getName().equals(relatedEntityName)) {
                // Busca a chave primária da entidade
                for (Field pkField : entity.getFields()) {
                    if (pkField.isPrimaryKey()) {
                        // Retorna o columnName da PK (não o nome do campo)
                        return getFieldColumnName(pkField);
                    }
                }
            }
        }

        // Fallback: se não encontrar, retorna null (Laravel usará 'id' como padrão)
        return null;
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

    /**
     * Gera um nome único para o relacionamento belongsTo.
     * Se a FK for padrão (id_entity ou idEntity), usa o nome da entidade.
     * Se for diferente (ex: id_naturalidade), usa o nome do próprio campo sem o prefixo 'id'.
     *
     * Exemplos:
     *   id_cidade + Cidade -> cidade()
     *   id_naturalidade + Cidade -> naturalidade()
     *   id_estado_civil + EstadoCivil -> estadoCivil()
     */
    private String generateUniqueBelongsToName(String foreignKeyName, String relatedEntity) {
        // Remove underscores e converte para camelCase
        String fkCamel = toCamelCase(foreignKeyName);

        // Remove o prefixo 'id' se existir
        if (fkCamel.startsWith("id") && fkCamel.length() > 2 && Character.isUpperCase(fkCamel.charAt(2))) {
            fkCamel = Character.toLowerCase(fkCamel.charAt(2)) + fkCamel.substring(3);
        }

        // Se após remover 'id' o nome resultante é igual ao nome da entidade em camelCase, usa o nome da entidade
        String entityCamel = toCamelCase(relatedEntity);
        if (fkCamel.equalsIgnoreCase(entityCamel)) {
            return entityCamel;
        }

        // Caso contrário, usa o nome derivado do campo FK (sem o 'id')
        return fkCamel;
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

    private String getPhpKeyType(DataType dataType) {
        if (dataType == null) {
            return "string";
        }

        return switch (dataType) {
            case INTEGER, LONG -> "int";
            default -> "string";
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
                String fieldColumnName = getFieldColumnName(field);
                String fieldNameUpper = fieldColumnName.toUpperCase();

                sb.append("\n    // Opções do campo ").append(fieldColumnName).append("\n");

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
                sb.append("     * Retorna todas as opções de ").append(fieldColumnName).append("\n");
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
                String accessorName = fieldColumnName + "_label";
                sb.append("\n    /**\n");
                sb.append("     * Retorna o label do ").append(fieldColumnName).append("\n");
                sb.append("     */\n");
                sb.append("    public function get").append(capitalize(toCamelCase(fieldName))).append("LabelAttribute()\n");
                sb.append("    {\n");
                sb.append("        $options = self::").append(methodName).append("();\n");
                sb.append("        return $options[$this->").append(fieldColumnName).append("] ?? '-';\n");
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

    /**
     * Retorna o nome da coluna do campo.
     * Se columnName estiver definido, usa ele. Caso contrário, converte para snake_case.
     */
    private String getFieldColumnName(Field field) {
        if (field.getColumnName() != null && !field.getColumnName().isEmpty()) {
            return field.getColumnName();
        }
        return toSnakeCase(field.getName());
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
