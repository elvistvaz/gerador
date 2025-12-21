package br.com.gerador.metamodel.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma constraint de banco de dados.
 */
public class Constraint {

    private String name;
    private ConstraintType type;
    private String expression;
    private List<String> fields = new ArrayList<>();

    public Constraint() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConstraintType getType() {
        return type;
    }

    public void setType(ConstraintType type) {
        this.type = type;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return String.format("Constraint{name='%s', type=%s}", name, type);
    }
}
