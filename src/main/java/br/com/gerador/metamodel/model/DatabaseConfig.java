package br.com.gerador.metamodel.model;

/**
 * Configuração do banco de dados.
 */
public class DatabaseConfig {

    private DatabaseType type;
    private String schema;

    public DatabaseConfig() {
    }

    public DatabaseType getType() {
        return type;
    }

    public void setType(DatabaseType type) {
        this.type = type;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public String toString() {
        return String.format("DatabaseConfig{type=%s, schema='%s'}", type, schema);
    }
}
