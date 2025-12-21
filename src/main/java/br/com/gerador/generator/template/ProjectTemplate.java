package br.com.gerador.generator.template;

import br.com.gerador.generator.config.ProjectConfig;
import br.com.gerador.metamodel.model.DatabaseConfig;

/**
 * Template para arquivos de projeto Spring Boot.
 */
public class ProjectTemplate {

    private final String basePackage;
    private final String projectName;
    private final String projectDescription;
    private ProjectConfig projectConfig;

    public ProjectTemplate(String basePackage, String projectName, String projectDescription) {
        this.basePackage = basePackage;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
    }

    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }

    public ProjectConfig getProjectConfig() {
        return projectConfig;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getBasePackage() {
        return basePackage;
    }

    /**
     * Gera o pom.xml do projeto.
     */
    public String generatePom() {
        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n");
        sb.append("         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
        sb.append("         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\">\n");
        sb.append("    <modelVersion>4.0.0</modelVersion>\n\n");

        sb.append("    <parent>\n");
        sb.append("        <groupId>org.springframework.boot</groupId>\n");
        sb.append("        <artifactId>spring-boot-starter-parent</artifactId>\n");
        sb.append("        <version>3.2.0</version>\n");
        sb.append("        <relativePath/>\n");
        sb.append("    </parent>\n\n");

        String groupId = basePackage.substring(0, basePackage.lastIndexOf('.'));
        String artifactId = basePackage.substring(basePackage.lastIndexOf('.') + 1);

        sb.append("    <groupId>").append(groupId).append("</groupId>\n");
        sb.append("    <artifactId>").append(artifactId).append("</artifactId>\n");
        sb.append("    <version>1.0.0-SNAPSHOT</version>\n");
        sb.append("    <name>").append(projectName).append("</name>\n");
        sb.append("    <description>").append(projectDescription).append("</description>\n\n");

        sb.append("    <properties>\n");
        sb.append("        <java.version>17</java.version>\n");
        sb.append("    </properties>\n\n");

        sb.append("    <dependencies>\n");
        sb.append("        <!-- Spring Boot Starters -->\n");
        sb.append("        <dependency>\n");
        sb.append("            <groupId>org.springframework.boot</groupId>\n");
        sb.append("            <artifactId>spring-boot-starter-web</artifactId>\n");
        sb.append("        </dependency>\n\n");

        sb.append("        <dependency>\n");
        sb.append("            <groupId>org.springframework.boot</groupId>\n");
        sb.append("            <artifactId>spring-boot-starter-data-jpa</artifactId>\n");
        sb.append("        </dependency>\n\n");

        sb.append("        <dependency>\n");
        sb.append("            <groupId>org.springframework.boot</groupId>\n");
        sb.append("            <artifactId>spring-boot-starter-validation</artifactId>\n");
        sb.append("        </dependency>\n\n");

        sb.append("        <dependency>\n");
        sb.append("            <groupId>org.springframework.boot</groupId>\n");
        sb.append("            <artifactId>spring-boot-starter-security</artifactId>\n");
        sb.append("        </dependency>\n\n");

        sb.append("        <!-- JWT -->\n");
        sb.append("        <dependency>\n");
        sb.append("            <groupId>io.jsonwebtoken</groupId>\n");
        sb.append("            <artifactId>jjwt-api</artifactId>\n");
        sb.append("            <version>0.12.3</version>\n");
        sb.append("        </dependency>\n\n");

        sb.append("        <dependency>\n");
        sb.append("            <groupId>io.jsonwebtoken</groupId>\n");
        sb.append("            <artifactId>jjwt-impl</artifactId>\n");
        sb.append("            <version>0.12.3</version>\n");
        sb.append("            <scope>runtime</scope>\n");
        sb.append("        </dependency>\n\n");

        sb.append("        <dependency>\n");
        sb.append("            <groupId>io.jsonwebtoken</groupId>\n");
        sb.append("            <artifactId>jjwt-jackson</artifactId>\n");
        sb.append("            <version>0.12.3</version>\n");
        sb.append("            <scope>runtime</scope>\n");
        sb.append("        </dependency>\n\n");

        sb.append("        <!-- SQL Server Driver -->\n");
        sb.append("        <dependency>\n");
        sb.append("            <groupId>com.microsoft.sqlserver</groupId>\n");
        sb.append("            <artifactId>mssql-jdbc</artifactId>\n");
        sb.append("            <scope>runtime</scope>\n");
        sb.append("        </dependency>\n\n");

        sb.append("        <!-- H2 para desenvolvimento -->\n");
        sb.append("        <dependency>\n");
        sb.append("            <groupId>com.h2database</groupId>\n");
        sb.append("            <artifactId>h2</artifactId>\n");
        sb.append("            <scope>runtime</scope>\n");
        sb.append("        </dependency>\n\n");

        sb.append("        <!-- Lombok (opcional) -->\n");
        sb.append("        <dependency>\n");
        sb.append("            <groupId>org.projectlombok</groupId>\n");
        sb.append("            <artifactId>lombok</artifactId>\n");
        sb.append("            <optional>true</optional>\n");
        sb.append("        </dependency>\n\n");

        sb.append("        <!-- OpenAPI/Swagger -->\n");
        sb.append("        <dependency>\n");
        sb.append("            <groupId>org.springdoc</groupId>\n");
        sb.append("            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>\n");
        sb.append("            <version>2.3.0</version>\n");
        sb.append("        </dependency>\n\n");

        sb.append("        <!-- Testes -->\n");
        sb.append("        <dependency>\n");
        sb.append("            <groupId>org.springframework.boot</groupId>\n");
        sb.append("            <artifactId>spring-boot-starter-test</artifactId>\n");
        sb.append("            <scope>test</scope>\n");
        sb.append("        </dependency>\n");
        sb.append("    </dependencies>\n\n");

        String appName = toPascalCase(projectName.replace(" ", ""));

        sb.append("    <build>\n");
        sb.append("        <plugins>\n");
        sb.append("            <plugin>\n");
        sb.append("                <groupId>org.springframework.boot</groupId>\n");
        sb.append("                <artifactId>spring-boot-maven-plugin</artifactId>\n");
        sb.append("                <configuration>\n");
        sb.append("                    <mainClass>").append(basePackage).append(".").append(appName).append("Application</mainClass>\n");
        sb.append("                    <excludes>\n");
        sb.append("                        <exclude>\n");
        sb.append("                            <groupId>org.projectlombok</groupId>\n");
        sb.append("                            <artifactId>lombok</artifactId>\n");
        sb.append("                        </exclude>\n");
        sb.append("                    </excludes>\n");
        sb.append("                </configuration>\n");
        sb.append("            </plugin>\n");
        sb.append("        </plugins>\n");
        sb.append("    </build>\n\n");

        sb.append("</project>\n");

        return sb.toString();
    }

    /**
     * Gera o application.properties (configuração base).
     */
    public String generateApplicationProperties(DatabaseConfig dbConfig) {
        StringBuilder sb = new StringBuilder();

        sb.append("# ===============================\n");
        sb.append("# ").append(projectName).append(" - Configurações\n");
        sb.append("# ===============================\n\n");

        sb.append("# Profile ativo (dev = H2 em memória, prod = SQL Server)\n");
        sb.append("spring.profiles.active=dev\n\n");

        sb.append("# Servidor\n");
        int port = projectConfig != null ? projectConfig.getBackendPort() : 8080;
        sb.append("server.port=").append(port).append("\n\n");

        sb.append("# OpenAPI/Swagger\n");
        sb.append("springdoc.api-docs.path=/api-docs\n");
        sb.append("springdoc.swagger-ui.path=/swagger-ui.html\n\n");

        sb.append("# Paginação\n");
        int defaultPageSize = projectConfig != null ? projectConfig.getDefaultPageSize() : 20;
        int maxPageSize = projectConfig != null ? projectConfig.getMaxPageSize() : 100;
        sb.append("spring.data.web.pageable.default-page-size=").append(defaultPageSize).append("\n");
        sb.append("spring.data.web.pageable.max-page-size=").append(maxPageSize).append("\n");

        return sb.toString();
    }

    /**
     * Gera o application-dev.properties (H2 em memória).
     */
    public String generateApplicationPropertiesDev() {
        StringBuilder sb = new StringBuilder();

        sb.append("# ===============================\n");
        sb.append("# ").append(projectName).append(" - Desenvolvimento (H2)\n");
        sb.append("# ===============================\n\n");

        sb.append("# Banco de Dados H2 em memória\n");
        sb.append("# INIT cria schema dbo, MODE=MSSQLServer para compatibilidade, CASE_INSENSITIVE_IDENTIFIERS para nomes de tabelas\n");
        sb.append("spring.datasource.url=jdbc:h2:mem:").append(projectName.toLowerCase()).append(";DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=MSSQLServer;CASE_INSENSITIVE_IDENTIFIERS=TRUE;INIT=CREATE SCHEMA IF NOT EXISTS dbo\\\\;SET SCHEMA dbo\n");
        sb.append("spring.datasource.username=sa\n");
        sb.append("spring.datasource.password=\n");
        sb.append("spring.datasource.driver-class-name=org.h2.Driver\n\n");

        sb.append("# Console H2 habilitado\n");
        sb.append("spring.h2.console.enabled=true\n");
        sb.append("spring.h2.console.path=/h2-console\n\n");

        sb.append("# JPA/Hibernate\n");
        sb.append("spring.jpa.hibernate.ddl-auto=create-drop\n");
        sb.append("spring.jpa.show-sql=true\n");
        sb.append("spring.jpa.properties.hibernate.format_sql=true\n");
        sb.append("spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect\n\n");

        sb.append("# Inicialização de dados\n");
        sb.append("# data.sql é executado APÓS o Hibernate criar as tabelas\n");
        sb.append("spring.jpa.defer-datasource-initialization=true\n");
        sb.append("spring.sql.init.mode=always\n");
        sb.append("spring.sql.init.encoding=UTF-8\n");

        return sb.toString();
    }

    /**
     * Gera o application-prod.properties (SQL Server).
     */
    public String generateApplicationPropertiesProd() {
        StringBuilder sb = new StringBuilder();

        sb.append("# ===============================\n");
        sb.append("# ").append(projectName).append(" - Produção (SQL Server)\n");
        sb.append("# ===============================\n\n");

        sb.append("# Banco de Dados SQL Server\n");
        sb.append("spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=").append(projectName).append(";encrypt=false;trustServerCertificate=true\n");
        sb.append("spring.datasource.username=sa\n");
        sb.append("spring.datasource.password=SuaSenhaAqui\n");
        sb.append("spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver\n\n");

        sb.append("# JPA/Hibernate\n");
        sb.append("spring.jpa.hibernate.ddl-auto=validate\n");
        sb.append("spring.jpa.show-sql=false\n");
        sb.append("spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect\n");

        return sb.toString();
    }

    /**
     * Gera a classe Application principal.
     */
    public String generateApplication() {
        StringBuilder sb = new StringBuilder();
        String appName = toPascalCase(projectName.replace(" ", ""));

        sb.append("package ").append(basePackage).append(";\n\n");

        sb.append("import org.springframework.boot.SpringApplication;\n");
        sb.append("import org.springframework.boot.autoconfigure.SpringBootApplication;\n\n");

        sb.append("/**\n");
        sb.append(" * Classe principal da aplicação ").append(projectName).append(".\n");
        sb.append(" */\n");
        sb.append("@SpringBootApplication\n");
        sb.append("public class ").append(appName).append("Application {\n\n");

        sb.append("    public static void main(String[] args) {\n");
        sb.append("        SpringApplication.run(").append(appName).append("Application.class, args);\n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o GlobalExceptionHandler.
     */
    public String generateExceptionHandler() {
        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(basePackage).append(".config;\n\n");

        sb.append("import org.springframework.http.HttpStatus;\n");
        sb.append("import org.springframework.http.ResponseEntity;\n");
        sb.append("import org.springframework.validation.FieldError;\n");
        sb.append("import org.springframework.web.bind.MethodArgumentNotValidException;\n");
        sb.append("import org.springframework.web.bind.annotation.ExceptionHandler;\n");
        sb.append("import org.springframework.web.bind.annotation.RestControllerAdvice;\n\n");

        sb.append("import java.time.LocalDateTime;\n");
        sb.append("import java.util.HashMap;\n");
        sb.append("import java.util.Map;\n\n");

        sb.append("/**\n");
        sb.append(" * Handler global de exceções.\n");
        sb.append(" */\n");
        sb.append("@RestControllerAdvice\n");
        sb.append("public class GlobalExceptionHandler {\n\n");

        // Validation Exception
        sb.append("    @ExceptionHandler(MethodArgumentNotValidException.class)\n");
        sb.append("    public ResponseEntity<Map<String, Object>> handleValidationExceptions(\n");
        sb.append("            MethodArgumentNotValidException ex) {\n");
        sb.append("        Map<String, Object> response = new HashMap<>();\n");
        sb.append("        Map<String, String> errors = new HashMap<>();\n\n");
        sb.append("        ex.getBindingResult().getAllErrors().forEach(error -> {\n");
        sb.append("            String fieldName = ((FieldError) error).getField();\n");
        sb.append("            String errorMessage = error.getDefaultMessage();\n");
        sb.append("            errors.put(fieldName, errorMessage);\n");
        sb.append("        });\n\n");
        sb.append("        response.put(\"timestamp\", LocalDateTime.now());\n");
        sb.append("        response.put(\"status\", HttpStatus.BAD_REQUEST.value());\n");
        sb.append("        response.put(\"error\", \"Validation Error\");\n");
        sb.append("        response.put(\"errors\", errors);\n\n");
        sb.append("        return ResponseEntity.badRequest().body(response);\n");
        sb.append("    }\n\n");

        // Generic Exception
        sb.append("    @ExceptionHandler(Exception.class)\n");
        sb.append("    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {\n");
        sb.append("        Map<String, Object> response = new HashMap<>();\n");
        sb.append("        response.put(\"timestamp\", LocalDateTime.now());\n");
        sb.append("        response.put(\"status\", HttpStatus.INTERNAL_SERVER_ERROR.value());\n");
        sb.append("        response.put(\"error\", \"Internal Server Error\");\n");
        sb.append("        response.put(\"message\", ex.getMessage());\n\n");
        sb.append("        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);\n");
        sb.append("    }\n\n");

        // Resource Not Found
        sb.append("    @ExceptionHandler(ResourceNotFoundException.class)\n");
        sb.append("    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {\n");
        sb.append("        Map<String, Object> response = new HashMap<>();\n");
        sb.append("        response.put(\"timestamp\", LocalDateTime.now());\n");
        sb.append("        response.put(\"status\", HttpStatus.NOT_FOUND.value());\n");
        sb.append("        response.put(\"error\", \"Not Found\");\n");
        sb.append("        response.put(\"message\", ex.getMessage());\n\n");
        sb.append("        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);\n");
        sb.append("    }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera a exceção ResourceNotFoundException.
     */
    public String generateResourceNotFoundException() {
        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(basePackage).append(".config;\n\n");

        sb.append("/**\n");
        sb.append(" * Exceção para recurso não encontrado.\n");
        sb.append(" */\n");
        sb.append("public class ResourceNotFoundException extends RuntimeException {\n\n");

        sb.append("    public ResourceNotFoundException(String message) {\n");
        sb.append("        super(message);\n");
        sb.append("    }\n\n");

        sb.append("    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {\n");
        sb.append("        super(String.format(\"%s não encontrado com %s: '%s'\", resourceName, fieldName, fieldValue));\n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera configuração de CORS.
     */
    public String generateCorsConfig() {
        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(basePackage).append(".config;\n\n");

        sb.append("import org.springframework.context.annotation.Bean;\n");
        sb.append("import org.springframework.context.annotation.Configuration;\n");
        sb.append("import org.springframework.web.cors.CorsConfiguration;\n");
        sb.append("import org.springframework.web.cors.UrlBasedCorsConfigurationSource;\n");
        sb.append("import org.springframework.web.filter.CorsFilter;\n\n");

        sb.append("import java.util.Arrays;\n\n");

        sb.append("/**\n");
        sb.append(" * Configuração de CORS.\n");
        sb.append(" */\n");
        sb.append("@Configuration\n");
        sb.append("public class CorsConfig {\n\n");

        sb.append("    @Bean\n");
        sb.append("    public CorsFilter corsFilter() {\n");
        sb.append("        CorsConfiguration config = new CorsConfiguration();\n");
        sb.append("        config.setAllowCredentials(true);\n");
        sb.append("        config.setAllowedOriginPatterns(Arrays.asList(\"*\"));\n");
        sb.append("        config.setAllowedHeaders(Arrays.asList(\"*\"));\n");
        sb.append("        config.setAllowedMethods(Arrays.asList(\"GET\", \"POST\", \"PUT\", \"DELETE\", \"OPTIONS\", \"PATCH\"));\n\n");

        sb.append("        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();\n");
        sb.append("        source.registerCorsConfiguration(\"/**\", config);\n\n");

        sb.append("        return new CorsFilter(source);\n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera configuração do OpenAPI/Swagger.
     */
    public String generateOpenApiConfig() {
        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(basePackage).append(".config;\n\n");

        sb.append("import io.swagger.v3.oas.models.OpenAPI;\n");
        sb.append("import io.swagger.v3.oas.models.info.Contact;\n");
        sb.append("import io.swagger.v3.oas.models.info.Info;\n");
        sb.append("import org.springframework.context.annotation.Bean;\n");
        sb.append("import org.springframework.context.annotation.Configuration;\n\n");

        sb.append("/**\n");
        sb.append(" * Configuração do OpenAPI/Swagger.\n");
        sb.append(" */\n");
        sb.append("@Configuration\n");
        sb.append("public class OpenApiConfig {\n\n");

        sb.append("    @Bean\n");
        sb.append("    public OpenAPI customOpenAPI() {\n");
        sb.append("        return new OpenAPI()\n");
        sb.append("            .info(new Info()\n");
        sb.append("                .title(\"").append(projectName).append(" API\")\n");
        sb.append("                .version(\"1.0.0\")\n");
        sb.append("                .description(\"").append(projectDescription).append("\")\n");
        sb.append("                .contact(new Contact()\n");
        sb.append("                    .name(\"Suporte\")\n");
        sb.append("                    .email(\"suporte@empresa.com\")));\n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o arquivo compilar.bat.
     */
    public String generateCompilarBat() {
        StringBuilder sb = new StringBuilder();

        sb.append("@echo off\n");
        sb.append("echo ============================================\n");
        sb.append("echo Compilando ").append(projectName).append("...\n");
        sb.append("echo ============================================\n");
        sb.append("echo.\n\n");

        sb.append("REM Verifica se o Maven esta instalado\n");
        sb.append("where mvn >nul 2>nul\n");
        sb.append("if %ERRORLEVEL% NEQ 0 (\n");
        sb.append("    echo ERRO: Maven nao encontrado no PATH.\n");
        sb.append("    echo Instale o Maven ou adicione-o ao PATH.\n");
        sb.append("    pause\n");
        sb.append("    exit /b 1\n");
        sb.append(")\n\n");

        sb.append("REM Compila o projeto\n");
        sb.append("call mvn clean compile -DskipTests\n\n");

        sb.append("if %ERRORLEVEL% EQU 0 (\n");
        sb.append("    echo.\n");
        sb.append("    echo ============================================\n");
        sb.append("    echo Compilacao concluida com sucesso!\n");
        sb.append("    echo ============================================\n");
        sb.append(") else (\n");
        sb.append("    echo.\n");
        sb.append("    echo ============================================\n");
        sb.append("    echo ERRO na compilacao!\n");
        sb.append("    echo ============================================\n");
        sb.append(")\n\n");

        sb.append("pause\n");

        return sb.toString();
    }

    /**
     * Gera o arquivo executar.bat.
     */
    public String generateExecutarBat() {
        StringBuilder sb = new StringBuilder();

        sb.append("@echo off\n");
        sb.append("echo ============================================\n");
        sb.append("echo Iniciando ").append(projectName).append("...\n");
        sb.append("echo ============================================\n");
        sb.append("echo.\n\n");

        sb.append("REM Verifica se o Maven esta instalado\n");
        sb.append("where mvn >nul 2>nul\n");
        sb.append("if %ERRORLEVEL% NEQ 0 (\n");
        sb.append("    echo ERRO: Maven nao encontrado no PATH.\n");
        sb.append("    echo Instale o Maven ou adicione-o ao PATH.\n");
        sb.append("    pause\n");
        sb.append("    exit /b 1\n");
        sb.append(")\n\n");

        sb.append("echo Servidor iniciando na porta 8080...\n");
        sb.append("echo.\n");
        sb.append("echo Acesse: http://localhost:8080/api/swagger-ui.html\n");
        sb.append("echo.\n");
        sb.append("echo Pressione Ctrl+C para parar o servidor.\n");
        sb.append("echo ============================================\n");
        sb.append("echo.\n\n");

        sb.append("REM Executa o Spring Boot\n");
        sb.append("call mvn spring-boot:run\n\n");

        sb.append("pause\n");

        return sb.toString();
    }

    private String toPascalCase(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
