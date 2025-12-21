package br.com.gerador.metamodel.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um índice de banco de dados.
 */
public class Index {

    private String name;
    private List<String> fields = new ArrayList<>();
    private boolean unique = false;

    public Index() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    @Override
    public String toString() {
        return String.format("Index{name='%s', fields=%s, unique=%s}", name, fields, unique);
    }
}
