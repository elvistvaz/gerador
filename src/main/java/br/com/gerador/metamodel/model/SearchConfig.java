package br.com.gerador.metamodel.model;

/**
 * Configuração de pesquisa para um campo.
 */
public class SearchConfig {

    private boolean enabled = false;
    private SearchOperator operator;

    public SearchConfig() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public SearchOperator getOperator() {
        return operator;
    }

    public void setOperator(SearchOperator operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return String.format("SearchConfig{enabled=%s, operator=%s}", enabled, operator);
    }
}
