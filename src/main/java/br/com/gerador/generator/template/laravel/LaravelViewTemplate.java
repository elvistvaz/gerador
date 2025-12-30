package br.com.gerador.generator.template.laravel;

import br.com.gerador.generator.config.ProjectConfig;
import br.com.gerador.metamodel.model.Field;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.MetaModel;

import java.util.stream.Collectors;

/**
 * Template para geração de views Blade do Laravel (CRUD).
 */
public class LaravelViewTemplate {

    private ProjectConfig projectConfig;
    private MetaModel metaModel;

    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }

    /**
     * Gera a view de listagem (index) para uma entidade.
     */
    public String generateIndexView(Entity entity, MetaModel metaModel) {
        this.metaModel = metaModel; // Armazenar para uso em métodos auxiliares
        String entityName = entity.getName();
        String entityNameLower = toLowerCamelCase(entityName);
        String entityNamePlural = toPlural(entityNameLower); // Para nomes de variáveis Blade
        String displayName = entity.getDisplayName() != null ? entity.getDisplayName() : entityName;

        StringBuilder html = new StringBuilder();
        html.append("@extends('layouts.app')\n\n");
        html.append("@section('title', '").append(displayName).append("')\n\n");
        html.append("@section('content')\n");
        html.append("<div class=\"container\">\n");
        html.append("    <div class=\"d-flex justify-content-between align-items-center mb-4\">\n");
        html.append("        <h2>").append(displayName).append("</h2>\n");
        html.append("        <a href=\"{{ route('").append(entityNameLower).append(".create') }}\" class=\"btn btn-primary\">\n");
        html.append("            <i class=\"fas ").append(getActionIcon("create")).append("\"></i> Novo\n");
        html.append("        </a>\n");
        html.append("    </div>\n\n");

        // Seção de filtros ativos (se a entidade usa filtros de sessão)
        if (hasSessionFilters(entity)) {
            html.append("    @if(session('avaliacao_id') || session('municipio_id'))\n");
            html.append("    <div class=\"alert alert-info mb-3\">\n");
            html.append("        <strong><i class=\"fas fa-filter\"></i> Filtros ativos:</strong>\n");
            html.append("        @if(session('avaliacao_id'))\n");
            html.append("            <span class=\"badge bg-primary ms-2\">\n");
            html.append("                Avaliação: {{ \\App\\Models\\Avaliacao::find(session('avaliacao_id'))?->nome ?? 'N/A' }}\n");
            html.append("            </span>\n");
            html.append("        @endif\n");
            html.append("        @if(session('municipio_id'))\n");
            html.append("            <span class=\"badge bg-primary ms-2\">\n");
            html.append("                Município: {{ \\App\\Models\\Municipio::find(session('municipio_id'))?->nome ?? 'N/A' }}\n");
            html.append("            </span>\n");
            html.append("        @endif\n");
            html.append("    </div>\n");
            html.append("    @endif\n\n");
        }

        // Tabela
        html.append("    <div class=\"card\">\n");
        html.append("        <div class=\"card-body\">\n");
        html.append("            <table class=\"table table-hover\">\n");
        html.append("                <thead>\n");
        html.append("                    <tr>\n");

        // Cabeçalhos das colunas (primeiros 5 campos)
        int colCount = 0;
        for (Field attr : entity.getFields()) {
            if (colCount >= 5) break;
            if (!attr.getName().endsWith("_at") && !attr.getName().equals("deleted_at")) {
                // Pular campos que são filtros de sessão (já exibidos no contexto superior)
                if (isSessionFilterField(attr)) {
                    continue;
                }

                // Se for chave primária, mostrar apenas "#"
                String label = attr.isPrimaryKey() ? "#" : getFieldLabel(attr);
                // Determinar alinhamento: enum centralizado, números à direita, FKs e texto à esquerda
                String alignment = getFieldAlignment(attr);
                html.append("                        <th").append(alignment).append(">").append(label).append("</th>\n");
                colCount++;
            }
        }
        html.append("                        <th class=\"text-center\">Ações</th>\n");
        html.append("                    </tr>\n");
        html.append("                </thead>\n");
        html.append("                <tbody>\n");
        html.append("                    @forelse($").append(entityNamePlural).append(" as $").append(entityNameLower).append(")\n");
        html.append("                        <tr>\n");

        // Células de dados
        colCount = 0;
        for (Field attr : entity.getFields()) {
            if (colCount >= 5) break;
            if (!attr.getName().endsWith("_at") && !attr.getName().equals("deleted_at")) {
                // Pular campos que são filtros de sessão (já exibidos no contexto superior)
                if (isSessionFilterField(attr)) {
                    continue;
                }

                String originalAttrName = attr.getName();
                String attrName = toSnakeCase(originalAttrName);
                // Determinar alinhamento: enum centralizado, números à direita, FKs e texto à esquerda
                String alignment = getFieldAlignment(attr);

                // Se for FK, mostrar o rótulo da entidade relacionada ao invés do ID
                if (isForeignKey(attr)) {
                    String relatedEntity = getRelatedEntityName(attr);
                    String displayField = getDisplayFieldForEntity(relatedEntity);
                    String relationName = toCamelCase(relatedEntity); // Usar camelCase para consistência com o model

                    html.append("                            <td").append(alignment).append(">{{ $").append(entityNameLower).append("->").append(relationName).append("?->").append(displayField).append(" ?? '-' }}</td>\n");
                } else if (hasFieldOptions(attr)) {
                    // Se o campo tem opções (ui.options), usar o accessor de label
                    String labelAccessor = attrName + "_label";
                    html.append("                            <td").append(alignment).append(">{{ $").append(entityNameLower).append("->").append(labelAccessor).append(" }}</td>\n");
                } else {
                    html.append("                            <td").append(alignment).append(">{{ $").append(entityNameLower).append("->").append(attrName).append(" }}</td>\n");
                }
                colCount++;
            }
        }

        // Botões de ação
        html.append("                            <td class=\"text-center\">\n");
        html.append("                                <div class=\"btn-group\" role=\"group\">\n");

        // Para rota de edição - usar a chave primária
        String primaryKeyAccess = getPrimaryKeyAccessExpression(entity, entityNameLower);
        html.append("                                    <a href=\"{{ route('").append(entityNameLower).append(".edit', ").append(primaryKeyAccess).append(") }}\" class=\"btn btn-sm btn-outline-primary\" title=\"Editar\">\n");
        html.append("                                        <i class=\"fas ").append(getActionIcon("edit")).append("\"></i> Editar\n");
        html.append("                                    </a>\n");
        html.append("                                    <form action=\"{{ route('").append(entityNameLower).append(".destroy', ").append(primaryKeyAccess).append(") }}\" method=\"POST\" class=\"d-inline\" onsubmit=\"return confirm('Deseja realmente excluir este registro?');\">\n");
        html.append("                                        @csrf\n");
        html.append("                                        @method('DELETE')\n");
        html.append("                                        <button type=\"submit\" class=\"btn btn-sm btn-outline-danger\" title=\"Excluir\">\n");
        html.append("                                            <i class=\"fas ").append(getActionIcon("delete")).append("\"></i> Excluir\n");
        html.append("                                        </button>\n");
        html.append("                                    </form>\n");
        html.append("                                </div>\n");
        html.append("                            </td>\n");
        html.append("                        </tr>\n");
        html.append("                    @empty\n");
        html.append("                        <tr>\n");
        html.append("                            <td colspan=\"").append(colCount + 1).append("\" class=\"text-center text-muted\">Nenhum registro encontrado.</td>\n");
        html.append("                        </tr>\n");
        html.append("                    @endforelse\n");
        html.append("                </tbody>\n");
        html.append("            </table>\n\n");

        // Paginação
        html.append("            <div class=\"d-flex flex-column align-items-center mt-4\">\n");
        html.append("                {{ $").append(entityNamePlural).append("->links('pagination::bootstrap-5') }}\n");
        html.append("            </div>\n");
        html.append("        </div>\n");
        html.append("    </div>\n");
        html.append("</div>\n");
        html.append("@endsection\n");

        return html.toString();
    }

    /**
     * Gera a view de formulário (create/edit) para uma entidade.
     */
    public String generateFormView(Entity entity, MetaModel metaModel) {
        this.metaModel = metaModel; // Armazenar para uso em métodos auxiliares
        String entityName = entity.getName();
        String entityNameLower = toLowerCamelCase(entityName);
        String displayName = entity.getDisplayName() != null ? entity.getDisplayName() : entityName;
        String displayNameSingular = toSingular(displayName);

        StringBuilder html = new StringBuilder();
        html.append("@extends('layouts.app')\n\n");
        html.append("@section('title', isset($").append(entityNameLower).append(") ? 'Editar ").append(displayNameSingular).append("' : 'Novo ").append(displayNameSingular).append("')\n\n");
        html.append("@section('content')\n");
        html.append("<div class=\"container\">\n");
        html.append("    <div class=\"row justify-content-center\">\n");
        html.append("        <div class=\"col-md-8\">\n");
        html.append("            <div class=\"card\">\n");
        html.append("                <div class=\"card-header\">\n");
        html.append("                    <h4>{{ isset($").append(entityNameLower).append(") ? 'Editar ").append(displayNameSingular).append("' : 'Novo ").append(displayNameSingular).append("' }}</h4>\n");
        html.append("                </div>\n");
        html.append("                <div class=\"card-body\">\n");
        html.append("                    <form action=\"{{ isset($").append(entityNameLower).append(") ? route('").append(entityNameLower).append(".update', $").append(entityNameLower).append(") : route('").append(entityNameLower).append(".store') }}\" method=\"POST\">\n");
        html.append("                        @csrf\n");
        html.append("                        @if(isset($").append(entityNameLower).append("))\n");
        html.append("                            @method('PUT')\n");
        html.append("                        @endif\n\n");

        // Campos do formulário
        for (Field attr : entity.getFields()) {
            String originalAttrName = attr.getName(); // Nome original em camelCase do metamodel
            String attrName = toSnakeCase(originalAttrName); // Nome em snake_case para Laravel

            // Pular campos automáticos
            if (attrName.endsWith("_at") || attrName.equals("deleted_at")) {
                continue;
            }

            // Verificar se é chave primária
            boolean isPrimary = entity.getPrimaryKeyFields().stream()
                .anyMatch(f -> toSnakeCase(f.getName()).equals(attrName));

            // Auto-increment: INTEGER primary keys são auto-increment, STRING não são
            boolean isAutoIncrement = isPrimary && isNumericType(attr.getDatabaseType());

            // Pular auto-increment no formulário de criação, mas mostrar no modo edição (readonly)
            if (isPrimary && isAutoIncrement) {
                // Mostrar apenas no modo edição (readonly)
                html.append("                        @if(isset($").append(entityNameLower).append("))\n");
                html.append("                        <div class=\"mb-3\">\n");
                html.append("                            <label for=\"").append(attrName).append("\" class=\"form-label\">").append(getFieldLabel(attr)).append("</label>\n");
                html.append("                            <input type=\"text\" class=\"form-control\" value=\"{{ $").append(entityNameLower).append("->").append(attrName).append(" }}\" readonly>\n");
                html.append("                        </div>\n");
                html.append("                        @endif\n\n");
                continue;
            }

            html.append("                        <div class=\"mb-3\">\n");
            html.append("                            <label for=\"").append(attrName).append("\" class=\"form-label\">").append(getFieldLabel(attr)).append("</label>\n");

            // Determinar tipo de input
            String inputType = getInputType(attr);
            String inputClass = "form-control";

            if (inputType.equals("textarea")) {
                html.append("                            <textarea name=\"").append(attrName).append("\" id=\"").append(attrName).append("\" class=\"").append(inputClass).append(" @error('").append(attrName).append("') is-invalid @enderror\" rows=\"3\">{{ old('").append(attrName).append("', $").append(entityNameLower).append("->").append(attrName).append(" ?? '') }}</textarea>\n");
            } else if (hasFieldOptions(attr)) {
                // Para campos com opções predefinidas (ui.options)
                String modelClass = "\\App\\Models\\" + entityName;
                String methodName = "get" + capitalize(originalAttrName) + "Options";

                html.append("                            <select name=\"").append(attrName).append("\" id=\"").append(attrName).append("\" class=\"").append(inputClass).append(" @error('").append(attrName).append("') is-invalid @enderror\"");
                if (attr.isRequired()) {
                    html.append(" required");
                }
                html.append(">\n");
                html.append("                                <option value=\"\">Selecione...</option>\n");
                html.append("                                @foreach(").append(modelClass).append("::").append(methodName).append("() as $key => $label)\n");
                html.append("                                    <option value=\"{{ $key }}\" {{ old('").append(attrName).append("', $").append(entityNameLower).append("->").append(attrName).append(" ?? '') == $key ? 'selected' : '' }}>{{ $label }}</option>\n");
                html.append("                                @endforeach\n");
                html.append("                            </select>\n");
            } else if (inputType.equals("select") || isForeignKey(attr)) {
                // Para relacionamentos (Foreign Keys)
                String relatedEntity = getRelatedEntityName(attr);
                String relatedEntityPlural = toPlural(relatedEntity.toLowerCase());
                String displayField = getDisplayFieldForEntity(relatedEntity);

                html.append("                            <select name=\"").append(attrName).append("\" id=\"").append(attrName).append("\" class=\"").append(inputClass).append(" @error('").append(attrName).append("') is-invalid @enderror\"");
                if (attr.isRequired()) {
                    html.append(" required");
                }
                html.append(">\n");
                html.append("                                <option value=\"\">Selecione...</option>\n");
                html.append("                                @foreach($").append(relatedEntityPlural).append(" as $item)\n");
                // O value deve ser a chave primária da entidade relacionada (geralmente 'id')
                html.append("                                    <option value=\"{{ $item->id }}\" {{ old('").append(attrName).append("', $").append(entityNameLower).append("->").append(attrName).append(" ?? '') == $item->id ? 'selected' : '' }}>{{ $item->").append(displayField).append(" }}</option>\n");
                html.append("                                @endforeach\n");
                html.append("                            </select>\n");
            } else {
                html.append("                            <input type=\"").append(inputType).append("\" name=\"").append(attrName).append("\" id=\"").append(attrName).append("\" class=\"").append(inputClass).append(" @error('").append(attrName).append("') is-invalid @enderror\" value=\"{{ old('").append(attrName).append("', $").append(entityNameLower).append("->").append(attrName).append(" ?? '') }}\"");

                if (attr.isRequired()) {
                    html.append(" required");
                }
                html.append(">\n");
            }

            html.append("                            @error('").append(attrName).append("')\n");
            html.append("                                <div class=\"invalid-feedback\">{{ $message }}</div>\n");
            html.append("                            @enderror\n");
            html.append("                        </div>\n\n");
        }

        // Botões
        html.append("                        <div class=\"d-flex justify-content-between\">\n");
        html.append("                            <a href=\"{{ route('").append(entityNameLower).append(".index') }}\" class=\"btn btn-secondary\">\n");
        html.append("                                <i class=\"fas ").append(getActionIcon("cancel")).append("\"></i> Cancelar\n");
        html.append("                            </a>\n");
        html.append("                            <button type=\"submit\" class=\"btn btn-primary\">\n");
        html.append("                                <i class=\"fas ").append(getActionIcon("save")).append("\"></i> {{ isset($").append(entityNameLower).append(") ? 'Atualizar' : 'Salvar' }}\n");
        html.append("                            </button>\n");
        html.append("                        </div>\n");
        html.append("                    </form>\n");
        html.append("                </div>\n");
        html.append("            </div>\n");
        html.append("        </div>\n");
        html.append("    </div>\n");
        html.append("</div>\n");
        html.append("@endsection\n");

        return html.toString();
    }

    private String getInputType(Field attr) {
        String type = attr.getDatabaseType() != null ? attr.getDatabaseType().toLowerCase() : "";

        if (type.contains("text") || type.contains("longtext")) {
            return "textarea";
        } else if (type.contains("int") || type.contains("decimal") || type.contains("float") || type.contains("double")) {
            return "number";
        } else if (type.contains("date") && !type.contains("time")) {
            return "date";
        } else if (type.contains("datetime") || type.contains("timestamp")) {
            return "datetime-local";
        } else if (type.contains("bool")) {
            return "checkbox";
        } else if (type.contains("email")) {
            return "email";
        }

        return "text";
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

    private String toSingular(String name) {
        if (name == null || name.isEmpty()) return name;
        // Regras básicas de singularização em português
        if (name.endsWith("ões")) return name.substring(0, name.length() - 3) + "ão"; // Avaliações -> Avaliação
        if (name.endsWith("ães")) return name.substring(0, name.length() - 3) + "ão"; // Capitães -> Capitão
        if (name.endsWith("ies")) return name.substring(0, name.length() - 3) + "y";  // Categories -> Category
        if (name.endsWith("ses")) return name.substring(0, name.length() - 2);        // Analyses -> Analysis
        if (name.endsWith("zes")) return name.substring(0, name.length() - 2);        // Análises -> Análise
        if (name.endsWith("res")) return name.substring(0, name.length() - 2);        // Valores -> Valore
        if (name.endsWith("ais")) return name.substring(0, name.length() - 2) + "l";  // Municipais -> Municipal
        if (name.endsWith("eis")) return name.substring(0, name.length() - 3) + "el"; // Papéis -> Papel
        if (name.endsWith("óis")) return name.substring(0, name.length() - 3) + "ol"; // Faróis -> Farol
        if (name.endsWith("is")) return name.substring(0, name.length() - 2) + "l";   // Territórios -> Território não, mas útil
        if (name.endsWith("s") && !name.endsWith("us") && !name.endsWith("ss")) {
            return name.substring(0, name.length() - 1); // Municípios -> Município
        }
        return name;
    }

    private String toSnakeCase(String name) {
        return name.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    /**
     * Converte uma string para camelCase (primeira letra minúscula).
     * Ex: "AprendizagemEsperada" -> "aprendizagemEsperada"
     */
    private String toCamelCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    private String toHumanReadable(String name) {
        // Converte snake_case para "Snake Case"
        String[] parts = name.split("_");
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            if (result.length() > 0) result.append(" ");
            result.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return result.toString();
    }

    /**
     * Gera a expressão para acessar a chave primária da entidade nas rotas.
     * Se for chave simples, retorna "$entidade->id_campo".
     * Se for chave composta, retorna "[$entidade->id_campo1, $entidade->id_campo2]".
     */
    private String getPrimaryKeyAccessExpression(Entity entity, String entityVariableName) {
        java.util.List<Field> primaryKeys = entity.getPrimaryKeyFields();

        if (primaryKeys.isEmpty()) {
            // Fallback: tentar encontrar campo "id"
            return "$" + entityVariableName + "->id";
        }

        if (primaryKeys.size() == 1) {
            // Chave primária simples - CORRIGIDO: usar snake_case
            String pkName = toSnakeCase(primaryKeys.get(0).getName());
            return "$" + entityVariableName + "->" + pkName;
        } else {
            // Chave primária composta - CORRIGIDO: usar snake_case
            String keys = primaryKeys.stream()
                .map(pk -> "$" + entityVariableName + "->" + toSnakeCase(pk.getName()))
                .collect(Collectors.joining(", "));
            return "[" + keys + "]";
        }
    }

    /**
     * Retorna o label do campo conforme definido no JSON ou fallback para nome humanizado.
     */
    private String getFieldLabel(Field field) {
        // Usar o label do metamodel JSON se disponível
        if (field.getLabel() != null && !field.getLabel().isEmpty()) {
            return field.getLabel();
        }
        // Fallback para nome humanizado
        return toHumanReadable(field.getName());
    }

    /**
     * Verifica se o tipo do banco de dados é numérico (indicativo de auto-increment).
     */
    private boolean isNumericType(String databaseType) {
        if (databaseType == null) return false;
        String type = databaseType.toLowerCase();
        return type.contains("int") || type.contains("serial") || type.contains("identity");
    }

    /**
     * Verifica se um campo é numérico (para alinhar à direita nas tabelas).
     * IMPORTANTE: FKs não devem ser consideradas numéricas porque exibem texto na grid.
     */
    private boolean isNumericField(Field field) {
        if (field == null) return false;

        // FKs não devem ser alinhadas à direita, mesmo que sejam números
        if (isForeignKey(field)) {
            return false;
        }

        // Verificar por DataType
        if (field.getDataType() != null) {
            String dataType = field.getDataType().toString().toLowerCase();
            if (dataType.contains("int") || dataType.contains("long") || dataType.contains("short") ||
                dataType.contains("byte") || dataType.contains("float") || dataType.contains("double") ||
                dataType.contains("decimal") || dataType.contains("numeric") || dataType.contains("number")) {
                return true;
            }
        }

        // Fallback: verificar por databaseType
        if (field.getDatabaseType() != null) {
            String dbType = field.getDatabaseType().toLowerCase();
            return dbType.contains("int") || dbType.contains("serial") || dbType.contains("numeric") ||
                   dbType.contains("decimal") || dbType.contains("number") || dbType.contains("float") ||
                   dbType.contains("double");
        }

        return false;
    }

    /**
     * Verifica se um campo é do tipo ENUM.
     */
    private boolean isEnumField(Field field) {
        if (field == null) return false;

        // Verificar se tem enumRef (principal indicador de ENUM no metamodel)
        if (field.getEnumRef() != null && !field.getEnumRef().isEmpty()) {
            return true;
        }

        // Verificar se o databaseType é enum
        if (field.getDatabaseType() != null) {
            String dbType = field.getDatabaseType().toLowerCase();
            if (dbType.contains("enum")) {
                return true;
            }
        }

        // Verificar se o DataType é ENUM
        if (field.getDataType() != null) {
            String dataType = field.getDataType().toString().toUpperCase();
            if (dataType.equals("ENUM")) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determina o alinhamento CSS para um campo na grid.
     * - ENUM: centralizado (text-center)
     * - Campos com opções (ui.options): centralizado (text-center)
     * - Números (exceto FK): alinhado à direita (text-end)
     * - FKs e texto: alinhado à esquerda (sem classe, padrão)
     */
    private String getFieldAlignment(Field field) {
        if (field == null) return "";

        // ENUM deve ser centralizado
        if (isEnumField(field)) {
            return " class=\"text-center\"";
        }

        // Campos com opções predefinidas devem ser centralizados
        if (hasFieldOptions(field)) {
            return " class=\"text-center\"";
        }

        // Números (mas não FKs) devem ser alinhados à direita
        if (isNumericField(field)) {
            return " class=\"text-end\"";
        }

        // Texto e FKs ficam à esquerda (padrão, sem classe)
        return "";
    }

    /**
     * Verifica se um campo tem opções predefinidas (ui.options).
     */
    private boolean hasFieldOptions(Field field) {
        if (field == null) return false;
        return field.getUi() != null && field.getUi().hasOptions();
    }

    /**
     * Verifica se a entidade tem campos de filtro de sessão.
     * Retorna true se a entidade possui municipio_id ou avaliacao_id.
     */
    private boolean hasSessionFilters(Entity entity) {
        if (entity == null || entity.getFields() == null) return false;

        for (Field field : entity.getFields()) {
            if (isSessionFilterField(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se um campo é usado como filtro de sessão.
     * Campos de sessão (município_id, avaliacao_id) não devem aparecer na grid
     * pois já são exibidos no contexto de sessão na parte superior.
     */
    private boolean isSessionFilterField(Field field) {
        if (field == null) return false;
        String fieldName = toSnakeCase(field.getName());
        return fieldName.equals("municipio_id") || fieldName.equals("avaliacao_id");
    }

    /**
     * Capitaliza a primeira letra de uma string.
     */
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * Verifica se um campo é uma Foreign Key (tem referência a outra entidade).
     */
    private boolean isForeignKey(Field field) {
        // Verifica se tem referência no metadata ou usa heurística
        if (field.isPrimaryKey()) return false;

        String fieldName = field.getName();
        if (fieldName.length() < 3) return false;

        // Suporta dois padrões:
        // 1. idEntidade (começa com "id" seguido de maiúscula) - ex: idCidade
        // 2. entidadeId (termina com "Id") - ex: cidadeId, territorioId
        return (fieldName.startsWith("id") && Character.isUpperCase(fieldName.charAt(2))) ||
               (fieldName.endsWith("Id") && fieldName.length() > 2);
    }

    /**
     * Obtém o nome da entidade relacionada de um campo FK.
     * Usa field.reference.entity se disponível, senão extrai do nome do campo.
     */
    private String getRelatedEntityName(Field field) {
        if (field.getReference() != null && field.getReference().getEntity() != null) {
            return field.getReference().getEntity();
        }
        return extractRelatedEntityName(field.getName());
    }

    /**
     * Extrai o nome da entidade relacionada a partir do nome do campo FK.
     * Exemplo: "idBairro" -> "Bairro", "cidadeId" -> "Cidade"
     */
    private String extractRelatedEntityName(String fieldName) {
        // Padrão 1: idEntidade -> Entidade
        if (fieldName.startsWith("id") && fieldName.length() > 2 && Character.isUpperCase(fieldName.charAt(2))) {
            return fieldName.substring(2); // Remove "id" do início
        }
        // Padrão 2: entidadeId -> Entidade
        if (fieldName.endsWith("Id") && fieldName.length() > 2) {
            String entityName = fieldName.substring(0, fieldName.length() - 2); // Remove "Id" do final
            // Capitaliza primeira letra
            return Character.toUpperCase(entityName.charAt(0)) + entityName.substring(1);
        }
        return fieldName;
    }

    /**
     * Retorna o campo de exibição padrão para uma entidade.
     * Busca no metamodel ou usa heurística se não encontrar.
     */
    private String getDisplayFieldForEntity(String entityName) {
        if (metaModel != null && metaModel.getEntities() != null) {
            // Buscar a entidade no metamodel
            for (Entity entity : metaModel.getEntities()) {
                if (entity.getName().equals(entityName)) {
                    // Se tiver campos, buscar o primeiro campo de texto não-FK
                    if (entity.getFields() != null && !entity.getFields().isEmpty()) {
                        for (Field field : entity.getFields()) {
                            // Pular chave primária e FKs
                            if (field.isPrimaryKey() || isForeignKey(field)) {
                                continue;
                            }
                            // Preferir campos chamados "nome", "descricao", "titulo", etc
                            String fieldName = toSnakeCase(field.getName());
                            if (fieldName.equals("nome") || fieldName.equals("name")) {
                                return fieldName;
                            }
                        }
                        // Se não encontrou "nome", retornar o primeiro campo de texto
                        for (Field field : entity.getFields()) {
                            if (field.isPrimaryKey() || isForeignKey(field)) {
                                continue;
                            }
                            String dataType = field.getDataType() != null ? field.getDataType().toString() : "";
                            if (dataType.equals("STRING") || dataType.equals("TEXT")) {
                                return toSnakeCase(field.getName());
                            }
                        }
                    }
                    break;
                }
            }
        }

        // Fallback: usa "nome" como padrão
        return "nome";
    }

    /**
     * Retorna o ícone configurado para uma ação específica.
     */
    private String getActionIcon(String action) {
        if (projectConfig == null) {
            return getDefaultActionIcon(action);
        }

        String iconPath = "ui.icons.actions." + action;
        String icon = projectConfig.getString(iconPath, null);
        return icon != null ? icon : getDefaultActionIcon(action);
    }

    /**
     * Retorna ícones padrão caso não estejam configurados.
     */
    private String getDefaultActionIcon(String action) {
        return switch (action) {
            case "create" -> "fa-plus";
            case "edit" -> "fa-edit";
            case "delete" -> "fa-trash";
            case "view" -> "fa-eye";
            case "save" -> "fa-save";
            case "cancel" -> "fa-times";
            case "back" -> "fa-arrow-left";
            case "search" -> "fa-search";
            case "filter" -> "fa-filter";
            case "export" -> "fa-download";
            default -> "fa-circle";
        };
    }
}
