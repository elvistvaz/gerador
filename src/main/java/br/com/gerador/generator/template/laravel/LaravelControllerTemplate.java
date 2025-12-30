package br.com.gerador.generator.template.laravel;

import br.com.gerador.generator.config.ProjectConfig;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.MetaModel;

/**
 * Template para geração de Controllers do Laravel.
 */
public class LaravelControllerTemplate {

    private ProjectConfig projectConfig;

    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }

    public String generate(Entity entity, MetaModel metaModel) {
        StringBuilder sb = new StringBuilder();
        String entityName = entity.getName();
        String varName = toCamelCase(entityName);

        // PHP opening tag
        sb.append("<?php\n\n");

        // Namespace
        sb.append("namespace App\\Http\\Controllers;\n\n");

        // Imports
        sb.append("use App\\Models\\").append(entityName).append(";\n");
        sb.append("use App\\Http\\Requests\\Store").append(entityName).append("Request;\n");
        sb.append("use App\\Http\\Requests\\Update").append(entityName).append("Request;\n");
        sb.append("use Illuminate\\Http\\JsonResponse;\n");
        sb.append("use Illuminate\\Http\\Request;\n");
        sb.append("use Exception;\n\n");

        // Class declaration
        sb.append("class ").append(entityName).append("Controller extends Controller\n");
        sb.append("{\n");

        // Index method
        generateIndexMethod(sb, entityName, varName);

        // Show method
        generateShowMethod(sb, entityName, varName);

        // Store method
        generateStoreMethod(sb, entityName, varName);

        // Update method
        generateUpdateMethod(sb, entityName, varName);

        // Destroy method
        generateDestroyMethod(sb, entityName, varName);

        sb.append("}\n");

        return sb.toString();
    }

    private void generateIndexMethod(StringBuilder sb, String entityName, String varName) {
        String tableName = toSnakeCase(entityName);

        sb.append("    /**\n");
        sb.append("     * Display a listing of the resource.\n");
        sb.append("     */\n");
        sb.append("    public function index(Request $request): JsonResponse\n");
        sb.append("    {\n");
        sb.append("        $query = ").append(entityName).append("::query();\n\n");
        sb.append("        // Add filters here if needed\n\n");
        sb.append("        $perPage = $request->input('per_page', 15);\n");
        sb.append("        $").append(varName).append(" = $query->paginate($perPage);\n\n");
        sb.append("        return response()->json($").append(varName).append(");\n");
        sb.append("    }\n\n");
    }

    private void generateShowMethod(StringBuilder sb, String entityName, String varName) {
        sb.append("    /**\n");
        sb.append("     * Display the specified resource.\n");
        sb.append("     */\n");
        sb.append("    public function show($id): JsonResponse\n");
        sb.append("    {\n");
        sb.append("        try {\n");
        sb.append("            $").append(varName).append(" = ").append(entityName).append("::findOrFail($id);\n");
        sb.append("            return response()->json($").append(varName).append(");\n");
        sb.append("        } catch (Exception $e) {\n");
        sb.append("            return response()->json([\n");
        sb.append("                'error' => true,\n");
        sb.append("                'message' => 'Registro não encontrado'\n");
        sb.append("            ], 404);\n");
        sb.append("        }\n");
        sb.append("    }\n\n");
    }

    private void generateStoreMethod(StringBuilder sb, String entityName, String varName) {
        sb.append("    /**\n");
        sb.append("     * Store a newly created resource in storage.\n");
        sb.append("     */\n");
        sb.append("    public function store(Store").append(entityName).append("Request $request): JsonResponse\n");
        sb.append("    {\n");
        sb.append("        try {\n");
        sb.append("            $").append(varName).append(" = ").append(entityName).append("::create($request->validated());\n\n");
        sb.append("            return response()->json([\n");
        sb.append("                'success' => true,\n");
        sb.append("                'message' => '").append(entityName).append(" criado com sucesso',\n");
        sb.append("                'data' => $").append(varName).append("\n");
        sb.append("            ], 201);\n");
        sb.append("        } catch (Exception $e) {\n");
        sb.append("            return response()->json([\n");
        sb.append("                'error' => true,\n");
        sb.append("                'message' => 'Erro ao criar ").append(entityName).append(": ' . $e->getMessage()\n");
        sb.append("            ], 500);\n");
        sb.append("        }\n");
        sb.append("    }\n\n");
    }

    private void generateUpdateMethod(StringBuilder sb, String entityName, String varName) {
        sb.append("    /**\n");
        sb.append("     * Update the specified resource in storage.\n");
        sb.append("     */\n");
        sb.append("    public function update(Update").append(entityName).append("Request $request, $id): JsonResponse\n");
        sb.append("    {\n");
        sb.append("        try {\n");
        sb.append("            $").append(varName).append(" = ").append(entityName).append("::findOrFail($id);\n");
        sb.append("            $").append(varName).append("->update($request->validated());\n\n");
        sb.append("            return response()->json([\n");
        sb.append("                'success' => true,\n");
        sb.append("                'message' => '").append(entityName).append(" atualizado com sucesso',\n");
        sb.append("                'data' => $").append(varName).append("\n");
        sb.append("            ]);\n");
        sb.append("        } catch (Exception $e) {\n");
        sb.append("            return response()->json([\n");
        sb.append("                'error' => true,\n");
        sb.append("                'message' => 'Erro ao atualizar ").append(entityName).append(": ' . $e->getMessage()\n");
        sb.append("            ], 500);\n");
        sb.append("        }\n");
        sb.append("    }\n\n");
    }

    private void generateDestroyMethod(StringBuilder sb, String entityName, String varName) {
        sb.append("    /**\n");
        sb.append("     * Remove the specified resource from storage.\n");
        sb.append("     */\n");
        sb.append("    public function destroy($id): JsonResponse\n");
        sb.append("    {\n");
        sb.append("        try {\n");
        sb.append("            $").append(varName).append(" = ").append(entityName).append("::findOrFail($id);\n");
        sb.append("            $").append(varName).append("->delete();\n\n");
        sb.append("            return response()->json([\n");
        sb.append("                'success' => true,\n");
        sb.append("                'message' => '").append(entityName).append(" excluído com sucesso'\n");
        sb.append("            ]);\n");
        sb.append("        } catch (Exception $e) {\n");
        sb.append("            return response()->json([\n");
        sb.append("                'error' => true,\n");
        sb.append("                'message' => 'Erro ao excluir ").append(entityName).append(": ' . $e->getMessage()\n");
        sb.append("            ], 500);\n");
        sb.append("        }\n");
        sb.append("    }\n");
    }

    private String toCamelCase(String str) {
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
}
