package br.com.gerador.metamodel.model;

/**
 * Tipos de banco de dados suportados.
 */
public enum DatabaseType {
    SQLSERVER("SQL Server", "com.microsoft.sqlserver.jdbc.SQLServerDriver"),
    MYSQL("MySQL", "com.mysql.cj.jdbc.Driver"),
    POSTGRESQL("PostgreSQL", "org.postgresql.Driver"),
    ORACLE("Oracle", "oracle.jdbc.OracleDriver"),
    H2("H2 Database", "org.h2.Driver");

    private final String displayName;
    private final String driverClass;

    DatabaseType(String displayName, String driverClass) {
        this.displayName = displayName;
        this.driverClass = driverClass;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDriverClass() {
        return driverClass;
    }
}
