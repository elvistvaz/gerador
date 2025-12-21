package br.com.gerador.generator;

import br.com.gerador.generator.config.ProjectConfig;
import br.com.gerador.generator.template.*;
import br.com.gerador.generator.template.audit.*;
import br.com.gerador.generator.template.security.*;
import br.com.gerador.metamodel.AccessControlConfig;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.EntityType;
import br.com.gerador.metamodel.model.MetaModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerador de código Spring Boot a partir do MetaModel.
 */
public class SpringBootGenerator {

    private final GeneratorConfig config;
    private final EntityTemplate entityTemplate;
    private final EmbeddableIdTemplate embeddableIdTemplate;
    private final RepositoryTemplate repositoryTemplate;
    private final ServiceTemplate serviceTemplate;
    private final ControllerTemplate controllerTemplate;
    private final DTOTemplate dtoTemplate;
    private final MapperTemplate mapperTemplate;
    private final AuditLogTemplate auditLogTemplate;
    private final AuditableServiceTemplate auditableServiceTemplate;
    private ProjectTemplate projectTemplate;
    private ProjectConfig projectConfig;

    private final List<String> generatedFiles = new ArrayList<>();
    private final List<String> errors = new ArrayList<>();

    public SpringBootGenerator(GeneratorConfig config) {
        this.config = config;
        this.entityTemplate = new EntityTemplate(config.getBasePackage());
        this.embeddableIdTemplate = new EmbeddableIdTemplate(config.getBasePackage());
        this.repositoryTemplate = new RepositoryTemplate(config.getBasePackage());
        this.serviceTemplate = new ServiceTemplate(config.getBasePackage());
        this.controllerTemplate = new ControllerTemplate(config.getBasePackage());
        this.dtoTemplate = new DTOTemplate(config.getBasePackage());
        this.mapperTemplate = new MapperTemplate(config.getBasePackage());
        this.auditLogTemplate = new AuditLogTemplate(config.getBasePackage());
        this.auditableServiceTemplate = new AuditableServiceTemplate(config.getBasePackage());
    }

