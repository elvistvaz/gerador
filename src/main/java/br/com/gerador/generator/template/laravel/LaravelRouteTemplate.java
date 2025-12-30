package br.com.gerador.generator.template.laravel;

import br.com.gerador.generator.config.ProjectConfig;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.MetaModel;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Template para geração de Routes do Laravel.
 */
public class LaravelRouteTemplate {

    private ProjectConfig projectConfig;

    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }

    public String generate(MetaModel metaModel) {
        StringBuilder sb = new StringBuilder();

        // Usar Set para evitar entidades duplicadas
        Set<String> uniqueEntities = new LinkedHashSet<>();
        for (Entity entity : metaModel.getEntities()) {
            uniqueEntities.add(entity.getName());
        }

        // PHP opening tag
        sb.append("<?php\n\n");

        // Imports
        sb.append("use Illuminate\\Support\\Facades\\Route;\n");

        // Import controllers (sem duplicados)
        for (String entityName : uniqueEntities) {
            sb.append("use App\\Http\\Controllers\\").append(entityName).append("Controller;\n");
        }

        sb.append("\n");

        // Routes comment
        sb.append("/*\n");
        sb.append(" |--------------------------------------------------------------------------\n");
        sb.append(" | API Routes V1\n");
        sb.append(" |--------------------------------------------------------------------------\n");
        sb.append(" |\n");
        sb.append(" | Here is where you can register API routes for your application.\n");
        sb.append(" |\n");
        sb.append(" */\n\n");

        // Protected routes group
        sb.append("Route::middleware(['auth:sanctum'])->group(function () {\n");

        // Resource routes for each entity (sem duplicados)
        for (String entityName : uniqueEntities) {
            String resourceName = toLowerCamelCase(entityName); // URLs semânticas em camelCase
            String controllerName = entityName + "Controller";

            // Se o nome do resource em snake_case for muito longo (>32 chars), adicionar parâmetro abreviado
            String snakeCaseName = toSnakeCase(entityName);
            if (snakeCaseName.length() > 32) {
                String shortName = abbreviateEntityName(entityName);
                sb.append("    Route::apiResource('").append(resourceName).append("', ")
                  .append(controllerName).append("::class)")
                  .append("->parameters(['").append(resourceName).append("' => '").append(shortName).append("'])")
                  .append("->names('api.").append(resourceName).append("');\n");
            } else {
                sb.append("    Route::apiResource('").append(resourceName).append("', ")
                  .append(controllerName).append("::class)")
                  .append("->names('api.").append(resourceName).append("');\n");
            }
        }

        sb.append("});\n\n");

        // Public routes (if needed)
        sb.append("// Public routes\n");
        sb.append("// Route::get('/public-endpoint', [Controller::class, 'method']);\n");

        return sb.toString();
    }

    private String toLowerCamelCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    private String toSnakeCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    /**
     * Abrevia nomes de entidades muito longos para usar em parâmetros de rota.
     * Remove vogais do meio, mantendo apenas consoantes e primeira/última letra.
     */
    private String abbreviateEntityName(String entityName) {
        String snakeCaseName = toSnakeCase(entityName);

        // Se já está dentro do limite, retornar como está
        if (snakeCaseName.length() <= 32) {
            return snakeCaseName;
        }

        // Estratégia: pegar primeira letra de cada palavra
        String[] words = snakeCaseName.split("_");
        StringBuilder abbreviated = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                // Para palavras pequenas (<=3 chars), manter completa
                if (word.length() <= 3) {
                    abbreviated.append(word);
                } else {
                    // Para palavras maiores, pegar primeiras 3 letras
                    abbreviated.append(word.substring(0, Math.min(3, word.length())));
                }
                abbreviated.append("_");
            }
        }

        // Remover último underscore
        if (abbreviated.length() > 0 && abbreviated.charAt(abbreviated.length() - 1) == '_') {
            abbreviated.setLength(abbreviated.length() - 1);
        }

        String result = abbreviated.toString();

        // Se ainda estiver muito longo, usar apenas iniciais
        if (result.length() > 32) {
            abbreviated = new StringBuilder();
            for (String word : words) {
                if (word.length() > 0) {
                    abbreviated.append(word.charAt(0));
                }
            }
            result = abbreviated.toString();
        }

        return result;
    }
}
