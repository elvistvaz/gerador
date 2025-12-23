package br.com.gerador.generator.template.angular;

import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.Field;
import br.com.gerador.metamodel.model.DataType;
import br.com.gerador.metamodel.model.MetaModel;

import java.util.LinkedHashSet;
import java.util.Set;

public class AngularListComponentTemplate {

    private final Entity entity;
    private final MetaModel metaModel;

    public AngularListComponentTemplate(Entity entity) {
        this.entity = entity;
        this.metaModel = null;
    }

    public AngularListComponentTemplate(Entity entity, MetaModel metaModel) {
        this.entity = entity;
        this.metaModel = metaModel;
    }

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

    /**
     * Retorna o segundo campo do filtro de sessão.
     */
    private String getSessionFilterField2() {
        if (entity.hasSessionFilter() && entity.getSessionFilter().hasField2()) {
            return entity.getSessionFilter().getField2();
        }
        return null;
    }

    /**
     * Verifica se o campo é o campo de filtro de sessão.
     */
    private boolean isSessionFilterField(Field field) {
        if (!entity.hasSessionFilter()) {
            return false;
        }
        String fieldName = field.getName();
        String filterField = entity.getSessionFilter().getField();
        String filterField2 = entity.getSessionFilter().getField2();
        return fieldName.equals(filterField) || (filterField2 != null && fieldName.equals(filterField2));
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
     * Coleta todas as entidades referenciadas (FK) nesta entidade que são visíveis no grid.
     */
    private Set<String> getReferencedEntitiesInGrid() {
        Set<String> refs = new LinkedHashSet<>();
        for (Field field : entity.getFields()) {
            if (field.isForeignKey() && field.getReference() != null && shouldShowInList(field)) {
                refs.add(field.getReference().getEntity());
            }
        }
        return refs;
    }

    /**
     * Verifica se o campo está visível no grid.
     */
    private boolean isFieldVisibleInList(Field f) {
        if (f.getUi() == null || f.getUi().getGrid() == null) {
            return f.isPrimaryKey();
        }
        return f.getUi().getGrid().isVisible();
    }

    /**
     * Determina o campo de exibição da entidade referenciada.
     */
    private String getReferencedDisplayField(String refEntity) {
        Entity refEntityObj = findEntity(refEntity);
        if (refEntityObj != null) {
            String[] patterns = {
                "nome" + refEntity,
                "nome",
                "descricao",
                "nomeFantasia",
                "razaoSocial"
            };

            for (String pattern : patterns) {
                for (Field f : refEntityObj.getFields()) {
                    if (f.getName().equalsIgnoreCase(pattern) && isFieldVisibleInList(f)) {
                        return f.getName();
                    }
                }
            }

            // Fallback: primeiro campo STRING que não seja PK nem FK
            for (Field f : refEntityObj.getFields()) {
                if (!f.isPrimaryKey() && !f.isForeignKey() &&
                    (f.getDataType() == DataType.STRING || f.getDataType() == DataType.TEXT) &&
                    isFieldVisibleInList(f)) {
                    return f.getName();
                }
            }
        }
        return "id";
    }

    /**
     * Retorna o nome do campo ID da entidade referenciada (a partir do campo FK).
     */
    private String getReferencedIdField(Field field) {
        String refEntityName = field.getReference().getEntity();
        return getReferencedIdFieldByEntity(refEntityName);
    }

    /**
     * Retorna o nome do campo ID da entidade referenciada (a partir do nome da entidade).
     */
    private String getReferencedIdFieldByEntity(String refEntityName) {
        Entity refEntityObj = findEntity(refEntityName);

        if (refEntityObj != null) {
            for (Field f : refEntityObj.getFields()) {
                if (f.isPrimaryKey()) {
                    return f.getName();
                }
            }
        }
        return "id";
    }

    public String generateTs() {
        String entityName = entity.getName();
        String entityLower = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
        String pkType = getPrimaryKeyType();
        Set<String> referencedEntities = getReferencedEntitiesInGrid();

        StringBuilder sb = new StringBuilder();

        sb.append("import { Component, OnInit } from '@angular/core';\n");
        sb.append("import { CommonModule } from '@angular/common';\n");
        sb.append("import { FormsModule } from '@angular/forms';\n");
        sb.append("import { Router, ActivatedRoute } from '@angular/router';\n");
        // Import EntityList e EntityId (se chave composta)
        if (hasCompositeKey()) {
            sb.append("import { ").append(entityName).append("List, ").append(entityName).append("Id } from '../models/").append(entityLower).append(".model';\n");
        } else {
            sb.append("import { ").append(entityName).append("List } from '../models/").append(entityLower).append(".model';\n");
        }
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
        sb.append("  selector: 'app-").append(entityLower).append("-list',\n");
        sb.append("  standalone: true,\n");
        sb.append("  imports: [CommonModule, FormsModule],\n");
        sb.append("  templateUrl: './").append(entityLower).append("-list.component.html',\n");
        sb.append("  styleUrls: ['./").append(entityLower).append("-list.component.css']\n");
        sb.append("})\n");
        sb.append("export class ").append(entityName).append("ListComponent implements OnInit {\n\n");

        sb.append("  items: ").append(entityName).append("List[] = [];\n");
        sb.append("  filteredItems: ").append(entityName).append("List[] = [];\n");
        sb.append("  searchTerm: string = '';\n");
        sb.append("  loading: boolean = false;\n");
        sb.append("  error: string | null = null;\n");
        sb.append("  successMessage: string | null = null;\n");
        sb.append("  parentFilter: { [key: string]: number } = {};\n");
        sb.append("  parentFilterKey: string | null = null;\n");
        sb.append("  parentName: string | null = null;\n");

        // Variável para armazenar o nome do contexto de sessão (ex: municipioNome, avaliacaoNome)
        if (hasSessionFilter()) {
            String filterField = getSessionFilterField();
            // Remove o sufixo "Id" para obter o nome base (ex: municipioId -> municipio)
            String contextBase = filterField.endsWith("Id")
                ? filterField.substring(0, filterField.length() - 2)
                : filterField;
            sb.append("  ").append(contextBase).append("Nome: string | null = null;\n");

            // Segundo campo de filtro de sessão (ex: avaliacaoId -> avaliacaoNome)
            String filterField2 = getSessionFilterField2();
            if (filterField2 != null) {
                String contextBase2 = filterField2.endsWith("Id")
                    ? filterField2.substring(0, filterField2.length() - 2)
                    : filterField2;
                sb.append("  ").append(contextBase2).append("Nome: string | null = null;\n");
            }
        }
        sb.append("\n");

        // Variáveis de paginação
        sb.append("  // Paginação\n");
        sb.append("  currentPage: number = 0;\n");
        sb.append("  pageSize: number = 20;\n");
        sb.append("  totalElements: number = 0;\n");
        sb.append("  totalPages: number = 0;\n\n");

        // Variáveis de ordenação (apenas se houver campos ordenáveis)
        if (hasSortableFields()) {
            sb.append("  // Ordenação\n");
            sb.append("  sortField: string = '';\n");
            sb.append("  sortDirection: 'asc' | 'desc' = 'asc';\n\n");
        }

        // Declarar maps para resolução de nomes das entidades referenciadas
        for (String refEntity : referencedEntities) {
            String refLower = refEntity.substring(0, 1).toLowerCase() + refEntity.substring(1);
            sb.append("  ").append(refLower).append("Map: Map<number, string> = new Map();\n");
        }
        if (!referencedEntities.isEmpty()) {
            sb.append("\n");
        }

        // Constructor com injeção de todos os services
        sb.append("  constructor(\n");
        sb.append("    private service: ").append(entityName).append("Service,\n");
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
        // Carregar nomes do contexto de sessão
        if (hasSessionFilter()) {
            String filterField = getSessionFilterField();
            String contextBase = filterField.endsWith("Id")
                ? filterField.substring(0, filterField.length() - 2)
                : filterField;
            String filterFieldCapitalized = filterField.substring(0, 1).toUpperCase() + filterField.substring(1);
            sb.append("    this.").append(contextBase).append("Nome = this.sessionService.get").append(filterFieldCapitalized).append("Nome();\n");

            // Segundo campo de filtro de sessão
            String filterField2 = getSessionFilterField2();
            if (filterField2 != null) {
                String contextBase2 = filterField2.endsWith("Id")
                    ? filterField2.substring(0, filterField2.length() - 2)
                    : filterField2;
                String filterField2Capitalized = filterField2.substring(0, 1).toUpperCase() + filterField2.substring(1);
                sb.append("    this.").append(contextBase2).append("Nome = this.sessionService.get").append(filterField2Capitalized).append("Nome();\n");
            }
        }
        // Ler query params para filtro de FK (navegação mestre-detalhe)
        sb.append("    this.route.queryParams.subscribe(params => {\n");
        sb.append("      this.parentFilter = {};\n");
        sb.append("      this.parentFilterKey = null;\n");
        sb.append("      this.parentName = null;\n");
        sb.append("      for (const key of Object.keys(params)) {\n");
        sb.append("        this.parentFilter[key] = +params[key];\n");
        sb.append("        this.parentFilterKey = key;\n");
        sb.append("      }\n");
        // Carregar dados das entidades referenciadas primeiro
        for (String refEntity : referencedEntities) {
            sb.append("      this.load").append(refEntity).append("();\n");
        }
        sb.append("      this.loadParentName();\n");
        sb.append("      this.loadItems();\n");
        sb.append("    });\n");
        sb.append("  }\n\n");

        // Gerar métodos para carregar entidades referenciadas
        for (String refEntity : referencedEntities) {
            String refLower = refEntity.substring(0, 1).toLowerCase() + refEntity.substring(1);
            String displayField = getReferencedDisplayField(refEntity);
            String idField = getReferencedIdFieldByEntity(refEntity);

            sb.append("  load").append(refEntity).append("(): void {\n");
            sb.append("    this.").append(refLower).append("Service.findAll(0, 10000).subscribe({\n");
            sb.append("      next: (data) => {\n");
            sb.append("        data.content.forEach((item: ").append(refEntity).append("List) => {\n");
            sb.append("          this.").append(refLower).append("Map.set(item.").append(idField).append(", String(item.").append(displayField).append("));\n");
            sb.append("        });\n");
            sb.append("      },\n");
            sb.append("      error: (err) => console.error('Erro ao carregar ").append(refLower).append(":', err)\n");
            sb.append("    });\n");
            sb.append("  }\n\n");
        }

        // Método para carregar o nome do pai baseado no parentFilter
        sb.append("  loadParentName(): void {\n");
        sb.append("    if (!this.parentFilterKey) {\n");
        sb.append("      this.parentName = null;\n");
        sb.append("      return;\n");
        sb.append("    }\n");
        sb.append("    const parentId = this.parentFilter[this.parentFilterKey];\n");
        sb.append("    // Aguardar um pouco para os maps serem carregados\n");
        sb.append("    setTimeout(() => {\n");
        // Gerar switch para cada entidade referenciada
        boolean hasReferences = !referencedEntities.isEmpty();
        if (hasReferences) {
            sb.append("      switch (this.parentFilterKey) {\n");
            for (String refEntity : referencedEntities) {
                String refLower = refEntity.substring(0, 1).toLowerCase() + refEntity.substring(1);
                // O campo FK geralmente é nomeEntityId, precisamos encontrar o padrão
                sb.append("        case '").append(refLower).append("Id':\n");
                sb.append("          this.parentName = this.").append(refLower).append("Map.get(parentId) || null;\n");
                sb.append("          break;\n");
            }
            sb.append("      }\n");
        }
        sb.append("    }, 500);\n");
        sb.append("  }\n\n");

        sb.append("  loadItems(): void {\n");
        sb.append("    this.loading = true;\n");
        sb.append("    this.error = null;\n");
        if (hasSortableFields()) {
            sb.append("    const sort = this.sortField ? `${this.sortField},${this.sortDirection}` : undefined;\n");
        }
        if (hasSessionFilter()) {
            String filterField = getSessionFilterField();
            String filterField2 = getSessionFilterField2();
            sb.append("    const ").append(filterField).append(" = this.sessionService.get").append(filterField.substring(0, 1).toUpperCase()).append(filterField.substring(1)).append("();\n");
            if (filterField2 != null) {
                sb.append("    const ").append(filterField2).append(" = this.sessionService.get").append(filterField2.substring(0, 1).toUpperCase()).append(filterField2.substring(1)).append("();\n");
            }
            // Monta a chamada do findAll com os campos de sessão
            StringBuilder findAllCall = new StringBuilder();
            findAllCall.append("    this.service.findAll(this.currentPage, this.pageSize, ").append(filterField);
            if (filterField2 != null) {
                findAllCall.append(", ").append(filterField2);
            }
            if (hasSortableFields()) {
                findAllCall.append(", sort");
            }
            findAllCall.append(").subscribe({\n");
            sb.append(findAllCall);
        } else {
            if (hasSortableFields()) {
                sb.append("    this.service.findAll(this.currentPage, this.pageSize, sort).subscribe({\n");
            } else {
                sb.append("    this.service.findAll(this.currentPage, this.pageSize).subscribe({\n");
            }
        }
        sb.append("      next: (data) => {\n");
        sb.append("        let items = data.content;\n");
        sb.append("        // Aplicar filtro de navegação mestre-detalhe (parentFilter)\n");
        sb.append("        if (Object.keys(this.parentFilter).length > 0) {\n");
        sb.append("          items = items.filter((item: any) => {\n");
        sb.append("            for (const key of Object.keys(this.parentFilter)) {\n");
        sb.append("              if (item[key] !== this.parentFilter[key]) return false;\n");
        sb.append("            }\n");
        sb.append("            return true;\n");
        sb.append("          });\n");
        sb.append("        }\n");
        sb.append("        this.items = items;\n");
        sb.append("        this.filteredItems = items;\n");
        sb.append("        // Usar valores de paginação do servidor\n");
        sb.append("        this.totalElements = data.totalElements;\n");
        sb.append("        this.totalPages = data.totalPages;\n");
        sb.append("        this.loading = false;\n");
        sb.append("      },\n");
        sb.append("      error: (err) => {\n");
        sb.append("        this.error = 'Erro ao carregar dados';\n");
        sb.append("        this.loading = false;\n");
        sb.append("        console.error(err);\n");
        sb.append("      }\n");
        sb.append("    });\n");
        sb.append("  }\n\n");

        sb.append("  search(): void {\n");
        sb.append("    if (!this.searchTerm) {\n");
        sb.append("      this.filteredItems = this.items;\n");
        sb.append("      return;\n");
        sb.append("    }\n\n");
        sb.append("    const term = this.searchTerm.toLowerCase();\n");
        sb.append("    this.filteredItems = this.items.filter(item =>\n");
        sb.append("      JSON.stringify(item).toLowerCase().includes(term)\n");
        sb.append("    );\n");
        sb.append("  }\n\n");

        sb.append("  novo(): void {\n");
        sb.append("    // Passar o parentFilter como queryParams para o formulário pré-selecionar o FK\n");
        sb.append("    if (Object.keys(this.parentFilter).length > 0) {\n");
        sb.append("      this.router.navigate(['/").append(entityLower).append("/novo'], { queryParams: this.parentFilter });\n");
        sb.append("    } else {\n");
        sb.append("      this.router.navigate(['/").append(entityLower).append("/novo']);\n");
        sb.append("    }\n");
        sb.append("  }\n\n");

        sb.append("  editar(id: ").append(pkType).append("): void {\n");
        if (hasCompositeKey()) {
            sb.append("    const idParam = Object.values(id).join(',');\n");
            sb.append("    this.router.navigate(['/").append(entityLower).append("/editar', idParam]);\n");
        } else {
            sb.append("    this.router.navigate(['/").append(entityLower).append("/editar', id]);\n");
        }
        sb.append("  }\n\n");

        // Método de navegação para entidades filhas
        if (entity.hasChildEntities()) {
            sb.append("  navigateToChild(childRoute: string, foreignKey: string, parentId: ").append(pkType).append("): void {\n");
            sb.append("    this.router.navigate(['/' + childRoute], { queryParams: { [foreignKey]: parentId } });\n");
            sb.append("  }\n\n");
        }

        sb.append("  delete(id: ").append(pkType).append("): void {\n");
        sb.append("    if (!confirm('Deseja realmente excluir este registro?')) {\n");
        sb.append("      return;\n");
        sb.append("    }\n\n");
        sb.append("    this.error = null;\n");
        sb.append("    this.successMessage = null;\n");
        sb.append("    this.service.delete(id).subscribe({\n");
        sb.append("      next: () => {\n");
        sb.append("        this.successMessage = 'Registro excluído com sucesso!';\n");
        sb.append("        this.loadItems();\n");
        sb.append("        setTimeout(() => this.successMessage = null, 3000);\n");
        sb.append("      },\n");
        sb.append("      error: (err) => {\n");
        sb.append("        this.error = err.error?.message || 'Erro ao excluir registro';\n");
        sb.append("        console.error(err);\n");
        sb.append("        setTimeout(() => this.error = null, 5000);\n");
        sb.append("      }\n");
        sb.append("    });\n");
        sb.append("  }\n\n");

        // Métodos de paginação
        sb.append("  // Métodos de paginação\n");
        sb.append("  goToPage(page: number): void {\n");
        sb.append("    if (page >= 0 && page < this.totalPages) {\n");
        sb.append("      this.currentPage = page;\n");
        sb.append("      this.loadItems();\n");
        sb.append("    }\n");
        sb.append("  }\n\n");

        sb.append("  nextPage(): void {\n");
        sb.append("    if (this.currentPage < this.totalPages - 1) {\n");
        sb.append("      this.currentPage++;\n");
        sb.append("      this.loadItems();\n");
        sb.append("    }\n");
        sb.append("  }\n\n");

        sb.append("  previousPage(): void {\n");
        sb.append("    if (this.currentPage > 0) {\n");
        sb.append("      this.currentPage--;\n");
        sb.append("      this.loadItems();\n");
        sb.append("    }\n");
        sb.append("  }\n\n");

        sb.append("  firstPage(): void {\n");
        sb.append("    this.currentPage = 0;\n");
        sb.append("    this.loadItems();\n");
        sb.append("  }\n\n");

        sb.append("  lastPage(): void {\n");
        sb.append("    this.currentPage = this.totalPages - 1;\n");
        sb.append("    this.loadItems();\n");
        sb.append("  }\n\n");

        sb.append("  onPageSizeChange(): void {\n");
        sb.append("    this.currentPage = 0;\n");
        sb.append("    this.loadItems();\n");
        sb.append("  }\n\n");

        // Método de ordenação (apenas se houver campos ordenáveis)
        if (hasSortableFields()) {
            sb.append("  toggleSort(field: string): void {\n");
            sb.append("    if (this.sortField === field) {\n");
            sb.append("      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';\n");
            sb.append("    } else {\n");
            sb.append("      this.sortField = field;\n");
            sb.append("      this.sortDirection = 'asc';\n");
            sb.append("    }\n");
            sb.append("    this.currentPage = 0;\n");
            sb.append("    this.loadItems();\n");
            sb.append("  }\n\n");
        }

        sb.append("  getPageNumbers(): number[] {\n");
        sb.append("    const pages: number[] = [];\n");
        sb.append("    const maxVisiblePages = 5;\n");
        sb.append("    let start = Math.max(0, this.currentPage - Math.floor(maxVisiblePages / 2));\n");
        sb.append("    let end = Math.min(this.totalPages, start + maxVisiblePages);\n");
        sb.append("    if (end - start < maxVisiblePages) {\n");
        sb.append("      start = Math.max(0, end - maxVisiblePages);\n");
        sb.append("    }\n");
        sb.append("    for (let i = start; i < end; i++) {\n");
        sb.append("      pages.push(i);\n");
        sb.append("    }\n");
        sb.append("    return pages;\n");
        sb.append("  }\n\n");

        sb.append("  getLastItemIndex(): number {\n");
        sb.append("    return Math.min((this.currentPage + 1) * this.pageSize, this.totalElements);\n");
        sb.append("  }\n\n");

        // Métodos de formatação de números (apenas se houver campos numéricos no grid)
        if (hasNumericFieldsInGrid()) {
            sb.append("  /**\n");
            sb.append("   * Formata número inteiro com separador de milhar (ponto).\n");
            sb.append("   */\n");
            sb.append("  formatInt(value: number | null | undefined): string {\n");
            sb.append("    if (value === null || value === undefined) return '';\n");
            sb.append("    return value.toLocaleString('pt-BR', { maximumFractionDigits: 0 });\n");
            sb.append("  }\n\n");

            sb.append("  /**\n");
            sb.append("   * Formata número decimal com separador de milhar (ponto) e decimal (vírgula).\n");
            sb.append("   */\n");
            sb.append("  formatDecimal(value: number | null | undefined, decimals: number = 2): string {\n");
            sb.append("    if (value === null || value === undefined) return '';\n");
            sb.append("    return value.toLocaleString('pt-BR', { minimumFractionDigits: decimals, maximumFractionDigits: decimals });\n");
            sb.append("  }\n");
        }

        // Método de formatação de datas (apenas se houver campos de data no grid)
        if (hasDateFieldsInGrid()) {
            sb.append("\n  /**\n");
            sb.append("   * Formata data no padrão brasileiro (dd/mm/aaaa).\n");
            sb.append("   */\n");
            sb.append("  formatDate(value: Date | string | null | undefined): string {\n");
            sb.append("    if (value === null || value === undefined) return '';\n");
            sb.append("    const date = typeof value === 'string' ? new Date(value + 'T00:00:00') : value;\n");
            sb.append("    return date.toLocaleDateString('pt-BR');\n");
            sb.append("  }\n");
        }

        // Métodos para traduzir valores de campos com options (enum) para labels
        generateOptionsLabelMethods(sb);

        sb.append("}\n");

        return sb.toString();
    }

    public String generateHtml() {
        String displayName = getDisplayName();

        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"container\">\n");
        sb.append("  <div class=\"header\">\n");

        // Título com nome do contexto de sessão (ex: "Avaliação SDA : 2025.2 - Aporá")
        // ou nome do pai na navegação mestre-detalhe (ex: "Segmentos Atendidos : Educadores")
        if (hasSessionFilter()) {
            String filterField = getSessionFilterField();
            String filterField2 = getSessionFilterField2();
            String contextBase = filterField.endsWith("Id")
                ? filterField.substring(0, filterField.length() - 2)
                : filterField;

            // Se tem dois campos de sessão, mostrar ambos (ex: "2025.2 - Aporá")
            if (filterField2 != null) {
                String contextBase2 = filterField2.endsWith("Id")
                    ? filterField2.substring(0, filterField2.length() - 2)
                    : filterField2;
                sb.append("    <h2>").append(displayName);
                sb.append(" <span *ngIf=\"").append(contextBase2).append("Nome || ").append(contextBase).append("Nome\" class=\"context-name\">");
                sb.append(": {{ ").append(contextBase2).append("Nome }}");
                sb.append("<ng-container *ngIf=\"").append(contextBase2).append("Nome && ").append(contextBase).append("Nome\"> - </ng-container>");
                sb.append("{{ ").append(contextBase).append("Nome }}");
                sb.append("</span></h2>\n");
            } else {
                // Apenas um campo de sessão
                sb.append("    <h2>").append(displayName).append(" <span *ngIf=\"").append(contextBase).append("Nome\" class=\"context-name\">: {{ ").append(contextBase).append("Nome }}</span></h2>\n");
            }
        } else {
            // Mostrar parentName quando navegando de uma tela pai (mestre-detalhe)
            sb.append("    <h2>").append(displayName).append(" <span *ngIf=\"parentName\" class=\"context-name\">: {{ parentName }}</span></h2>\n");
        }

        sb.append("    <div class=\"header-actions\">\n");
        sb.append("      <div class=\"search-box\">\n");
        sb.append("        <svg class=\"search-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("          <circle cx=\"11\" cy=\"11\" r=\"8\"></circle>\n");
        sb.append("          <line x1=\"21\" y1=\"21\" x2=\"16.65\" y2=\"16.65\"></line>\n");
        sb.append("        </svg>\n");
        sb.append("        <input \n");
        sb.append("          type=\"text\" \n");
        sb.append("          [(ngModel)]=\"searchTerm\" \n");
        sb.append("          (input)=\"search()\" \n");
        sb.append("          placeholder=\"Buscar...\" \n");
        sb.append("          class=\"search-input\"\n");
        sb.append("        />\n");
        sb.append("      </div>\n");
        sb.append("      <button class=\"btn btn-primary\" (click)=\"novo()\">\n");
        sb.append("        <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("          <line x1=\"12\" y1=\"5\" x2=\"12\" y2=\"19\"></line>\n");
        sb.append("          <line x1=\"5\" y1=\"12\" x2=\"19\" y2=\"12\"></line>\n");
        sb.append("        </svg>\n");
        sb.append("        <span>Novo</span>\n");
        sb.append("      </button>\n");
        sb.append("    </div>\n");
        sb.append("  </div>\n\n");

        sb.append("  <div *ngIf=\"loading\" class=\"loading\">Carregando...</div>\n\n");

        sb.append("  <div *ngIf=\"successMessage\" class=\"success\">{{ successMessage }}</div>\n\n");

        sb.append("  <div *ngIf=\"error\" class=\"error\">{{ error }}</div>\n\n");

        sb.append("  <div *ngIf=\"!loading && !error\" class=\"table-container\">\n");
        sb.append("    <table class=\"data-table\">\n");
        sb.append("      <thead>\n");
        sb.append("        <tr>\n");

        // Headers das colunas
        for (Field field : entity.getFields()) {
            if (shouldShowInList(field)) {
                // Para PK, usar "#" em vez de "Código"
                String label = field.isPrimaryKey() ? "#" : getFieldLabel(field);
                String fieldName = field.getName();
                // Determinar classes CSS do header
                // Não alinhar à direita se for FK (exibe texto, não número)
                boolean isNumeric = isNumericField(field) && !field.isPrimaryKey() && !field.isForeignKey();
                String thClass = isNumeric ? "text-right" : "";

                // Se é FK, envolver com ng-container para esconder quando filtrado pelo parentFilter
                boolean isFk = field.isForeignKey() && field.getReference() != null;
                if (isFk) {
                    sb.append("          <ng-container *ngIf=\"parentFilterKey !== '").append(fieldName).append("'\">\n");
                }

                if (isSortable(field)) {
                    // Coluna ordenável - header clicável
                    String sortableClass = thClass.isEmpty() ? "sortable" : "sortable " + thClass;
                    sb.append("          <th class=\"").append(sortableClass).append("\" (click)=\"toggleSort('").append(fieldName).append("')\">\n");
                    sb.append("            <span class=\"th-content\">\n");
                    sb.append("              ").append(label).append("\n");
                    sb.append("              <span class=\"sort-icon\" *ngIf=\"sortField === '").append(fieldName).append("'\">\n");
                    sb.append("                <svg *ngIf=\"sortDirection === 'asc'\" viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 14l5-5 5 5z\"/></svg>\n");
                    sb.append("                <svg *ngIf=\"sortDirection === 'desc'\" viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 10l5 5 5-5z\"/></svg>\n");
                    sb.append("              </span>\n");
                    sb.append("              <span class=\"sort-icon inactive\" *ngIf=\"sortField !== '").append(fieldName).append("'\">\n");
                    sb.append("                <svg viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 10l5-5 5 5M7 14l5 5 5-5\" stroke=\"currentColor\" fill=\"none\" stroke-width=\"1.5\"/></svg>\n");
                    sb.append("              </span>\n");
                    sb.append("            </span>\n");
                    sb.append("          </th>\n");
                } else {
                    // Coluna não ordenável
                    if (thClass.isEmpty()) {
                        sb.append("          <th>").append(label).append("</th>\n");
                    } else {
                        sb.append("          <th class=\"").append(thClass).append("\">").append(label).append("</th>\n");
                    }
                }

                // Fechar ng-container para FK
                if (isFk) {
                    sb.append("          </ng-container>\n");
                }
            }
        }
        sb.append("          <th class=\"actions-column\">Ações</th>\n");
        sb.append("        </tr>\n");
        sb.append("      </thead>\n");
        sb.append("      <tbody>\n");
        sb.append("        <tr *ngFor=\"let item of filteredItems\">\n");

        // Dados das colunas
        for (Field field : entity.getFields()) {
            if (shouldShowInList(field)) {
                String fieldName = field.getName();
                // Não alinhar à direita se for FK (exibe texto, não número)
                // Também não alinhar à direita se tiver options (é um enum)
                boolean hasOptions = field.getUi() != null && field.getUi().hasOptions();
                boolean isNumeric = isNumericField(field) && !field.isPrimaryKey() && !field.isForeignKey() && !hasOptions;
                boolean isDecimal = isDecimalField(field) && !hasOptions;
                boolean isDate = isDateField(field);
                String tdClass = isNumeric ? " class=\"text-right\"" : "";

                // Se é FK, usar o map para mostrar o nome em vez do ID
                // E esconder quando filtrado pelo parentFilter
                if (field.isForeignKey() && field.getReference() != null) {
                    String refEntity = field.getReference().getEntity();
                    String refLower = refEntity.substring(0, 1).toLowerCase() + refEntity.substring(1);
                    // Envolver com ng-container para esconder quando filtrado
                    sb.append("          <ng-container *ngIf=\"parentFilterKey !== '").append(fieldName).append("'\">\n");
                    sb.append("            <td>{{ ").append(refLower).append("Map.get(item.").append(fieldName).append(") || item.").append(fieldName).append(" }}</td>\n");
                    sb.append("          </ng-container>\n");
                } else if (hasOptions) {
                    // Campo com options (enum) - usar método para traduzir valor para label
                    sb.append("          <td>{{ get").append(capitalize(fieldName)).append("Label(item.").append(fieldName).append(") }}</td>\n");
                } else if (isDate) {
                    // Campo de data - usar formatDate para exibir no padrão brasileiro
                    sb.append("          <td>{{ formatDate(item.").append(fieldName).append(") }}</td>\n");
                } else if (isDecimal) {
                    // Número decimal - usar formatDecimal
                    sb.append("          <td").append(tdClass).append(">{{ formatDecimal(item.").append(fieldName).append(") }}</td>\n");
                } else if (isNumeric) {
                    // Número inteiro - usar formatInt
                    sb.append("          <td").append(tdClass).append(">{{ formatInt(item.").append(fieldName).append(") }}</td>\n");
                } else {
                    sb.append("          <td>{{ item.").append(fieldName).append(" }}</td>\n");
                }
            }
        }

        sb.append("          <td class=\"actions-column\">\n");

        // Botões de navegação para entidades filhas
        if (entity.hasChildEntities()) {
            for (var childEntity : entity.getChildEntities()) {
                // Usar camelCase para a rota (mesmo padrão das rotas Angular)
                String childRoute = toCamelCase(childEntity.getEntity());
                String childLabel = childEntity.getLabel();
                sb.append("            <button class=\"btn btn-child btn-sm\" (click)=\"navigateToChild('").append(childRoute).append("', '").append(childEntity.getForeignKey()).append("', ").append(getDeleteIdExpression()).append(")\" title=\"").append(childLabel).append("\">\n");
                sb.append("              <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
                sb.append("                <path d=\"M9 5H7a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V7a2 2 0 0 0-2-2h-2\"></path>\n");
                sb.append("                <rect x=\"9\" y=\"3\" width=\"6\" height=\"4\" rx=\"1\"></rect>\n");
                sb.append("                <path d=\"M9 12h6\"></path>\n");
                sb.append("                <path d=\"M9 16h6\"></path>\n");
                sb.append("              </svg>\n");
                sb.append("              <span>").append(childLabel).append("</span>\n");
                sb.append("            </button>\n");
            }
        }

        sb.append("            <button class=\"btn btn-edit btn-sm\" (click)=\"editar(").append(getDeleteIdExpression()).append(")\" title=\"Editar\">\n");
        sb.append("              <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("                <path d=\"M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7\"></path>\n");
        sb.append("                <path d=\"M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z\"></path>\n");
        sb.append("              </svg>\n");
        sb.append("              <span>Editar</span>\n");
        sb.append("            </button>\n");
        sb.append("            <button class=\"btn btn-danger btn-sm\" (click)=\"delete(").append(getDeleteIdExpression()).append(")\" title=\"Excluir\">\n");
        sb.append("              <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("                <polyline points=\"3 6 5 6 21 6\"></polyline>\n");
        sb.append("                <path d=\"M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2\"></path>\n");
        sb.append("              </svg>\n");
        sb.append("              <span>Excluir</span>\n");
        sb.append("            </button>\n");
        sb.append("          </td>\n");
        sb.append("        </tr>\n");
        sb.append("        <tr *ngIf=\"filteredItems.length === 0\">\n");
        sb.append("          <td colspan=\"").append(getVisibleFieldCount() + 1).append("\" class=\"no-data\">\n");
        sb.append("            Nenhum registro encontrado\n");
        sb.append("          </td>\n");
        sb.append("        </tr>\n");
        sb.append("      </tbody>\n");
        sb.append("    </table>\n\n");

        // Controles de paginação
        sb.append("    <!-- Paginação -->\n");
        sb.append("    <div class=\"pagination-container\" *ngIf=\"totalPages > 1\">\n");
        sb.append("      <div class=\"pagination-info\">\n");
        sb.append("        Mostrando {{ currentPage * pageSize + 1 }} - {{ getLastItemIndex() }} de {{ totalElements }} registros\n");
        sb.append("      </div>\n");
        sb.append("      <div class=\"pagination-controls\">\n");
        sb.append("        <button class=\"btn btn-pagination\" (click)=\"firstPage()\" [disabled]=\"currentPage === 0\" title=\"Primeira página\">\n");
        sb.append("          <svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><polyline points=\"11 17 6 12 11 7\"></polyline><polyline points=\"18 17 13 12 18 7\"></polyline></svg>\n");
        sb.append("        </button>\n");
        sb.append("        <button class=\"btn btn-pagination\" (click)=\"previousPage()\" [disabled]=\"currentPage === 0\" title=\"Página anterior\">\n");
        sb.append("          <svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><polyline points=\"15 18 9 12 15 6\"></polyline></svg>\n");
        sb.append("        </button>\n");
        sb.append("        <span class=\"page-numbers\">\n");
        sb.append("          <button \n");
        sb.append("            *ngFor=\"let page of getPageNumbers()\" \n");
        sb.append("            class=\"btn btn-page\" \n");
        sb.append("            [class.active]=\"page === currentPage\"\n");
        sb.append("            (click)=\"goToPage(page)\">\n");
        sb.append("            {{ page + 1 }}\n");
        sb.append("          </button>\n");
        sb.append("        </span>\n");
        sb.append("        <button class=\"btn btn-pagination\" (click)=\"nextPage()\" [disabled]=\"currentPage >= totalPages - 1\" title=\"Próxima página\">\n");
        sb.append("          <svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><polyline points=\"9 18 15 12 9 6\"></polyline></svg>\n");
        sb.append("        </button>\n");
        sb.append("        <button class=\"btn btn-pagination\" (click)=\"lastPage()\" [disabled]=\"currentPage >= totalPages - 1\" title=\"Última página\">\n");
        sb.append("          <svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><polyline points=\"13 17 18 12 13 7\"></polyline><polyline points=\"6 17 11 12 6 7\"></polyline></svg>\n");
        sb.append("        </button>\n");
        sb.append("        <select class=\"page-size-select\" [(ngModel)]=\"pageSize\" (change)=\"onPageSizeChange()\">\n");
        sb.append("          <option [value]=\"10\">10 por página</option>\n");
        sb.append("          <option [value]=\"20\">20 por página</option>\n");
        sb.append("          <option [value]=\"50\">50 por página</option>\n");
        sb.append("          <option [value]=\"100\">100 por página</option>\n");
        sb.append("        </select>\n");
        sb.append("      </div>\n");
        sb.append("    </div>\n");
        sb.append("  </div>\n");
        sb.append("</div>\n");

        return sb.toString();
    }

    public String generateCss() {
        StringBuilder sb = new StringBuilder();

        sb.append(".container {\n");
        sb.append("  padding: 0;\n");
        sb.append("}\n\n");

        sb.append(".header {\n");
        sb.append("  display: flex;\n");
        sb.append("  justify-content: space-between;\n");
        sb.append("  align-items: center;\n");
        sb.append("  margin-bottom: 1.5rem;\n");
        sb.append("}\n\n");

        sb.append(".header h2 {\n");
        sb.append("  margin: 0;\n");
        sb.append("  color: #333;\n");
        sb.append("}\n\n");

        sb.append(".context-name {\n");
        sb.append("  color: #667eea;\n");
        sb.append("  font-weight: 600;\n");
        sb.append("}\n\n");

        sb.append(".header-actions {\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 1rem;\n");
        sb.append("}\n\n");

        sb.append(".search-box {\n");
        sb.append("  position: relative;\n");
        sb.append("  flex: 1;\n");
        sb.append("  max-width: 300px;\n");
        sb.append("}\n\n");

        sb.append(".search-icon {\n");
        sb.append("  position: absolute;\n");
        sb.append("  left: 10px;\n");
        sb.append("  top: 50%;\n");
        sb.append("  transform: translateY(-50%);\n");
        sb.append("  width: 18px;\n");
        sb.append("  height: 18px;\n");
        sb.append("  color: #999;\n");
        sb.append("}\n\n");

        sb.append(".search-input {\n");
        sb.append("  width: 100%;\n");
        sb.append("  padding: 0.5rem 1rem 0.5rem 36px;\n");
        sb.append("  border: 1px solid #ddd;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  font-size: 14px;\n");
        sb.append("}\n\n");

        sb.append(".search-input:focus {\n");
        sb.append("  outline: none;\n");
        sb.append("  border-color: #667eea;\n");
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

        sb.append(".table-container {\n");
        sb.append("  background: white;\n");
        sb.append("  border-radius: 8px;\n");
        sb.append("  box-shadow: 0 2px 4px rgba(0,0,0,0.1);\n");
        sb.append("  overflow: hidden;\n");
        sb.append("}\n\n");

        sb.append(".data-table {\n");
        sb.append("  width: 100%;\n");
        sb.append("  border-collapse: collapse;\n");
        sb.append("}\n\n");

        sb.append(".data-table thead {\n");
        sb.append("  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n");
        sb.append("  color: white;\n");
        sb.append("}\n\n");

        sb.append(".data-table th {\n");
        sb.append("  padding: 1rem;\n");
        sb.append("  text-align: left;\n");
        sb.append("  font-weight: 600;\n");
        sb.append("}\n\n");

        sb.append(".data-table th.sortable {\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  user-select: none;\n");
        sb.append("  transition: background 0.2s;\n");
        sb.append("}\n\n");

        sb.append(".data-table th.sortable:hover {\n");
        sb.append("  background: rgba(255, 255, 255, 0.1);\n");
        sb.append("}\n\n");

        sb.append(".th-content {\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 0.5rem;\n");
        sb.append("}\n\n");

        sb.append(".sort-icon {\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("}\n\n");

        sb.append(".sort-icon svg {\n");
        sb.append("  width: 16px;\n");
        sb.append("  height: 16px;\n");
        sb.append("}\n\n");

        sb.append(".sort-icon.inactive {\n");
        sb.append("  opacity: 0.4;\n");
        sb.append("}\n\n");

        sb.append(".data-table td {\n");
        sb.append("  padding: 0.75rem 1rem;\n");
        sb.append("  border-bottom: 1px solid #eee;\n");
        sb.append("}\n\n");

        sb.append(".data-table tbody tr:hover {\n");
        sb.append("  background: #f8f9fa;\n");
        sb.append("}\n\n");

        sb.append(".actions-column {\n");
        // Largura dinâmica: mais espaço se tiver botões de childEntities
        int childCount = entity.hasChildEntities() ? entity.getChildEntities().size() : 0;
        int actionsWidth = 180 + (childCount * 100); // 100px extra por cada botão filho
        sb.append("  width: ").append(actionsWidth).append("px;\n");
        sb.append("  text-align: center;\n");
        sb.append("}\n\n");

        sb.append("td.actions-column {\n");
        sb.append("  display: flex;\n");
        sb.append("  justify-content: center;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 0.5rem;\n");
        sb.append("}\n\n");

        sb.append(".text-right {\n");
        sb.append("  text-align: right;\n");
        sb.append("}\n\n");

        sb.append(".text-right .th-content {\n");
        sb.append("  justify-content: flex-end;\n");
        sb.append("}\n\n");

        sb.append(".no-data {\n");
        sb.append("  text-align: center;\n");
        sb.append("  color: #999;\n");
        sb.append("  padding: 2rem !important;\n");
        sb.append("}\n\n");

        sb.append(".btn {\n");
        sb.append("  display: inline-flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 0.4rem;\n");
        sb.append("  padding: 0.5rem 1rem;\n");
        sb.append("  border: none;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  font-size: 14px;\n");
        sb.append("  font-weight: 500;\n");
        sb.append("  transition: all 0.2s;\n");
        sb.append("}\n\n");

        sb.append(".btn .icon {\n");
        sb.append("  width: 16px;\n");
        sb.append("  height: 16px;\n");
        sb.append("  flex-shrink: 0;\n");
        sb.append("}\n\n");

        sb.append(".btn-sm {\n");
        sb.append("  padding: 0.35rem 0.6rem;\n");
        sb.append("  font-size: 12px;\n");
        sb.append("  gap: 0.3rem;\n");
        sb.append("}\n\n");

        sb.append(".btn-sm .icon {\n");
        sb.append("  width: 14px;\n");
        sb.append("  height: 14px;\n");
        sb.append("}\n\n");

        sb.append(".btn-primary {\n");
        sb.append("  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n");
        sb.append("  color: white;\n");
        sb.append("}\n\n");

        sb.append(".btn-primary:hover {\n");
        sb.append("  background: linear-gradient(135deg, #5a6fd6 0%, #6a4190 100%);\n");
        sb.append("}\n\n");

        sb.append(".btn-edit {\n");
        sb.append("  background: #17a2b8;\n");
        sb.append("  color: white;\n");
        sb.append("}\n\n");

        sb.append(".btn-edit:hover {\n");
        sb.append("  background: #138496;\n");
        sb.append("}\n\n");

        sb.append(".btn-danger {\n");
        sb.append("  background: #dc3545;\n");
        sb.append("  color: white;\n");
        sb.append("}\n\n");

        sb.append(".btn-danger:hover {\n");
        sb.append("  background: #c82333;\n");
        sb.append("}\n\n");

        sb.append(".btn-child {\n");
        sb.append("  background: #6f42c1;\n");
        sb.append("  color: white;\n");
        sb.append("}\n\n");

        sb.append(".btn-child:hover {\n");
        sb.append("  background: #5a32a3;\n");
        sb.append("}\n\n");

        // Estilos de paginação
        sb.append("/* Paginação */\n");
        sb.append(".pagination-container {\n");
        sb.append("  display: flex;\n");
        sb.append("  justify-content: space-between;\n");
        sb.append("  align-items: center;\n");
        sb.append("  padding: 1rem;\n");
        sb.append("  border-top: 1px solid #eee;\n");
        sb.append("  background: #fafafa;\n");
        sb.append("}\n\n");

        sb.append(".pagination-info {\n");
        sb.append("  color: #666;\n");
        sb.append("  font-size: 14px;\n");
        sb.append("}\n\n");

        sb.append(".pagination-controls {\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 0.5rem;\n");
        sb.append("}\n\n");

        sb.append(".btn-pagination {\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  justify-content: center;\n");
        sb.append("  width: 32px;\n");
        sb.append("  height: 32px;\n");
        sb.append("  padding: 0;\n");
        sb.append("  border: 1px solid #ddd;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  background: white;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  transition: all 0.2s;\n");
        sb.append("}\n\n");

        sb.append(".btn-pagination:hover:not(:disabled) {\n");
        sb.append("  background: #f0f0f0;\n");
        sb.append("  border-color: #ccc;\n");
        sb.append("}\n\n");

        sb.append(".btn-pagination:disabled {\n");
        sb.append("  opacity: 0.5;\n");
        sb.append("  cursor: not-allowed;\n");
        sb.append("}\n\n");

        sb.append(".btn-pagination svg {\n");
        sb.append("  width: 16px;\n");
        sb.append("  height: 16px;\n");
        sb.append("  color: #666;\n");
        sb.append("}\n\n");

        sb.append(".page-numbers {\n");
        sb.append("  display: flex;\n");
        sb.append("  gap: 0.25rem;\n");
        sb.append("}\n\n");

        sb.append(".btn-page {\n");
        sb.append("  min-width: 32px;\n");
        sb.append("  height: 32px;\n");
        sb.append("  padding: 0 0.5rem;\n");
        sb.append("  border: 1px solid #ddd;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  background: white;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  font-size: 14px;\n");
        sb.append("  transition: all 0.2s;\n");
        sb.append("}\n\n");

        sb.append(".btn-page:hover {\n");
        sb.append("  background: #f0f0f0;\n");
        sb.append("  border-color: #ccc;\n");
        sb.append("}\n\n");

        sb.append(".btn-page.active {\n");
        sb.append("  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n");
        sb.append("  color: white;\n");
        sb.append("  border-color: transparent;\n");
        sb.append("}\n\n");

        sb.append(".page-size-select {\n");
        sb.append("  padding: 0.4rem 0.75rem;\n");
        sb.append("  border: 1px solid #ddd;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  font-size: 14px;\n");
        sb.append("  background: white;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  margin-left: 1rem;\n");
        sb.append("}\n\n");

        sb.append(".page-size-select:focus {\n");
        sb.append("  outline: none;\n");
        sb.append("  border-color: #667eea;\n");
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
            // Prefixos de ID
            .replaceAll("^Id ", "")
            .replaceAll(" Id$", "")
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
            .replace("Ir", "IR");
    }

    private boolean isVisibleInGrid(Field field) {
        // Mesmo critério usado no backend para gerar o ListDTO
        return field.getUi() != null
            && field.getUi().getGrid() != null
            && field.getUi().getGrid().isVisible();
    }

    private boolean isSortable(Field field) {
        return field.getUi() != null
            && field.getUi().getGrid() != null
            && field.getUi().getGrid().isSortable();
    }

    /**
     * Verifica se o campo é numérico (INTEGER, LONG, DECIMAL, MONEY).
     */
    private boolean isNumericField(Field field) {
        if (field.getDataType() == null) {
            return false;
        }
        switch (field.getDataType()) {
            case INTEGER:
            case LONG:
            case DECIMAL:
            case MONEY:
                return true;
            default:
                return false;
        }
    }

    /**
     * Verifica se o campo é decimal (DECIMAL, MONEY).
     */
    private boolean isDecimalField(Field field) {
        if (field.getDataType() == null) {
            return false;
        }
        switch (field.getDataType()) {
            case DECIMAL:
            case MONEY:
                return true;
            default:
                return false;
        }
    }

    /**
     * Verifica se o campo é de data (DATE, DATETIME).
     */
    private boolean isDateField(Field field) {
        if (field.getDataType() == null) {
            return false;
        }
        switch (field.getDataType()) {
            case DATE:
            case DATETIME:
                return true;
            default:
                return false;
        }
    }

    /**
     * Verifica se a entidade tem campos numéricos visíveis no grid.
     */
    private boolean hasNumericFieldsInGrid() {
        for (Field field : entity.getFields()) {
            if (shouldShowInList(field) && isNumericField(field) && !field.isPrimaryKey()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se a entidade tem campos de data visíveis no grid.
     */
    private boolean hasDateFieldsInGrid() {
        for (Field field : entity.getFields()) {
            if (shouldShowInList(field) && isDateField(field)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSortableFields() {
        for (Field field : entity.getFields()) {
            if (isSortable(field)) {
                return true;
            }
        }
        return false;
    }

    private boolean shouldShowInList(Field field) {
        // Não exibir o campo de sessionFilter no grid (já que está filtrado pelo contexto)
        if (isSessionFilterField(field)) {
            return false;
        }
        // Mesmo critério usado no backend: isVisibleInGrid(field) || field.isPrimaryKey()
        return isVisibleInGrid(field) || field.isPrimaryKey();
    }

    private int getVisibleFieldCount() {
        int count = 0;
        for (Field field : entity.getFields()) {
            if (shouldShowInList(field)) {
                count++;
            }
        }
        return count;
    }

    private String getPrimaryKeyFieldName() {
        for (Field field : entity.getFields()) {
            if (field.isPrimaryKey()) {
                return field.getName();
            }
        }
        // Fallback para o primeiro campo caso nenhum seja marcado como PK
        if (entity.getFields() != null && !entity.getFields().isEmpty()) {
            return entity.getFields().get(0).getName();
        }
        return "id";
    }

    private String getPrimaryKeyType() {
        // Se tem chave composta, retorna o tipo EntityId
        if (hasCompositeKey()) {
            return entity.getName() + "Id";
        }
        // Se tem PK simples, retorna o tipo TypeScript correspondente
        for (Field field : entity.getFields()) {
            if (field.isPrimaryKey()) {
                return toTypeScriptType(field);
            }
        }
        // Fallback para number se nenhum campo for marcado como PK
        return "number";
    }

    private boolean hasCompositeKey() {
        int pkCount = 0;
        for (Field field : entity.getFields()) {
            if (field.isPrimaryKey()) {
                pkCount++;
            }
        }
        return pkCount > 1;
    }

    private java.util.List<Field> getPrimaryKeyFields() {
        java.util.List<Field> pkFields = new java.util.ArrayList<>();
        for (Field field : entity.getFields()) {
            if (field.isPrimaryKey()) {
                pkFields.add(field);
            }
        }
        return pkFields;
    }

    private String getDeleteIdExpression() {
        if (hasCompositeKey()) {
            // Para chave composta, cria um objeto com todos os campos da PK
            StringBuilder sb = new StringBuilder();
            sb.append("{ ");
            boolean first = true;
            for (Field field : getPrimaryKeyFields()) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(field.getName()).append(": item.").append(field.getName());
                first = false;
            }
            sb.append(" }");
            return sb.toString();
        } else {
            // Para PK simples, usa o campo diretamente
            return "item." + getPrimaryKeyFieldName();
        }
    }

    private String toTypeScriptType(Field field) {
        DataType dataType = field.getDataType();

        switch (dataType) {
            case INTEGER:
            case LONG:
            case DECIMAL:
            case MONEY:
                return "number";
            case STRING:
            case TEXT:
                return "string";
            case BOOLEAN:
                return "boolean";
            case DATE:
            case DATETIME:
            case TIME:
                return "Date | string";
            default:
                return "any";
        }
    }

    /**
     * Converte PascalCase para camelCase.
     * Ex: SegmentoAtendido -> segmentoAtendido
     */
    private String toCamelCase(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    /**
     * Capitaliza a primeira letra de uma string.
     */
    private String capitalize(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * Gera métodos para traduzir valores de campos com options (enum) para seus labels.
     * Exemplo: getValorLabel(value) retorna 'Realizado Total' para value 3.
     */
    private void generateOptionsLabelMethods(StringBuilder sb) {
        for (Field field : entity.getFields()) {
            if (field.getUi() != null && field.getUi().hasOptions() && shouldShowInList(field)) {
                String fieldName = field.getName();
                String methodName = "get" + capitalize(fieldName) + "Label";

                sb.append("\n  /**\n");
                sb.append("   * Retorna o label correspondente ao valor do campo ").append(fieldName).append(".\n");
                sb.append("   */\n");
                sb.append("  ").append(methodName).append("(value: number | null | undefined): string {\n");
                sb.append("    if (value === null || value === undefined) return '';\n");
                sb.append("    switch (value) {\n");

                for (var option : field.getUi().getOptions()) {
                    Object optValue = option.getValue();
                    String label = option.getLabel();
                    // Converter Double para Integer se for um número inteiro
                    String valueStr;
                    if (optValue instanceof Number) {
                        Number numValue = (Number) optValue;
                        if (numValue instanceof Double && numValue.doubleValue() == Math.floor(numValue.doubleValue())) {
                            valueStr = String.valueOf(numValue.intValue());
                        } else {
                            valueStr = String.valueOf(numValue);
                        }
                    } else {
                        valueStr = String.valueOf(optValue);
                    }
                    sb.append("      case ").append(valueStr).append(": return '").append(label).append("';\n");
                }

                sb.append("      default: return String(value);\n");
                sb.append("    }\n");
                sb.append("  }\n");
            }
        }
    }
}
