package br.com.gerador.metamodel.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um módulo/menu da aplicação.
 */
public class Module {

    private String id;
    private String name;
    private String description;
    private String icon;
    private int order;
    private List<MenuItem> items = new ArrayList<>();

    public Module() {
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return String.format("Module{id='%s', name='%s', items=%d}", id, name, items.size());
    }
}
