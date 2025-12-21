package br.com.gerador.metamodel.model;

/**
 * Representa uma referência (foreign key) para outra entidade.
 */
public class Reference {

    private String entity;
    private String field;
    private ReferentialAction onDelete = ReferentialAction.NO_ACTION;
    private ReferentialAction onUpdate = ReferentialAction.NO_ACTION;

    public Reference() {
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public ReferentialAction getOnDelete() {
        return onDelete;
    }

    public void setOnDelete(ReferentialAction onDelete) {
        this.onDelete = onDelete;
    }

    public ReferentialAction getOnUpdate() {
        return onUpdate;
    }

    public void setOnUpdate(ReferentialAction onUpdate) {
        this.onUpdate = onUpdate;
    }

    @Override
    public String toString() {
        return String.format("Reference{entity='%s', field='%s'}", entity, field);
    }
}
