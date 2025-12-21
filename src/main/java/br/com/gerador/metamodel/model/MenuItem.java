package br.com.gerador.metamodel.model;

/**
 * Representa um item de menu dentro de um módulo.
 */
public class MenuItem {

    private String id;
    private String name;
    private String entityRef;
    private String icon;
    private int order;

    public MenuItem() {
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

    public String getEntityRef() {
        return entityRef;
    }

    public void setEntityRef(String entityRef) {
        this.entityRef = entityRef;
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

    @Override
    public String toString() {
        return String.format("MenuItem{id='%s', name='%s', entityRef='%s'}", id, name, entityRef);
    }
}
