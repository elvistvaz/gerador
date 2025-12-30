package br.com.gerador.generator.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa as configurações de um projeto.
 * Carrega e disponibiliza as configurações do arquivo config.json.
 */
public class ProjectConfig {

    private final JsonObject config;
    private final Path configPath;

    public ProjectConfig(Path configPath) throws IOException {
        this.configPath = configPath;
        String content = Files.readString(configPath);
        this.config = new Gson().fromJson(content, JsonObject.class);
    }

    // ==================== PROJECT ====================

    public String getProjectId() {
        return getString("project.id", "app");
    }

    public String getProjectName() {
        return getString("project.name", "Application");
    }

    public String getProjectDescription() {
        return getString("project.description", "");
    }

    public String getProjectVersion() {
        return getString("project.version", "1.0.0");
    }

    // ==================== OUTPUT ====================

    public String getOutputBaseDir() {
        return getString("output.baseDir", "generated");
    }

    public String getOutputBackendDir() {
        return getString("output.backendDir", "back");
    }

    public String getOutputFrontendDir() {
        return getString("output.frontendDir", "front");
    }

    public boolean isCleanBeforeGenerate() {
        return getBoolean("output.cleanBeforeGenerate", false);
    }

    // ==================== BACKEND ====================

    public String getBackendLanguage() {
        return getString("backend.language", "java");
    }

    public String getBackendFramework() {
        return getString("backend.framework", "spring-boot");
    }

    public String getJavaVersion() {
        return getString("backend.javaVersion", "21");
    }

    public String getSpringBootVersion() {
        return getString("backend.springBootVersion", "3.2.0");
    }

    public String getBasePackage() {
        return getString("backend.basePackage", "br.com.app");
    }

    public int getBackendPort() {
        return getInt("backend.port", 8080);
    }

    public String getContextPath() {
        return getString("backend.contextPath", "/api");
    }

    public boolean isSwaggerEnabled() {
        return getBoolean("backend.features.swagger", true);
    }

    public boolean isActuatorEnabled() {
        return getBoolean("backend.features.actuator", true);
    }

    public boolean isSecurityEnabled() {
        return getBoolean("backend.features.security", true);
    }

    public boolean isAuditEnabled() {
        return getBoolean("backend.features.audit", true);
    }

    /**
     * Retorna o tipo de geração (spring-boot, laravel, etc).
     */
    public String getGenerationType() {
        return getString("generationType", getBackendFramework());
    }

    /**
     * Retorna um mapa com todas as features do backend.
     */
    public java.util.Map<String, Boolean> getBackendFeatures() {
        java.util.Map<String, Boolean> features = new java.util.HashMap<>();
        features.put("swagger", isSwaggerEnabled());
        features.put("actuator", isActuatorEnabled());
        features.put("security", isSecurityEnabled());
        features.put("audit", isAuditEnabled());
        return features;
    }

    // ==================== FRONTEND ====================

    public String getFrontendFramework() {
        return getString("frontend.framework", "angular");
    }

    public String getAngularVersion() {
        return getString("frontend.angularVersion", "17");
    }

    public int getFrontendPort() {
        return getInt("frontend.port", 4200);
    }

    public String getFrontendTitle() {
        return getString("frontend.title", getProjectName());
    }

    public String getThemePrimaryColor() {
        return getString("frontend.theme.primaryColor", "#667eea");
    }

    public String getThemeSecondaryColor() {
        return getString("frontend.theme.secondaryColor", "#764ba2");
    }

    public String getThemeGradient() {
        return getString("frontend.theme.gradient", "linear-gradient(135deg, #667eea 0%, #764ba2 100%)");
    }

    // ==================== DATABASE ====================

    public String getDatabaseType() {
        return getString("database.type", "sqlserver");
    }

    public String getDatabaseHost() {
        return getString("database.host", "localhost");
    }

    public int getDatabasePort() {
        return getInt("database.port", 1433);
    }

    public String getDatabaseName() {
        return getString("database.name", getProjectName());
    }

