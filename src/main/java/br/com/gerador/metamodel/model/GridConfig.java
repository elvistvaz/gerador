package br.com.gerador.metamodel.model;

/**
 * Configuração de exibição do campo em grid/tabela.
 */
public class GridConfig {

    private boolean visible = true;
    private Integer order;
    private Integer width;
    private boolean sortable = false;

    public GridConfig() {
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    @Override
    public String toString() {
        return String.format("GridConfig{visible=%s, order=%s, width=%s, sortable=%s}", visible, order, width, sortable);
    }
}
