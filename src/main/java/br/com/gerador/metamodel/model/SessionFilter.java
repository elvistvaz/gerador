package br.com.gerador.metamodel.model;

/**
 * Representa um filtro de sessão para uma entidade.
 * Define qual campo da entidade deve ser filtrado pelo contexto de sessão.
 */
public class SessionFilter {

    private String field;
    private String field2;

    public SessionFilter() {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public boolean hasField2() {
        return field2 != null && !field2.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("SessionFilter{field='%s', field2='%s'}", field, field2);
    }
}
