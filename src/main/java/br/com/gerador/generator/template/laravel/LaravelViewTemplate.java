package br.com.gerador.generator.template.laravel;

import br.com.gerador.generator.config.ProjectConfig;
import br.com.gerador.metamodel.model.DataType;
import br.com.gerador.metamodel.model.Field;
import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.MetaModel;
import br.com.gerador.metamodel.model.UIComponent;

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
     * Obtém o nome da coluna para um campo.
     * Se o campo tem columnName definido, usa ele. Senão, converte para snake_case.
     */
    private String getFieldColumnName(Field field) {
        if (field.getColumnName() != null && !field.getColumnName().isEmpty()) {
            return field.getColumnName();
        }
        return toSnakeCase(field.getName());
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
        if (hasSessionFilters(entity) && metaModel != null && metaModel.getMetadata() != null) {
            var sessionContexts = metaModel.getMetadata().getSessionContext();
            if (sessionContexts != null && !sessionContexts.isEmpty()) {
                // Construir condições para @if
                StringBuilder conditions = new StringBuilder();
                for (int i = 0; i < sessionContexts.size(); i++) {
                    var ctx = sessionContexts.get(i);
                    String fieldSnake = toSnakeCase(ctx.getField());
                    if (i > 0) conditions.append(" || ");
                    conditions.append("session('").append(fieldSnake).append("')");
                }

                html.append("    @if(").append(conditions).append(")\n");
                html.append("    <div class=\"alert alert-info mb-3\">\n");
                html.append("        <strong><i class=\"fas fa-filter\"></i> Contexto ativo:</strong>\n");

                // Gerar badges para cada contexto
                for (var ctx : sessionContexts) {
                    String ctxEntity = ctx.getEntity();
                    String fieldSnake = toSnakeCase(ctx.getField());
                    String displayFieldName = ctx.getDisplayField() != null ? ctx.getDisplayField() : "nome";

                    // Buscar o columnName correto do displayField na entidade
                    String displayField = getColumnNameForField(ctxEntity, displayFieldName);

                    String label = ctx.getLabel() != null ? ctx.getLabel().replace("Selecione a ", "").replace("Selecione o ", "") : ctxEntity;

                    html.append("        @if(session('").append(fieldSnake).append("'))\n");
                    html.append("            <span class=\"badge bg-primary ms-2\">\n");
                    html.append("                ").append(label).append(": {{ \\App\\Models\\").append(ctxEntity).append("::find(session('").append(fieldSnake).append("'))?->").append(displayField).append(" ?? 'N/A' }}\n");
                    html.append("            </span>\n");
                    html.append("        @endif\n");
                }

                html.append("    </div>\n");
                html.append("    @endif\n\n");
            }
        }

        // Formulário de pesquisa (se houver campos com search habilitado)
        String searchForm = generateSearchForm(entity);
        if (!searchForm.isEmpty()) {
            html.append(searchForm);
        }

        // Tabela
        html.append("    <div class=\"card\">\n");
        html.append("        <div class=\"card-body\">\n");
        html.append("            <table class=\"table table-hover\">\n");
        html.append("                <thead>\n");
        html.append("                    <tr>\n");

        // Obter campos visíveis no grid ordenados
        java.util.List<Field> gridFields = getGridVisibleFields(entity);

        // Cabeçalhos das colunas
        for (Field attr : gridFields) {
            // Se for chave primária, mostrar apenas "#"
            String label = attr.isPrimaryKey() ? "#" : getFieldLabel(attr);
            // Determinar alinhamento
            String alignment = getFieldAlignment(attr);

            // Aplicar largura se definida
            String widthStyle = "";
            if (attr.getUi() != null && attr.getUi().getGrid() != null && attr.getUi().getGrid().getWidth() != null) {
                widthStyle = " style=\"width: " + attr.getUi().getGrid().getWidth() + "px\"";
            }

            // Verificar se é sortable
            boolean isSortable = attr.getUi() != null && attr.getUi().getGrid() != null && attr.getUi().getGrid().isSortable();

            if (isSortable) {
                String columnName = getFieldColumnName(attr);
                html.append("                        <th").append(alignment).append(widthStyle).append(">\n");
                html.append("                            <a href=\"{{ route('").append(entityNameLower).append(".index', ['sort' => '").append(columnName).append("', 'direction' => request('sort') == '").append(columnName).append("' && request('direction') == 'asc' ? 'desc' : 'asc']) }}\" class=\"text-decoration-none text-dark\">\n");
                html.append("                                ").append(label).append("\n");
                html.append("                                @if(request('sort') == '").append(columnName).append("')\n");
                html.append("                                    <i class=\"fas fa-sort-{{ request('direction') == 'asc' ? 'up' : 'down' }}\"></i>\n");
                html.append("                                @else\n");
                html.append("                                    <i class=\"fas fa-sort text-muted\"></i>\n");
                html.append("                                @endif\n");
                html.append("                            </a>\n");
                html.append("                        </th>\n");
            } else {
                html.append("                        <th").append(alignment).append(widthStyle).append(">").append(label).append("</th>\n");
            }
        }
        html.append("                        <th class=\"text-center\">Ações</th>\n");
        html.append("                    </tr>\n");
        html.append("                </thead>\n");
        html.append("                <tbody>\n");
        html.append("                    @forelse($").append(entityNamePlural).append(" as $").append(entityNameLower).append(")\n");
        html.append("                        <tr>\n");

        // Células de dados (usar mesmos campos do grid)
        for (Field attr : gridFields) {
            String attrName = getFieldColumnName(attr);
            // Determinar alinhamento: enum centralizado, números à direita, FKs e texto à esquerda
            String alignment = getFieldAlignment(attr);

            // Se for FK, mostrar o rótulo da entidade relacionada ao invés do ID
            if (isForeignKey(attr)) {
                String relatedEntity = getRelatedEntityName(attr);
                String displayField = getDisplayFieldForEntity(relatedEntity);
                String relationName = toCamelCase(relatedEntity);

                html.append("                            <td").append(alignment).append(">{{ $").append(entityNameLower).append("->").append(relationName).append("?->").append(displayField).append(" ?? '-' }}</td>\n");
            } else if (attr.getEnumRef() != null && !attr.getEnumRef().isEmpty()) {
                // Se o campo é ENUM, usar o accessor de label
                String labelAccessor = toCamelCase(attr.getName()) + "_label";
                html.append("                            <td").append(alignment).append(">{{ $").append(entityNameLower).append("->").append(labelAccessor).append(" }}</td>\n");
            } else if (hasFieldOptions(attr)) {
                // Se o campo tem opções (ui.options), usar o accessor de label
                String labelAccessor = attrName + "_label";
                html.append("                            <td").append(alignment).append(">{{ $").append(entityNameLower).append("->").append(labelAccessor).append(" }}</td>\n");
            } else if (isDateField(attr)) {
                // Se for campo de data, formatar como dd/mm/yyyy
                html.append("                            <td").append(alignment).append(">{{ $").append(entityNameLower).append("->").append(attrName).append(" ? \\Carbon\\Carbon::parse($").append(entityNameLower).append("->").append(attrName).append(")->format('d/m/Y') : '-' }}</td>\n");
            } else if (isDateTimeField(attr)) {
                // Se for campo de data/hora, formatar como dd/mm/yyyy HH:mm
                html.append("                            <td").append(alignment).append(">{{ $").append(entityNameLower).append("->").append(attrName).append(" ? \\Carbon\\Carbon::parse($").append(entityNameLower).append("->").append(attrName).append(")->format('d/m/Y H:i') : '-' }}</td>\n");
            } else if (isDecimalField(attr)) {
                // Se for campo decimal/float, formatar com vírgula (padrão brasileiro)
                html.append("                            <td").append(alignment).append(">{{ $").append(entityNameLower).append("->").append(attrName).append(" !== null ? number_format($").append(entityNameLower).append("->").append(attrName).append(", 2, ',', '.') : '-' }}</td>\n");
            } else {
                html.append("                            <td").append(alignment).append(">{{ $").append(entityNameLower).append("->").append(attrName).append(" }}</td>\n");
            }
        }

        // Botões de ação
        html.append("                            <td class=\"text-center\">\n");
        html.append("                                <div class=\"btn-group\" role=\"group\">\n");

        // Para rota de edição - usar Route Model Binding (passa a variável $entidade diretamente)
        html.append("                                    <a href=\"{{ route('").append(entityNameLower).append(".edit', $").append(entityNameLower).append(") }}\" class=\"btn btn-sm btn-outline-primary\" title=\"Editar\">\n");
        html.append("                                        <i class=\"fas ").append(getActionIcon("edit")).append("\"></i> Editar\n");
        html.append("                                    </a>\n");
        html.append("                                    <form action=\"{{ route('").append(entityNameLower).append(".destroy', $").append(entityNameLower).append(") }}\" method=\"POST\" class=\"d-inline\" onsubmit=\"return confirm('Deseja realmente excluir este registro?');\">\n");
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
        html.append("                            <td colspan=\"").append(gridFields.size() + 1).append("\" class=\"text-center text-muted\">Nenhum registro encontrado.</td>\n");
        html.append("                        </tr>\n");
        html.append("                    @endforelse\n");
        html.append("                </tbody>\n");
        html.append("            </table>\n\n");

        // Paginação (centralizada)
        html.append("            <div class=\"d-flex justify-content-center mt-4\">\n");
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

        // Campos do formulário (usar campos visíveis e ordenados conforme metamodel)
        java.util.List<Field> formFields = getFormVisibleFields(entity);

        // Adicionar row do Bootstrap para suportar colSpan
        html.append("                        <div class=\"row\">\n");

        for (Field attr : formFields) {
            String attrName = getFieldColumnName(attr);
            int colSpan = getFormColSpan(attr);

            // Verificar se é chave primária
            boolean isPrimary = attr.isPrimaryKey();

            // Auto-increment: verificar se o campo tem autoIncrement definido
            boolean isAutoIncrement = isPrimary && attr.isAutoIncrement();

            // Pular auto-increment no formulário de criação, mas mostrar no modo edição (readonly)
            if (isPrimary && isAutoIncrement) {
                // Mostrar apenas no modo edição (readonly)
                html.append("                        @if(isset($").append(entityNameLower).append("))\n");
                html.append("                        <div class=\"col-md-").append(colSpan).append(" mb-3\">\n");
                html.append("                            <label for=\"").append(attrName).append("\" class=\"form-label\">").append(getFieldLabel(attr)).append("</label>\n");
                html.append("                            <input type=\"text\" class=\"form-control\" value=\"{{ $").append(entityNameLower).append("->").append(attrName).append(" }}\" readonly>\n");
                html.append("                        </div>\n");
                html.append("                        @endif\n\n");
                continue;
            }

            // Para chaves primárias não auto-increment (como string PKs)
            if (isPrimary && !isAutoIncrement) {
                html.append("                        <div class=\"col-md-").append(colSpan).append(" mb-3\">\n");
                html.append("                            <label for=\"").append(attrName).append("\" class=\"form-label\">").append(getFieldLabel(attr)).append("</label>\n");
                html.append("                            <input type=\"text\" name=\"").append(attrName).append("\" id=\"").append(attrName).append("\" class=\"form-control @error('").append(attrName).append("') is-invalid @enderror\" value=\"{{ old('").append(attrName).append("', $").append(entityNameLower).append("->").append(attrName).append(" ?? '') }}\" {{ isset($").append(entityNameLower).append(") ? 'readonly' : 'required' }}");
                // Adicionar maxlength se o campo tiver tamanho definido
                if (attr.getLength() != null && attr.getLength() > 0) {
                    html.append(" maxlength=\"").append(attr.getLength()).append("\"");
                }
                html.append(">\n");
                html.append("                            @error('").append(attrName).append("')\n");
                html.append("                                <div class=\"invalid-feedback\">{{ $message }}</div>\n");
                html.append("                            @enderror\n");
                html.append("                        </div>\n\n");
                continue;
            }

            html.append("                        <div class=\"col-md-").append(colSpan).append(" mb-3\">\n");

            // Determinar tipo de input
            String inputType = getInputType(attr);
            String inputClass = "form-control";

            // Label (não gerar para checkbox, pois ele tem label próprio)
            if (!inputType.equals("checkbox")) {
                html.append("                            <label for=\"").append(attrName).append("\" class=\"form-label\">").append(getFieldLabel(attr)).append("</label>\n");
            }

            if (inputType.equals("textarea")) {
                html.append("                            <textarea name=\"").append(attrName).append("\" id=\"").append(attrName).append("\" class=\"").append(inputClass).append(" @error('").append(attrName).append("') is-invalid @enderror\" rows=\"3\"");

                // Adicionar placeholder se configurado
                if (attr.getUi() != null && attr.getUi().getPlaceholder() != null && !attr.getUi().getPlaceholder().isEmpty()) {
                    html.append(" placeholder=\"").append(attr.getUi().getPlaceholder()).append("\"");
                }

                html.append(">{{ old('").append(attrName).append("', $").append(entityNameLower).append("->").append(attrName).append(" ?? '') }}</textarea>\n");
            } else if (hasFieldOptions(attr)) {
                // Para campos com opções predefinidas (ui.options)
                String modelClass = "\\App\\Models\\" + entityName;
                String methodName = "get" + capitalize(attr.getName()) + "Options";

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
            } else if (attr.getEnumRef() != null && !attr.getEnumRef().isEmpty()) {
                // Para campos ENUM - gera options hardcoded baseado no metamodel
                html.append("                            <select name=\"").append(attrName).append("\" id=\"").append(attrName).append("\" class=\"").append(inputClass).append(" @error('").append(attrName).append("') is-invalid @enderror\"");
                if (attr.isRequired()) {
                    html.append(" required");
                }
                html.append(">\n");
                html.append("                                <option value=\"\">Selecione...</option>\n");

                // Buscar enum no metamodel e gerar opções
                generateEnumOptions(html, attr, entityNameLower, attrName);

                html.append("                            </select>\n");
            } else if (inputType.equals("select") || isForeignKey(attr)) {
                // Para relacionamentos (Foreign Keys)
                String relatedEntity = getRelatedEntityName(attr);
                String relatedEntityPlural = toPlural(relatedEntity.toLowerCase());

                // Busca o nome correto da PK e do campo de exibição da entidade relacionada
                String relatedPkField = getRelatedEntityPrimaryKey(relatedEntity);
                String displayField = getDisplayFieldForEntity(relatedEntity);

                html.append("                            <select name=\"").append(attrName).append("\" id=\"").append(attrName).append("\" class=\"").append(inputClass).append(" @error('").append(attrName).append("') is-invalid @enderror\"");
                if (attr.isRequired()) {
                    html.append(" required");
                }
                html.append(">\n");
                html.append("                                <option value=\"\">Selecione...</option>\n");
                html.append("                                @foreach($").append(relatedEntityPlural).append(" as $item)\n");
                // Usa a PK correta da entidade relacionada
                html.append("                                    <option value=\"{{ $item->").append(relatedPkField).append(" }}\" {{ old('").append(attrName).append("', $").append(entityNameLower).append("->").append(attrName).append(" ?? '') == $item->").append(relatedPkField).append(" ? 'selected' : '' }}>{{ $item->").append(displayField).append(" }}</option>\n");
                html.append("                                @endforeach\n");
                html.append("                            </select>\n");
            } else if (inputType.equals("checkbox")) {
                // Tratamento especial para checkbox com Bootstrap
                html.append("                            <div class=\"form-check\">\n");
                html.append("                                <input type=\"checkbox\" name=\"").append(attrName).append("\" id=\"").append(attrName).append("\" class=\"form-check-input @error('").append(attrName).append("') is-invalid @enderror\" value=\"1\" {{ old('").append(attrName).append("', $").append(entityNameLower).append("->").append(attrName).append(" ?? false) ? 'checked' : '' }}>\n");
                html.append("                                <label for=\"").append(attrName).append("\" class=\"form-check-label\">").append(getFieldLabel(attr)).append("</label>\n");
                html.append("                                @error('").append(attrName).append("')\n");
                html.append("                                    <div class=\"invalid-feedback\">{{ $message }}</div>\n");
                html.append("                                @enderror\n");
                html.append("                            </div>\n");
            } else {
                html.append("                            <input type=\"").append(inputType).append("\" name=\"").append(attrName).append("\" id=\"").append(attrName).append("\" class=\"").append(inputClass);

                // Adicionar classe para mask se configurado
                if (attr.getUi() != null && attr.getUi().getMask() != null && !attr.getUi().getMask().isEmpty()) {
                    html.append(" input-mask");
                }

                html.append(" @error('").append(attrName).append("') is-invalid @enderror\" value=\"{{ old('").append(attrName).append("', $").append(entityNameLower).append("->").append(attrName).append(" ?? '') }}\"");

                // Adicionar placeholder se configurado
                if (attr.getUi() != null && attr.getUi().getPlaceholder() != null && !attr.getUi().getPlaceholder().isEmpty()) {
                    html.append(" placeholder=\"").append(attr.getUi().getPlaceholder()).append("\"");
                }

                // Adicionar data-mask se configurado
                if (attr.getUi() != null && attr.getUi().getMask() != null && !attr.getUi().getMask().isEmpty()) {
                    html.append(" data-mask=\"").append(attr.getUi().getMask()).append("\"");
                }

                // Adicionar validações HTML5 se configurado
                if (attr.getValidation() != null) {
                    // Min/Max para campos numéricos
                    if (inputType.equals("number")) {
                        if (attr.getValidation().getMin() != null) {
                            html.append(" min=\"").append(attr.getValidation().getMin()).append("\"");
                        }
                        if (attr.getValidation().getMax() != null) {
                            html.append(" max=\"").append(attr.getValidation().getMax()).append("\"");
                        }
                    }

                    // MinLength/MaxLength para campos de texto
                    if (inputType.equals("text") || inputType.equals("email") || inputType.equals("tel")) {
                        if (attr.getValidation().getMinLength() != null) {
                            html.append(" minlength=\"").append(attr.getValidation().getMinLength()).append("\"");
                        }
                        if (attr.getValidation().getMaxLength() != null) {
                            html.append(" maxlength=\"").append(attr.getValidation().getMaxLength()).append("\"");
                        }
                    }

                    // Pattern (regex)
                    if (attr.getValidation().getPattern() != null && !attr.getValidation().getPattern().isEmpty()) {
                        html.append(" pattern=\"").append(attr.getValidation().getPattern()).append("\"");
                    }
                }

                if (attr.isRequired()) {
                    html.append(" required");
                }
                html.append(">\n");
            }

            // @error (não gerar para checkbox, pois ele já tem dentro do form-check)
            if (!inputType.equals("checkbox")) {
                html.append("                            @error('").append(attrName).append("')\n");
                html.append("                                <div class=\"invalid-feedback\">{{ $message }}</div>\n");
                html.append("                            @enderror\n");
            }
            html.append("                        </div>\n\n");
        }

        // Fechar a row do Bootstrap
        html.append("                        </div>\n\n");

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
        // Prioridade 1: ui.component (fonte primária)
        if (attr.getUi() != null && attr.getUi().getComponent() != null) {
            UIComponent component = attr.getUi().getComponent();
            switch (component) {
                case TEXT: return "text";
                case TEXTAREA: return "textarea";
                case NUMBER: return "number";
                case DECIMAL: return "number";
                case DATE: return "date";
                case DATETIME: return "datetime-local";
                case CHECKBOX: return "checkbox";
                case SELECT: return "select";
                case AUTOCOMPLETE: return "text"; // Will add JS later
                case RADIO: return "radio";
                case PASSWORD: return "password";
                case EMAIL: return "email";
                case PHONE: return "tel";
                case CPF: return "text";
                case CNPJ: return "text";
                case CEP: return "text";
                case HIDDEN: return "hidden";
            }
        }

        // Prioridade 2: fallback para databaseType
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
     * Campos de sessão não devem aparecer na grid
     * pois já são exibidos no contexto de sessão na parte superior.
     */
    private boolean isSessionFilterField(Field field) {
        if (field == null || metaModel == null || metaModel.getMetadata() == null) return false;

        String fieldName = toSnakeCase(field.getName());

        // Verifica se o campo está na lista de sessionContext do metamodel
        if (metaModel.getMetadata().getSessionContext() != null) {
            for (var ctx : metaModel.getMetadata().getSessionContext()) {
                String contextField = toSnakeCase(ctx.getField());
                if (fieldName.equals(contextField)) {
                    return true;
                }
            }
        }

        return false;
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
     * Retorna o nome da coluna da chave primária de uma entidade.
     * Busca no metamodel e retorna o columnName da PK.
     */
    private String getRelatedEntityPrimaryKey(String entityName) {
        if (metaModel != null && metaModel.getEntities() != null) {
            // Buscar a entidade no metamodel
            for (Entity entity : metaModel.getEntities()) {
                if (entity.getName().equals(entityName)) {
                    // Buscar a chave primária
                    for (Field field : entity.getFields()) {
                        if (field.isPrimaryKey()) {
                            return getFieldColumnName(field);
                        }
                    }
                }
            }
        }
        // Fallback: retorna 'id' como padrão
        return "id";
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
                            String fieldName = getFieldColumnName(field);
                            String fieldNameLower = fieldName.toLowerCase();
                            if (fieldNameLower.contains("nome") || fieldNameLower.equals("name") ||
                                fieldNameLower.contains("descricao") || fieldNameLower.contains("titulo")) {
                                return fieldName;
                            }
                        }
                        // Se não encontrou campo preferencial, retornar o primeiro campo de texto
                        for (Field field : entity.getFields()) {
                            if (field.isPrimaryKey() || isForeignKey(field)) {
                                continue;
                            }
                            String dataType = field.getDataType() != null ? field.getDataType().toString() : "";
                            if (dataType.equals("STRING") || dataType.equals("TEXT")) {
                                return getFieldColumnName(field);
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

    /**
     * Verifica se o campo é do tipo DATE.
     */
    private boolean isDateField(Field field) {
        return field.getDataType() == DataType.DATE;
    }

    /**
     * Verifica se o campo é do tipo DATETIME.
     */
    private boolean isDateTimeField(Field field) {
        return field.getDataType() == DataType.DATETIME;
    }

    /**
     * Verifica se o campo é do tipo DECIMAL/FLOAT/DOUBLE (ponto flutuante).
     */
    private boolean isDecimalField(Field field) {
        if (field == null) return false;

        // Verificar por DataType
        if (field.getDataType() != null) {
            DataType dataType = field.getDataType();
            if (dataType == DataType.DECIMAL || dataType == DataType.MONEY) {
                return true;
            }
        }

        // Verificar por databaseType
        if (field.getDatabaseType() != null) {
            String dbType = field.getDatabaseType().toLowerCase();
            return dbType.contains("decimal") || dbType.contains("float") ||
                   dbType.contains("double") || dbType.contains("numeric") ||
                   dbType.contains("real") || dbType.contains("money");
        }

        return false;
    }

    /**
     * Busca o columnName correto de um campo em uma entidade.
     * Usado para converter nomes de campos em camelCase para o columnName real (ex: nomeEmpresa -> NomeEmpresa).
     */
    private String getColumnNameForField(String entityName, String fieldName) {
        if (metaModel != null && metaModel.getEntities() != null) {
            // Buscar a entidade no metamodel
            for (Entity entity : metaModel.getEntities()) {
                if (entity.getName().equals(entityName)) {
                    // Buscar o campo pelo nome
                    for (Field field : entity.getFields()) {
                        // Comparar ignorando case e underscores
                        String normalizedFieldName = fieldName.replace("_", "").toLowerCase();
                        String normalizedName = field.getName().replace("_", "").toLowerCase();

                        if (normalizedName.equals(normalizedFieldName)) {
                            return getFieldColumnName(field);
                        }
                    }
                    break;
                }
            }
        }
        // Fallback: retorna o nome original
        return fieldName;
    }

    /**
     * Retorna lista de campos visíveis no grid, ordenados conforme configuração.
     * Usa propriedades grid.visible e grid.order do metamodel.
     */
    private java.util.List<Field> getGridVisibleFields(Entity entity) {
        java.util.List<Field> visibleFields = new java.util.ArrayList<>();

        for (Field field : entity.getFields()) {
            // Pular campos de timestamp e deleted_at
            if (field.getName().endsWith("_at") || field.getName().equals("deleted_at")) {
                continue;
            }

            // Pular campos que são filtros de sessão
            if (isSessionFilterField(field)) {
                continue;
            }

            // Se o campo tem configuração de grid
            if (field.getUi() != null && field.getUi().getGrid() != null) {
                // Verificar se está visível (padrão: true)
                if (field.getUi().getGrid().isVisible()) {
                    visibleFields.add(field);
                }
            } else {
                // Se não tem configuração de grid, incluir por padrão (máximo 5 campos)
                if (visibleFields.size() < 5) {
                    visibleFields.add(field);
                }
            }
        }

        // Ordenar campos conforme grid.order
        visibleFields.sort((f1, f2) -> {
            Integer order1 = (f1.getUi() != null && f1.getUi().getGrid() != null)
                ? f1.getUi().getGrid().getOrder() : null;
            Integer order2 = (f2.getUi() != null && f2.getUi().getGrid() != null)
                ? f2.getUi().getGrid().getOrder() : null;

            // Se ambos têm order, comparar
            if (order1 != null && order2 != null) {
                return order1.compareTo(order2);
            }
            // Se apenas f1 tem order, vem primeiro
            if (order1 != null) {
                return -1;
            }
            // Se apenas f2 tem order, vem primeiro
            if (order2 != null) {
                return 1;
            }
            // Se nenhum tem order, manter ordem original
            return 0;
        });

        return visibleFields;
    }

    /**
     * Retorna lista de campos visíveis no formulário, ordenados conforme configuração.
     * Usa propriedades form.visible e form.order do metamodel.
     */
    private java.util.List<Field> getFormVisibleFields(Entity entity) {
        java.util.List<Field> visibleFields = new java.util.ArrayList<>();

        for (Field field : entity.getFields()) {
            String attrName = getFieldColumnName(field);

            // Pular campos automáticos
            if (attrName.endsWith("_at") || attrName.equals("deleted_at")) {
                continue;
            }

            // Pular campos de sessão (são filtros, não aparecem no form)
            if (isSessionFilterField(field)) {
                continue;
            }

            // Se o campo tem configuração de form
            if (field.getUi() != null && field.getUi().getForm() != null) {
                // Verificar se está visível (padrão: true)
                if (field.getUi().getForm().isVisible()) {
                    visibleFields.add(field);
                }
            } else {
                // Se não tem configuração de form, incluir por padrão
                visibleFields.add(field);
            }
        }

        // Ordenar campos conforme form.order
        visibleFields.sort((f1, f2) -> {
            Integer order1 = (f1.getUi() != null && f1.getUi().getForm() != null)
                ? f1.getUi().getForm().getOrder() : null;
            Integer order2 = (f2.getUi() != null && f2.getUi().getForm() != null)
                ? f2.getUi().getForm().getOrder() : null;

            // Se ambos têm order, comparar
            if (order1 != null && order2 != null) {
                return order1.compareTo(order2);
            }
            // Se apenas f1 tem order, vem primeiro
            if (order1 != null) {
                return -1;
            }
            // Se apenas f2 tem order, vem primeiro
            if (order2 != null) {
                return 1;
            }
            // Se nenhum tem order, manter ordem original
            return 0;
        });

        return visibleFields;
    }

    /**
     * Retorna o número de colunas Bootstrap para o campo (col-md-X).
     * Usa form.colSpan do metamodel (padrão: 12 = linha inteira).
     * Nota: Se colSpan for 1 (default da classe FormConfig), trata como 12.
     */
    private int getFormColSpan(Field field) {
        if (field.getUi() != null && field.getUi().getForm() != null) {
            Integer colSpan = field.getUi().getForm().getColSpan();
            if (colSpan != null && colSpan >= 2 && colSpan <= 12) {
                return colSpan;
            }
        }
        // Padrão: 12 (linha inteira) - também usado quando colSpan == 1
        return 12;
    }

    /**
     * Agrupa campos por tab.
     */
    private java.util.Map<String, java.util.List<Field>> groupFieldsByTab(java.util.List<Field> fields) {
        java.util.Map<String, java.util.List<Field>> groups = new java.util.LinkedHashMap<>();

        for (Field field : fields) {
            String tab = null;
            if (field.getUi() != null && field.getUi().getForm() != null) {
                tab = field.getUi().getForm().getTab();
            }

            if (!groups.containsKey(tab)) {
                groups.put(tab, new java.util.ArrayList<>());
            }
            groups.get(tab).add(field);
        }

        return groups;
    }

    /**
     * Agrupa campos por group dentro de uma mesma tab.
     */
    private java.util.Map<String, java.util.List<Field>> groupFieldsByGroup(java.util.List<Field> fields) {
        java.util.Map<String, java.util.List<Field>> groups = new java.util.LinkedHashMap<>();

        for (Field field : fields) {
            String group = null;
            if (field.getUi() != null && field.getUi().getForm() != null) {
                group = field.getUi().getForm().getGroup();
            }

            if (!groups.containsKey(group)) {
                groups.put(group, new java.util.ArrayList<>());
            }
            groups.get(group).add(field);
        }

        return groups;
    }

    /**
     * Gera formulário de pesquisa baseado em fields com ui.search.enabled = true.
     */
    private String generateSearchForm(Entity entity) {
        StringBuilder form = new StringBuilder();
        java.util.List<Field> searchableFields = new java.util.ArrayList<>();

        // Coletar campos com search habilitado
        for (Field field : entity.getFields()) {
            if (field.getUi() != null && field.getUi().getSearch() != null && field.getUi().getSearch().isEnabled()) {
                searchableFields.add(field);
            }
        }

        if (searchableFields.isEmpty()) {
            return "";
        }

        String entityNameLower = toLowerCamelCase(entity.getName());

        form.append("    <div class=\"card mb-3\">\n");
        form.append("        <div class=\"card-header\">\n");
        form.append("            <h5 class=\"mb-0\">\n");
        form.append("                <i class=\"fas fa-search\"></i> Filtros de Pesquisa\n");
        form.append("                <button class=\"btn btn-sm btn-link float-end\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#searchForm\">\n");
        form.append("                    <i class=\"fas fa-chevron-down\"></i>\n");
        form.append("                </button>\n");
        form.append("            </h5>\n");
        form.append("        </div>\n");
        form.append("        <div class=\"collapse\" id=\"searchForm\">\n");
        form.append("            <div class=\"card-body\">\n");
        form.append("                <form method=\"GET\" action=\"{{ route('").append(entityNameLower).append(".index') }}\">\n");
        form.append("                    <div class=\"row\">\n");

        // Gerar campos de pesquisa
        for (Field field : searchableFields) {
            String paramName = toSnakeCase(field.getName());
            String label = getFieldLabel(field);

            br.com.gerador.metamodel.model.SearchOperator operator = field.getUi().getSearch().getOperator();
            if (operator == null) {
                operator = br.com.gerador.metamodel.model.SearchOperator.EQUALS;
            }

            form.append("                        <div class=\"col-md-4 mb-3\">\n");
            form.append("                            <label for=\"search_").append(paramName).append("\" class=\"form-label\">").append(label).append("</label>\n");

            if (operator == br.com.gerador.metamodel.model.SearchOperator.BETWEEN) {
                // Para BETWEEN, gerar dois campos (from/to)
                form.append("                            <div class=\"input-group\">\n");
                form.append("                                <input type=\"text\" name=\"").append(paramName).append("_from\" id=\"search_").append(paramName).append("_from\" class=\"form-control\" placeholder=\"De\" value=\"{{ request('").append(paramName).append("_from') }}\">\n");
                form.append("                                <span class=\"input-group-text\">até</span>\n");
                form.append("                                <input type=\"text\" name=\"").append(paramName).append("_to\" id=\"search_").append(paramName).append("_to\" class=\"form-control\" placeholder=\"Até\" value=\"{{ request('").append(paramName).append("_to') }}\">\n");
                form.append("                            </div>\n");
            } else if (isForeignKey(field)) {
                // Para FKs, gerar select
                String relatedEntity = getRelatedEntityName(field);
                String relatedEntityPlural = toPlural(relatedEntity.toLowerCase());
                String displayField = getDisplayFieldForEntity(relatedEntity);
                String relatedPkField = getRelatedEntityPrimaryKey(relatedEntity);

                form.append("                            <select name=\"").append(paramName).append("\" id=\"search_").append(paramName).append("\" class=\"form-control\">\n");
                form.append("                                <option value=\"\">Todos</option>\n");
                form.append("                                @foreach($").append(relatedEntityPlural).append(" ?? [] as $item)\n");
                form.append("                                    <option value=\"{{ $item->").append(relatedPkField).append(" }}\" {{ request('").append(paramName).append("') == $item->").append(relatedPkField).append(" ? 'selected' : '' }}>{{ $item->").append(displayField).append(" }}</option>\n");
                form.append("                                @endforeach\n");
                form.append("                            </select>\n");
            } else {
                // Campo de texto simples
                String placeholder = "";
                if (operator == br.com.gerador.metamodel.model.SearchOperator.CONTAINS) {
                    placeholder = " placeholder=\"Buscar em " + label + "...\"";
                }
                form.append("                            <input type=\"text\" name=\"").append(paramName).append("\" id=\"search_").append(paramName).append("\" class=\"form-control\"").append(placeholder).append(" value=\"{{ request('").append(paramName).append("') }}\">\n");
            }

            form.append("                        </div>\n");
        }

        form.append("                    </div>\n");
        form.append("                    <div class=\"d-flex justify-content-end gap-2\">\n");
        form.append("                        <a href=\"{{ route('").append(entityNameLower).append(".index') }}\" class=\"btn btn-secondary\">\n");
        form.append("                            <i class=\"fas fa-eraser\"></i> Limpar\n");
        form.append("                        </a>\n");
        form.append("                        <button type=\"submit\" class=\"btn btn-primary\">\n");
        form.append("                            <i class=\"fas fa-search\"></i> Pesquisar\n");
        form.append("                        </button>\n");
        form.append("                    </div>\n");
        form.append("                </form>\n");
        form.append("            </div>\n");
        form.append("        </div>\n");
        form.append("    </div>\n\n");

        return form.toString();
    }

    /**
     * Gera as opções de um campo ENUM baseado no metamodel.
     */
    private void generateEnumOptions(StringBuilder html, Field field, String entityNameLower, String attrName) {
        if (metaModel == null || metaModel.getEnums() == null) {
            return;
        }

        String enumRef = field.getEnumRef();
        if (enumRef == null || enumRef.isEmpty()) {
            return;
        }

        // Buscar o enum no metamodel
        var enumDef = metaModel.getEnums().stream()
            .filter(e -> e.getId().equals(enumRef))
            .findFirst()
            .orElse(null);

        if (enumDef == null || enumDef.getValues() == null) {
            return;
        }

        // Gerar uma option para cada valor do enum
        for (var enumValue : enumDef.getValues()) {
            Object code = enumValue.getCode();
            String label = enumValue.getLabel();

            html.append("                                <option value=\"").append(code).append("\" {{ old('")
                .append(attrName).append("', $").append(entityNameLower).append("->").append(attrName)
                .append(" ?? '') == ").append(code).append(" ? 'selected' : '' }}>")
                .append(label).append("</option>\n");
        }
    }
}
