package br.com.gerador.generator.template.angular;

import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.Field;
import br.com.gerador.metamodel.model.DataType;
import br.com.gerador.metamodel.model.MetaModel;
import br.com.gerador.metamodel.model.UIComponent;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AngularFormComponentTemplate {

    /**
     * Verifica se a entidade tem filtro de sessão.
     */
    private boolean hasSessionFilter() {
        return entity.hasSessionFilter();
    }

    /**
     * Retorna o campo do filtro de sessão.
     */
    private String getSessionFilterField() {
        if (entity.hasSessionFilter()) {
            return entity.getSessionFilter().getField();
        }
        return null;
    }

    private final Entity entity;
    private final MetaModel metaModel;

    public AngularFormComponentTemplate(Entity entity) {
        this.entity = entity;
        this.metaModel = null;
    }

    public AngularFormComponentTemplate(Entity entity, MetaModel metaModel) {
        this.entity = entity;
        this.metaModel = metaModel;
    }

    /**
     * Coleta todas as entidades referenciadas (FK) nesta entidade.
     */
    private Set<String> getReferencedEntities() {
        Set<String> refs = new LinkedHashSet<>();
        for (Field field : entity.getFields()) {
            if (field.isForeignKey() && field.getReference() != null) {
                refs.add(field.getReference().getEntity());
            }
        }
        return refs;
    }

    /**
     * Encontra a entidade pelo nome no metamodel.
     */
    private Entity findEntity(String entityName) {
        if (metaModel == null || metaModel.getEntities() == null) {
            return null;
        }
        for (Entity e : metaModel.getEntities()) {
            if (e.getName().equals(entityName)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Verifica se o campo está visível no grid (incluído no XxxList).
     */
    private boolean isFieldVisibleInList(Field f) {
        if (f.getUi() == null || f.getUi().getGrid() == null) {
            // Se não tem configuração de grid, assume visível para campos PK
            return f.isPrimaryKey();
        }
        return f.getUi().getGrid().isVisible();
    }

    /**
     * Determina o campo de exibição da entidade referenciada.
     * Procura por campos como "nome", "nome" + EntityName, "descricao", etc.
     * IMPORTANTE: O campo deve estar visível no grid (existir em XxxList).
     */
    private String getReferencedDisplayField(String refEntity) {
        Entity refEntityObj = findEntity(refEntity);
        if (refEntityObj != null) {
            // Prioridade: nome + EntityName, nome, descricao, nomeXxx, primeiro campo string não-PK
            // IMPORTANTE: o campo deve estar visível no grid (existir em XxxList)
            String[] patterns = {
                "nome" + refEntity,           // nomeBairro, nomeCidade
                "nome",                        // nome (Pessoa tem apenas "nome")
                "descricao",                   // descricao
                "nomeFantasia",                // empresas
                "razaoSocial"                  // empresas
            };

            for (String pattern : patterns) {
                for (Field f : refEntityObj.getFields()) {
                    if (f.getName().equalsIgnoreCase(pattern) && isFieldVisibleInList(f)) {
                        return f.getName();
                    }
                }
            }

            // Fallback: primeiro campo STRING que não seja PK nem FK e visível no grid
            for (Field f : refEntityObj.getFields()) {
                if (!f.isPrimaryKey() && !f.isForeignKey() &&
                    (f.getDataType() == DataType.STRING || f.getDataType() == DataType.TEXT) &&
                    isFieldVisibleInList(f)) {
                    return f.getName();
                }
            }

            // Fallback: usa o campo PK visível no grid (útil para entidades sem campo de descrição)
            for (Field f : refEntityObj.getFields()) {
                if (f.isPrimaryKey() && isFieldVisibleInList(f)) {
                    return f.getName();
                }
            }
        }
        // Fallback final: usa o padrão "id" + NomeEntidade
        return "id" + refEntity;
    }

    public String generateTs() {
        String entityName = entity.getName();
        String entityLower = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
        Set<String> referencedEntities = getReferencedEntities();

        StringBuilder sb = new StringBuilder();

        sb.append("import { Component, OnInit, HostListener } from '@angular/core';\n");
        sb.append("import { CommonModule } from '@angular/common';\n");
        sb.append("import { FormsModule } from '@angular/forms';\n");
        sb.append("import { Router, ActivatedRoute } from '@angular/router';\n");
        sb.append("import { ").append(entityName).append("Request, ").append(entityName).append("Response } from '../models/").append(entityLower).append(".model';\n");
        sb.append("import { ").append(entityName).append("Service } from '../services/").append(entityLower).append(".service';\n");

        // Import SessionService se a entidade tem sessionFilter
        if (hasSessionFilter()) {
            sb.append("import { SessionService } from '../services/session.service';\n");
        }

        // Importar models e services das entidades referenciadas
        for (String refEntity : referencedEntities) {
            String refLower = refEntity.substring(0, 1).toLowerCase() + refEntity.substring(1);
            sb.append("import { ").append(refEntity).append("List } from '../models/").append(refLower).append(".model';\n");
            sb.append("import { ").append(refEntity).append("Service } from '../services/").append(refLower).append(".service';\n");
        }
        sb.append("\n");

        sb.append("@Component({\n");
        sb.append("  selector: 'app-").append(entityLower).append("-form',\n");
        sb.append("  standalone: true,\n");
        sb.append("  imports: [CommonModule, FormsModule],\n");
        sb.append("  templateUrl: './").append(entityLower).append("-form.component.html',\n");
        sb.append("  styleUrls: ['./").append(entityLower).append("-form.component.css']\n");
        sb.append("})\n");
        sb.append("export class ").append(entityName).append("FormComponent implements OnInit {\n\n");

        sb.append("  item: ").append(entityName).append("Request = {} as ").append(entityName).append("Request;\n");
        sb.append("  isEditing: boolean = false;\n");
        sb.append("  loading: boolean = false;\n");
        sb.append("  saving: boolean = false;\n");
        sb.append("  error: string | null = null;\n");
        sb.append("  successMessage: string | null = null;\n");
        sb.append("  private id: any = null;\n");
        sb.append("  private returnQueryParams: { [key: string]: any } = {};\n\n");

        // Declarar arrays para armazenar opções dos combos - usar XxxList
        for (String refEntity : referencedEntities) {
            String refLower = refEntity.substring(0, 1).toLowerCase() + refEntity.substring(1);
            sb.append("  ").append(refLower).append("Options: ").append(refEntity).append("List[] = [];\n");
        }
        if (!referencedEntities.isEmpty()) {
            sb.append("\n");
        }

        // Declarar variáveis de controle para os searchable selects (por campo FK)
        for (Field field : entity.getFields()) {
            if (field.isForeignKey()) {
                String fieldName = field.getName();
                sb.append("  ").append(fieldName).append("DropdownOpen: boolean = false;\n");
                sb.append("  ").append(fieldName).append("SearchTerm: string = '';\n");
            }
        }
        sb.append("\n");

        sb.append("  constructor(\n");
        sb.append("    private service: ").append(entityName).append("Service,\n");
        // Injetar services das entidades referenciadas
        for (String refEntity : referencedEntities) {
            String refLower = refEntity.substring(0, 1).toLowerCase() + refEntity.substring(1);
            sb.append("    private ").append(refLower).append("Service: ").append(refEntity).append("Service,\n");
        }
        if (hasSessionFilter()) {
            sb.append("    private sessionService: SessionService,\n");
        }
        sb.append("    private router: Router,\n");
        sb.append("    private route: ActivatedRoute\n");
        sb.append("  ) {}\n\n");

        sb.append("  ngOnInit(): void {\n");
        // Carregar opções dos combos
        for (String refEntity : referencedEntities) {
            sb.append("    this.load").append(refEntity).append("Options();\n");
        }
        sb.append("    const idParam = this.route.snapshot.paramMap.get('id');\n");
        sb.append("    if (idParam) {\n");
        sb.append("      this.isEditing = true;\n");
        sb.append("      this.id = ").append(isNumericPrimaryKey() ? "Number(idParam)" : "idParam").append(";\n");
        sb.append("      this.loadItem();\n");
        sb.append("    } else {\n");
        // Auto-preencher campos FK vindos de navegação mestre-detalhe e guardar para retorno
        sb.append("      // Pré-selecionar campos FK vindos de navegação mestre-detalhe\n");
        sb.append("      this.route.queryParams.subscribe(params => {\n");
        sb.append("        this.returnQueryParams = { ...params };\n");
        sb.append("        for (const key of Object.keys(params)) {\n");
        sb.append("          if ((this.item as any).hasOwnProperty(key) || key.endsWith('Id')) {\n");
        sb.append("            (this.item as any)[key] = +params[key];\n");
        sb.append("          }\n");
        sb.append("        }\n");
        sb.append("      });\n");
        // Auto-preencher campo sessionFilter quando for novo registro
        if (hasSessionFilter()) {
            String filterField = getSessionFilterField();
            String capitalField = filterField.substring(0, 1).toUpperCase() + filterField.substring(1);
            sb.append("      // Auto-preencher com o valor da sessão\n");
            sb.append("      const sessionValue = this.sessionService.get").append(capitalField).append("();\n");
            sb.append("      if (sessionValue) {\n");
            sb.append("        this.item.").append(filterField).append(" = sessionValue;\n");
            sb.append("      }\n");
        }
        sb.append("    }\n");
        sb.append("  }\n\n");

        // Gerar métodos para carregar opções dos combos - usar data.content
        for (String refEntity : referencedEntities) {
            String refLower = refEntity.substring(0, 1).toLowerCase() + refEntity.substring(1);
            sb.append("  load").append(refEntity).append("Options(): void {\n");
            sb.append("    this.").append(refLower).append("Service.findAll(0, 1000).subscribe({\n");
            sb.append("      next: (data) => this.").append(refLower).append("Options = data.content,\n");
            sb.append("      error: (err) => console.error('Erro ao carregar ").append(refLower).append(":', err)\n");
            sb.append("    });\n");
            sb.append("  }\n\n");
        }

        sb.append("  loadItem(): void {\n");
        sb.append("    this.loading = true;\n");
        sb.append("    this.service.findById(this.id).subscribe({\n");
        sb.append("      next: (data) => {\n");
        sb.append("        this.item = data as ").append(entityName).append("Request;\n");
        sb.append("        this.loading = false;\n");
        sb.append("      },\n");
        sb.append("      error: (err) => {\n");
        sb.append("        this.error = 'Erro ao carregar registro';\n");
        sb.append("        this.loading = false;\n");
        sb.append("        console.error(err);\n");
        sb.append("      }\n");
        sb.append("    });\n");
        sb.append("  }\n\n");

        sb.append("  salvar(): void {\n");
        sb.append("    this.saving = true;\n");
        sb.append("    this.error = null;\n");
        sb.append("    this.successMessage = null;\n\n");
        sb.append("    const operation = this.isEditing\n");
        sb.append("      ? this.service.update(this.id, this.item)\n");
        sb.append("      : this.service.create(this.item);\n\n");
        sb.append("    operation.subscribe({\n");
        sb.append("      next: () => {\n");
        sb.append("        this.successMessage = this.isEditing ? 'Registro atualizado com sucesso!' : 'Registro criado com sucesso!';\n");
        sb.append("        setTimeout(() => {\n");
        sb.append("          this.navegarParaLista();\n");
        sb.append("        }, 1500);\n");
        sb.append("      },\n");
        sb.append("      error: (err) => {\n");
        sb.append("        this.error = err.error?.message || 'Erro ao salvar registro';\n");
        sb.append("        this.saving = false;\n");
        sb.append("        console.error(err);\n");
        sb.append("      }\n");
        sb.append("    });\n");
        sb.append("  }\n\n");

        sb.append("  cancelar(): void {\n");
        sb.append("    this.navegarParaLista();\n");
        sb.append("  }\n\n");

        sb.append("  private navegarParaLista(): void {\n");
        sb.append("    if (Object.keys(this.returnQueryParams).length > 0) {\n");
        sb.append("      this.router.navigate(['/").append(entityLower).append("'], { queryParams: this.returnQueryParams });\n");
        sb.append("    } else {\n");
        sb.append("      this.router.navigate(['/").append(entityLower).append("']);\n");
        sb.append("    }\n");
        sb.append("  }\n\n");

        // Gerar métodos para cada searchable select (por campo FK)
        for (Field field : entity.getFields()) {
            if (field.isForeignKey()) {
                String fieldName = field.getName();
                String fieldCapitalized = capitalize(fieldName);
                String refEntity = field.getReference().getEntity();
                String refEntityLower = refEntity.substring(0, 1).toLowerCase() + refEntity.substring(1);
                String refIdField = getReferencedIdField(field);
                String refDisplayField = getReferencedDisplayField(refEntity);

                // Método toggle dropdown
                sb.append("  toggle").append(fieldCapitalized).append("Dropdown(): void {\n");
                sb.append("    this.").append(fieldName).append("DropdownOpen = !this.").append(fieldName).append("DropdownOpen;\n");
                sb.append("    if (this.").append(fieldName).append("DropdownOpen) {\n");
                sb.append("      this.").append(fieldName).append("SearchTerm = '';\n");
                sb.append("    }\n");
                sb.append("  }\n\n");

                // Método select
                sb.append("  select").append(fieldCapitalized).append("(value: any): void {\n");
                sb.append("    this.item.").append(fieldName).append(" = value;\n");
                sb.append("    this.").append(fieldName).append("DropdownOpen = false;\n");
                sb.append("  }\n\n");

                // Método getSelectedText
                sb.append("  getSelected").append(fieldCapitalized).append("Text(): string {\n");
                sb.append("    if (this.item.").append(fieldName).append(" === null || this.item.").append(fieldName).append(" === undefined) {\n");
                sb.append("      return 'Selecione...';\n");
                sb.append("    }\n");
                sb.append("    const selected = this.").append(refEntityLower).append("Options.find(opt => opt.").append(refIdField).append(" === this.item.").append(fieldName).append(");\n");
                sb.append("    return selected ? String(selected.").append(refDisplayField).append(") : 'Selecione...';\n");
                sb.append("  }\n\n");

                // Método filter options
                sb.append("  filtered").append(fieldCapitalized).append("Options(): any[] {\n");
                sb.append("    if (!this.").append(fieldName).append("SearchTerm) {\n");
                sb.append("      return this.").append(refEntityLower).append("Options;\n");
                sb.append("    }\n");
                sb.append("    const term = this.").append(fieldName).append("SearchTerm.toLowerCase();\n");
                sb.append("    return this.").append(refEntityLower).append("Options.filter(opt => \n");
                sb.append("      String(opt.").append(refDisplayField).append(").toLowerCase().includes(term)\n");
                sb.append("    );\n");
                sb.append("  }\n\n");
            }
        }

        // Método para fechar dropdowns ao clicar fora
        sb.append("  @HostListener('document:click', ['$event'])\n");
        sb.append("  onDocumentClick(event: Event): void {\n");
        sb.append("    const target = event.target as HTMLElement;\n");
        sb.append("    if (!target.closest('.searchable-select')) {\n");
        for (Field field : entity.getFields()) {
            if (field.isForeignKey()) {
                sb.append("      this.").append(field.getName()).append("DropdownOpen = false;\n");
            }
        }
        sb.append("    }\n");
        sb.append("  }\n");

        sb.append("}\n");

        return sb.toString();
    }

    public String generateHtml() {
        String displayName = getDisplayName();

        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"container\">\n");
        sb.append("  <div class=\"header\">\n");
        sb.append("    <h2>{{ isEditing ? 'Editar' : 'Novo' }} ").append(displayName).append("</h2>\n");
        sb.append("  </div>\n\n");

        sb.append("  <div *ngIf=\"loading\" class=\"loading\">Carregando...</div>\n\n");

        sb.append("  <div *ngIf=\"successMessage\" class=\"success\">{{ successMessage }}</div>\n\n");

        sb.append("  <div *ngIf=\"error\" class=\"error\">{{ error }}</div>\n\n");

        sb.append("  <form *ngIf=\"!loading\" (ngSubmit)=\"salvar()\" #form=\"ngForm\" class=\"form-container\">\n");

        // Gerar campos do formulário
        for (Field field : entity.getFields()) {
            if (shouldShowInForm(field)) {
                generateFormField(sb, field);
            }
        }

        sb.append("    <div class=\"form-actions\">\n");
        sb.append("      <button type=\"button\" class=\"btn btn-secondary\" (click)=\"cancelar()\">\n");
        sb.append("        <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("          <line x1=\"18\" y1=\"6\" x2=\"6\" y2=\"18\"></line>\n");
        sb.append("          <line x1=\"6\" y1=\"6\" x2=\"18\" y2=\"18\"></line>\n");
        sb.append("        </svg>\n");
        sb.append("        <span>Cancelar</span>\n");
        sb.append("      </button>\n");
        sb.append("      <button type=\"submit\" class=\"btn btn-primary\" [disabled]=\"saving || form.invalid\">\n");
        sb.append("        <svg class=\"icon\" *ngIf=\"!saving\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("          <path d=\"M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z\"></path>\n");
        sb.append("          <polyline points=\"17 21 17 13 7 13 7 21\"></polyline>\n");
        sb.append("          <polyline points=\"7 3 7 8 15 8\"></polyline>\n");
        sb.append("        </svg>\n");
        sb.append("        <span>{{ saving ? 'Salvando...' : 'Salvar' }}</span>\n");
        sb.append("      </button>\n");
        sb.append("    </div>\n");
        sb.append("  </form>\n");
        sb.append("</div>\n");

        return sb.toString();
    }

    private void generateFormField(StringBuilder sb, Field field) {
        String fieldName = field.getName();
        String label = getFieldLabel(field);
        String inputType = getInputType(field);
        boolean required = field.isRequired() && !field.isPrimaryKey();

        sb.append("    <div class=\"form-group\">\n");
        sb.append("      <label for=\"").append(fieldName).append("\">").append(label);
        if (required) {
            sb.append(" <span class=\"required\">*</span>");
        }
        sb.append("</label>\n");

        // Se o campo tem opções (enum), gerar um SELECT simples
        if (field.getUi() != null && field.getUi().hasOptions()) {
            generateSelectWithOptions(sb, field, required);
        // Se é um campo FK, gerar um SELECT/COMBO com pesquisa
        } else if (field.isForeignKey() && field.getReference() != null) {
            generateSelectField(sb, field, required);
        } else if (field.getDataType() == DataType.TEXT || isTextAreaComponent(field)) {
            sb.append("      <textarea\n");
            sb.append("        id=\"").append(fieldName).append("\"\n");
            sb.append("        name=\"").append(fieldName).append("\"\n");
            sb.append("        [(ngModel)]=\"item.").append(fieldName).append("\"\n");
            if (required) {
                sb.append("        required\n");
            }
            sb.append("        rows=\"3\"\n");
            sb.append("        class=\"form-control\"\n");
            sb.append("      ></textarea>\n");
        } else if (field.getDataType() == DataType.BOOLEAN) {
            sb.append("      <input\n");
            sb.append("        type=\"checkbox\"\n");
            sb.append("        id=\"").append(fieldName).append("\"\n");
            sb.append("        name=\"").append(fieldName).append("\"\n");
            sb.append("        [(ngModel)]=\"item.").append(fieldName).append("\"\n");
            sb.append("        class=\"form-check\"\n");
            sb.append("      />\n");
        } else {
            sb.append("      <input\n");
            sb.append("        type=\"").append(inputType).append("\"\n");
            sb.append("        id=\"").append(fieldName).append("\"\n");
            sb.append("        name=\"").append(fieldName).append("\"\n");
            sb.append("        [(ngModel)]=\"item.").append(fieldName).append("\"\n");
            if (required) {
                sb.append("        required\n");
            }
            if (field.getLength() != null && field.getLength() > 0) {
                sb.append("        maxlength=\"").append(field.getLength()).append("\"\n");
            }
            sb.append("        class=\"form-control\"\n");
            if (field.isPrimaryKey() && !hasAutoGeneratedPrimaryKey()) {
                sb.append("        [readonly]=\"isEditing\"\n");
            }
            sb.append("      />\n");
        }

        sb.append("    </div>\n\n");
    }

    /**
     * Gera um campo SELECT com pesquisa (searchable dropdown) para FK.
     */
    private void generateSelectField(StringBuilder sb, Field field, boolean required) {
        String fieldName = field.getName();
        String refEntity = field.getReference().getEntity();
        String refEntityLower = refEntity.substring(0, 1).toLowerCase() + refEntity.substring(1);

        // Determinar o campo de ID e descrição da entidade referenciada
        String refIdField = getReferencedIdField(field);
        String refDisplayField = getReferencedDisplayField(refEntity);

        // Container do searchable select
        sb.append("      <div class=\"searchable-select\" [class.open]=\"").append(fieldName).append("DropdownOpen\">\n");

        // Campo de exibição/seleção
        sb.append("        <div class=\"select-display\" (click)=\"toggle").append(capitalize(fieldName)).append("Dropdown()\">\n");
        sb.append("          <span class=\"selected-text\">{{ getSelected").append(capitalize(fieldName)).append("Text() }}</span>\n");
        sb.append("          <span class=\"dropdown-arrow\">▼</span>\n");
        sb.append("        </div>\n");

        // Dropdown com pesquisa
        sb.append("        <div class=\"select-dropdown\" *ngIf=\"").append(fieldName).append("DropdownOpen\">\n");
        sb.append("          <input\n");
        sb.append("            type=\"text\"\n");
        sb.append("            class=\"search-input\"\n");
        sb.append("            [(ngModel)]=\"").append(fieldName).append("SearchTerm\"\n");
        sb.append("            [ngModelOptions]=\"{standalone: true}\"\n");
        sb.append("            placeholder=\"Pesquisar...\"\n");
        sb.append("            (click)=\"$event.stopPropagation()\"\n");
        sb.append("          />\n");
        sb.append("          <div class=\"options-list\">\n");
        sb.append("            <div\n");
        sb.append("              class=\"option\"\n");
        sb.append("              (click)=\"select").append(capitalize(fieldName)).append("(null)\"\n");
        sb.append("              [class.selected]=\"item.").append(fieldName).append(" === null\"\n");
        sb.append("            >\n");
        sb.append("              Selecione...\n");
        sb.append("            </div>\n");
        sb.append("            <div\n");
        sb.append("              *ngFor=\"let opt of filtered").append(capitalize(fieldName)).append("Options()\"\n");
        sb.append("              class=\"option\"\n");
        sb.append("              (click)=\"select").append(capitalize(fieldName)).append("(opt.").append(refIdField).append(")\"\n");
        sb.append("              [class.selected]=\"item.").append(fieldName).append(" === opt.").append(refIdField).append("\"\n");
        sb.append("            >\n");
        sb.append("              {{ opt.").append(refDisplayField).append(" }}\n");
        sb.append("            </div>\n");
        sb.append("          </div>\n");
        sb.append("        </div>\n");
        sb.append("      </div>\n");

        // Input hidden para o valor real (para validação do form)
        sb.append("      <input type=\"hidden\" name=\"").append(fieldName).append("\" [(ngModel)]=\"item.").append(fieldName).append("\"");
        if (required) {
            sb.append(" required");
        }
        sb.append(" />\n");
    }

    /**
     * Gera um campo SELECT simples com opções predefinidas (para campos enum/lookup).
     */
    private void generateSelectWithOptions(StringBuilder sb, Field field, boolean required) {
        String fieldName = field.getName();

        sb.append("      <select\n");
        sb.append("        id=\"").append(fieldName).append("\"\n");
        sb.append("        name=\"").append(fieldName).append("\"\n");
        sb.append("        [(ngModel)]=\"item.").append(fieldName).append("\"\n");
        if (required) {
            sb.append("        required\n");
        }
        sb.append("        class=\"form-control\"\n");
        sb.append("      >\n");
        sb.append("        <option [ngValue]=\"null\">Selecione...</option>\n");

        // Gerar as opções a partir do field.ui.options
        for (var option : field.getUi().getOptions()) {
            Object value = option.getValue();
            String label = option.getLabel();
            // Se o valor é numérico, não usar aspas
            if (value instanceof Number) {
                // Converter Double para Integer se for um número inteiro
                Number numValue = (Number) value;
                String valueStr;
                if (numValue instanceof Double && numValue.doubleValue() == Math.floor(numValue.doubleValue())) {
                    valueStr = String.valueOf(numValue.intValue());
                } else {
                    valueStr = String.valueOf(numValue);
                }
                sb.append("        <option [ngValue]=\"").append(valueStr).append("\">").append(label).append("</option>\n");
            } else {
                sb.append("        <option value=\"").append(value).append("\">").append(label).append("</option>\n");
            }
        }

        sb.append("      </select>\n");
    }

    /**
     * Capitaliza a primeira letra de uma string.
     */
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * Retorna o nome do campo ID da entidade referenciada.
     */
    private String getReferencedIdField(Field field) {
        String refEntityName = field.getReference().getEntity();
        Entity refEntityObj = findEntity(refEntityName);

        // Se a referência especifica o campo, verificar se ele existe na entidade
        if (field.getReference() != null && field.getReference().getField() != null) {
            String refField = field.getReference().getField();
            // Verificar se o campo existe na entidade referenciada
            if (refEntityObj != null) {
                for (Field f : refEntityObj.getFields()) {
                    if (f.getName().equals(refField)) {
                        return refField;
                    }
                }
            } else {
                // Se não encontrou a entidade, usar o valor especificado
                return refField;
            }
        }

        // Buscar o campo PK real da entidade referenciada
        if (refEntityObj != null) {
            for (Field f : refEntityObj.getFields()) {
                if (f.isPrimaryKey()) {
                    return f.getName();
                }
            }
        }

        // Fallback: usa o padrão id + EntityName
        return "id" + refEntityName;
    }

    public String generateCss() {
        StringBuilder sb = new StringBuilder();

        sb.append(".container {\n");
        sb.append("  padding: 0;\n");
        sb.append("  max-width: 800px;\n");
        sb.append("}\n\n");

        sb.append(".header {\n");
        sb.append("  margin-bottom: 1.5rem;\n");
        sb.append("}\n\n");

        sb.append(".header h2 {\n");
        sb.append("  margin: 0;\n");
        sb.append("  color: #333;\n");
        sb.append("}\n\n");

        sb.append(".loading, .error {\n");
        sb.append("  padding: 2rem;\n");
        sb.append("  text-align: center;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("}\n\n");

        sb.append(".loading {\n");
        sb.append("  background: #f0f0f0;\n");
        sb.append("  color: #666;\n");
        sb.append("}\n\n");

        sb.append(".error {\n");
        sb.append("  background: #fee;\n");
        sb.append("  color: #c33;\n");
        sb.append("  margin-bottom: 1rem;\n");
        sb.append("}\n\n");

        sb.append(".success {\n");
        sb.append("  padding: 1rem;\n");
        sb.append("  text-align: center;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  background: #d4edda;\n");
        sb.append("  color: #155724;\n");
        sb.append("  border: 1px solid #c3e6cb;\n");
        sb.append("  margin-bottom: 1rem;\n");
        sb.append("}\n\n");

        sb.append(".form-container {\n");
        sb.append("  background: white;\n");
        sb.append("  border-radius: 8px;\n");
        sb.append("  box-shadow: 0 2px 4px rgba(0,0,0,0.1);\n");
        sb.append("  padding: 2rem;\n");
        sb.append("}\n\n");

        sb.append(".form-group {\n");
        sb.append("  margin-bottom: 1.5rem;\n");
        sb.append("}\n\n");

        sb.append(".form-group label {\n");
        sb.append("  display: block;\n");
        sb.append("  margin-bottom: 0.5rem;\n");
        sb.append("  font-weight: 600;\n");
        sb.append("  color: #333;\n");
        sb.append("}\n\n");

        sb.append(".required {\n");
        sb.append("  color: #dc3545;\n");
        sb.append("}\n\n");

        sb.append(".form-control {\n");
        sb.append("  width: 100%;\n");
        sb.append("  padding: 0.5rem 1rem;\n");
        sb.append("  border: 1px solid #ddd;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  font-size: 14px;\n");
        sb.append("  box-sizing: border-box;\n");
        sb.append("}\n\n");

        sb.append(".form-control:focus {\n");
        sb.append("  outline: none;\n");
        sb.append("  border-color: #667eea;\n");
        sb.append("  box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.2);\n");
        sb.append("}\n\n");

        sb.append(".form-control[readonly] {\n");
        sb.append("  background-color: #e9ecef;\n");
        sb.append("}\n\n");

        sb.append(".form-check {\n");
        sb.append("  width: auto;\n");
        sb.append("  margin-top: 0.5rem;\n");
        sb.append("}\n\n");

        sb.append("textarea.form-control {\n");
        sb.append("  resize: vertical;\n");
        sb.append("  min-height: 100px;\n");
        sb.append("}\n\n");

        // Estilos para select (combo)
        sb.append("select.form-control {\n");
        sb.append("  appearance: none;\n");
        sb.append("  background-image: url(\"data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 12 12'%3E%3Cpath fill='%23333' d='M10.293 3.293L6 7.586 1.707 3.293A1 1 0 00.293 4.707l5 5a1 1 0 001.414 0l5-5a1 1 0 10-1.414-1.414z'/%3E%3C/svg%3E\");\n");
        sb.append("  background-repeat: no-repeat;\n");
        sb.append("  background-position: right 0.75rem center;\n");
        sb.append("  padding-right: 2.5rem;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("}\n\n");

        sb.append("select.form-control:hover {\n");
        sb.append("  border-color: #aaa;\n");
        sb.append("}\n\n");

        sb.append(".form-actions {\n");
        sb.append("  display: flex;\n");
        sb.append("  justify-content: flex-end;\n");
        sb.append("  gap: 1rem;\n");
        sb.append("  margin-top: 2rem;\n");
        sb.append("  padding-top: 1.5rem;\n");
        sb.append("  border-top: 1px solid #eee;\n");
        sb.append("}\n\n");

        sb.append(".btn {\n");
        sb.append("  display: inline-flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 0.5rem;\n");
        sb.append("  padding: 0.6rem 1.5rem;\n");
        sb.append("  border: none;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  font-size: 14px;\n");
        sb.append("  font-weight: 500;\n");
        sb.append("  transition: all 0.2s;\n");
        sb.append("}\n\n");

        sb.append(".btn .icon {\n");
        sb.append("  width: 18px;\n");
        sb.append("  height: 18px;\n");
        sb.append("  flex-shrink: 0;\n");
        sb.append("}\n\n");

        sb.append(".btn:disabled {\n");
        sb.append("  opacity: 0.6;\n");
        sb.append("  cursor: not-allowed;\n");
        sb.append("}\n\n");

        sb.append(".btn-primary {\n");
        sb.append("  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n");
        sb.append("  color: white;\n");
        sb.append("}\n\n");

        sb.append(".btn-primary:hover:not(:disabled) {\n");
        sb.append("  background: linear-gradient(135deg, #5a6fd6 0%, #6a4190 100%);\n");
        sb.append("}\n\n");

        sb.append(".btn-secondary {\n");
        sb.append("  background: #6c757d;\n");
        sb.append("  color: white;\n");
        sb.append("}\n\n");

        sb.append(".btn-secondary:hover {\n");
        sb.append("  background: #5a6268;\n");
        sb.append("}\n\n");

        // Estilos para searchable select (combo com pesquisa)
        sb.append("/* Searchable Select - Combo com Pesquisa */\n");
        sb.append(".searchable-select {\n");
        sb.append("  position: relative;\n");
        sb.append("  width: 100%;\n");
        sb.append("}\n\n");

        sb.append(".select-display {\n");
        sb.append("  display: flex;\n");
        sb.append("  justify-content: space-between;\n");
        sb.append("  align-items: center;\n");
        sb.append("  padding: 0.5rem 1rem;\n");
        sb.append("  border: 1px solid #ddd;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  background: white;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  min-height: 38px;\n");
        sb.append("  box-sizing: border-box;\n");
        sb.append("}\n\n");

        sb.append(".select-display:hover {\n");
        sb.append("  border-color: #aaa;\n");
        sb.append("}\n\n");

        sb.append(".searchable-select.open .select-display {\n");
        sb.append("  border-color: #667eea;\n");
        sb.append("  box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.2);\n");
        sb.append("}\n\n");

        sb.append(".selected-text {\n");
        sb.append("  flex: 1;\n");
        sb.append("  overflow: hidden;\n");
        sb.append("  text-overflow: ellipsis;\n");
        sb.append("  white-space: nowrap;\n");
        sb.append("  color: #333;\n");
        sb.append("  font-size: 14px;\n");
        sb.append("}\n\n");

        sb.append(".dropdown-arrow {\n");
        sb.append("  font-size: 10px;\n");
        sb.append("  color: #666;\n");
        sb.append("  margin-left: 0.5rem;\n");
        sb.append("  transition: transform 0.2s;\n");
        sb.append("}\n\n");

        sb.append(".searchable-select.open .dropdown-arrow {\n");
        sb.append("  transform: rotate(180deg);\n");
        sb.append("}\n\n");

        sb.append(".select-dropdown {\n");
        sb.append("  position: absolute;\n");
        sb.append("  top: 100%;\n");
        sb.append("  left: 0;\n");
        sb.append("  right: 0;\n");
        sb.append("  z-index: 1000;\n");
        sb.append("  background: white;\n");
        sb.append("  border: 1px solid #ddd;\n");
        sb.append("  border-top: none;\n");
        sb.append("  border-radius: 0 0 4px 4px;\n");
        sb.append("  box-shadow: 0 4px 12px rgba(0,0,0,0.15);\n");
        sb.append("  max-height: 300px;\n");
        sb.append("  display: flex;\n");
        sb.append("  flex-direction: column;\n");
        sb.append("}\n\n");

        sb.append(".search-input {\n");
        sb.append("  padding: 0.75rem 1rem;\n");
        sb.append("  border: none;\n");
        sb.append("  border-bottom: 1px solid #eee;\n");
        sb.append("  font-size: 14px;\n");
        sb.append("  outline: none;\n");
        sb.append("  width: 100%;\n");
        sb.append("  box-sizing: border-box;\n");
        sb.append("}\n\n");

        sb.append(".search-input::placeholder {\n");
        sb.append("  color: #999;\n");
        sb.append("}\n\n");

        sb.append(".options-list {\n");
        sb.append("  overflow-y: auto;\n");
        sb.append("  max-height: 250px;\n");
        sb.append("}\n\n");

        sb.append(".option {\n");
        sb.append("  padding: 0.6rem 1rem;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  font-size: 14px;\n");
        sb.append("  color: #333;\n");
        sb.append("  transition: background-color 0.15s;\n");
        sb.append("}\n\n");

        sb.append(".option:hover {\n");
        sb.append("  background-color: #f5f5f5;\n");
        sb.append("}\n\n");

        sb.append(".option.selected {\n");
        sb.append("  background-color: #667eea;\n");
        sb.append("  color: white;\n");
        sb.append("}\n\n");

        sb.append(".option.selected:hover {\n");
        sb.append("  background-color: #5a6fd6;\n");
        sb.append("}\n");

        return sb.toString();
    }

    private String getDisplayName() {
        // Usa displayName se definido, senão converte CamelCase para texto com espaços
        if (entity.getDisplayName() != null && !entity.getDisplayName().isEmpty()) {
            return entity.getDisplayName();
        }
        String name = entity.getName();
        return name.replaceAll("([A-Z])", " $1").trim();
    }

    private String getFieldLabel(Field field) {
        // Se o campo tem um label definido no metamodel, usar ele (com friendly labels aplicados)
        if (field.getLabel() != null && !field.getLabel().isEmpty()) {
            return applyFriendlyLabel(field.getLabel());
        }

        String name = field.getName();
        // Remove prefixo "id" se existir (ex: idCidade -> Cidade)
        if (name.startsWith("id") && name.length() > 2 && Character.isUpperCase(name.charAt(2))) {
            name = name.substring(2);
        }
        // Converte camelCase para palavras separadas
        String label = name.replaceAll("([a-z])([A-Z])", "$1 $2");
        // Primeira letra maiúscula
        label = label.substring(0, 1).toUpperCase() + label.substring(1);
        // Aplica mapeamento de labels amigáveis
        return applyFriendlyLabel(label);
    }

    private String applyFriendlyLabel(String label) {
        // Mapeamento de termos técnicos para labels amigáveis com acentos
        return label
            // Termos comuns
            .replace("Numero", "Número")
            .replace("Telefone", "Telefone")
            .replace("Celular", "Celular")
            .replace("Endereco", "Endereço")
            .replace("Codigo", "Código")
            .replace("Descricao", "Descrição")
            .replace("Observacao", "Observação")
            .replace("Situacao", "Situação")
            .replace("Data Adesao", "Data de Adesão")
            .replace("Data Inativo", "Data de Inativação")
            .replace("Data Filiacao", "Data de Filiação")
            .replace("Data Desligamento", "Data de Desligamento")
            .replace("Data Alteracao", "Data de Alteração")
            .replace("Data Indicacao", "Data de Indicação")
            .replace("Atualizacao", "Atualização")
            .replace("Inscricao", "Inscrição")
            .replace("Orgao Emissor", "Órgão Emissor")
            .replace("Orgao", "Órgão")
            .replace("Emissao", "Emissão")
            .replace("Conselho", "Conselho")
            .replace("Especialidade", "Especialidade")
            .replace("Operadora", "Operadora")
            .replace("Retencao", "Retenção")
            .replace("Indicacao", "Indicação")
            .replace("Poupanca", "Poupança")
            .replace("Agencia", "Agência")
            .replace("Naturalidade", "Naturalidade")
            .replace("Nascimento", "Nascimento")
            .replace("Estado Civil", "Estado Civil")
            .replace("Mae", "Mãe")
            .replace("Pai", "Pai")
            .replace("Nao Socio", "Não Sócio")
            .replace("Socio", "Sócio")
            .replace("Usuario", "Usuário")
            .replace("Parametro", "Parâmetro")
            .replace("Orcamento", "Orçamento")
            .replace("Comissao", "Comissão")
            .replace("Valor De Cada Cota", "Valor de Cada Cota")
            .replace("Numero Parcelas Cota", "Número de Parcelas da Cota")
            .replace("Valor Pago No Desligamento", "Valor Pago no Desligamento")
            .replace("Dv Conta", "Dígito Verificador")
            .replace("Conta Poupanca", "Conta Poupança")
            .replace("Classe Inss", "Classe INSS")
            .replace("Taxa Especial", "Taxa Especial")
            .replace("Percentual", "Percentual")
            .replace("Plano Retencao", "Plano de Retenção")
            .replace("Cpf", "CPF")
            .replace("Cnpj", "CNPJ")
            .replace("Rg", "RG")
            .replace("Cep", "CEP")
            .replace("Uf", "UF")
            .replace("Nf", "NF")
            .replace("Cbo", "CBO")
            .replace("Iss", "ISS")
            .replace("Inss", "INSS")
            .replace("Ir", "IR")
            .replace("Nome Banco", "Nome do Banco")
            .replace("Numero Conselho", "Número do Conselho")
            .replace("Observacao Pessoa", "Observação");
    }

    private String getInputType(Field field) {
        DataType dataType = field.getDataType();

        switch (dataType) {
            case INTEGER:
            case LONG:
            case DECIMAL:
            case MONEY:
                return "number";
            case DATE:
                return "date";
            case DATETIME:
                return "datetime-local";
            case TIME:
                return "time";
            case BOOLEAN:
                return "checkbox";
            default:
                return "text";
        }
    }

    private boolean shouldShowInForm(Field field) {
        // Não mostrar campos auto-gerados (IDENTITY)
        if (field.isPrimaryKey() && hasAutoGeneratedPrimaryKey()) {
            return false;
        }
        return true;
    }

    private boolean hasAutoGeneratedPrimaryKey() {
        for (Field field : entity.getFields()) {
            if (field.isPrimaryKey()) {
                // Apenas se explicitamente marcado como autoIncrement
                if (field.isAutoIncrement()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isNumericPrimaryKey() {
        for (Field field : entity.getFields()) {
            if (field.isPrimaryKey()) {
                DataType dt = field.getDataType();
                return dt == DataType.INTEGER || dt == DataType.LONG;
            }
        }
        return true;
    }

    /**
     * Verifica se o campo deve usar TEXTAREA baseado na configuração de UI.
     */
    private boolean isTextAreaComponent(Field field) {
        if (field.getUi() != null && field.getUi().getComponent() != null) {
            return field.getUi().getComponent() == UIComponent.TEXTAREA;
        }
        return false;
    }
}
