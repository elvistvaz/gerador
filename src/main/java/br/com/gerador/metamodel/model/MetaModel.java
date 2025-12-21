package br.com.gerador.metamodel.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Classe principal que representa o meta-modelo completo.
 * Contém todas as informações necessárias para geração de código.
 */
public class MetaModel {

    private Metadata metadata;
    private List<Module> modules = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private List<EnumDefinition> enums = new ArrayList<>();

    // Cache para acesso rápido
    private transient Map<String, Entity> entityById;
    private transient Map<String, Entity> entityByName;
    private transient Map<String, Module> moduleById;

    public MetaModel() {
    }

    // Getters e Setters
    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
        this.moduleById = null; // Invalida cache
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
        this.entityById = null; // Invalida cache
        this.entityByName = null;
    }

    public List<EnumDefinition> getEnums() {
        return enums;
    }

    public void setEnums(List<EnumDefinition> enums) {
        this.enums = enums;
    }

    // Métodos de conveniência

    /**
     * Busca uma entidade pelo ID.
     */
    public Optional<Entity> findEntityById(String id) {
        if (entityById == null) {
            buildEntityCache();
        }
        return Optional.ofNullable(entityById.get(id));
    }

    /**
     * Busca uma entidade pelo nome.
     */
    public Optional<Entity> findEntityByName(String name) {
        if (entityByName == null) {
            buildEntityCache();
        }
        return Optional.ofNullable(entityByName.get(name));
    }

    /**
     * Busca um módulo pelo ID.
     */
    public Optional<Module> findModuleById(String id) {
        if (moduleById == null) {
            buildModuleCache();
        }
        return Optional.ofNullable(moduleById.get(id));
    }

    /**
     * Retorna todas as entidades do tipo especificado.
     */
    public List<Entity> getEntitiesByType(EntityType type) {
        List<Entity> result = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getType() == type) {
                result.add(entity);
            }
        }
        return result;
    }

    /**
     * Retorna todas as entidades filhas de uma entidade pai.
     */
    public List<Entity> getChildEntities(String parentEntityId) {
        List<Entity> result = new ArrayList<>();
        for (Entity entity : entities) {
            if (parentEntityId.equals(entity.getParentEntity())) {
                result.add(entity);
            }
        }
        return result;
    }

    /**
     * Retorna todas as entidades principais (MAIN).
     */
    public List<Entity> getMainEntities() {
        return getEntitiesByType(EntityType.MAIN);
    }

    /**
     * Retorna todas as entidades de lookup.
     */
    public List<Entity> getLookupEntities() {
        return getEntitiesByType(EntityType.LOOKUP);
    }

    private void buildEntityCache() {
        entityById = new HashMap<>();
        entityByName = new HashMap<>();
        for (Entity entity : entities) {
            entityById.put(entity.getId(), entity);
            entityByName.put(entity.getName(), entity);
        }
    }

    private void buildModuleCache() {
        moduleById = new HashMap<>();
        for (Module module : modules) {
            moduleById.put(module.getId(), module);
        }
    }

    /**
     * Valida o modelo e retorna lista de erros encontrados.
     */
    public List<String> validate() {
        List<String> errors = new ArrayList<>();

        if (metadata == null) {
            errors.add("Metadata é obrigatório");
        } else {
            if (metadata.getName() == null || metadata.getName().isEmpty()) {
                errors.add("Metadata.name é obrigatório");
            }
        }

        // Valida referências
        for (Entity entity : entities) {
            for (Field field : entity.getFields()) {
                if (field.getReference() != null) {
                    String refEntity = field.getReference().getEntity();
                    if (findEntityByName(refEntity).isEmpty()) {
                        errors.add(String.format(
                            "Entidade '%s', campo '%s': referência para entidade inexistente '%s'",
                            entity.getName(), field.getName(), refEntity
                        ));
                    }
                }
            }

            // Valida parentEntity
            if (entity.getParentEntity() != null && !entity.getParentEntity().isEmpty()) {
                if (findEntityByName(entity.getParentEntity()).isEmpty()) {
                    errors.add(String.format(
                        "Entidade '%s': parentEntity '%s' não existe",
                        entity.getName(), entity.getParentEntity()
                    ));
                }
            }
        }

        return errors;
    }

    @Override
    public String toString() {
        return String.format("MetaModel{name='%s', entities=%d, modules=%d}",
            metadata != null ? metadata.getName() : "null",
            entities.size(),
            modules.size()
        );
    }
}
