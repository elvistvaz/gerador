package br.com.gerador.generator;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configurações do gerador de código.
 */
public class GeneratorConfig {

    private String basePackage = "com.example.app";
    private Path outputDir = Paths.get("generated");
    private String schema = "dbo";
    private boolean generateEntities = true;

    public GeneratorConfig schema(String schema) {
        this.schema = schema;
        return this;
    }

    public String getSchema() {
        return schema;
    }
    private boolean generateRepositories = true;
    private boolean generateServices = true;
    private boolean generateControllers = true;
    private boolean generateDTOs = true;
    private boolean generateMappers = true;
    private boolean overwriteExisting = false;

    public GeneratorConfig() {
    }

    public GeneratorConfig(String basePackage, Path outputDir) {
        this.basePackage = basePackage;
        this.outputDir = outputDir;
    }

    // Builder pattern
    public GeneratorConfig basePackage(String basePackage) {
        this.basePackage = basePackage;
        return this;
    }

    public GeneratorConfig outputDir(Path outputDir) {
        this.outputDir = outputDir;
        return this;
    }

    public GeneratorConfig outputDir(String outputDir) {
        this.outputDir = Paths.get(outputDir);
        return this;
    }

    public GeneratorConfig generateEntities(boolean generate) {
        this.generateEntities = generate;
        return this;
    }

    public GeneratorConfig generateRepositories(boolean generate) {
        this.generateRepositories = generate;
        return this;
    }

    public GeneratorConfig generateServices(boolean generate) {
        this.generateServices = generate;
        return this;
    }

    public GeneratorConfig generateControllers(boolean generate) {
        this.generateControllers = generate;
        return this;
    }

    public GeneratorConfig generateDTOs(boolean generate) {
        this.generateDTOs = generate;
        return this;
    }

    public GeneratorConfig generateMappers(boolean generate) {
        this.generateMappers = generate;
        return this;
    }

    public GeneratorConfig overwriteExisting(boolean overwrite) {
        this.overwriteExisting = overwrite;
        return this;
    }

    // Getters
    public String getBasePackage() {
        return basePackage;
    }

    public Path getOutputDir() {
        return outputDir;
    }

    public boolean isGenerateEntities() {
        return generateEntities;
    }

    public boolean isGenerateRepositories() {
        return generateRepositories;
    }

    public boolean isGenerateServices() {
        return generateServices;
    }

    public boolean isGenerateControllers() {
        return generateControllers;
    }

    public boolean isGenerateDTOs() {
        return generateDTOs;
    }

    public boolean isGenerateMappers() {
        return generateMappers;
    }

    public boolean isOverwriteExisting() {
        return overwriteExisting;
    }

    /**
     * Converte o pacote base para caminho de diretório.
     */
    public Path getPackagePath() {
        return Paths.get(basePackage.replace('.', '/'));
    }

    /**
     * Retorna o caminho completo para um sub-pacote.
     */
    public Path getSubPackagePath(String subPackage) {
        return outputDir.resolve(getPackagePath()).resolve(subPackage);
    }
}
