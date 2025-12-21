package br.com.gerador.metamodel.model;

/**
 * Representa um valor de enum.
 */
public class EnumValue {

    private String code;
    private String label;
    private String description;

    public EnumValue() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Override
    public String toString() {
        return String.format("EnumValue{code='%s', label='%s'}", code, label);
    }
}
