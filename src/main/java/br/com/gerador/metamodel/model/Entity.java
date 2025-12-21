package br.com.gerador.metamodel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Representa uma entidade/tabela do sistema.
 */
public class Entity {

    private String id;
    private String name;
    private String displayName;
    private String tableName;
    private String description;
    private String schema = "dbo";
    private EntityType type = EntityType.MAIN;
    private String parentEntity;
    private boolean audit = false;
    private SessionFilter sessionFilter;
    private String sessionContextField; // Alias para sessionFilter.field (usado no JSON)
    private DefaultSort defaultSort;
    private List<ChildEntity> childEntities = new ArrayList<>();
    private List<Field> fields = new ArrayList<>();
    private List<Index> indexes = new ArrayList<>();
    private List<Constraint> constraints = new ArrayList<>();

    public Entity() {
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public String getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(String parentEntity) {
        this.parentEntity = parentEntity;
    }

    public boolean isAudit() {
        return audit;
    }

    public void setAudit(boolean audit) {
        this.audit = audit;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    public SessionFilter getSessionFilter() {
        // Se sessionFilter não foi definido mas sessionContextField foi, cria um SessionFilter
        if (sessionFilter == null && sessionContextField != null && !sessionContextField.isEmpty()) {
            sessionFilter = new SessionFilter();
            sessionFilter.setField(sessionContextField);
        }
        return sessionFilter;
    }

    public void setSessionFilter(SessionFilter sessionFilter) {
        this.sessionFilter = sessionFilter;
    }

    public String getSessionContextField() {
        return sessionContextField;
    }

    public void setSessionContextField(String sessionContextField) {
        this.sessionContextField = sessionContextField;
    }

    /**
     * Verifica se a entidade possui filtro de sessão.
     */
    public boolean hasSessionFilter() {
        // Verifica se tem sessionFilter explícito ou sessionContextField
        if (sessionFilter != null && sessionFilter.getField() != null) {
            return true;
        }
        return sessionContextField != null && !sessionContextField.isEmpty();
    }

    public DefaultSort getDefaultSort() {
        return defaultSort;
    }

    public void setDefaultSort(DefaultSort defaultSort) {
        this.defaultSort = defaultSort;
    }

    /**
     * Verifica se a entidade possui ordenação padrão.
     */
    public boolean hasDefaultSort() {
        return defaultSort != null && defaultSort.getField() != null;
    }

    public List<ChildEntity> getChildEntities() {
        return childEntities;
    }

    public void setChildEntities(List<ChildEntity> childEntities) {
        this.childEntities = childEntities;
    }

    /**
     * Verifica se a entidade possui entidades filhas para navegação.
     */
    public boolean hasChildEntities() {
        return childEntities != null && !childEntities.isEmpty();
    }

    // Métodos de conveniência

    /**
     * Retorna o nome completo da tabela (schema.tableName).
     */
    public String getFullTableName() {
        return schema != null && !schema.isEmpty()
            ? schema + "." + tableName
            : tableName;
    }

    /**
     * Busca um campo pelo nome.
     */
    public Optional<Field> findFieldByName(String name) {
        return fields.stream()
            .filter(f -> f.getName().equals(name))
            .findFirst();
    }

    /**
     * Busca um campo pelo nome da coluna.
     */
    public Optional<Field> findFieldByColumnName(String columnName) {
        return fields.stream()
            .filter(f -> f.getColumnName().equals(columnName))
            .findFirst();
    }

    /**
     * Retorna os campos que são chave primária.
     */
    public List<Field> getPrimaryKeyFields() {
        List<Field> pkFields = new ArrayList<>();
        for (Field field : fields) {
            if (field.isPrimaryKey()) {
                pkFields.add(field);
            }
        }
        return pkFields;
    }

    /**
     * Retorna os campos que são foreign keys.
     */
    public List<Field> getForeignKeyFields() {
        List<Field> fkFields = new ArrayList<>();
        for (Field field : fields) {
            if (field.getReference() != null) {
                fkFields.add(field);
            }
        }
        return fkFields;
    }

    /**
     * Retorna os campos obrigatórios.
     */
    public List<Field> getRequiredFields() {
        List<Field> requiredFields = new ArrayList<>();
        for (Field field : fields) {
            if (field.isRequired()) {
                requiredFields.add(field);
            }
        }
        return requiredFields;
    }

    /**
     * Verifica se a entidade possui chave primária composta.
     */
    public boolean hasCompositePrimaryKey() {
        return getPrimaryKeyFields().size() > 1;
    }

    /**
     * Verifica se é uma entidade filha.
     */
    public boolean isChild() {
        return type == EntityType.CHILD || type == EntityType.JUNCTION;
    }

    @Override
    public String toString() {
        return String.format("Entity{id='%s', name='%s', tableName='%s', type=%s, fields=%d}",
            id, name, tableName, type, fields.size());
    }
}
