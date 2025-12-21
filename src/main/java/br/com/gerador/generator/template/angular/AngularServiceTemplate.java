package br.com.gerador.generator.template.angular;

import br.com.gerador.metamodel.model.Entity;
import br.com.gerador.metamodel.model.Field;
import br.com.gerador.metamodel.model.MetaModel;
import br.com.gerador.generator.util.NamingUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Template para geração de Services Angular.
 */
public class AngularServiceTemplate {

    private MetaModel metaModel;

    public void setMetaModel(MetaModel metaModel) {
        this.metaModel = metaModel;
    }

    /**
     * Gera service TypeScript para a entidade.
     */
    public String generate(Entity entity) {
        StringBuilder sb = new StringBuilder();
        String entityName = entity.getName();
        String entityLower = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
        String apiEndpoint = NamingUtils.toRestEndpoint(entityName); // Ex: /api/estados-civis
        String pkType = getPrimaryKeyType(entity);

        // Imports
        sb.append("import { Injectable } from '@angular/core';\n");
        sb.append("import { HttpClient, HttpParams } from '@angular/common/http';\n");
        sb.append("import { Observable } from 'rxjs';\n");
        sb.append("import { ").append(entityName).append(", ").append(entityName).append("Request, ");
        sb.append(entityName).append("List");

        // Adiciona import do tipo ID se for chave composta
        if (pkType.equals(entityName + "Id")) {
            sb.append(", ").append(entityName).append("Id");
        }

        sb.append(" } from '../models/").append(entityLower).append(".model';\n");
        // Se for entidade Usuario, importa Perfil do model separado
        if ("Usuario".equals(entityName)) {
            sb.append("import { Perfil } from '../models/perfil.model';\n");
        }
        sb.append("import { environment } from '../../environments/environment';\n\n");

        // Comentário
        sb.append("/**\n");
        sb.append(" * Service: ").append(entity.getDescription() != null ? entity.getDescription() : entityName).append("\n");
        sb.append(" * Auto-gerado pelo gerador de código\n");
        sb.append(" */\n");

        // Classe do Service
        sb.append("@Injectable({\n");
        sb.append("  providedIn: 'root'\n");
        sb.append("})\n");
        sb.append("export class ").append(entityName).append("Service {\n\n");

        // URL base da API - usa o mesmo endpoint do backend
        sb.append("  private readonly apiUrl = `${environment.apiUrl}").append(apiEndpoint.substring(4)).append("`;\n\n");

        // Construtor
        sb.append("  constructor(private http: HttpClient) {}\n\n");

        // Método findAll
        sb.append("  /**\n");
        sb.append("   * Lista todos os registros com paginação.\n");
        sb.append("   */\n");

        // Verifica se a entity tem sessionFilter e defaultSort
        boolean hasSessionFilter = entity.hasSessionFilter();
        String sessionFilterField = hasSessionFilter ? entity.getSessionFilter().getField() : null;
        String sessionFilterField2 = hasSessionFilter && entity.getSessionFilter().hasField2() ? entity.getSessionFilter().getField2() : null;
        boolean hasDefaultSort = entity.hasDefaultSort();
        String defaultSortField = hasDefaultSort ? entity.getDefaultSort().getField() : null;
        String defaultSortDirection = hasDefaultSort ? entity.getDefaultSort().getDirection() : "asc";

        // Para entidades com chave composta, verifica se o campo de sort faz parte da PK
        // Se sim, precisa prefixar com "id." para JPA/Hibernate
        String sortFieldForQuery = defaultSortField;
        boolean hasCompositeKey = entity.hasCompositePrimaryKey();
        if (hasDefaultSort && hasCompositeKey && defaultSortField != null) {
            // Verifica se o campo de sort é parte da chave composta
            boolean sortFieldIsPk = entity.getFields().stream()
                .anyMatch(f -> f.getName().equals(defaultSortField) && f.isPrimaryKey());
            if (sortFieldIsPk) {
                sortFieldForQuery = "id." + defaultSortField;
            }
        }
        String defaultSortValue = hasDefaultSort ? sortFieldForQuery + "," + defaultSortDirection : null;

        if (hasSessionFilter) {
            // Gera assinatura do método com parâmetros de sessão
            sb.append("  findAll(page: number = 0, size: number = 20, ").append(sessionFilterField).append("?: number | null");
            if (sessionFilterField2 != null) {
                sb.append(", ").append(sessionFilterField2).append("?: number | null");
            }
            sb.append(", sort?: string): Observable<Page<").append(entityName).append("List>> {\n");
            sb.append("    let params = new HttpParams()\n");
            sb.append("      .set('page', page.toString())\n");
            sb.append("      .set('size', size.toString())");
            if (hasDefaultSort) {
                sb.append("\n      .set('sort', sort || '").append(defaultSortValue).append("')");
            } else {
                sb.append(";\n");
                sb.append("    if (sort) {\n");
                sb.append("      params = params.set('sort', sort);\n");
                sb.append("    }");
            }
            sb.append(";\n");
            sb.append("    if (").append(sessionFilterField).append(") {\n");
            sb.append("      params = params.set('").append(sessionFilterField).append("', ").append(sessionFilterField).append(".toString());\n");
            sb.append("    }\n");
            if (sessionFilterField2 != null) {
                sb.append("    if (").append(sessionFilterField2).append(") {\n");
                sb.append("      params = params.set('").append(sessionFilterField2).append("', ").append(sessionFilterField2).append(".toString());\n");
                sb.append("    }\n");
            }
            sb.append("    return this.http.get<Page<").append(entityName).append("List>>(this.apiUrl, { params });\n");
        } else {
            sb.append("  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<").append(entityName).append("List>> {\n");
            sb.append("    let params = new HttpParams()\n");
            sb.append("      .set('page', page.toString())\n");
            sb.append("      .set('size', size.toString());\n");
            if (hasDefaultSort) {
                sb.append("    params = params.set('sort', sort || '").append(defaultSortValue).append("');\n");
            } else {
                sb.append("    if (sort) {\n");
                sb.append("      params = params.set('sort', sort);\n");
                sb.append("    }\n");
            }
            sb.append("    return this.http.get<Page<").append(entityName).append("List>>(this.apiUrl, { params });\n");
        }
        sb.append("  }\n\n");

        // Método findById
        sb.append("  /**\n");
        sb.append("   * Busca um registro por ID.\n");
        sb.append("   */\n");
        sb.append("  findById(id: ").append(pkType).append("): Observable<").append(entityName).append("> {\n");
        sb.append("    return this.http.get<").append(entityName).append(">(`${this.apiUrl}/${id}`);\n");
        sb.append("  }\n\n");

        // Método create
        sb.append("  /**\n");
        sb.append("   * Cria um novo registro.\n");
        sb.append("   */\n");
        sb.append("  create(data: ").append(entityName).append("Request): Observable<").append(entityName).append("> {\n");
        sb.append("    return this.http.post<").append(entityName).append(">(this.apiUrl, data);\n");
        sb.append("  }\n\n");

        // Método update
        sb.append("  /**\n");
        sb.append("   * Atualiza um registro existente.\n");
        sb.append("   */\n");
        sb.append("  update(id: ").append(pkType).append(", data: ").append(entityName).append("Request): Observable<").append(entityName).append("> {\n");
        sb.append("    return this.http.put<").append(entityName).append(">(`${this.apiUrl}/${id}`, data);\n");
        sb.append("  }\n\n");

        // Método delete
        sb.append("  /**\n");
        sb.append("   * Remove um registro por ID.\n");
        sb.append("   */\n");
        sb.append("  delete(id: ").append(pkType).append("): Observable<void> {\n");
        sb.append("    return this.http.delete<void>(`${this.apiUrl}/${id}`);\n");
        sb.append("  }\n");

        // Se for entidade Usuario, adiciona método getPerfis para controle de acesso
        if ("Usuario".equals(entityName)) {
            sb.append("\n");
            sb.append("  /**\n");
            sb.append("   * Lista todos os perfis disponíveis.\n");
            sb.append("   */\n");
            sb.append("  getPerfis(): Observable<Perfil[]> {\n");
            sb.append("    return this.http.get<Perfil[]>(`${this.apiUrl}/perfis`);\n");
            sb.append("  }\n");
        }

        sb.append("}\n\n");

        // Interface Page (helper para paginação Spring)
        sb.append("/**\n");
        sb.append(" * Interface auxiliar para paginação do Spring Boot.\n");
        sb.append(" */\n");
        sb.append("export interface Page<T> {\n");
        sb.append("  content: T[];\n");
        sb.append("  totalElements: number;\n");
        sb.append("  totalPages: number;\n");
        sb.append("  size: number;\n");
        sb.append("  number: number;\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Determina o tipo TypeScript da chave primária.
     */
    private String getPrimaryKeyType(Entity entity) {
        List<Field> pkFields = entity.getFields().stream()
            .filter(Field::isPrimaryKey)
            .collect(Collectors.toList());

        if (pkFields.isEmpty()) {
            return "number"; // ID artificial
        }

        if (pkFields.size() == 1) {
            return toTypeScriptType(pkFields.get(0));
        }

        // PK composta - retorna tipo complexo
        return entity.getName() + "Id";
    }

    /**
     * Converte tipo para TypeScript.
     */
    private String toTypeScriptType(Field field) {
        String dataType = field.getDataType().name();

        switch (dataType.toUpperCase()) {
            case "INTEGER":
            case "LONG":
            case "BIGINT":
            case "DECIMAL":
                return "number";
            case "STRING":
            case "TEXT":
                return "string";
            default:
                return "any";
        }
    }
}
