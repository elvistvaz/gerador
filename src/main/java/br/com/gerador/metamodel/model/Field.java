package br.com.gerador.metamodel.model;

/**
 * Representa um campo/coluna de uma entidade.
 */
public class Field {

    private String id;
    private String name;
    private String columnName;
    private String label;
    private String description;
    private DataType dataType;
    private String databaseType;
    private Integer length;
    private Integer precision;
    private Integer scale;
    private boolean primaryKey = false;
    private boolean autoIncrement = false;
    private boolean required = false;
    private boolean unique = false;
    private String defaultValue;
    private Reference reference;
    private String enumRef;
    private FieldUI ui;
    private Validation validation;

    public Field() {
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

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public String getEnumRef() {
        return enumRef;
    }

    public void setEnumRef(String enumRef) {
        this.enumRef = enumRef;
    }

    public FieldUI getUi() {
        return ui;
    }

    public void setUi(FieldUI ui) {
        this.ui = ui;
    }

    public Validation getValidation() {
        return validation;
    }

    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    // Métodos de conveniência

    /**
     * Verifica se o campo é uma foreign key.
     */
    public boolean isForeignKey() {
        return reference != null;
    }

    /**
     * Retorna o tipo Java correspondente ao dataType.
     */
    public String getJavaType() {
        if (dataType == null) {
            return "Object";
        }
        switch (dataType) {
            case STRING:
            case TEXT:
                return "String";
            case INTEGER:
                return "Integer";
            case LONG:
                return "Long";
            case DECIMAL:
            case MONEY:
                return "java.math.BigDecimal";
            case BOOLEAN:
                return "Boolean";
            case DATE:
                return "java.time.LocalDate";
            case DATETIME:
                return "java.time.LocalDateTime";
            case TIME:
                return "java.time.LocalTime";
            case BINARY:
                return "byte[]";
            default:
                return "Object";
        }
    }

    /**
     * Retorna o tipo Java simples (sem pacote).
     */
    public String getSimpleJavaType() {
        String javaType = getJavaType();
        int lastDot = javaType.lastIndexOf('.');
        return lastDot > 0 ? javaType.substring(lastDot + 1) : javaType;
    }

    @Override
    public String toString() {
        return String.format("Field{name='%s', columnName='%s', dataType=%s, pk=%s}",
            name, columnName, dataType, primaryKey);
    }
}
