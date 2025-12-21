package br.com.gerador.metamodel.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Configurações de interface de usuário para um campo.
 */
public class FieldUI {

    private UIComponent component;
    private String placeholder;
    private String mask;
    private GridConfig grid;
    private FormConfig form;
    private SearchConfig search;
    private List<FieldOption> options = new ArrayList<>();

    public FieldUI() {
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public GridConfig getGrid() {
        return grid;
    }

    public void setGrid(GridConfig grid) {
        this.grid = grid;
    }

    public FormConfig getForm() {
        return form;
    }

    public void setForm(FormConfig form) {
        this.form = form;
    }

    public SearchConfig getSearch() {
        return search;
    }

    public void setSearch(SearchConfig search) {
        this.search = search;
    }

    public List<FieldOption> getOptions() {
        return options;
    }

    public void setOptions(List<FieldOption> options) {
        this.options = options;
    }

    public boolean hasOptions() {
        return options != null && !options.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("FieldUI{component=%s}", component);
    }
}
