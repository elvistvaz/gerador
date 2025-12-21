package br.com.gerador.metamodel.model;

/**
 * Representa uma opção para campos SELECT/RADIO.
 */
public class FieldOption {

    private Object value;
    private String label;

    public FieldOption() {
    }

    public FieldOption(Object value, String label) {
        this.value = value;
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format("FieldOption{value=%s, label='%s'}", value, label);
    }
}
