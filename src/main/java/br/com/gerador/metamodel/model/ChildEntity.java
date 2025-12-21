package br.com.gerador.metamodel.model;

/**
 * Representa uma configuração de entidade filha para navegação mestre-detalhe.
 * Quando uma entidade "pai" tem childEntities, a tela de listagem do pai
 * terá um botão para navegar para a listagem da entidade filha filtrada.
 */
public class ChildEntity {

    private String entity;      // Nome da entidade filha
    private String foreignKey;  // Nome do campo FK na entidade filha que referencia o pai
    private String label;       // Label do botão de navegação
    private String icon;        // Ícone do botão (opcional)

    public ChildEntity() {
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Retorna o nome da entidade em camelCase (primeira letra minúscula).
     */
    public String getEntityCamelCase() {
        if (entity == null || entity.isEmpty()) {
            return entity;
        }
        return entity.substring(0, 1).toLowerCase() + entity.substring(1);
    }

    /**
     * Retorna o nome da rota (kebab-case).
     */
    public String getEntityRoute() {
        if (entity == null || entity.isEmpty()) {
            return entity;
        }
        // Converte PascalCase para kebab-case
        return entity.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase();
    }

    @Override
    public String toString() {
        return String.format("ChildEntity{entity='%s', foreignKey='%s', label='%s'}",
            entity, foreignKey, label);
    }
}
