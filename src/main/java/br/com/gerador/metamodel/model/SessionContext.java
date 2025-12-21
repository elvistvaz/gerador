package br.com.gerador.metamodel.model;

/**
 * Representa um item de contexto de sessão.
 * Define entidades que o usuário deve selecionar após login e que ficam na sessão.
 */
public class SessionContext {

    private String entity;
    private String field;
    private String displayField;
    private String label;
    private boolean required = true;
    private String inputType = "select"; // "select" ou "radio"

    public SessionContext() {
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDisplayField() {
        return displayField;
    }

    public void setDisplayField(String displayField) {
        this.displayField = displayField;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public boolean isRadio() {
        return "radio".equalsIgnoreCase(inputType);
    }

    @Override
    public String toString() {
        return String.format("SessionContext{entity='%s', field='%s', displayField='%s'}",
            entity, field, displayField);
    }
}
