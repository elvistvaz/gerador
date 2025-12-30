package br.com.gerador.generator.template.laravel;

import br.com.gerador.generator.config.ProjectConfig;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.MetaModel;

/**
 * Template para geração de Web Controllers do Laravel (retornam views).
 */
public class LaravelWebControllerTemplate {

    private ProjectConfig projectConfig;

    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }

    /**
     * Gera um Web Controller completo para uma entidade.
     */
    public String generate(Entity entity, MetaModel metaModel) {
        String entityName = entity.getName();
        String entityNameLower = toLowerCamelCase(entityName);
        String entityNamePlural = toPlural(entityNameLower); // Para nomes de variáveis
        String namespace = getNamespace();

        StringBuilder code = new StringBuilder();

        // Namespace e imports
        code.append("<?php\n\n");
        code.append("namespace ").append(namespace).append("\\Http\\Controllers\\Web;\n\n");
        code.append("use ").append(namespace).append("\\Http\\Controllers\\Controller;\n");
        code.append("use ").append(namespace).append("\\Models\\").append(entityName).append(";\n");
        code.append("use ").append(namespace).append("\\Http\\Requests\\Store").append(entityName).append("Request;\n");
        code.append("use ").append(namespace).append("\\Http\\Requests\\Update").append(entityName).append("Request;\n");
        code.append("use Illuminate\\Http\\Request;\n\n");

        // Classe
        code.append("/**\n");
        code.append(" * Web Controller para ").append(entityName).append(".\n");
        code.append(" * Retorna views Blade para o CRUD.\n");
        code.append(" */\n");
        code.append("class ").append(entityName).append("Controller extends Controller\n");
        code.append("{\n");

        // Method: index
        code.append("    /**\n");
        code.append("     * Lista todos os registros.\n");
        code.append("     */\n");
        code.append("    public function index()\n");
        code.append("    {\n");
        code.append("        $").append(entityNamePlural).append(" = ").append(entityName).append("::paginate(15);\n");
        code.append("        return view('").append(entityNameLower).append(".index', compact('").append(entityNamePlural).append("'));\n");
        code.append("    }\n\n");

        // Method: create
        code.append("    /**\n");
        code.append("     * Mostra o formulário de criação.\n");
        code.append("     */\n");
        code.append("    public function create()\n");
        code.append("    {\n");

        // Carregar dados de FKs
        String fkLoads = generateForeignKeyLoads(entity, metaModel);
        if (!fkLoads.isEmpty()) {
            code.append(fkLoads);
            code.append("        return view('").append(entityNameLower).append(".form', compact(");
            code.append(generateCompactList(entity));
            code.append("));\n");
        } else {
            code.append("        return view('").append(entityNameLower).append(".form');\n");
        }

        code.append("    }\n\n");

        // Method: store
        code.append("    /**\n");
        code.append("     * Armazena um novo registro.\n");
        code.append("     */\n");
        code.append("    public function store(Store").append(entityName).append("Request $request)\n");
        code.append("    {\n");
        code.append("        $").append(entityNameLower).append(" = ").append(entityName).append("::create($request->validated());\n");
        code.append("        return redirect()->route('").append(entityNameLower).append(".index')\n");
        code.append("            ->with('success', '").append(entityName).append(" criado com sucesso!');\n");
        code.append("    }\n\n");

        // Method: edit
        code.append("    /**\n");
        code.append("     * Mostra o formulário de edição.\n");
        code.append("     */\n");
        code.append("    public function edit(").append(entityName).append(" $").append(entityNameLower).append(")\n");
        code.append("    {\n");

        // Carregar dados de FKs
        if (!fkLoads.isEmpty()) {
            code.append(fkLoads);
            code.append("        return view('").append(entityNameLower).append(".form', compact('").append(entityNameLower).append("', ");
            code.append(generateCompactList(entity));
            code.append("));\n");
        } else {
            code.append("        return view('").append(entityNameLower).append(".form', compact('").append(entityNameLower).append("'));\n");
        }

        code.append("    }\n\n");

        // Method: update
        code.append("    /**\n");
        code.append("     * Atualiza um registro existente.\n");
        code.append("     */\n");
        code.append("    public function update(Update").append(entityName).append("Request $request, ").append(entityName).append(" $").append(entityNameLower).append(")\n");
        code.append("    {\n");
        code.append("        $").append(entityNameLower).append("->update($request->validated());\n");
        code.append("        return redirect()->route('").append(entityNameLower).append(".index')\n");
        code.append("            ->with('success', '").append(entityName).append(" atualizado com sucesso!');\n");
        code.append("    }\n\n");

        // Method: destroy
        code.append("    /**\n");
        code.append("     * Remove um registro.\n");
        code.append("     */\n");
        code.append("    public function destroy(").append(entityName).append(" $").append(entityNameLower).append(")\n");
        code.append("    {\n");
        code.append("        $").append(entityNameLower).append("->delete();\n");
        code.append("        return redirect()->route('").append(entityNameLower).append(".index')\n");
        code.append("            ->with('success', '").append(entityName).append(" excluído com sucesso!');\n");
        code.append("    }\n");

        code.append("}\n");

        return code.toString();
    }

    private String getNamespace() {
        // Laravel usa sempre "App" como namespace base
        return "App";
    }

    private String toLowerCamelCase(String name) {
        if (name == null || name.isEmpty()) return name;
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }

    private String toPlural(String name) {
        if (name.endsWith("s")) return name + "es";
        if (name.endsWith("y")) return name.substring(0, name.length() - 1) + "ies";
        return name + "s";
    }

    /**
     * Gera o código para carregar entidades relacionadas (FKs).
     */
    private String generateForeignKeyLoads(Entity entity, MetaModel metaModel) {
        StringBuilder loads = new StringBuilder();
        java.util.Set<String> loadedEntities = new java.util.HashSet<>();

        for (br.com.gerador.metamodel.model.Field field : entity.getFields()) {
            if (isForeignKey(field)) {
                String relatedEntityName = extractRelatedEntityName(field.getName());

                // Evitar duplicatas
                if (!loadedEntities.contains(relatedEntityName)) {
                    loadedEntities.add(relatedEntityName);
                    String relatedEntityPlural = toPlural(relatedEntityName.toLowerCase());

                    loads.append("        $").append(relatedEntityPlural)
                         .append(" = \\App\\Models\\").append(relatedEntityName)
                         .append("::all();\n");
                }
            }
        }

        return loads.toString();
    }

    /**
     * Gera a lista de variáveis para o compact().
     */
    private String generateCompactList(Entity entity) {
        StringBuilder list = new StringBuilder();
        java.util.Set<String> entities = new java.util.HashSet<>();

        for (br.com.gerador.metamodel.model.Field field : entity.getFields()) {
            if (isForeignKey(field)) {
                String relatedEntityName = extractRelatedEntityName(field.getName());
                if (!entities.contains(relatedEntityName)) {
                    entities.add(relatedEntityName);
                    String relatedEntityPlural = toPlural(relatedEntityName.toLowerCase());

                    if (list.length() > 0) {
                        list.append(", ");
                    }
                    list.append("'").append(relatedEntityPlural).append("'");
                }
            }
        }

        return list.toString();
    }

    /**
     * Verifica se um campo é FK.
     */
    private boolean isForeignKey(br.com.gerador.metamodel.model.Field field) {
        if (field.isPrimaryKey()) return false;
        String fieldName = field.getName();
        if (fieldName.length() < 3) return false;

        return (fieldName.startsWith("id") && Character.isUpperCase(fieldName.charAt(2))) ||
               (fieldName.endsWith("Id") && fieldName.length() > 2);
    }

    /**
     * Extrai o nome da entidade relacionada.
     */
    private String extractRelatedEntityName(String fieldName) {
        if (fieldName.startsWith("id") && fieldName.length() > 2 && Character.isUpperCase(fieldName.charAt(2))) {
            return fieldName.substring(2);
        }
        if (fieldName.endsWith("Id") && fieldName.length() > 2) {
            String entityName = fieldName.substring(0, fieldName.length() - 2);
            return Character.toUpperCase(entityName.charAt(0)) + entityName.substring(1);
        }
        return fieldName;
    }
}
