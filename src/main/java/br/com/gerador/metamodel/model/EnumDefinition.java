package br.com.gerador.metamodel.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma definição de enum no meta-modelo.
 */
public class EnumDefinition {

    private String id;
    private String name;
    private String description;
    private List<EnumValue> values = new ArrayList<>();

    public EnumDefinition() {
    }

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

    public List<EnumValue> getValues() {
        return values;
    }

    public void setValues(List<EnumValue> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return String.format("EnumDefinition{id='%s', name='%s', values=%d}", id, name, values.size());
    }
}