    public String getDatabaseSchema() {
        return getString("database.schema", "dbo");
    }

    public String getDatabaseDriver() {
        return getString("database.driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }

    public String getDatabaseConnectionString() {
        return getString("database.connectionString",
            "jdbc:sqlserver://localhost:1433;databaseName=" + getDatabaseName() + ";encrypt=true;trustServerCertificate=true");
    }

    public String getDevDatabaseType() {
        return getString("database.development.type", "h2");
    }

    public String getDevDatabaseName() {
        return getString("database.development.name", getProjectId() + "db");
    }

    public boolean isDevDatabaseConsoleEnabled() {
        return getBoolean("database.development.console", true);
    }

    public int getDevDatabaseConsolePort() {
        return getInt("database.development.consolePort", 8082);
    }

    // ==================== SECURITY ====================

    public String getJwtSecret() {
        return getString("security.jwt.secret", "default-secret-key-change-in-production-minimum-256-bits");
    }

    public long getJwtExpiration() {
        return getLong("security.jwt.expiration", 86400000L);
    }

    public long getJwtRefreshExpiration() {
        return getLong("security.jwt.refreshExpiration", 604800000L);
    }

    public List<String> getCorsAllowedOrigins() {
        return getStringList("security.cors.allowedOrigins");
    }

    public List<String> getCorsAllowedMethods() {
        return getStringList("security.cors.allowedMethods");
    }

    public boolean isCorsAllowCredentials() {
        return getBoolean("security.cors.allowCredentials", true);
    }

    public String getDefaultAdminUsername() {
        return getString("security.defaultAdmin.username", "admin");
    }

    public String getDefaultAdminPassword() {
        return getString("security.defaultAdmin.password", "admin123");
    }

    public String getDefaultAdminName() {
        return getString("security.defaultAdmin.name", "Administrador");
    }

    public String getDefaultAdminEmail() {
        return getString("security.defaultAdmin.email", "admin@example.com");
    }

    // ==================== LOGGING ====================

    public String getLoggingLevel() {
        return getString("logging.level", "INFO");
    }

    public String getLoggingFile() {
        return getString("logging.file", "logs/" + getProjectId() + ".log");
    }

    public String getLoggingMaxFileSize() {
        return getString("logging.maxFileSize", "10MB");
    }

    public int getLoggingMaxHistory() {
        return getInt("logging.maxHistory", 30);
    }

    // ==================== FEATURES ====================

    public int getDefaultPageSize() {
        return getInt("features.pagination.defaultPageSize", 20);
    }

    public int getMaxPageSize() {
        return getInt("features.pagination.maxPageSize", 100);
    }

    public boolean isUploadEnabled() {
        return getBoolean("features.upload.enabled", false);
    }

    public String getUploadMaxFileSize() {
        return getString("features.upload.maxFileSize", "10MB");
    }

    public List<String> getUploadAllowedTypes() {
        return getStringList("features.upload.allowedTypes");
    }

    public boolean isEmailEnabled() {
        return getBoolean("features.email.enabled", false);
    }

    public String getEmailHost() {
        return getString("features.email.host", "");
    }

    public int getEmailPort() {
        return getInt("features.email.port", 587);
    }

    public String getEmailUsername() {
        return getString("features.email.username", "");
    }

    public String getEmailPassword() {
        return getString("features.email.password", "");
    }

    // ==================== HELPER METHODS ====================

    public String getString(String path, String defaultValue) {
        try {
            String[] parts = path.split("\\.");
            JsonObject current = config;
            for (int i = 0; i < parts.length - 1; i++) {
                if (current.has(parts[i]) && current.get(parts[i]).isJsonObject()) {
                    current = current.getAsJsonObject(parts[i]);
                } else {
                    return defaultValue;
                }
            }
            String lastPart = parts[parts.length - 1];
            if (current.has(lastPart) && !current.get(lastPart).isJsonNull()) {
                return current.get(lastPart).getAsString();
            }
            return defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private int getInt(String path, int defaultValue) {
        try {
            String[] parts = path.split("\\.");
            JsonObject current = config;
            for (int i = 0; i < parts.length - 1; i++) {
                if (current.has(parts[i]) && current.get(parts[i]).isJsonObject()) {
                    current = current.getAsJsonObject(parts[i]);
                } else {
                    return defaultValue;
                }
            }
            String lastPart = parts[parts.length - 1];
            if (current.has(lastPart) && !current.get(lastPart).isJsonNull()) {
                return current.get(lastPart).getAsInt();
            }
            return defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private long getLong(String path, long defaultValue) {
        try {
            String[] parts = path.split("\\.");
            JsonObject current = config;
            for (int i = 0; i < parts.length - 1; i++) {
                if (current.has(parts[i]) && current.get(parts[i]).isJsonObject()) {
                    current = current.getAsJsonObject(parts[i]);
                } else {
                    return defaultValue;
                }
            }
            String lastPart = parts[parts.length - 1];
            if (current.has(lastPart) && !current.get(lastPart).isJsonNull()) {
                return current.get(lastPart).getAsLong();
            }
            return defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private boolean getBoolean(String path, boolean defaultValue) {
        try {
            String[] parts = path.split("\\.");
            JsonObject current = config;
            for (int i = 0; i < parts.length - 1; i++) {
                if (current.has(parts[i]) && current.get(parts[i]).isJsonObject()) {
                    current = current.getAsJsonObject(parts[i]);
                } else {
                    return defaultValue;
                }
            }
            String lastPart = parts[parts.length - 1];
            if (current.has(lastPart) && !current.get(lastPart).isJsonNull()) {
                return current.get(lastPart).getAsBoolean();
            }
            return defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private List<String> getStringList(String path) {
        List<String> result = new ArrayList<>();
        try {
            String[] parts = path.split("\\.");
            JsonObject current = config;
            for (int i = 0; i < parts.length - 1; i++) {
                if (current.has(parts[i]) && current.get(parts[i]).isJsonObject()) {
                    current = current.getAsJsonObject(parts[i]);
                } else {
                    return result;
                }
            }
            String lastPart = parts[parts.length - 1];
            if (current.has(lastPart) && current.get(lastPart).isJsonArray()) {
                JsonArray array = current.getAsJsonArray(lastPart);
                for (int i = 0; i < array.size(); i++) {
                    result.add(array.get(i).getAsString());
                }
            }
        } catch (Exception e) {
            // Ignore
        }
        return result;
    }

    /**
     * Carrega config.json de uma pasta de projeto.
     */
    public static ProjectConfig load(Path projectDir) throws IOException {
        Path configPath = projectDir.resolve("config.json");
        if (!Files.exists(configPath)) {
            throw new IOException("Arquivo config.json não encontrado em: " + projectDir);
        }
        return new ProjectConfig(configPath);
    }

    /**
     * Carrega um arquivo de configuração específico.
     */
    public static ProjectConfig load(Path projectDir, String configFileName) throws IOException {
        Path configPath = projectDir.resolve(configFileName);
        if (!Files.exists(configPath)) {
            throw new IOException("Arquivo " + configFileName + " não encontrado em: " + projectDir);
        }
        return new ProjectConfig(configPath);
    }

    /**
     * Verifica se o arquivo config.json existe.
     */
    public static boolean exists(Path projectDir) {
        return Files.exists(projectDir.resolve("config.json"));
    }

    /**
     * Retorna o objeto JSON de configuração completo.
     */
    public JsonObject getConfig() {
        return this.config;
    }

    @Override
    public String toString() {
        return "ProjectConfig{" +
                "projectId='" + getProjectId() + '\'' +
                ", projectName='" + getProjectName() + '\'' +
                ", basePackage='" + getBasePackage() + '\'' +
                ", backendPort=" + getBackendPort() +
                ", frontendPort=" + getFrontendPort() +
                ", databaseType='" + getDatabaseType() + '\'' +
                '}';
    }
}
