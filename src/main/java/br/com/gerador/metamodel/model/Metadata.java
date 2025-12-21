package br.com.gerador.metamodel.model;

import br.com.gerador.metamodel.AccessControlConfig;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Metadados do projeto/modelo.
 */
public class Metadata {

    private String name;
    private String version;
    private String description;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private DatabaseConfig database;
    private List<SessionContext> sessionContext = new ArrayList<>();
    private AccessControlConfig accessControl;

    public Metadata() {
    }

    // Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public DatabaseConfig getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseConfig database) {
        this.database = database;
    }

    public List<SessionContext> getSessionContext() {
        return sessionContext;
    }

    public void setSessionContext(List<SessionContext> sessionContext) {
        this.sessionContext = sessionContext;
    }

    public AccessControlConfig getAccessControl() {
        return accessControl;
    }

    public void setAccessControl(AccessControlConfig accessControl) {
        this.accessControl = accessControl;
    }

    /**
     * Verifica se há contexto de sessão configurado.
     */
    public boolean hasSessionContext() {
        return sessionContext != null && !sessionContext.isEmpty();
    }

    /**
     * Verifica se há controle de acesso configurado.
     */
    public boolean hasAccessControl() {
        return accessControl != null && accessControl.getRoles() != null && !accessControl.getRoles().isEmpty();
    }

    @Override
    public String toString() {
        return String.format("Metadata{name='%s', version='%s'}", name, version);
    }
}
