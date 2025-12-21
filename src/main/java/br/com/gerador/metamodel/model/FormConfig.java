package br.com.gerador.metamodel.model;

/**
 * Configuração de exibição do campo em formulário.
 */
public class FormConfig {

    private boolean visible = true;
    private Integer order;
    private Integer colSpan = 1;
    private String tab;
    private String group;

    public FormConfig() {
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

    public Integer getColSpan() {
        return colSpan;
    }

    public void setColSpan(Integer colSpan) {
        this.colSpan = colSpan;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return String.format("FormConfig{visible=%s, order=%s, tab='%s'}", visible, order, tab);
    }
}
