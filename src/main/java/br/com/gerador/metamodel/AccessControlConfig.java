package br.com.gerador.metamodel;

import java.util.List;

/**
 * Configuração de controle de acesso definida no metamodel JSON.
 */
public class AccessControlConfig {

    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * Representa um perfil de acesso.
     */
    public static class Role {
        private String id;
        private String name;
        private String description;
        private List<Permission> permissions;

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

        public List<Permission> getPermissions() {
            return permissions;
        }

        public void setPermissions(List<Permission> permissions) {
            this.permissions = permissions;
        }
    }

    /**
     * Representa uma permissão de acesso.
     */
    public static class Permission {
        private String module;
        private String access;

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getAccess() {
            return access;
        }

        public void setAccess(String access) {
            this.access = access;
        }
    }
}
