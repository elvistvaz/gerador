package br.com.gerador.metamodel.model;

/**
 * Regras de validação para um campo.
 */
public class Validation {

    private Integer minLength;
    private Integer maxLength;
    private Number min;
    private Number max;
    private String pattern;
    private String custom;

    public Validation() {
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Number getMin() {
        return min;
    }

    public void setMin(Number min) {
        this.min = min;
    }

    public Number getMax() {
        return max;
    }

    public void setMax(Number max) {
        this.max = max;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    @Override
    public String toString() {
        return String.format("Validation{minLength=%s, maxLength=%s, pattern='%s'}", minLength, maxLength, pattern);
    }
}
