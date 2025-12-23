package br.com.gerador.generator;

import br.com.gerador.generator.config.ProjectConfig;
import br.com.gerador.generator.template.angular.*;
import br.com.gerador.generator.template.angular.AngularAuditLogTemplate;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.MetaModel;
import br.com.gerador.metamodel.model.SessionContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerador de código Angular a partir do MetaModel.
 */
public class AngularGenerator {

    private final Path outputDir;
    private final AngularModelTemplate modelTemplate;
    private final AngularServiceTemplate serviceTemplate;
    private final AngularAuditLogTemplate auditLogTemplate;
    private final AngularAccessControlTemplate accessControlTemplate;
    private final List<String> generatedFiles = new ArrayList<>();
    private final List<String> errors = new ArrayList<>();
    private MetaModel metaModel;
    private ProjectConfig projectConfig;

    public AngularGenerator(Path outputDir) {
        this.outputDir = outputDir;
        this.modelTemplate = new AngularModelTemplate();
        this.serviceTemplate = new AngularServiceTemplate();
        this.auditLogTemplate = new AngularAuditLogTemplate();
        this.accessControlTemplate = new AngularAccessControlTemplate();
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
     * Gera todo o código Angular para o MetaModel.
     */
    public GenerationResult generate(MetaModel metaModel) {
        this.metaModel = metaModel;
        this.serviceTemplate.setMetaModel(metaModel);
        generatedFiles.clear();
        errors.clear();

        String projectName = metaModel.getMetadata() != null ? metaModel.getMetadata().getName() : "Application";

        System.out.println("=".repeat(60));
        System.out.println("Iniciando geração de código Angular");
        System.out.println("Projeto: " + projectName);
        System.out.println("Diretório de saída: " + outputDir.toAbsolutePath());
        System.out.println("=".repeat(60));

        // Cria estrutura de diretórios
        createDirectories();

        // Gera arquivos de configuração do projeto Angular
        generateProjectFiles(metaModel);

        // Gera código para cada entidade
        for (Entity entity : metaModel.getEntities()) {
            generateForEntity(entity);
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("Geração Angular concluída!");
        System.out.println("Arquivos gerados: " + generatedFiles.size());
        if (!errors.isEmpty()) {
            System.out.println("Erros: " + errors.size());
        }
        System.out.println("=".repeat(60));

        return new GenerationResult(generatedFiles, errors);
    }

    /**
     * Cria estrutura de diretórios do projeto Angular.
     */
    private void createDirectories() {
        try {
            Files.createDirectories(outputDir.resolve("src/app/models"));
            Files.createDirectories(outputDir.resolve("src/app/services"));
            Files.createDirectories(outputDir.resolve("src/app/components"));
            Files.createDirectories(outputDir.resolve("src/app/guards"));
            Files.createDirectories(outputDir.resolve("src/app/interceptors"));
            Files.createDirectories(outputDir.resolve("src/environments"));
        } catch (IOException e) {
            errors.add("Erro ao criar diretórios: " + e.getMessage());
        }
    }

    /**
     * Gera arquivos de configuração do projeto Angular.
     */
    private void generateProjectFiles(MetaModel metaModel) {
        System.out.println("\nGerando arquivos de configuração Angular...");

        // Gera environment.ts
        writeFile("src/environments/environment.ts", generateEnvironment(false), "environment.ts");
        writeFile("src/environments/environment.prod.ts", generateEnvironment(true), "environment.prod.ts");

        // Gera package.json
        writeFile("package.json", generatePackageJson(metaModel), "package.json");

        // Gera angular.json
        writeFile("angular.json", generateAngularJson(metaModel), "angular.json");

        // Gera tsconfig.json
        writeFile("tsconfig.json", generateTsConfig(), "tsconfig.json");

        // Gera scripts de execução
        writeFile("instalar.bat", generateInstalarBat(), "instalar.bat");
        writeFile("executar.bat", generateExecutarBat(), "executar.bat");

        // Gera arquivos básicos do Angular
        writeFile("src/main.ts", generateMainTs(metaModel), "main.ts");
        writeFile("src/index.html", generateIndexHtml(metaModel), "index.html");
        writeFile("src/styles.css", generateStylesCss(), "styles.css");
        writeFile("src/app/app.component.ts", generateAppComponentTs(metaModel), "app.component.ts");
        writeFile("src/app/app.component.html", generateAppComponentHtml(metaModel), "app.component.html");
        writeFile("src/app/app.component.css", generateAppComponentCss(), "app.component.css");

        // Gera arquivos de autenticação
        generateAuthFiles();
    }

    /**
     * Gera arquivos de autenticação.
     */
    private void generateAuthFiles() {
        System.out.println("\nGerando arquivos de autenticação...");

        // Login component
        AngularLoginComponentTemplate loginTemplate = new AngularLoginComponentTemplate();
        writeFile("src/app/components/login.component.ts", loginTemplate.generateComponentTs(), "components/login.component.ts");
        writeFile("src/app/components/login.component.html", loginTemplate.generateComponentHtml(), "components/login.component.html");
        writeFile("src/app/components/login.component.css", loginTemplate.generateComponentCss(), "components/login.component.css");

        // Home component
        AngularHomeComponentTemplate homeTemplate = new AngularHomeComponentTemplate(new GeneratorConfig());
        writeFile("src/app/components/home.component.ts", homeTemplate.generateTs(), "components/home.component.ts");
        writeFile("src/app/components/home.component.html", homeTemplate.generateHtml(), "components/home.component.html");
        writeFile("src/app/components/home.component.css", homeTemplate.generateCss(), "components/home.component.css");

        // Auth service
        AngularAuthServiceTemplate authServiceTemplate = new AngularAuthServiceTemplate();
        writeFile("src/app/services/auth.service.ts", authServiceTemplate.generate(), "services/auth.service.ts");

        // Auth guard
        AngularAuthGuardTemplate authGuardTemplate = new AngularAuthGuardTemplate();
        writeFile("src/app/guards/auth.guard.ts", authGuardTemplate.generate(), "guards/auth.guard.ts");

        // Auth interceptor
        AngularAuthInterceptorTemplate authInterceptorTemplate = new AngularAuthInterceptorTemplate();
        writeFile("src/app/interceptors/auth.interceptor.ts", authInterceptorTemplate.generate(), "interceptors/auth.interceptor.ts");

        // Environment
        AngularEnvironmentTemplate envTemplate = new AngularEnvironmentTemplate();
        // Usa a porta do backend do config.json, se disponível
        if (projectConfig != null) {
            envTemplate.setBackendPort(projectConfig.getBackendPort());
        }
        writeFile("src/environments/environment.ts", envTemplate.generate(), "environments/environment.ts");

        // Session files (se configurado)
        generateSessionFiles();

        // Audit log files
        generateAuditLogFiles();

        // Access control files (usuário e perfil)
        generateAccessControlFiles();
    }

    /**
     * Gera arquivos de sessão (session.service e session-selector).
     */
    private void generateSessionFiles() {
        if (metaModel.getMetadata() == null || !metaModel.getMetadata().hasSessionContext()) {
            return;
        }

        System.out.println("\nGerando arquivos de sessão...");

        // Session service
        AngularSessionServiceTemplate sessionServiceTemplate = new AngularSessionServiceTemplate(metaModel);
        writeFile("src/app/services/session.service.ts", sessionServiceTemplate.generate(), "services/session.service.ts");

        // Session selector component
        AngularSessionSelectorTemplate sessionSelectorTemplate = new AngularSessionSelectorTemplate(metaModel);
        writeFile("src/app/components/session-selector.component.ts", sessionSelectorTemplate.generateTs(), "components/session-selector.component.ts");
        writeFile("src/app/components/session-selector.component.html", sessionSelectorTemplate.generateHtml(), "components/session-selector.component.html");
        writeFile("src/app/components/session-selector.component.css", sessionSelectorTemplate.generateCss(), "components/session-selector.component.css");
    }

    /**
     * Gera arquivos de auditoria (audit log).
     */
    private void generateAuditLogFiles() {
        System.out.println("\nGerando arquivos de auditoria...");

        // Model
        writeFile("src/app/models/audit-log.model.ts", auditLogTemplate.generateModel(), "models/audit-log.model.ts");

        // Service
        writeFile("src/app/services/audit-log.service.ts", auditLogTemplate.generateService(), "services/audit-log.service.ts");

        // List Component
        writeFile("src/app/components/audit-log-list.component.ts", auditLogTemplate.generateListComponentTs(), "components/audit-log-list.component.ts");
        writeFile("src/app/components/audit-log-list.component.html", auditLogTemplate.generateListComponentHtml(), "components/audit-log-list.component.html");
        writeFile("src/app/components/audit-log-list.component.css", auditLogTemplate.generateListComponentCss(), "components/audit-log-list.component.css");
    }

    /**
     * Gera arquivos de controle de acesso (usuário e perfil).
     */
    private void generateAccessControlFiles() {
        System.out.println("\nGerando arquivos de controle de acesso...");

        // Perfil Model (separado para não conflitar com entidade Usuario do metamodelo)
        writeFile("src/app/models/perfil.model.ts", accessControlTemplate.generatePerfilModel(), "models/perfil.model.ts");

        // Verifica se existe entidade Usuario no metamodelo
        boolean hasUsuarioEntity = metaModel.getEntities().stream()
            .anyMatch(e -> "Usuario".equals(e.getName()));

        // Se houver entidade Usuario no metamodelo, usa os componentes gerados pelo metamodelo
        // Caso contrário, usa os componentes genéricos do AccessControl
        if (!hasUsuarioEntity) {
            // Usuario Model
            writeFile("src/app/models/usuario.model.ts", accessControlTemplate.generateUsuarioModel(), "models/usuario.model.ts");

            // Service
            writeFile("src/app/services/usuario.service.ts", accessControlTemplate.generateUsuarioService(), "services/usuario.service.ts");

            // Usuario List Component
            writeFile("src/app/components/usuario-list.component.ts", accessControlTemplate.generateUsuarioListComponentTs(), "components/usuario-list.component.ts");
            writeFile("src/app/components/usuario-list.component.html", accessControlTemplate.generateUsuarioListComponentHtml(), "components/usuario-list.component.html");
            writeFile("src/app/components/usuario-list.component.css", accessControlTemplate.generateUsuarioListComponentCss(), "components/usuario-list.component.css");

            // Usuario Form Component
            writeFile("src/app/components/usuario-form.component.ts", accessControlTemplate.generateUsuarioFormComponentTs(), "components/usuario-form.component.ts");
            writeFile("src/app/components/usuario-form.component.html", accessControlTemplate.generateUsuarioFormComponentHtml(), "components/usuario-form.component.html");
            writeFile("src/app/components/usuario-form.component.css", accessControlTemplate.generateUsuarioFormComponentCss(), "components/usuario-form.component.css");
        }
        // Nota: quando existe entidade Usuario no metamodelo, os componentes são gerados pelo generateForEntity
        // e o service é gerado com o método getPerfis() adicionado pelo AngularServiceTemplate

        // Perfil List Component (sempre gera)
        writeFile("src/app/components/perfil-list.component.ts", accessControlTemplate.generatePerfilListComponentTs(), "components/perfil-list.component.ts");
        writeFile("src/app/components/perfil-list.component.html", accessControlTemplate.generatePerfilListComponentHtml(), "components/perfil-list.component.html");
        writeFile("src/app/components/perfil-list.component.css", accessControlTemplate.generatePerfilListComponentCss(), "components/perfil-list.component.css");
    }

    /**
     * Gera código para uma entidade específica.
     */
    private void generateForEntity(Entity entity) {
        String entityName = entity.getName();
        String entityLower = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);

        System.out.println("\nProcessando: " + entityName);

        // Model
        String modelCode = modelTemplate.generate(entity);
        writeFile("src/app/models/" + entityLower + ".model.ts", modelCode, "models/" + entityLower + ".model.ts");

        // Service (para Usuario adiciona método getPerfis)
        String serviceCode = serviceTemplate.generate(entity);
        writeFile("src/app/services/" + entityLower + ".service.ts", serviceCode, "services/" + entityLower + ".service.ts");

        // List Component
        AngularListComponentTemplate listTemplate = new AngularListComponentTemplate(entity, metaModel);
        writeFile("src/app/components/" + entityLower + "-list.component.ts", listTemplate.generateTs(), "components/" + entityLower + "-list.component.ts");
        writeFile("src/app/components/" + entityLower + "-list.component.html", listTemplate.generateHtml(), "components/" + entityLower + "-list.component.html");
        writeFile("src/app/components/" + entityLower + "-list.component.css", listTemplate.generateCss(), "components/" + entityLower + "-list.component.css");

        // Form Component (novo/editar)
        AngularFormComponentTemplate formTemplate = new AngularFormComponentTemplate(entity, metaModel);
        writeFile("src/app/components/" + entityLower + "-form.component.ts", formTemplate.generateTs(), "components/" + entityLower + "-form.component.ts");
        writeFile("src/app/components/" + entityLower + "-form.component.html", formTemplate.generateHtml(), "components/" + entityLower + "-form.component.html");
        writeFile("src/app/components/" + entityLower + "-form.component.css", formTemplate.generateCss(), "components/" + entityLower + "-form.component.css");
    }

    /**
     * Gera arquivo environment.ts
     */
    private String generateEnvironment(boolean production) {
        StringBuilder sb = new StringBuilder();

        int backendPort = projectConfig != null ? projectConfig.getBackendPort() : 8080;
        sb.append("export const environment = {\n");
        sb.append("  production: ").append(production).append(",\n");
        sb.append("  apiUrl: '").append(production ? "https://api.example.com/api" : "http://localhost:" + backendPort + "/api").append("'\n");
        sb.append("};\n");

        return sb.toString();
    }

    /**
     * Gera package.json
     */
    private String generatePackageJson(MetaModel metaModel) {
        String projectName = metaModel.getMetadata() != null ?
            metaModel.getMetadata().getName().toLowerCase().replace(" ", "-") : "angular-app";

        StringBuilder sb = new StringBuilder();

        sb.append("{\n");
        sb.append("  \"name\": \"").append(projectName).append("\",\n");
        sb.append("  \"version\": \"1.0.0\",\n");
        sb.append("  \"scripts\": {\n");
        sb.append("    \"ng\": \"ng\",\n");
        sb.append("    \"start\": \"ng serve\",\n");
        sb.append("    \"build\": \"ng build\",\n");
        sb.append("    \"watch\": \"ng build --watch --configuration development\",\n");
        sb.append("    \"test\": \"ng test\"\n");
        sb.append("  },\n");
        sb.append("  \"private\": true,\n");
        sb.append("  \"dependencies\": {\n");
        sb.append("    \"@angular/animations\": \"^17.0.0\",\n");
        sb.append("    \"@angular/common\": \"^17.0.0\",\n");
        sb.append("    \"@angular/compiler\": \"^17.0.0\",\n");
        sb.append("    \"@angular/core\": \"^17.0.0\",\n");
        sb.append("    \"@angular/forms\": \"^17.0.0\",\n");
        sb.append("    \"@angular/platform-browser\": \"^17.0.0\",\n");
        sb.append("    \"@angular/platform-browser-dynamic\": \"^17.0.0\",\n");
        sb.append("    \"@angular/router\": \"^17.0.0\",\n");
        sb.append("    \"rxjs\": \"~7.8.0\",\n");
        sb.append("    \"tslib\": \"^2.3.0\",\n");
        sb.append("    \"zone.js\": \"~0.14.2\"\n");
        sb.append("  },\n");
        sb.append("  \"devDependencies\": {\n");
        sb.append("    \"@angular-devkit/build-angular\": \"^17.0.0\",\n");
        sb.append("    \"@angular/cli\": \"^17.0.0\",\n");
        sb.append("    \"@angular/compiler-cli\": \"^17.0.0\",\n");
        sb.append("    \"typescript\": \"~5.2.2\"\n");
        sb.append("  }\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera angular.json
     */
    private String generateAngularJson(MetaModel metaModel) {
        String projectName = metaModel.getMetadata() != null ?
            metaModel.getMetadata().getName().toLowerCase().replace(" ", "-") : "angular-app";

        return "{\n" +
            "  \"$schema\": \"./node_modules/@angular/cli/lib/config/schema.json\",\n" +
            "  \"version\": 1,\n" +
            "  \"newProjectRoot\": \"projects\",\n" +
            "  \"projects\": {\n" +
            "    \"" + projectName + "\": {\n" +
            "      \"projectType\": \"application\",\n" +
            "      \"schematics\": {},\n" +
            "      \"root\": \"\",\n" +
            "      \"sourceRoot\": \"src\",\n" +
            "      \"prefix\": \"app\",\n" +
            "      \"architect\": {\n" +
            "        \"build\": {\n" +
            "          \"builder\": \"@angular-devkit/build-angular:browser\",\n" +
            "          \"options\": {\n" +
            "            \"outputPath\": \"dist/" + projectName + "\",\n" +
            "            \"index\": \"src/index.html\",\n" +
            "            \"main\": \"src/main.ts\",\n" +
            "            \"polyfills\": [\"zone.js\"],\n" +
            "            \"tsConfig\": \"tsconfig.json\",\n" +
            "            \"assets\": [\"src/favicon.ico\", \"src/assets\"],\n" +
            "            \"styles\": [\"src/styles.css\"],\n" +
            "            \"scripts\": []\n" +
            "          },\n" +
            "          \"configurations\": {\n" +
            "            \"production\": {\n" +
            "              \"budgets\": [\n" +
            "                {\n" +
            "                  \"type\": \"initial\",\n" +
            "                  \"maximumWarning\": \"500kb\",\n" +
            "                  \"maximumError\": \"2mb\"\n" +
            "                },\n" +
            "                {\n" +
            "                  \"type\": \"anyComponentStyle\",\n" +
            "                  \"maximumWarning\": \"8kb\",\n" +
            "                  \"maximumError\": \"16kb\"\n" +
            "                }\n" +
            "              ],\n" +
            "              \"outputHashing\": \"all\"\n" +
            "            },\n" +
            "            \"development\": {\n" +
            "              \"optimization\": false,\n" +
            "              \"extractLicenses\": false,\n" +
            "              \"sourceMap\": true\n" +
            "            }\n" +
            "          },\n" +
            "          \"defaultConfiguration\": \"production\"\n" +
            "        },\n" +
            "        \"serve\": {\n" +
            "          \"builder\": \"@angular-devkit/build-angular:dev-server\",\n" +
            "          \"configurations\": {\n" +
            "            \"production\": {\n" +
            "              \"buildTarget\": \"" + projectName + ":build:production\"\n" +
            "            },\n" +
            "            \"development\": {\n" +
            "              \"buildTarget\": \"" + projectName + ":build:development\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"defaultConfiguration\": \"development\",\n" +
            "          \"options\": {\n" +
            "            \"port\": 4200\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"cli\": {\n" +
            "    \"analytics\": false\n" +
            "  }\n" +
            "}\n";
    }

    /**
     * Gera tsconfig.json
     */
    private String generateTsConfig() {
        return "{\n" +
            "  \"compileOnSave\": false,\n" +
            "  \"compilerOptions\": {\n" +
            "    \"outDir\": \"./dist/out-tsc\",\n" +
            "    \"forceConsistentCasingInFileNames\": true,\n" +
            "    \"strict\": true,\n" +
            "    \"noImplicitOverride\": true,\n" +
            "    \"noPropertyAccessFromIndexSignature\": true,\n" +
            "    \"noImplicitReturns\": true,\n" +
            "    \"noFallthroughCasesInSwitch\": true,\n" +
            "    \"esModuleInterop\": true,\n" +
            "    \"sourceMap\": true,\n" +
            "    \"declaration\": false,\n" +
            "    \"experimentalDecorators\": true,\n" +
            "    \"moduleResolution\": \"node\",\n" +
            "    \"importHelpers\": true,\n" +
            "    \"target\": \"ES2022\",\n" +
            "    \"module\": \"ES2022\",\n" +
            "    \"useDefineForClassFields\": false,\n" +
            "    \"lib\": [\"ES2022\", \"dom\"],\n" +
            "    \"skipLibCheck\": true\n" +
            "  },\n" +
            "  \"angularCompilerOptions\": {\n" +
            "    \"enableI18nLegacyMessageIdFormat\": false,\n" +
            "    \"strictInjectionParameters\": true,\n" +
            "    \"strictInputAccessModifiers\": true,\n" +
            "    \"strictTemplates\": true\n" +
            "  }\n" +
            "}\n";
    }

    /**
     * Gera main.ts
     */
    private String generateMainTs(MetaModel metaModel) {
        StringBuilder sb = new StringBuilder();

        sb.append("import { bootstrapApplication } from '@angular/platform-browser';\n");
        sb.append("import { AppComponent } from './app/app.component';\n");
        sb.append("import { provideHttpClient, withInterceptors } from '@angular/common/http';\n");
        sb.append("import { provideRouter, Routes } from '@angular/router';\n");
        sb.append("import { LoginComponent } from './app/components/login.component';\n");
        sb.append("import { HomeComponent } from './app/components/home.component';\n");
        sb.append("import { AuditLogListComponent } from './app/components/audit-log-list.component';\n");
        sb.append("import { UsuarioListComponent } from './app/components/usuario-list.component';\n");
        sb.append("import { UsuarioFormComponent } from './app/components/usuario-form.component';\n");
        sb.append("import { PerfilListComponent } from './app/components/perfil-list.component';\n");
        sb.append("import { authGuard } from './app/guards/auth.guard';\n");
        sb.append("import { authInterceptor } from './app/interceptors/auth.interceptor';\n\n");

        // Imports dos componentes de listagem e formulário
        for (Entity entity : metaModel.getEntities()) {
            String entityName = entity.getName();
            // Pula Usuario pois já está importado acima (controle de acesso)
            if ("Usuario".equals(entityName)) {
                continue;
            }
            String entityLower = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
            sb.append("import { ").append(entityName).append("ListComponent } from './app/components/")
              .append(entityLower).append("-list.component';\n");
            sb.append("import { ").append(entityName).append("FormComponent } from './app/components/")
              .append(entityLower).append("-form.component';\n");
        }

        sb.append("\nconst routes: Routes = [\n");
        sb.append("  { path: 'login', component: LoginComponent },\n");
        sb.append("  { path: '', component: HomeComponent, canActivate: [authGuard] },\n");

        // Rotas para cada entidade (lista, novo, editar)
        for (Entity entity : metaModel.getEntities()) {
            String entityName = entity.getName();
            // Pula Usuario pois as rotas estão definidas abaixo (controle de acesso)
            if ("Usuario".equals(entityName)) {
                continue;
            }
            String entityLower = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
            sb.append("  { path: '").append(entityLower).append("', component: ")
              .append(entityName).append("ListComponent, canActivate: [authGuard] },\n");
            sb.append("  { path: '").append(entityLower).append("/novo', component: ")
              .append(entityName).append("FormComponent, canActivate: [authGuard] },\n");
            sb.append("  { path: '").append(entityLower).append("/editar/:id', component: ")
              .append(entityName).append("FormComponent, canActivate: [authGuard] },\n");
        }

        // Rota de auditoria
        sb.append("  { path: 'audit-log', component: AuditLogListComponent, canActivate: [authGuard] },\n");

        // Rotas de controle de acesso
        sb.append("  { path: 'usuario', component: UsuarioListComponent, canActivate: [authGuard] },\n");
        sb.append("  { path: 'usuario/novo', component: UsuarioFormComponent, canActivate: [authGuard] },\n");
        sb.append("  { path: 'usuario/editar/:id', component: UsuarioFormComponent, canActivate: [authGuard] },\n");
        sb.append("  { path: 'perfil', component: PerfilListComponent, canActivate: [authGuard] },\n");

        sb.append("  { path: '**', redirectTo: '' }\n");
        sb.append("];\n\n");

        sb.append("bootstrapApplication(AppComponent, {\n");
        sb.append("  providers: [\n");
        sb.append("    provideHttpClient(withInterceptors([authInterceptor])),\n");
        sb.append("    provideRouter(routes)\n");
        sb.append("  ]\n");
        sb.append("}).catch(err => console.error(err));\n");

        return sb.toString();
    }

    /**
     * Gera index.html
     */
    private String generateIndexHtml(MetaModel metaModel) {
        String projectName = metaModel.getMetadata() != null ?
            metaModel.getMetadata().getName() : "Application";

        return "<!doctype html>\n" +
            "<html lang=\"pt-BR\">\n" +
            "<head>\n" +
            "  <meta charset=\"utf-8\">\n" +
            "  <title>" + projectName + "</title>\n" +
            "  <base href=\"/\">\n" +
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "  <link rel=\"icon\" type=\"image/x-icon\" href=\"favicon.ico\">\n" +
            "</head>\n" +
            "<body>\n" +
            "  <app-root></app-root>\n" +
            "</body>\n" +
            "</html>\n";
    }

    /**
     * Gera styles.css
     */
    private String generateStylesCss() {
        return "/* Global Styles */\n" +
            "* {\n" +
            "  margin: 0;\n" +
            "  padding: 0;\n" +
            "  box-sizing: border-box;\n" +
            "}\n" +
            "\n" +
            "body {\n" +
            "  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;\n" +
            "  font-size: 14px;\n" +
            "  line-height: 1.5;\n" +
            "  color: #333;\n" +
            "  background-color: #f5f5f5;\n" +
            "}\n";
    }

    /**
     * Gera app.component.ts
     */
    private String generateAppComponentTs(MetaModel metaModel) {
        String projectName = metaModel.getMetadata() != null ?
            metaModel.getMetadata().getName() : "Application";
        boolean hasSessionContext = metaModel.getMetadata() != null && metaModel.getMetadata().hasSessionContext();

        StringBuilder sb = new StringBuilder();
        sb.append("import { Component, OnInit } from '@angular/core';\n");
        sb.append("import { CommonModule } from '@angular/common';\n");
        sb.append("import { Router, RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';\n");
        sb.append("import { AuthService } from './services/auth.service';\n");

        if (hasSessionContext) {
            sb.append("import { SessionService } from './services/session.service';\n");
            sb.append("import { SessionSelectorComponent } from './components/session-selector.component';\n");
        }
        sb.append("\n");

        sb.append("@Component({\n");
        sb.append("  selector: 'app-root',\n");
        sb.append("  standalone: true,\n");
        sb.append("  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive");
        if (hasSessionContext) {
            sb.append(", SessionSelectorComponent");
        }
        sb.append("],\n");
        sb.append("  templateUrl: './app.component.html',\n");
        sb.append("  styleUrls: ['./app.component.css']\n");
        sb.append("})\n");
        sb.append("export class AppComponent implements OnInit {\n");
        sb.append("  title = '").append(projectName).append("';\n");
        sb.append("  menuCollapsed = false;\n");
        sb.append("  expandedCategories: { [key: string]: boolean } = {};\n");

        if (hasSessionContext) {
            sb.append("  showSessionSelector = false;\n");
            // Adiciona propriedade para exibir o contexto selecionado no header
            for (SessionContext ctx : metaModel.getMetadata().getSessionContext()) {
                String fieldName = ctx.getField();
                String capitalField = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                sb.append("  selected").append(capitalField).append("Nome: string | null = null;\n");
            }
        }
        sb.append("\n");

        sb.append("  constructor(\n");
        sb.append("    public authService: AuthService,\n");
        if (hasSessionContext) {
            sb.append("    public sessionService: SessionService,\n");
        }
        sb.append("    private router: Router\n");
        sb.append("  ) {}\n\n");

        sb.append("  ngOnInit(): void {\n");
        if (hasSessionContext) {
            sb.append("    // Verifica se precisa mostrar o seletor de contexto\n");
            sb.append("    this.authService.isAuthenticated$.subscribe(isAuth => {\n");
            sb.append("      if (isAuth && !this.sessionService.isContextComplete()) {\n");
            sb.append("        this.showSessionSelector = true;\n");
            sb.append("      }\n");
            sb.append("    });\n");
            // Subscribe para atualizar o nome exibido
            for (SessionContext ctx : metaModel.getMetadata().getSessionContext()) {
                String fieldName = ctx.getField();
                String capitalField = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                sb.append("    this.sessionService.").append(fieldName).append("$.subscribe(() => {\n");
                sb.append("      this.selected").append(capitalField).append("Nome = this.sessionService.get")
                  .append(capitalField).append("Nome();\n");
                sb.append("    });\n");
            }
        }
        sb.append("  }\n\n");

        sb.append("  toggleMenu(): void {\n");
        sb.append("    this.menuCollapsed = !this.menuCollapsed;\n");
        sb.append("  }\n\n");

        sb.append("  toggleCategory(categoryId: string): void {\n");
        sb.append("    this.expandedCategories[categoryId] = !this.expandedCategories[categoryId];\n");
        sb.append("  }\n\n");

        if (hasSessionContext) {
            sb.append("  onContextSelected(): void {\n");
            sb.append("    this.showSessionSelector = false;\n");
            // Atualiza o nome exibido no header
            for (SessionContext ctx : metaModel.getMetadata().getSessionContext()) {
                String fieldName = ctx.getField();
                String capitalField = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                sb.append("    this.selected").append(capitalField).append("Nome = this.sessionService.get")
                  .append(capitalField).append("Nome();\n");
            }
            // Força recarregar a rota atual para atualizar os dados
            sb.append("    const currentUrl = this.router.url;\n");
            sb.append("    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {\n");
            sb.append("      this.router.navigateByUrl(currentUrl);\n");
            sb.append("    });\n");
            sb.append("  }\n\n");

            sb.append("  openContextSelector(): void {\n");
            sb.append("    this.showSessionSelector = true;\n");
            sb.append("  }\n\n");
        }

        sb.append("  logout(): void {\n");
        sb.append("    this.authService.logout();\n");
        if (hasSessionContext) {
            sb.append("    this.sessionService.clearContext();\n");
        }
        sb.append("    this.router.navigate(['/login']);\n");
        sb.append("  }\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera app.component.html
     */
    private String generateAppComponentHtml(MetaModel metaModel) {
        String projectName = metaModel.getMetadata() != null ?
            metaModel.getMetadata().getName() : "Application";
        boolean hasSessionContext = metaModel.getMetadata() != null && metaModel.getMetadata().hasSessionContext();

        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"app-container\">\n");

        // Session Selector Modal
        if (hasSessionContext) {
            sb.append("  <!-- Session Selector Modal -->\n");
            sb.append("  <app-session-selector\n");
            sb.append("    *ngIf=\"showSessionSelector\"\n");
            sb.append("    (contextSelected)=\"onContextSelected()\">\n");
            sb.append("  </app-session-selector>\n\n");
        }

        sb.append("  <!-- Header -->\n");
        sb.append("  <header *ngIf=\"(authService.isAuthenticated$ | async)\" class=\"header\">\n");
        sb.append("    <div class=\"header-left\">\n");
        sb.append("      <button class=\"menu-toggle\" (click)=\"toggleMenu()\">\n");
        sb.append("        <span class=\"hamburger\"></span>\n");
        sb.append("      </button>\n");
        sb.append("      <h1>").append(projectName).append("</h1>\n");
        sb.append("    </div>\n");

        // Indicador de contexto selecionado - centralizado no header
        if (hasSessionContext) {
            sb.append("    <div class=\"header-center\">\n");
            for (SessionContext ctx : metaModel.getMetadata().getSessionContext()) {
                String fieldName = ctx.getField();
                String capitalField = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                String propName = "selected" + capitalField + "Nome"; // idEmpresa -> selectedIdEmpresaNome
                String label = ctx.getLabel() != null ? ctx.getLabel() : "Município";
                sb.append("      <div class=\"context-indicator\" (click)=\"openContextSelector()\">\n");
                sb.append("        <svg class=\"context-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
                sb.append("          <path d=\"M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z\"></path>\n");
                sb.append("          <circle cx=\"12\" cy=\"10\" r=\"3\"></circle>\n");
                sb.append("        </svg>\n");
                sb.append("        <span>{{ ").append(propName).append(" || '").append(label).append("' }}</span>\n");
                sb.append("        <svg class=\"edit-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
                sb.append("          <path d=\"M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7\"></path>\n");
                sb.append("          <path d=\"M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z\"></path>\n");
                sb.append("        </svg>\n");
                sb.append("      </div>\n");
            }
            sb.append("    </div>\n");
        }

        sb.append("    <div class=\"header-right\">\n");
        sb.append("      <span>Bem-vindo, <strong>{{ authService.getUsername() }}</strong></span>\n");
        sb.append("      <button class=\"btn-logout\" (click)=\"logout()\">\n");
        sb.append("        <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("          <path d=\"M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4\"></path>\n");
        sb.append("          <polyline points=\"16 17 21 12 16 7\"></polyline>\n");
        sb.append("          <line x1=\"21\" y1=\"12\" x2=\"9\" y2=\"12\"></line>\n");
        sb.append("        </svg>\n");
        sb.append("        <span>Sair</span>\n");
        sb.append("      </button>\n");
        sb.append("    </div>\n");
        sb.append("  </header>\n\n");

        sb.append("  <!-- Layout com Sidebar -->\n");
        sb.append("  <div class=\"main-layout\" *ngIf=\"(authService.isAuthenticated$ | async)\">\n");
        sb.append("    <!-- Sidebar Menu -->\n");
        sb.append("    <aside class=\"sidebar\" [class.collapsed]=\"menuCollapsed\">\n");
        sb.append("      <nav class=\"nav-menu\">\n");

        // Menu Configuração (fixo, sempre presente, primeiro item) - inclui Usuários, Perfis e Análise de Log
        sb.append("        <div class=\"menu-category\">\n");
        sb.append("          <div class=\"category-header\" (click)=\"toggleCategory('configuracao')\">\n");
        sb.append("            <svg class=\"category-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><circle cx=\"12\" cy=\"12\" r=\"3\"></circle><path d=\"M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z\"></path></svg>\n");
        sb.append("            <span>Configuração</span>\n");
        sb.append("            <span class=\"arrow\" [class.expanded]=\"expandedCategories['configuracao']\">▼</span>\n");
        sb.append("          </div>\n");
        sb.append("          <ul class=\"category-items\" [class.expanded]=\"expandedCategories['configuracao']\">\n");
        sb.append("            <li>\n");
        sb.append("              <a routerLink=\"/usuario\" routerLinkActive=\"active\">\n");
        sb.append("                <svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2\"></path><circle cx=\"12\" cy=\"7\" r=\"4\"></circle></svg>\n");
        sb.append("                <span>Usuários</span>\n");
        sb.append("              </a>\n");
        sb.append("            </li>\n");
        sb.append("            <li>\n");
        sb.append("              <a routerLink=\"/perfil\" routerLinkActive=\"active\">\n");
        sb.append("                <svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2\"></path><circle cx=\"9\" cy=\"7\" r=\"4\"></circle><path d=\"M23 21v-2a4 4 0 0 0-3-3.87\"></path><path d=\"M16 3.13a4 4 0 0 1 0 7.75\"></path></svg>\n");
        sb.append("                <span>Perfis</span>\n");
        sb.append("              </a>\n");
        sb.append("            </li>\n");
        sb.append("            <li>\n");
        sb.append("              <a routerLink=\"/audit-log\" routerLinkActive=\"active\">\n");
        sb.append("                <svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z\"></path><polyline points=\"14 2 14 8 20 8\"></polyline><line x1=\"16\" y1=\"13\" x2=\"8\" y2=\"13\"></line><line x1=\"16\" y1=\"17\" x2=\"8\" y2=\"17\"></line><polyline points=\"10 9 9 9 8 9\"></polyline></svg>\n");
        sb.append("                <span>Análise de Log</span>\n");
        sb.append("              </a>\n");
        sb.append("            </li>\n");
        sb.append("          </ul>\n");
        sb.append("        </div>\n");

        // Gerar menu baseado nos modules do metamodel
        if (metaModel.getModules() != null && !metaModel.getModules().isEmpty()) {
            for (br.com.gerador.metamodel.model.Module module : metaModel.getModules()) {
                String moduleId = module.getId();
                String moduleName = module.getName();
                sb.append("        <div class=\"menu-category\">\n");
                sb.append("          <div class=\"category-header\" (click)=\"toggleCategory('").append(moduleId).append("')\">\n");
                sb.append("            ").append(getCategoryIcon(moduleId, moduleName)).append("\n");
                sb.append("            <span>").append(moduleName).append("</span>\n");
                sb.append("            <span class=\"arrow\" [class.expanded]=\"expandedCategories['").append(moduleId).append("']\">▼</span>\n");
                sb.append("          </div>\n");
                sb.append("          <ul class=\"category-items\" [class.expanded]=\"expandedCategories['").append(moduleId).append("']\">\n");

                if (module.getItems() != null) {
                    for (br.com.gerador.metamodel.model.MenuItem item : module.getItems()) {
                        String entityRef = item.getEntityRef();
                        // Não exibir no menu se for uma entidade filha de outra (navegação mestre-detalhe)
                        if (isChildEntity(entityRef)) {
                            continue;
                        }
                        String route = entityRef.substring(0, 1).toLowerCase() + entityRef.substring(1);
                        String itemName = item.getName();
                        sb.append("            <li>\n");
                        sb.append("              <a routerLink=\"/").append(route).append("\" routerLinkActive=\"active\">\n");
                        sb.append("                ").append(getMenuItemIcon(entityRef, itemName)).append("\n");
                        sb.append("                <span>").append(itemName).append("</span>\n");
                        sb.append("              </a>\n");
                        sb.append("            </li>\n");
                    }
                }

                sb.append("          </ul>\n");
                sb.append("        </div>\n");
            }
        }

        sb.append("      </nav>\n");
        sb.append("    </aside>\n\n");

        sb.append("    <!-- Content Area -->\n");
        sb.append("    <main class=\"content\">\n");
        sb.append("      <router-outlet></router-outlet>\n");
        sb.append("    </main>\n");
        sb.append("  </div>\n\n");

        sb.append("  <!-- Router outlet for login (when not authenticated) -->\n");
        sb.append("  <router-outlet *ngIf=\"!(authService.isAuthenticated$ | async)\"></router-outlet>\n");
        sb.append("</div>\n");

        return sb.toString();
    }

    /**
     * Retorna o ícone SVG para uma categoria de menu
     */
    private String getCategoryIcon(String moduleId, String moduleName) {
        String nameLower = moduleName.toLowerCase();
        String idLower = moduleId.toLowerCase();

        // Ícones por categoria
        if (idLower.contains("admin") || nameLower.contains("administra")) {
            // Ícone de engrenagem
            return "<svg class=\"category-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><circle cx=\"12\" cy=\"12\" r=\"3\"></circle><path d=\"M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z\"></path></svg>";
        } else if (idLower.contains("cadastro") || nameLower.contains("cadastro")) {
            // Ícone de pasta/arquivo
            return "<svg class=\"category-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z\"></path></svg>";
        } else if (idLower.contains("financeir") || nameLower.contains("financeir")) {
            // Ícone de dinheiro
            return "<svg class=\"category-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><line x1=\"12\" y1=\"1\" x2=\"12\" y2=\"23\"></line><path d=\"M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6\"></path></svg>";
        } else if (idLower.contains("config") || nameLower.contains("configura")) {
            // Ícone de sliders
            return "<svg class=\"category-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><line x1=\"4\" y1=\"21\" x2=\"4\" y2=\"14\"></line><line x1=\"4\" y1=\"10\" x2=\"4\" y2=\"3\"></line><line x1=\"12\" y1=\"21\" x2=\"12\" y2=\"12\"></line><line x1=\"12\" y1=\"8\" x2=\"12\" y2=\"3\"></line><line x1=\"20\" y1=\"21\" x2=\"20\" y2=\"16\"></line><line x1=\"20\" y1=\"12\" x2=\"20\" y2=\"3\"></line><line x1=\"1\" y1=\"14\" x2=\"7\" y2=\"14\"></line><line x1=\"9\" y1=\"8\" x2=\"15\" y2=\"8\"></line><line x1=\"17\" y1=\"16\" x2=\"23\" y2=\"16\"></line></svg>";
        } else if (idLower.contains("gestao") || nameLower.contains("gestão") || nameLower.contains("gestao")) {
            // Ícone de usuários
            return "<svg class=\"category-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2\"></path><circle cx=\"9\" cy=\"7\" r=\"4\"></circle><path d=\"M23 21v-2a4 4 0 0 0-3-3.87\"></path><path d=\"M16 3.13a4 4 0 0 1 0 7.75\"></path></svg>";
        } else if (idLower.contains("anuidade") || nameLower.contains("anuidade")) {
            // Ícone de calendário
            return "<svg class=\"category-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><rect x=\"3\" y=\"4\" width=\"18\" height=\"18\" rx=\"2\" ry=\"2\"></rect><line x1=\"16\" y1=\"2\" x2=\"16\" y2=\"6\"></line><line x1=\"8\" y1=\"2\" x2=\"8\" y2=\"6\"></line><line x1=\"3\" y1=\"10\" x2=\"21\" y2=\"10\"></line></svg>";
        } else {
            // Ícone genérico de lista
            return "<svg class=\"category-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><line x1=\"8\" y1=\"6\" x2=\"21\" y2=\"6\"></line><line x1=\"8\" y1=\"12\" x2=\"21\" y2=\"12\"></line><line x1=\"8\" y1=\"18\" x2=\"21\" y2=\"18\"></line><line x1=\"3\" y1=\"6\" x2=\"3.01\" y2=\"6\"></line><line x1=\"3\" y1=\"12\" x2=\"3.01\" y2=\"12\"></line><line x1=\"3\" y1=\"18\" x2=\"3.01\" y2=\"18\"></line></svg>";
        }
    }

    /**
     * Verifica se uma entidade é filha de outra (está configurada como childEntity em alguma entidade pai).
     * Entidades filhas não devem aparecer no menu principal, pois são acessadas via navegação mestre-detalhe.
     */
    private boolean isChildEntity(String entityName) {
        if (metaModel == null || metaModel.getEntities() == null) {
            return false;
        }
        for (Entity entity : metaModel.getEntities()) {
            if (entity.hasChildEntities()) {
                for (var child : entity.getChildEntities()) {
                    if (child.getEntity().equals(entityName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Retorna o ícone SVG para um item de menu
     */
    private String getMenuItemIcon(String entityRef, String itemName) {
        String refLower = entityRef.toLowerCase();
        String nameLower = itemName.toLowerCase();

        // Ícones específicos para ICEP
        if (refLower.contains("territorio") || nameLower.contains("território")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><circle cx=\"12\" cy=\"12\" r=\"10\"></circle><line x1=\"2\" y1=\"12\" x2=\"22\" y2=\"12\"></line><path d=\"M12 2a15.3 15.3 0 0 1 4 10 15.3 15.3 0 0 1-4 10 15.3 15.3 0 0 1-4-10 15.3 15.3 0 0 1 4-10z\"></path></svg>";
        } else if (refLower.contains("municipio") || nameLower.contains("município")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M3 21h18\"></path><path d=\"M5 21V7l8-4v18\"></path><path d=\"M19 21V11l-6-4\"></path><path d=\"M9 9v.01\"></path><path d=\"M9 12v.01\"></path><path d=\"M9 15v.01\"></path><path d=\"M9 18v.01\"></path></svg>";
        } else if (refLower.contains("ambitogestao") || nameLower.contains("âmbito")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><circle cx=\"18\" cy=\"18\" r=\"3\"></circle><circle cx=\"6\" cy=\"6\" r=\"3\"></circle><path d=\"M6 21V9a9 9 0 0 0 9 9\"></path></svg>";
        } else if (refLower.contains("publicoalvo") || nameLower.contains("público")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2\"></path><circle cx=\"9\" cy=\"7\" r=\"4\"></circle><path d=\"M23 21v-2a4 4 0 0 0-3-3.87\"></path><path d=\"M16 3.13a4 4 0 0 1 0 7.75\"></path></svg>";
        } else if (refLower.contains("segmentoatendido") || nameLower.contains("segmento")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><line x1=\"8\" y1=\"6\" x2=\"21\" y2=\"6\"></line><line x1=\"8\" y1=\"12\" x2=\"21\" y2=\"12\"></line><line x1=\"8\" y1=\"18\" x2=\"21\" y2=\"18\"></line><line x1=\"3\" y1=\"6\" x2=\"3.01\" y2=\"6\"></line><line x1=\"3\" y1=\"12\" x2=\"3.01\" y2=\"12\"></line><line x1=\"3\" y1=\"18\" x2=\"3.01\" y2=\"18\"></line></svg>";
        } else if (refLower.contains("anoescolar") || nameLower.contains("ano escolar")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><rect x=\"3\" y=\"4\" width=\"18\" height=\"18\" rx=\"2\" ry=\"2\"></rect><line x1=\"16\" y1=\"2\" x2=\"16\" y2=\"6\"></line><line x1=\"8\" y1=\"2\" x2=\"8\" y2=\"6\"></line><line x1=\"3\" y1=\"10\" x2=\"21\" y2=\"10\"></line></svg>";
        } else if (refLower.contains("componentecurricular") || nameLower.contains("componente curricular")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M4 19.5A2.5 2.5 0 0 1 6.5 17H20\"></path><path d=\"M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z\"></path></svg>";
        } else if (refLower.contains("conceitoaprendido") || nameLower.contains("conceito")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><circle cx=\"12\" cy=\"12\" r=\"5\"></circle><line x1=\"12\" y1=\"1\" x2=\"12\" y2=\"3\"></line><line x1=\"12\" y1=\"21\" x2=\"12\" y2=\"23\"></line><line x1=\"4.22\" y1=\"4.22\" x2=\"5.64\" y2=\"5.64\"></line><line x1=\"18.36\" y1=\"18.36\" x2=\"19.78\" y2=\"19.78\"></line><line x1=\"1\" y1=\"12\" x2=\"3\" y2=\"12\"></line><line x1=\"21\" y1=\"12\" x2=\"23\" y2=\"12\"></line><line x1=\"4.22\" y1=\"19.78\" x2=\"5.64\" y2=\"18.36\"></line><line x1=\"18.36\" y1=\"5.64\" x2=\"19.78\" y2=\"4.22\"></line></svg>";
        } else if (refLower.contains("aprendizagemesp") || nameLower.contains("aprendizagem")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M22 10v6M2 10l10-5 10 5-10 5z\"></path><path d=\"M6 12v5c3 3 9 3 12 0v-5\"></path></svg>";
        } else if (refLower.contains("avaliacaosda") || nameLower.contains("avaliação sda")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M22 11.08V12a10 10 0 1 1-5.93-9.14\"></path><polyline points=\"22 4 12 14.01 9 11.01\"></polyline></svg>";
        } else if (refLower.contains("avaliacaoindica") || nameLower.contains("avaliação de indicador")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><line x1=\"18\" y1=\"20\" x2=\"18\" y2=\"10\"></line><line x1=\"12\" y1=\"20\" x2=\"12\" y2=\"4\"></line><line x1=\"6\" y1=\"20\" x2=\"6\" y2=\"14\"></line></svg>";
        } else if (refLower.contains("comentarioindica") || nameLower.contains("comentário")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z\"></path></svg>";
        } else if (refLower.contains("atendimentomunic") || nameLower.contains("atendimento")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2\"></path><circle cx=\"9\" cy=\"7\" r=\"4\"></circle><path d=\"M23 21v-2a4 4 0 0 0-3-3.87\"></path><path d=\"M16 3.13a4 4 0 0 1 0 7.75\"></path></svg>";
        } else if (refLower.contains("cargahoraria") || nameLower.contains("carga horária")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><circle cx=\"12\" cy=\"12\" r=\"10\"></circle><polyline points=\"12 6 12 12 16 14\"></polyline></svg>";
        } else if (refLower.equals("indicador") || nameLower.equals("indicadores")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M12 20V10\"></path><path d=\"M18 20V4\"></path><path d=\"M6 20v-4\"></path></svg>";
        // Ícones específicos por entidade/nome
        } else if (refLower.contains("usuario") || nameLower.contains("usuário")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2\"></path><circle cx=\"12\" cy=\"7\" r=\"4\"></circle></svg>";
        } else if (refLower.contains("pessoa") || nameLower.contains("pessoa")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2\"></path><circle cx=\"12\" cy=\"7\" r=\"4\"></circle></svg>";
        } else if (refLower.contains("empresa") || nameLower.contains("empresa")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z\"></path><polyline points=\"9 22 9 12 15 12 15 22\"></polyline></svg>";
        } else if (refLower.contains("cliente") || nameLower.contains("cliente")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2\"></path><circle cx=\"9\" cy=\"7\" r=\"4\"></circle><path d=\"M23 21v-2a4 4 0 0 0-3-3.87\"></path><path d=\"M16 3.13a4 4 0 0 1 0 7.75\"></path></svg>";
        } else if (refLower.contains("banco") || nameLower.contains("banco")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><rect x=\"1\" y=\"4\" width=\"22\" height=\"16\" rx=\"2\" ry=\"2\"></rect><line x1=\"1\" y1=\"10\" x2=\"23\" y2=\"10\"></line></svg>";
        } else if (refLower.contains("cidade") || nameLower.contains("cidade")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z\"></path><circle cx=\"12\" cy=\"10\" r=\"3\"></circle></svg>";
        } else if (refLower.contains("bairro") || nameLower.contains("bairro")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z\"></path><circle cx=\"12\" cy=\"10\" r=\"3\"></circle></svg>";
        } else if (refLower.contains("lancamento") || nameLower.contains("lançamento")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z\"></path><polyline points=\"14 2 14 8 20 8\"></polyline><line x1=\"16\" y1=\"13\" x2=\"8\" y2=\"13\"></line><line x1=\"16\" y1=\"17\" x2=\"8\" y2=\"17\"></line><polyline points=\"10 9 9 9 8 9\"></polyline></svg>";
        } else if (refLower.contains("nf") || nameLower.contains("nota") || nameLower.contains("fiscal")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z\"></path><polyline points=\"14 2 14 8 20 8\"></polyline><line x1=\"16\" y1=\"13\" x2=\"8\" y2=\"13\"></line><line x1=\"16\" y1=\"17\" x2=\"8\" y2=\"17\"></line><polyline points=\"10 9 9 9 8 9\"></polyline></svg>";
        } else if (refLower.contains("contas") || nameLower.contains("pagar") || nameLower.contains("receber")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><line x1=\"12\" y1=\"1\" x2=\"12\" y2=\"23\"></line><path d=\"M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6\"></path></svg>";
        } else if (refLower.contains("parametro") || nameLower.contains("parâmetro")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><circle cx=\"12\" cy=\"12\" r=\"3\"></circle><path d=\"M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z\"></path></svg>";
        } else if (refLower.contains("email") || nameLower.contains("e-mail")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z\"></path><polyline points=\"22,6 12,13 2,6\"></polyline></svg>";
        } else if (refLower.contains("conselho") || nameLower.contains("conselho")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M22 11.08V12a10 10 0 1 1-5.93-9.14\"></path><polyline points=\"22 4 12 14.01 9 11.01\"></polyline></svg>";
        } else if (refLower.contains("especialidade") || nameLower.contains("especialidade")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><polygon points=\"12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2\"></polygon></svg>";
        } else if (refLower.contains("operadora") || nameLower.contains("operadora")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z\"></path></svg>";
        } else if (refLower.contains("tiposervico") || nameLower.contains("serviço")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z\"></path></svg>";
        } else if (refLower.contains("tipocontato") || nameLower.contains("contato")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z\"></path></svg>";
        } else if (refLower.contains("estadocivil") || nameLower.contains("estado civil")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z\"></path></svg>";
        } else if (refLower.contains("cbo")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><rect x=\"2\" y=\"7\" width=\"20\" height=\"14\" rx=\"2\" ry=\"2\"></rect><path d=\"M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16\"></path></svg>";
        } else if (refLower.contains("cartorio") || nameLower.contains("cartório")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M3 21h18\"></path><path d=\"M5 21V7l8-4v18\"></path><path d=\"M19 21V11l-6-4\"></path><path d=\"M9 9v.01\"></path><path d=\"M9 12v.01\"></path><path d=\"M9 15v.01\"></path><path d=\"M9 18v.01\"></path></svg>";
        } else if (refLower.contains("plano") || nameLower.contains("plano")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><path d=\"M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z\"></path></svg>";
        } else if (refLower.contains("despesa") || refLower.contains("receita") || nameLower.contains("despesa") || nameLower.contains("receita")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><line x1=\"12\" y1=\"1\" x2=\"12\" y2=\"23\"></line><path d=\"M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6\"></path></svg>";
        } else if (refLower.contains("imposto") || nameLower.contains("imposto") || nameLower.contains("tabela ir")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><rect x=\"1\" y=\"4\" width=\"22\" height=\"16\" rx=\"2\" ry=\"2\"></rect><line x1=\"1\" y1=\"10\" x2=\"23\" y2=\"10\"></line></svg>";
        } else if (refLower.contains("indicacao") || nameLower.contains("indica")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><circle cx=\"18\" cy=\"18\" r=\"3\"></circle><circle cx=\"6\" cy=\"6\" r=\"3\"></circle><path d=\"M13 6h3a2 2 0 0 1 2 2v7\"></path><path d=\"M11 18H8a2 2 0 0 1-2-2V9\"></path></svg>";
        } else if (refLower.contains("anuidade") || nameLower.contains("anuidade")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><rect x=\"3\" y=\"4\" width=\"18\" height=\"18\" rx=\"2\" ry=\"2\"></rect><line x1=\"16\" y1=\"2\" x2=\"16\" y2=\"6\"></line><line x1=\"8\" y1=\"2\" x2=\"8\" y2=\"6\"></line><line x1=\"3\" y1=\"10\" x2=\"21\" y2=\"10\"></line></svg>";
        } else if (refLower.contains("repasse") || nameLower.contains("repasse")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><polyline points=\"17 1 21 5 17 9\"></polyline><path d=\"M3 11V9a4 4 0 0 1 4-4h14\"></path><polyline points=\"7 23 3 19 7 15\"></polyline><path d=\"M21 13v2a4 4 0 0 1-4 4H3\"></path></svg>";
        } else if (refLower.contains("pagamento") || nameLower.contains("pagamento")) {
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><rect x=\"1\" y=\"4\" width=\"22\" height=\"16\" rx=\"2\" ry=\"2\"></rect><line x1=\"1\" y1=\"10\" x2=\"23\" y2=\"10\"></line></svg>";
        } else {
            // Ícone genérico de círculo
            return "<svg class=\"menu-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><circle cx=\"12\" cy=\"12\" r=\"10\"></circle></svg>";
        }
    }

    /**
     * Gera app.component.css
     */
    private String generateAppComponentCss() {
        StringBuilder sb = new StringBuilder();

        // Container principal
        sb.append(".app-container {\n");
        sb.append("  min-height: 100vh;\n");
        sb.append("  display: flex;\n");
        sb.append("  flex-direction: column;\n");
        sb.append("}\n\n");

        // Header
        sb.append(".header {\n");
        sb.append("  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n");
        sb.append("  color: white;\n");
        sb.append("  padding: 0.75rem 1.5rem;\n");
        sb.append("  display: flex;\n");
        sb.append("  justify-content: space-between;\n");
        sb.append("  align-items: center;\n");
        sb.append("  position: fixed;\n");
        sb.append("  top: 0;\n");
        sb.append("  left: 0;\n");
        sb.append("  right: 0;\n");
        sb.append("  z-index: 1000;\n");
        sb.append("  box-shadow: 0 2px 4px rgba(0,0,0,0.1);\n");
        sb.append("}\n\n");

        sb.append(".header-left {\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 1rem;\n");
        sb.append("}\n\n");

        sb.append(".header-left h1 {\n");
        sb.append("  margin: 0;\n");
        sb.append("  font-size: 1.25rem;\n");
        sb.append("  font-weight: 600;\n");
        sb.append("}\n\n");

        sb.append(".header-right {\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 1rem;\n");
        sb.append("}\n\n");

        sb.append(".header-center {\n");
        sb.append("  position: absolute;\n");
        sb.append("  left: 50%;\n");
        sb.append("  transform: translateX(-50%);\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  justify-content: center;\n");
        sb.append("}\n\n");

        sb.append(".context-indicator {\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 0.5rem;\n");
        sb.append("  background: rgba(255, 255, 255, 0.15);\n");
        sb.append("  padding: 0.4rem 0.8rem;\n");
        sb.append("  border-radius: 20px;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  transition: background 0.2s;\n");
        sb.append("}\n\n");

        sb.append(".context-indicator:hover {\n");
        sb.append("  background: rgba(255, 255, 255, 0.25);\n");
        sb.append("}\n\n");

        sb.append(".context-indicator .context-icon {\n");
        sb.append("  width: 18px;\n");
        sb.append("  height: 18px;\n");
        sb.append("  flex-shrink: 0;\n");
        sb.append("}\n\n");

        sb.append(".context-indicator span {\n");
        sb.append("  font-size: 0.9rem;\n");
        sb.append("  white-space: nowrap;\n");
        sb.append("}\n\n");

        sb.append(".context-indicator .edit-icon {\n");
        sb.append("  width: 14px;\n");
        sb.append("  height: 14px;\n");
        sb.append("  opacity: 0.7;\n");
        sb.append("  flex-shrink: 0;\n");
        sb.append("}\n\n");

        // Menu toggle button
        sb.append(".menu-toggle {\n");
        sb.append("  background: transparent;\n");
        sb.append("  border: none;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  padding: 0.5rem;\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  justify-content: center;\n");
        sb.append("}\n\n");

        sb.append(".hamburger {\n");
        sb.append("  display: block;\n");
        sb.append("  width: 20px;\n");
        sb.append("  height: 2px;\n");
        sb.append("  background: white;\n");
        sb.append("  position: relative;\n");
        sb.append("}\n\n");

        sb.append(".hamburger::before,\n");
        sb.append(".hamburger::after {\n");
        sb.append("  content: '';\n");
        sb.append("  position: absolute;\n");
        sb.append("  width: 20px;\n");
        sb.append("  height: 2px;\n");
        sb.append("  background: white;\n");
        sb.append("  left: 0;\n");
        sb.append("}\n\n");

        sb.append(".hamburger::before { top: -6px; }\n");
        sb.append(".hamburger::after { top: 6px; }\n\n");

        // Logout button
        sb.append(".btn-logout {\n");
        sb.append("  display: inline-flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 0.4rem;\n");
        sb.append("  background: white;\n");
        sb.append("  color: #667eea;\n");
        sb.append("  border: none;\n");
        sb.append("  padding: 0.5rem 1rem;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  font-weight: 600;\n");
        sb.append("  transition: all 0.2s;\n");
        sb.append("}\n\n");

        sb.append(".btn-logout .icon {\n");
        sb.append("  width: 18px;\n");
        sb.append("  height: 18px;\n");
        sb.append("}\n\n");

        sb.append(".btn-logout:hover {\n");
        sb.append("  background: #f0f0f0;\n");
        sb.append("}\n\n");

        // Main layout
        sb.append(".main-layout {\n");
        sb.append("  display: flex;\n");
        sb.append("  margin-top: 56px;\n");
        sb.append("  min-height: calc(100vh - 56px);\n");
        sb.append("}\n\n");

        // Sidebar
        sb.append(".sidebar {\n");
        sb.append("  width: 250px;\n");
        sb.append("  background: #2c3e50;\n");
        sb.append("  color: white;\n");
        sb.append("  position: fixed;\n");
        sb.append("  top: 56px;\n");
        sb.append("  left: 0;\n");
        sb.append("  bottom: 0;\n");
        sb.append("  overflow-y: auto;\n");
        sb.append("  transition: transform 0.3s ease;\n");
        sb.append("  z-index: 999;\n");
        sb.append("}\n\n");

        sb.append(".sidebar.collapsed {\n");
        sb.append("  transform: translateX(-250px);\n");
        sb.append("}\n\n");

        // Nav menu
        sb.append(".nav-menu {\n");
        sb.append("  padding: 0.5rem 0;\n");
        sb.append("}\n\n");

        // Menu category
        sb.append(".menu-category {\n");
        sb.append("  border-bottom: 1px solid rgba(255,255,255,0.1);\n");
        sb.append("}\n\n");

        sb.append(".category-header {\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 0.5rem;\n");
        sb.append("  padding: 0.75rem 1rem;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  font-weight: 600;\n");
        sb.append("  font-size: 0.85rem;\n");
        sb.append("  text-transform: uppercase;\n");
        sb.append("  letter-spacing: 0.5px;\n");
        sb.append("  background: rgba(0,0,0,0.1);\n");
        sb.append("  transition: background 0.2s;\n");
        sb.append("}\n\n");

        sb.append(".category-header span:last-child {\n");
        sb.append("  margin-left: auto;\n");
        sb.append("}\n\n");

        sb.append(".category-header:hover {\n");
        sb.append("  background: rgba(0,0,0,0.2);\n");
        sb.append("}\n\n");

        sb.append(".category-icon {\n");
        sb.append("  width: 18px;\n");
        sb.append("  height: 18px;\n");
        sb.append("  flex-shrink: 0;\n");
        sb.append("}\n\n");

        sb.append(".arrow {\n");
        sb.append("  font-size: 0.7rem;\n");
        sb.append("  transition: transform 0.2s;\n");
        sb.append("}\n\n");

        sb.append(".arrow.expanded {\n");
        sb.append("  transform: rotate(180deg);\n");
        sb.append("}\n\n");

        // Category items
        sb.append(".category-items {\n");
        sb.append("  list-style: none;\n");
        sb.append("  margin: 0;\n");
        sb.append("  padding: 0;\n");
        sb.append("  max-height: 0;\n");
        sb.append("  overflow: hidden;\n");
        sb.append("  transition: max-height 0.3s ease;\n");
        sb.append("}\n\n");

        sb.append(".category-items.expanded {\n");
        sb.append("  max-height: 500px;\n");
        sb.append("}\n\n");

        sb.append(".category-items li a {\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 0.5rem;\n");
        sb.append("  padding: 0.6rem 1rem 0.6rem 1.5rem;\n");
        sb.append("  color: rgba(255,255,255,0.8);\n");
        sb.append("  text-decoration: none;\n");
        sb.append("  font-size: 0.9rem;\n");
        sb.append("  transition: all 0.2s;\n");
        sb.append("}\n\n");

        sb.append(".menu-icon {\n");
        sb.append("  width: 16px;\n");
        sb.append("  height: 16px;\n");
        sb.append("  flex-shrink: 0;\n");
        sb.append("  opacity: 0.7;\n");
        sb.append("}\n\n");

        sb.append(".category-items li a:hover {\n");
        sb.append("  background: rgba(255,255,255,0.1);\n");
        sb.append("  color: white;\n");
        sb.append("  padding-left: 1.75rem;\n");
        sb.append("}\n\n");

        sb.append(".category-items li a:hover .menu-icon {\n");
        sb.append("  opacity: 1;\n");
        sb.append("}\n\n");

        sb.append(".category-items li a.active {\n");
        sb.append("  background: rgba(102, 126, 234, 0.3);\n");
        sb.append("  color: white;\n");
        sb.append("  border-left: 3px solid #667eea;\n");
        sb.append("}\n\n");

        sb.append(".category-items li a.active .menu-icon {\n");
        sb.append("  opacity: 1;\n");
        sb.append("}\n\n");

        // Content area
        sb.append(".content {\n");
        sb.append("  flex: 1;\n");
        sb.append("  margin-left: 250px;\n");
        sb.append("  padding: 1.5rem;\n");
        sb.append("  background: #f5f7fa;\n");
        sb.append("  transition: margin-left 0.3s ease;\n");
        sb.append("}\n\n");

        sb.append(".sidebar.collapsed + .content {\n");
        sb.append("  margin-left: 0;\n");
        sb.append("}\n\n");

        // Responsive
        sb.append("@media (max-width: 768px) {\n");
        sb.append("  .sidebar {\n");
        sb.append("    transform: translateX(-250px);\n");
        sb.append("  }\n\n");
        sb.append("  .sidebar:not(.collapsed) {\n");
        sb.append("    transform: translateX(0);\n");
        sb.append("  }\n\n");
        sb.append("  .content {\n");
        sb.append("    margin-left: 0;\n");
        sb.append("  }\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera script instalar.bat
     */
    private String generateInstalarBat() {
        StringBuilder sb = new StringBuilder();

        sb.append("@echo off\n");
        sb.append("echo ============================================\n");
        sb.append("echo Instalando dependencias do projeto Angular...\n");
        sb.append("echo ============================================\n");
        sb.append("echo.\n\n");

        sb.append("REM Verifica se o Node.js esta instalado\n");
        sb.append("where node >nul 2>nul\n");
        sb.append("if %ERRORLEVEL% NEQ 0 (\n");
        sb.append("    echo ERRO: Node.js nao encontrado no PATH.\n");
        sb.append("    echo Instale o Node.js em https://nodejs.org/\n");
        sb.append("    pause\n");
        sb.append("    exit /b 1\n");
        sb.append(")\n\n");

        sb.append("REM Verifica se o npm esta instalado\n");
        sb.append("where npm >nul 2>nul\n");
        sb.append("if %ERRORLEVEL% NEQ 0 (\n");
        sb.append("    echo ERRO: npm nao encontrado no PATH.\n");
        sb.append("    echo O npm geralmente vem com o Node.js.\n");
        sb.append("    pause\n");
        sb.append("    exit /b 1\n");
        sb.append(")\n\n");

        sb.append("REM Exibe versões\n");
        sb.append("echo Versoes instaladas:\n");
        sb.append("node --version\n");
        sb.append("npm --version\n");
        sb.append("echo.\n\n");

        sb.append("REM Instala as dependencias\n");
        sb.append("echo Instalando dependencias (isso pode levar alguns minutos)...\n");
        sb.append("call npm install\n\n");

        sb.append("if %ERRORLEVEL% EQU 0 (\n");
        sb.append("    echo.\n");
        sb.append("    echo ============================================\n");
        sb.append("    echo Instalacao concluida com sucesso!\n");
        sb.append("    echo ============================================\n");
        sb.append("    echo.\n");
        sb.append("    echo Para executar o projeto, use: executar.bat\n");
        sb.append(") else (\n");
        sb.append("    echo.\n");
        sb.append("    echo ============================================\n");
        sb.append("    echo ERRO na instalacao!\n");
        sb.append("    echo ============================================\n");
        sb.append(")\n\n");

        sb.append("pause\n");

        return sb.toString();
    }

    /**
     * Gera script executar.bat
     */
    private String generateExecutarBat() {
        StringBuilder sb = new StringBuilder();

        sb.append("@echo off\n");
        sb.append("setlocal enabledelayedexpansion\n\n");
        sb.append("echo ============================================\n");
        sb.append("echo Executando projeto Angular...\n");
        sb.append("echo ============================================\n");
        sb.append("echo.\n\n");

        sb.append("REM Verifica se o Node.js esta instalado\n");
        sb.append("where node >nul 2>nul\n");
        sb.append("if %ERRORLEVEL% NEQ 0 (\n");
        sb.append("    echo ERRO: Node.js nao encontrado no PATH.\n");
        sb.append("    echo Instale o Node.js em https://nodejs.org/\n");
        sb.append("    pause\n");
        sb.append("    exit /b 1\n");
        sb.append(")\n\n");

        sb.append("REM Verifica se o npm esta instalado\n");
        sb.append("where npm >nul 2>nul\n");
        sb.append("if %ERRORLEVEL% NEQ 0 (\n");
        sb.append("    echo ERRO: npm nao encontrado no PATH.\n");
        sb.append("    pause\n");
        sb.append("    exit /b 1\n");
        sb.append(")\n\n");

        sb.append("REM Verifica se node_modules existe\n");
        sb.append("if not exist \"node_modules\" (\n");
        sb.append("    echo AVISO: Dependencias nao instaladas.\n");
        sb.append("    echo Execute instalar.bat primeiro.\n");
        sb.append("    echo.\n");
        sb.append("    set /p \"resposta=Deseja instalar agora? (S/N): \"\n");
        sb.append("    if /i \"!resposta!\"==\"S\" (\n");
        sb.append("        call instalar.bat\n");
        sb.append("        if errorlevel 1 (\n");
        sb.append("            echo Falha na instalacao.\n");
        sb.append("            pause\n");
        sb.append("            exit /b 1\n");
        sb.append("        )\n");
        sb.append("    ) else (\n");
        sb.append("        echo Instalacao cancelada.\n");
        sb.append("        pause\n");
        sb.append("        exit /b 1\n");
        sb.append("    )\n");
        sb.append(")\n\n");

        sb.append("echo Iniciando servidor de desenvolvimento...\n");
        sb.append("echo.\n");
        sb.append("echo A aplicacao estara disponivel em: http://localhost:4200\n");
        sb.append("echo Pressione Ctrl+C para parar o servidor.\n");
        sb.append("echo.\n\n");

        sb.append("REM Inicia o servidor Angular\n");
        sb.append("call npm start\n");

        return sb.toString();
    }

    /**
     * Escreve um arquivo no disco.
     */
    private void writeFile(String relativePath, String content, String displayName) {
        try {
            Path filePath = outputDir.resolve(relativePath);
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, content, StandardCharsets.UTF_8);
            generatedFiles.add(filePath.toString());
            System.out.println("  [OK] " + displayName);
        } catch (IOException e) {
            String error = "Erro ao gerar " + displayName + ": " + e.getMessage();
            errors.add(error);
            System.err.println("  [ERRO] " + error);
        }
    }

    /**
     * Classe para resultado da geração.
     */
    public static class GenerationResult {
        private final List<String> files;
        private final List<String> errors;

        public GenerationResult(List<String> files, List<String> errors) {
            this.files = new ArrayList<>(files);
            this.errors = new ArrayList<>(errors);
        }

        public List<String> getFiles() {
            return files;
        }

        public List<String> getErrors() {
            return errors;
        }

        public int getFileCount() {
            return files.size();
        }

        public boolean hasErrors() {
            return !errors.isEmpty();
        }
    }
}
