package br.com.gerador.metamodel.model;

/**
 * Representa a ordenação padrão para uma entidade.
 * Define qual campo deve ser usado para ordenação nas listagens.
 */
public class DefaultSort {

    private String field;
    private String direction = "asc";

    public DefaultSort() {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isAscending() {
        return "asc".equalsIgnoreCase(direction);
    }

    @Override
    public String toString() {
        return String.format("DefaultSort{field='%s', direction='%s'}", field, direction);
    }
}