    /**
     * Define as configurações do projeto (config.json).
     */
    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }

    /**
     * Obtém as configurações do projeto.
     */
    public ProjectConfig getProjectConfig() {
        return projectConfig;
    }

    /**
     * Gera todo o código para o MetaModel.
     */
    public GenerationResult generate(MetaModel metaModel) {
        generatedFiles.clear();
        errors.clear();

        // Inicializa ProjectTemplate com dados do MetaModel
        String projectName = metaModel.getMetadata() != null ? metaModel.getMetadata().getName() : "Application";
        String projectDesc = metaModel.getMetadata() != null ? metaModel.getMetadata().getDescription() : "Spring Boot Application";
        this.projectTemplate = new ProjectTemplate(config.getBasePackage(), projectName, projectDesc);
        // Passa configurações do projeto para o ProjectTemplate
        if (this.projectConfig != null) {
            this.projectTemplate.setProjectConfig(this.projectConfig);
        }

        System.out.println("=".repeat(60));
        System.out.println("Iniciando geração de código Spring Boot");
        System.out.println("Projeto: " + projectName);
        System.out.println("Pacote base: " + config.getBasePackage());
        System.out.println("Diretório de saída: " + config.getOutputDir().toAbsolutePath());
        System.out.println("=".repeat(60));

        // Cria diretórios
        createDirectories();

        // Gera arquivos de infraestrutura do projeto
        generateProjectFiles(metaModel);

        // Gera código para cada entidade
        for (Entity entity : metaModel.getEntities()) {
            generateForEntity(entity);
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("Geração concluída!");
        System.out.println("Arquivos gerados: " + generatedFiles.size());
        if (!errors.isEmpty()) {
            System.out.println("Erros: " + errors.size());
        }
        System.out.println("=".repeat(60));

        return new GenerationResult(generatedFiles, errors);
    }

    /**
     * Gera os arquivos de infraestrutura do projeto.
     */
    private void generateProjectFiles(MetaModel metaModel) {
        System.out.println("\nGerando arquivos de infraestrutura...");

        try {
            // outputDir = generated/src/main/java
            // projectRoot = generated (subir 3 níveis: java -> main -> src -> generated)
            Path projectRoot = config.getOutputDir().getParent().getParent().getParent();

            // pom.xml na raiz do projeto
            writeFileAbsolute(projectRoot.resolve("pom.xml"), projectTemplate.generatePom(), "pom.xml");

            // application.properties em src/main/resources
            Path resourcesPath = config.getOutputDir().getParent().resolve("resources");
            Files.createDirectories(resourcesPath);
            writeFileAbsolute(resourcesPath.resolve("application.properties"),
                projectTemplate.generateApplicationProperties(
                    metaModel.getMetadata() != null ? metaModel.getMetadata().getDatabase() : null),
                "application.properties");

            // application-dev.properties (H2 em memória)
            writeFileAbsolute(resourcesPath.resolve("application-dev.properties"),
                projectTemplate.generateApplicationPropertiesDev(),
                "application-dev.properties");

            // application-prod.properties (SQL Server)
            writeFileAbsolute(resourcesPath.resolve("application-prod.properties"),
                projectTemplate.generateApplicationPropertiesProd(),
                "application-prod.properties");

            // Application.java (main class)
            String appClassName = toPascalCase(projectTemplate.getProjectName().replace(" ", "")) + "Application.java";
            writeFile("", appClassName, projectTemplate.generateApplication());

            // Config classes
            Files.createDirectories(config.getSubPackagePath("config"));
            writeFile("config", "GlobalExceptionHandler.java", projectTemplate.generateExceptionHandler());
            writeFile("config", "ResourceNotFoundException.java", projectTemplate.generateResourceNotFoundException());
            writeFile("config", "CorsConfig.java", projectTemplate.generateCorsConfig());
            writeFile("config", "OpenApiConfig.java", projectTemplate.generateOpenApiConfig());

            // Security classes
            Files.createDirectories(config.getSubPackagePath("security"));
            JwtUtilTemplate jwtUtilTemplate = new JwtUtilTemplate(config);
            writeFile("security", "JwtUtil.java", jwtUtilTemplate.generate());

            JwtAuthenticationFilterTemplate jwtFilterTemplate = new JwtAuthenticationFilterTemplate(config);
            writeFile("security", "JwtAuthenticationFilter.java", jwtFilterTemplate.generate());

            SecurityConfigTemplate securityConfigTemplate = new SecurityConfigTemplate(config);
            if (this.projectConfig != null) {
                securityConfigTemplate.setProjectConfig(this.projectConfig);
            }
            writeFile("config", "SecurityConfig.java", securityConfigTemplate.generate());

            AuthControllerTemplate authControllerTemplate = new AuthControllerTemplate(config);
            writeFile("controller", "AuthController.java", authControllerTemplate.generate());

            CustomUserDetailsServiceTemplate userDetailsTemplate = new CustomUserDetailsServiceTemplate(config);
            writeFile("security", "CustomUserDetailsService.java", userDetailsTemplate.generate());

            // Audit Log classes
            System.out.println("\nGerando classes de auditoria...");
            writeFile("entity", "AuditLog.java", auditLogTemplate.generateEntity());
            writeFile("repository", "AuditLogRepository.java", auditLogTemplate.generateRepository());
            writeFile("dto", "AuditLogListDTO.java", auditLogTemplate.generateDTOs());
            writeFile("dto", "AuditLogResponseDTO.java", auditLogTemplate.generateResponseDTO());
            writeFile("dto", "AuditLogFilterDTO.java", auditLogTemplate.generateFilterDTO());
            writeFile("service", "AuditLogService.java", auditLogTemplate.generateService());
            writeFile("controller", "AuditLogController.java", auditLogTemplate.generateController());

            // Access Control classes (se configurado no metamodel)
            if (metaModel.getMetadata() != null && metaModel.getMetadata().hasAccessControl()) {
                System.out.println("\nGerando módulo de controle de acesso...");
                AccessControlConfig accessControl = metaModel.getMetadata().getAccessControl();
                AccessControlTemplate accessControlTemplate = new AccessControlTemplate(config, accessControl);

                writeFile("entity", "Perfil.java", accessControlTemplate.generatePerfilEntity());
                writeFile("entity", "Usuario.java", accessControlTemplate.generateUsuarioEntity());
                writeFile("repository", "PerfilRepository.java", accessControlTemplate.generatePerfilRepository());
                writeFile("repository", "UsuarioRepository.java", accessControlTemplate.generateUsuarioRepository());
                writeFile("dto", "UsuarioResponseDTO.java", accessControlTemplate.generateUsuarioResponseDTO());
                writeFile("dto", "UsuarioRequestDTO.java", accessControlTemplate.generateUsuarioRequestDTO());
                writeFile("dto", "UsuarioListDTO.java", accessControlTemplate.generateUsuarioListDTO());
                writeFile("dto", "PerfilResponseDTO.java", accessControlTemplate.generatePerfilResponseDTO());
                writeFile("service", "UsuarioService.java", accessControlTemplate.generateUsuarioService());
                writeFile("controller", "UsuarioController.java", accessControlTemplate.generateUsuarioController());

                // Atualiza CustomUserDetailsService para usar banco de dados
                CustomUserDetailsServiceTemplate dbUserDetailsTemplate = new CustomUserDetailsServiceTemplate(config);
                writeFile("security", "CustomUserDetailsService.java", dbUserDetailsTemplate.generateWithDatabase());
            }

            // Scripts batch na raiz do projeto
            writeFileAbsolute(projectRoot.resolve("compilar.bat"), projectTemplate.generateCompilarBat(), "compilar.bat");
            writeFileAbsolute(projectRoot.resolve("executar.bat"), projectTemplate.generateExecutarBat(), "executar.bat");

        } catch (IOException e) {
            errors.add("Erro ao gerar arquivos de projeto: " + e.getMessage());
            System.err.println("  ERRO: " + e.getMessage());
        }
    }

    private void writeFileAbsolute(Path filePath, String content, String displayName) throws IOException {
        if (Files.exists(filePath) && !config.isOverwriteExisting()) {
            System.out.println("  [SKIP] " + displayName + " (já existe)");
            return;
        }
        Files.writeString(filePath, content, StandardCharsets.UTF_8);
        generatedFiles.add(filePath.toString());
        System.out.println("  [OK] " + displayName);
    }

    private String toPascalCase(String name) {
        if (name == null || name.isEmpty()) return name;
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    /**
     * Gera código para uma entidade específica.
     */
    public void generateForEntity(Entity entity) {
        String entityName = entity.getName();
        System.out.println("\nProcessando: " + entityName);

        try {
            // Entity
            if (config.isGenerateEntities()) {
                String code = entityTemplate.generate(entity);
                writeFile("entity", entityName + ".java", code);

                // Se tem chave composta, gera a classe Id
                if (embeddableIdTemplate.hasCompositeKey(entity)) {
                    String idCode = embeddableIdTemplate.generate(entity);
                    writeFile("entity", entityName + "Id.java", idCode);
                }
            }

            // Não gera CRUD completo para entidades CHILD ou JUNCTION
            boolean generateCrud = entity.getType() != EntityType.CHILD
                && entity.getType() != EntityType.JUNCTION;

            // Repository
            if (config.isGenerateRepositories()) {
                String code = repositoryTemplate.generate(entity);
                writeFile("repository", entityName + "Repository.java", code);
            }

            // DTOs
            if (config.isGenerateDTOs()) {
                writeFile("dto", entityName + "ResponseDTO.java", dtoTemplate.generateResponseDTO(entity));
                writeFile("dto", entityName + "RequestDTO.java", dtoTemplate.generateRequestDTO(entity));
                writeFile("dto", entityName + "ListDTO.java", dtoTemplate.generateListDTO(entity));
            }

            // Mapper
            if (config.isGenerateMappers()) {
                String code = mapperTemplate.generate(entity);
                writeFile("mapper", entityName + "Mapper.java", code);
            }

            // Service (com suporte a auditoria)
            if (config.isGenerateServices() && generateCrud) {
                String code = auditableServiceTemplate.generate(entity);
                writeFile("service", entityName + "Service.java", code);
            }

            // Controller
            if (config.isGenerateControllers() && generateCrud) {
                String code = controllerTemplate.generate(entity);
                writeFile("controller", entityName + "Controller.java", code);
            }

        } catch (Exception e) {
            String error = "Erro ao gerar " + entityName + ": " + e.getMessage();
            errors.add(error);
            System.err.println("  ERRO: " + e.getMessage());
        }
    }

    private void createDirectories() {
        try {
            if (config.isGenerateEntities()) {
                Files.createDirectories(config.getSubPackagePath("entity"));
            }
            if (config.isGenerateRepositories()) {
                Files.createDirectories(config.getSubPackagePath("repository"));
            }
            if (config.isGenerateServices()) {
                Files.createDirectories(config.getSubPackagePath("service"));
            }
            if (config.isGenerateControllers()) {
                Files.createDirectories(config.getSubPackagePath("controller"));
            }
            if (config.isGenerateDTOs()) {
                Files.createDirectories(config.getSubPackagePath("dto"));
            }
            if (config.isGenerateMappers()) {
                Files.createDirectories(config.getSubPackagePath("mapper"));
            }
        } catch (IOException e) {
            errors.add("Erro ao criar diretórios: " + e.getMessage());
        }
    }

    private void writeFile(String subPackage, String fileName, String content) throws IOException {
        Path filePath = config.getSubPackagePath(subPackage).resolve(fileName);

        if (Files.exists(filePath) && !config.isOverwriteExisting()) {
            System.out.println("  [SKIP] " + subPackage + "/" + fileName + " (já existe)");
            return;
        }

        Files.writeString(filePath, content, StandardCharsets.UTF_8);
        generatedFiles.add(filePath.toString());
        System.out.println("  [OK] " + subPackage + "/" + fileName);
    }

    /**
     * Resultado da geração.
     */
    public static class GenerationResult {
        private final List<String> generatedFiles;
        private final List<String> errors;

        public GenerationResult(List<String> generatedFiles, List<String> errors) {
            this.generatedFiles = new ArrayList<>(generatedFiles);
            this.errors = new ArrayList<>(errors);
        }

        public List<String> getGeneratedFiles() {
            return generatedFiles;
        }

        public List<String> getErrors() {
            return errors;
        }

        public boolean hasErrors() {
            return !errors.isEmpty();
        }

        public int getFileCount() {
            return generatedFiles.size();
        }
    }
}
