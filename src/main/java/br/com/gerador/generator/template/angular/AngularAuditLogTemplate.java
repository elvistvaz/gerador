package br.com.gerador.generator.template.angular;

/**
 * Template para geração de componentes Angular de análise de log de auditoria.
 */
public class AngularAuditLogTemplate {

    /**
     * Gera o model TypeScript para AuditLog.
     */
    public String generateModel() {
        StringBuilder sb = new StringBuilder();

        sb.append("export interface AuditLogList {\n");
        sb.append("  id: number;\n");
        sb.append("  acao: string;\n");
        sb.append("  entidade: string;\n");
        sb.append("  chave: string;\n");
        sb.append("  usuario: string;\n");
        sb.append("  dataHora: string;\n");
        sb.append("}\n\n");

        sb.append("export interface AuditLogResponse {\n");
        sb.append("  id: number;\n");
        sb.append("  acao: string;\n");
        sb.append("  entidade: string;\n");
        sb.append("  chave: string;\n");
        sb.append("  usuario: string;\n");
        sb.append("  dataHora: string;\n");
        sb.append("  dadosAnteriores: string;\n");
        sb.append("}\n\n");

        sb.append("export interface AuditLogFilter {\n");
        sb.append("  entidade?: string;\n");
        sb.append("  usuario?: string;\n");
        sb.append("  dataInicio?: string;\n");
        sb.append("  dataFim?: string;\n");
        sb.append("}\n\n");

        sb.append("export interface PageResponse<T> {\n");
        sb.append("  content: T[];\n");
        sb.append("  totalElements: number;\n");
        sb.append("  totalPages: number;\n");
        sb.append("  size: number;\n");
        sb.append("  number: number;\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o service TypeScript para AuditLog.
     */
    public String generateService() {
        StringBuilder sb = new StringBuilder();

        sb.append("import { Injectable } from '@angular/core';\n");
        sb.append("import { HttpClient, HttpParams } from '@angular/common/http';\n");
        sb.append("import { Observable } from 'rxjs';\n");
        sb.append("import { AuditLogList, AuditLogResponse, AuditLogFilter, PageResponse } from '../models/audit-log.model';\n");
        sb.append("import { environment } from '../../environments/environment';\n\n");

        sb.append("@Injectable({\n");
        sb.append("  providedIn: 'root'\n");
        sb.append("})\n");
        sb.append("export class AuditLogService {\n\n");

        sb.append("  private apiUrl = `${environment.apiUrl}/audit-log`;\n\n");

        sb.append("  constructor(private http: HttpClient) { }\n\n");

        // findAll
        sb.append("  findAll(filter: AuditLogFilter, page: number = 0, size: number = 10, sort: string = 'dataHora,desc'): Observable<PageResponse<AuditLogList>> {\n");
        sb.append("    let params = new HttpParams()\n");
        sb.append("      .set('page', page.toString())\n");
        sb.append("      .set('size', size.toString())\n");
        sb.append("      .set('sort', sort);\n\n");
        sb.append("    if (filter.entidade) {\n");
        sb.append("      params = params.set('entidade', filter.entidade);\n");
        sb.append("    }\n");
        sb.append("    if (filter.usuario) {\n");
        sb.append("      params = params.set('usuario', filter.usuario);\n");
        sb.append("    }\n");
        sb.append("    if (filter.dataInicio) {\n");
        sb.append("      params = params.set('dataInicio', filter.dataInicio);\n");
        sb.append("    }\n");
        sb.append("    if (filter.dataFim) {\n");
        sb.append("      params = params.set('dataFim', filter.dataFim);\n");
        sb.append("    }\n\n");
        sb.append("    return this.http.get<PageResponse<AuditLogList>>(this.apiUrl, { params });\n");
        sb.append("  }\n\n");

        // findById
        sb.append("  findById(id: number): Observable<AuditLogResponse> {\n");
        sb.append("    return this.http.get<AuditLogResponse>(`${this.apiUrl}/${id}`);\n");
        sb.append("  }\n\n");

        // getEntidades
        sb.append("  getEntidades(): Observable<string[]> {\n");
        sb.append("    return this.http.get<string[]>(`${this.apiUrl}/entidades`);\n");
        sb.append("  }\n\n");

        // getUsuarios
        sb.append("  getUsuarios(): Observable<string[]> {\n");
        sb.append("    return this.http.get<string[]>(`${this.apiUrl}/usuarios`);\n");
        sb.append("  }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o componente de lista de auditoria (TypeScript).
     */
    public String generateListComponentTs() {
        StringBuilder sb = new StringBuilder();

        sb.append("import { Component, OnInit } from '@angular/core';\n");
        sb.append("import { CommonModule } from '@angular/common';\n");
        sb.append("import { FormsModule } from '@angular/forms';\n");
        sb.append("import { AuditLogService } from '../services/audit-log.service';\n");
        sb.append("import { AuditLogList, AuditLogResponse, AuditLogFilter } from '../models/audit-log.model';\n\n");

        sb.append("@Component({\n");
        sb.append("  selector: 'app-audit-log-list',\n");
        sb.append("  standalone: true,\n");
        sb.append("  imports: [CommonModule, FormsModule],\n");
        sb.append("  templateUrl: './audit-log-list.component.html',\n");
        sb.append("  styleUrls: ['./audit-log-list.component.css']\n");
        sb.append("})\n");
        sb.append("export class AuditLogListComponent implements OnInit {\n\n");

        // Propriedades
        sb.append("  logs: AuditLogList[] = [];\n");
        sb.append("  entidades: string[] = [];\n");
        sb.append("  usuarios: string[] = [];\n");
        sb.append("  totalElements = 0;\n");
        sb.append("  totalPages = 0;\n");
        sb.append("  currentPage = 0;\n");
        sb.append("  pageSize = 10;\n");
        sb.append("  loading = false;\n");
        sb.append("  error = '';\n");
        sb.append("  showFilters = false;\n\n");

        sb.append("  // Ordenação\n");
        sb.append("  sortField = 'dataHora';\n");
        sb.append("  sortDirection: 'asc' | 'desc' = 'desc';\n\n");

        sb.append("  // Filtros\n");
        sb.append("  filter: AuditLogFilter = {};\n");
        sb.append("  dataInicioStr = '';\n");
        sb.append("  dataFimStr = '';\n\n");

        sb.append("  // Modal\n");
        sb.append("  showModal = false;\n");
        sb.append("  selectedLog: AuditLogResponse | null = null;\n");
        sb.append("  dadosAnterioresObj: { [key: string]: any } = {};\n\n");

        // Constructor
        sb.append("  constructor(private auditLogService: AuditLogService) { }\n\n");

        // ngOnInit
        sb.append("  ngOnInit(): void {\n");
        sb.append("    this.loadEntidades();\n");
        sb.append("    this.loadUsuarios();\n");
        sb.append("    this.loadLogs();\n");
        sb.append("  }\n\n");

        // loadEntidades
        sb.append("  loadEntidades(): void {\n");
        sb.append("    this.auditLogService.getEntidades().subscribe({\n");
        sb.append("      next: (data) => this.entidades = data,\n");
        sb.append("      error: (err) => console.error('Erro ao carregar entidades:', err)\n");
        sb.append("    });\n");
        sb.append("  }\n\n");

        // loadUsuarios
        sb.append("  loadUsuarios(): void {\n");
        sb.append("    this.auditLogService.getUsuarios().subscribe({\n");
        sb.append("      next: (data) => this.usuarios = data,\n");
        sb.append("      error: (err) => console.error('Erro ao carregar usuários:', err)\n");
        sb.append("    });\n");
        sb.append("  }\n\n");

        // loadLogs
        sb.append("  loadLogs(): void {\n");
        sb.append("    this.loading = true;\n");
        sb.append("    this.error = '';\n");
        sb.append("    \n");
        sb.append("    const filterToSend: AuditLogFilter = { ...this.filter };\n");
        sb.append("    if (this.dataInicioStr) {\n");
        sb.append("      filterToSend.dataInicio = new Date(this.dataInicioStr).toISOString();\n");
        sb.append("    }\n");
        sb.append("    if (this.dataFimStr) {\n");
        sb.append("      filterToSend.dataFim = new Date(this.dataFimStr).toISOString();\n");
        sb.append("    }\n");
        sb.append("    \n");
        sb.append("    const sort = `${this.sortField},${this.sortDirection}`;\n");
        sb.append("    this.auditLogService.findAll(filterToSend, this.currentPage, this.pageSize, sort).subscribe({\n");
        sb.append("      next: (response) => {\n");
        sb.append("        this.logs = response.content;\n");
        sb.append("        this.totalElements = response.totalElements;\n");
        sb.append("        this.totalPages = response.totalPages;\n");
        sb.append("        this.loading = false;\n");
        sb.append("      },\n");
        sb.append("      error: (err) => {\n");
        sb.append("        console.error('Erro ao carregar logs:', err);\n");
        sb.append("        this.error = 'Erro ao carregar registros';\n");
        sb.append("        this.loading = false;\n");
        sb.append("      }\n");
        sb.append("    });\n");
        sb.append("  }\n\n");

        // toggleSort
        sb.append("  toggleSort(field: string): void {\n");
        sb.append("    if (this.sortField === field) {\n");
        sb.append("      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';\n");
        sb.append("    } else {\n");
        sb.append("      this.sortField = field;\n");
        sb.append("      this.sortDirection = 'asc';\n");
        sb.append("    }\n");
        sb.append("    this.loadLogs();\n");
        sb.append("  }\n\n");

        // toggleFilters
        sb.append("  toggleFilters(): void {\n");
        sb.append("    this.showFilters = !this.showFilters;\n");
        sb.append("  }\n\n");

        // aplicarFiltro
        sb.append("  aplicarFiltro(): void {\n");
        sb.append("    this.currentPage = 0;\n");
        sb.append("    this.loadLogs();\n");
        sb.append("  }\n\n");

        // limparFiltro
        sb.append("  limparFiltro(): void {\n");
        sb.append("    this.filter = {};\n");
        sb.append("    this.dataInicioStr = '';\n");
        sb.append("    this.dataFimStr = '';\n");
        sb.append("    this.currentPage = 0;\n");
        sb.append("    this.loadLogs();\n");
        sb.append("  }\n\n");

        // Paginação
        sb.append("  firstPage(): void {\n");
        sb.append("    this.currentPage = 0;\n");
        sb.append("    this.loadLogs();\n");
        sb.append("  }\n\n");

        sb.append("  previousPage(): void {\n");
        sb.append("    if (this.currentPage > 0) {\n");
        sb.append("      this.currentPage--;\n");
        sb.append("      this.loadLogs();\n");
        sb.append("    }\n");
        sb.append("  }\n\n");

        sb.append("  nextPage(): void {\n");
        sb.append("    if (this.currentPage < this.totalPages - 1) {\n");
        sb.append("      this.currentPage++;\n");
        sb.append("      this.loadLogs();\n");
        sb.append("    }\n");
        sb.append("  }\n\n");

        sb.append("  lastPage(): void {\n");
        sb.append("    this.currentPage = this.totalPages - 1;\n");
        sb.append("    this.loadLogs();\n");
        sb.append("  }\n\n");

        sb.append("  goToPage(page: number): void {\n");
        sb.append("    if (page >= 0 && page < this.totalPages) {\n");
        sb.append("      this.currentPage = page;\n");
        sb.append("      this.loadLogs();\n");
        sb.append("    }\n");
        sb.append("  }\n\n");

        sb.append("  getPageNumbers(): number[] {\n");
        sb.append("    const pages: number[] = [];\n");
        sb.append("    const start = Math.max(0, this.currentPage - 2);\n");
        sb.append("    const end = Math.min(this.totalPages, start + 5);\n");
        sb.append("    for (let i = start; i < end; i++) {\n");
        sb.append("      pages.push(i);\n");
        sb.append("    }\n");
        sb.append("    return pages;\n");
        sb.append("  }\n\n");

        sb.append("  getLastItemIndex(): number {\n");
        sb.append("    return Math.min((this.currentPage + 1) * this.pageSize, this.totalElements);\n");
        sb.append("  }\n\n");

        sb.append("  onPageSizeChange(): void {\n");
        sb.append("    this.currentPage = 0;\n");
        sb.append("    this.loadLogs();\n");
        sb.append("  }\n\n");

        // Modal
        sb.append("  exibirDetalhes(log: AuditLogList): void {\n");
        sb.append("    this.auditLogService.findById(log.id).subscribe({\n");
        sb.append("      next: (response) => {\n");
        sb.append("        this.selectedLog = response;\n");
        sb.append("        if (response.dadosAnteriores) {\n");
        sb.append("          try {\n");
        sb.append("            this.dadosAnterioresObj = JSON.parse(response.dadosAnteriores);\n");
        sb.append("          } catch {\n");
        sb.append("            this.dadosAnterioresObj = {};\n");
        sb.append("          }\n");
        sb.append("        } else {\n");
        sb.append("          this.dadosAnterioresObj = {};\n");
        sb.append("        }\n");
        sb.append("        this.showModal = true;\n");
        sb.append("      },\n");
        sb.append("      error: (err) => console.error('Erro ao carregar detalhes:', err)\n");
        sb.append("    });\n");
        sb.append("  }\n\n");

        sb.append("  fecharModal(): void {\n");
        sb.append("    this.showModal = false;\n");
        sb.append("    this.selectedLog = null;\n");
        sb.append("    this.dadosAnterioresObj = {};\n");
        sb.append("  }\n\n");

        sb.append("  getObjectKeys(obj: any): string[] {\n");
        sb.append("    return Object.keys(obj || {});\n");
        sb.append("  }\n\n");

        // Formata data
        sb.append("  formatDate(dateStr: string): string {\n");
        sb.append("    if (!dateStr) return '';\n");
        sb.append("    const date = new Date(dateStr);\n");
        sb.append("    return date.toLocaleString('pt-BR');\n");
        sb.append("  }\n\n");

        // Cor da ação
        sb.append("  getAcaoClass(acao: string): string {\n");
        sb.append("    switch (acao) {\n");
        sb.append("      case 'CREATE': return 'badge-success';\n");
        sb.append("      case 'UPDATE': return 'badge-warning';\n");
        sb.append("      case 'DELETE': return 'badge-danger';\n");
        sb.append("      default: return 'badge-secondary';\n");
        sb.append("    }\n");
        sb.append("  }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o template HTML do componente de lista de auditoria.
     */
    public String generateListComponentHtml() {
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"container\">\n");
        sb.append("  <div class=\"header\">\n");
        sb.append("    <h2>Análise de Log</h2>\n");
        sb.append("    <div class=\"header-actions\">\n");
        sb.append("      <button class=\"btn btn-filter\" (click)=\"toggleFilters()\">\n");
        sb.append("        <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("          <polygon points=\"22 3 2 3 10 12.46 10 19 14 21 14 12.46 22 3\"></polygon>\n");
        sb.append("        </svg>\n");
        sb.append("        <span>Filtros</span>\n");
        sb.append("      </button>\n");
        sb.append("    </div>\n");
        sb.append("  </div>\n\n");

        // Filtros
        sb.append("  <div class=\"filter-section\" *ngIf=\"showFilters\">\n");
        sb.append("    <div class=\"filter-row\">\n");
        sb.append("      <div class=\"filter-group\">\n");
        sb.append("        <label for=\"entidade\">Entidade</label>\n");
        sb.append("        <select id=\"entidade\" [(ngModel)]=\"filter.entidade\" class=\"filter-select\">\n");
        sb.append("          <option value=\"\">Todas</option>\n");
        sb.append("          <option *ngFor=\"let ent of entidades\" [value]=\"ent\">{{ ent }}</option>\n");
        sb.append("        </select>\n");
        sb.append("      </div>\n");
        sb.append("      <div class=\"filter-group\">\n");
        sb.append("        <label for=\"usuario\">Usuário</label>\n");
        sb.append("        <select id=\"usuario\" [(ngModel)]=\"filter.usuario\" class=\"filter-select\">\n");
        sb.append("          <option value=\"\">Todos</option>\n");
        sb.append("          <option *ngFor=\"let usr of usuarios\" [value]=\"usr\">{{ usr }}</option>\n");
        sb.append("        </select>\n");
        sb.append("      </div>\n");
        sb.append("      <div class=\"filter-group\">\n");
        sb.append("        <label for=\"dataInicio\">Data Início</label>\n");
        sb.append("        <input type=\"datetime-local\" id=\"dataInicio\" [(ngModel)]=\"dataInicioStr\" class=\"filter-input\">\n");
        sb.append("      </div>\n");
        sb.append("      <div class=\"filter-group\">\n");
        sb.append("        <label for=\"dataFim\">Data Fim</label>\n");
        sb.append("        <input type=\"datetime-local\" id=\"dataFim\" [(ngModel)]=\"dataFimStr\" class=\"filter-input\">\n");
        sb.append("      </div>\n");
        sb.append("      <div class=\"filter-actions\">\n");
        sb.append("        <button class=\"btn btn-primary\" (click)=\"aplicarFiltro()\">\n");
        sb.append("          <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("            <circle cx=\"11\" cy=\"11\" r=\"8\"></circle>\n");
        sb.append("            <line x1=\"21\" y1=\"21\" x2=\"16.65\" y2=\"16.65\"></line>\n");
        sb.append("          </svg>\n");
        sb.append("          <span>Filtrar</span>\n");
        sb.append("        </button>\n");
        sb.append("        <button class=\"btn btn-secondary\" (click)=\"limparFiltro()\">\n");
        sb.append("          <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("            <line x1=\"18\" y1=\"6\" x2=\"6\" y2=\"18\"></line>\n");
        sb.append("            <line x1=\"6\" y1=\"6\" x2=\"18\" y2=\"18\"></line>\n");
        sb.append("          </svg>\n");
        sb.append("          <span>Limpar</span>\n");
        sb.append("        </button>\n");
        sb.append("      </div>\n");
        sb.append("    </div>\n");
        sb.append("  </div>\n\n");

        sb.append("  <div *ngIf=\"loading\" class=\"loading\">Carregando...</div>\n\n");
        sb.append("  <div *ngIf=\"error\" class=\"error\">{{ error }}</div>\n\n");

        // Tabela
        sb.append("  <div *ngIf=\"!loading && !error\" class=\"table-container\">\n");
        sb.append("    <table class=\"data-table\">\n");
        sb.append("      <thead>\n");
        sb.append("        <tr>\n");
        sb.append("          <th>#</th>\n");
        // Data/Hora com sort
        sb.append("          <th class=\"sortable\" (click)=\"toggleSort('dataHora')\">\n");
        sb.append("            <span class=\"th-content\">\n");
        sb.append("              Data/Hora\n");
        sb.append("              <span class=\"sort-icon\" *ngIf=\"sortField === 'dataHora'\">\n");
        sb.append("                <svg *ngIf=\"sortDirection === 'asc'\" viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 14l5-5 5 5z\"/></svg>\n");
        sb.append("                <svg *ngIf=\"sortDirection === 'desc'\" viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 10l5 5 5-5z\"/></svg>\n");
        sb.append("              </span>\n");
        sb.append("              <span class=\"sort-icon inactive\" *ngIf=\"sortField !== 'dataHora'\">\n");
        sb.append("                <svg viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 10l5-5 5 5M7 14l5 5 5-5\" stroke=\"currentColor\" fill=\"none\" stroke-width=\"1.5\"/></svg>\n");
        sb.append("              </span>\n");
        sb.append("            </span>\n");
        sb.append("          </th>\n");
        // Ação com sort
        sb.append("          <th class=\"sortable\" (click)=\"toggleSort('acao')\">\n");
        sb.append("            <span class=\"th-content\">\n");
        sb.append("              Ação\n");
        sb.append("              <span class=\"sort-icon\" *ngIf=\"sortField === 'acao'\">\n");
        sb.append("                <svg *ngIf=\"sortDirection === 'asc'\" viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 14l5-5 5 5z\"/></svg>\n");
        sb.append("                <svg *ngIf=\"sortDirection === 'desc'\" viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 10l5 5 5-5z\"/></svg>\n");
        sb.append("              </span>\n");
        sb.append("              <span class=\"sort-icon inactive\" *ngIf=\"sortField !== 'acao'\">\n");
        sb.append("                <svg viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 10l5-5 5 5M7 14l5 5 5-5\" stroke=\"currentColor\" fill=\"none\" stroke-width=\"1.5\"/></svg>\n");
        sb.append("              </span>\n");
        sb.append("            </span>\n");
        sb.append("          </th>\n");
        // Entidade com sort
        sb.append("          <th class=\"sortable\" (click)=\"toggleSort('entidade')\">\n");
        sb.append("            <span class=\"th-content\">\n");
        sb.append("              Entidade\n");
        sb.append("              <span class=\"sort-icon\" *ngIf=\"sortField === 'entidade'\">\n");
        sb.append("                <svg *ngIf=\"sortDirection === 'asc'\" viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 14l5-5 5 5z\"/></svg>\n");
        sb.append("                <svg *ngIf=\"sortDirection === 'desc'\" viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 10l5 5 5-5z\"/></svg>\n");
        sb.append("              </span>\n");
        sb.append("              <span class=\"sort-icon inactive\" *ngIf=\"sortField !== 'entidade'\">\n");
        sb.append("                <svg viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 10l5-5 5 5M7 14l5 5 5-5\" stroke=\"currentColor\" fill=\"none\" stroke-width=\"1.5\"/></svg>\n");
        sb.append("              </span>\n");
        sb.append("            </span>\n");
        sb.append("          </th>\n");
        sb.append("          <th>Chave</th>\n");
        // Usuário com sort
        sb.append("          <th class=\"sortable\" (click)=\"toggleSort('usuario')\">\n");
        sb.append("            <span class=\"th-content\">\n");
        sb.append("              Usuário\n");
        sb.append("              <span class=\"sort-icon\" *ngIf=\"sortField === 'usuario'\">\n");
        sb.append("                <svg *ngIf=\"sortDirection === 'asc'\" viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 14l5-5 5 5z\"/></svg>\n");
        sb.append("                <svg *ngIf=\"sortDirection === 'desc'\" viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 10l5 5 5-5z\"/></svg>\n");
        sb.append("              </span>\n");
        sb.append("              <span class=\"sort-icon inactive\" *ngIf=\"sortField !== 'usuario'\">\n");
        sb.append("                <svg viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 10l5-5 5 5M7 14l5 5 5-5\" stroke=\"currentColor\" fill=\"none\" stroke-width=\"1.5\"/></svg>\n");
        sb.append("              </span>\n");
        sb.append("            </span>\n");
        sb.append("          </th>\n");
        sb.append("          <th class=\"actions-column\">Ações</th>\n");
        sb.append("        </tr>\n");
        sb.append("      </thead>\n");
        sb.append("      <tbody>\n");
        sb.append("        <tr *ngFor=\"let log of logs\">\n");
        sb.append("          <td>{{ log.id }}</td>\n");
        sb.append("          <td>{{ formatDate(log.dataHora) }}</td>\n");
        sb.append("          <td><span class=\"badge\" [ngClass]=\"getAcaoClass(log.acao)\">{{ log.acao }}</span></td>\n");
        sb.append("          <td>{{ log.entidade }}</td>\n");
        sb.append("          <td>{{ log.chave }}</td>\n");
        sb.append("          <td>{{ log.usuario }}</td>\n");
        sb.append("          <td class=\"actions-column\">\n");
        sb.append("            <button class=\"btn btn-edit btn-sm\" (click)=\"exibirDetalhes(log)\" title=\"Exibir Detalhes\">\n");
        sb.append("              <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("                <path d=\"M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z\"></path>\n");
        sb.append("                <circle cx=\"12\" cy=\"12\" r=\"3\"></circle>\n");
        sb.append("              </svg>\n");
        sb.append("              <span>Exibir</span>\n");
        sb.append("            </button>\n");
        sb.append("          </td>\n");
        sb.append("        </tr>\n");
        sb.append("        <tr *ngIf=\"logs.length === 0\">\n");
        sb.append("          <td colspan=\"7\" class=\"no-data\">\n");
        sb.append("            Nenhum registro encontrado\n");
        sb.append("          </td>\n");
        sb.append("        </tr>\n");
        sb.append("      </tbody>\n");
        sb.append("    </table>\n\n");

        // Paginação
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
        sb.append("  </div>\n\n");

        // Modal
        sb.append("  <!-- Modal de Detalhes -->\n");
        sb.append("  <div class=\"modal-overlay\" *ngIf=\"showModal\" (click)=\"fecharModal()\">\n");
        sb.append("    <div class=\"modal-content\" (click)=\"$event.stopPropagation()\">\n");
        sb.append("      <div class=\"modal-header\">\n");
        sb.append("        <h3>Detalhes do Log</h3>\n");
        sb.append("        <button class=\"close-btn\" (click)=\"fecharModal()\">\n");
        sb.append("          <svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("            <line x1=\"18\" y1=\"6\" x2=\"6\" y2=\"18\"></line>\n");
        sb.append("            <line x1=\"6\" y1=\"6\" x2=\"18\" y2=\"18\"></line>\n");
        sb.append("          </svg>\n");
        sb.append("        </button>\n");
        sb.append("      </div>\n");
        sb.append("      <div class=\"modal-body\" *ngIf=\"selectedLog\">\n");
        sb.append("        <div class=\"detail-grid\">\n");
        sb.append("          <div class=\"detail-item\">\n");
        sb.append("            <label>Data/Hora</label>\n");
        sb.append("            <span>{{ formatDate(selectedLog.dataHora) }}</span>\n");
        sb.append("          </div>\n");
        sb.append("          <div class=\"detail-item\">\n");
        sb.append("            <label>Ação</label>\n");
        sb.append("            <span class=\"badge\" [ngClass]=\"getAcaoClass(selectedLog.acao)\">{{ selectedLog.acao }}</span>\n");
        sb.append("          </div>\n");
        sb.append("          <div class=\"detail-item\">\n");
        sb.append("            <label>Entidade</label>\n");
        sb.append("            <span>{{ selectedLog.entidade }}</span>\n");
        sb.append("          </div>\n");
        sb.append("          <div class=\"detail-item\">\n");
        sb.append("            <label>Chave</label>\n");
        sb.append("            <span>{{ selectedLog.chave }}</span>\n");
        sb.append("          </div>\n");
        sb.append("          <div class=\"detail-item\">\n");
        sb.append("            <label>Usuário</label>\n");
        sb.append("            <span>{{ selectedLog.usuario }}</span>\n");
        sb.append("          </div>\n");
        sb.append("        </div>\n");
        sb.append("        \n");
        sb.append("        <div class=\"dados-anteriores\" *ngIf=\"getObjectKeys(dadosAnterioresObj).length > 0\">\n");
        sb.append("          <h4>Dados Anteriores</h4>\n");
        sb.append("          <table class=\"data-table details-table\">\n");
        sb.append("            <thead>\n");
        sb.append("              <tr>\n");
        sb.append("                <th>Campo</th>\n");
        sb.append("                <th>Valor</th>\n");
        sb.append("              </tr>\n");
        sb.append("            </thead>\n");
        sb.append("            <tbody>\n");
        sb.append("              <tr *ngFor=\"let key of getObjectKeys(dadosAnterioresObj)\">\n");
        sb.append("                <td>{{ key }}</td>\n");
        sb.append("                <td>{{ dadosAnterioresObj[key] }}</td>\n");
        sb.append("              </tr>\n");
        sb.append("            </tbody>\n");
        sb.append("          </table>\n");
        sb.append("        </div>\n");
        sb.append("        \n");
        sb.append("        <div class=\"no-data-message\" *ngIf=\"getObjectKeys(dadosAnterioresObj).length === 0\">\n");
        sb.append("          <em>Nenhum dado anterior registrado</em>\n");
        sb.append("        </div>\n");
        sb.append("      </div>\n");
        sb.append("      <div class=\"modal-footer\">\n");
        sb.append("        <button class=\"btn btn-secondary\" (click)=\"fecharModal()\">\n");
        sb.append("          <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("            <line x1=\"18\" y1=\"6\" x2=\"6\" y2=\"18\"></line>\n");
        sb.append("            <line x1=\"6\" y1=\"6\" x2=\"18\" y2=\"18\"></line>\n");
        sb.append("          </svg>\n");
        sb.append("          <span>Fechar</span>\n");
        sb.append("        </button>\n");
        sb.append("      </div>\n");
        sb.append("    </div>\n");
        sb.append("  </div>\n");

        sb.append("</div>\n");

        return sb.toString();
    }

    /**
     * Gera o CSS do componente de lista de auditoria.
     */
    public String generateListComponentCss() {
        StringBuilder sb = new StringBuilder();

        sb.append(".container {\n");
        sb.append("  padding: 0;\n");
        sb.append("}\n\n");

        // Header
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

        sb.append(".header-actions {\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 1rem;\n");
        sb.append("}\n\n");

        // Filtros
        sb.append(".filter-section {\n");
        sb.append("  background: white;\n");
        sb.append("  padding: 1rem;\n");
        sb.append("  border-radius: 8px;\n");
        sb.append("  margin-bottom: 1.5rem;\n");
        sb.append("  box-shadow: 0 2px 4px rgba(0,0,0,0.1);\n");
        sb.append("}\n\n");

        sb.append(".filter-row {\n");
        sb.append("  display: flex;\n");
        sb.append("  flex-wrap: wrap;\n");
        sb.append("  gap: 1rem;\n");
        sb.append("  align-items: flex-end;\n");
        sb.append("}\n\n");

        sb.append(".filter-group {\n");
        sb.append("  display: flex;\n");
        sb.append("  flex-direction: column;\n");
        sb.append("  gap: 0.5rem;\n");
        sb.append("}\n\n");

        sb.append(".filter-group label {\n");
        sb.append("  font-size: 0.875rem;\n");
        sb.append("  color: #666;\n");
        sb.append("  font-weight: 500;\n");
        sb.append("}\n\n");

        sb.append(".filter-select,\n");
        sb.append(".filter-input {\n");
        sb.append("  padding: 0.5rem 1rem;\n");
        sb.append("  border: 1px solid #ddd;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  min-width: 160px;\n");
        sb.append("  font-size: 14px;\n");
        sb.append("}\n\n");

        sb.append(".filter-select:focus,\n");
        sb.append(".filter-input:focus {\n");
        sb.append("  outline: none;\n");
        sb.append("  border-color: #667eea;\n");
        sb.append("}\n\n");

        sb.append(".filter-actions {\n");
        sb.append("  display: flex;\n");
        sb.append("  gap: 0.5rem;\n");
        sb.append("}\n\n");

        // Loading e Error
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

        // Tabela
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
        sb.append("  width: 120px;\n");
        sb.append("  text-align: center;\n");
        sb.append("}\n\n");

        sb.append("td.actions-column {\n");
        sb.append("  display: flex;\n");
        sb.append("  justify-content: center;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 0.5rem;\n");
        sb.append("}\n\n");

        sb.append(".no-data {\n");
        sb.append("  text-align: center;\n");
        sb.append("  color: #999;\n");
        sb.append("  padding: 2rem !important;\n");
        sb.append("}\n\n");

        // Badges
        sb.append(".badge {\n");
        sb.append("  padding: 4px 8px;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  font-size: 0.75rem;\n");
        sb.append("  font-weight: 600;\n");
        sb.append("  text-transform: uppercase;\n");
        sb.append("}\n\n");

        sb.append(".badge-success {\n");
        sb.append("  background: #d4edda;\n");
        sb.append("  color: #155724;\n");
        sb.append("}\n\n");

        sb.append(".badge-warning {\n");
        sb.append("  background: #fff3cd;\n");
        sb.append("  color: #856404;\n");
        sb.append("}\n\n");

        sb.append(".badge-danger {\n");
        sb.append("  background: #f8d7da;\n");
        sb.append("  color: #721c24;\n");
        sb.append("}\n\n");

        sb.append(".badge-secondary {\n");
        sb.append("  background: #e2e3e5;\n");
        sb.append("  color: #383d41;\n");
        sb.append("}\n\n");

        // Botões
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

        sb.append(".btn-secondary {\n");
        sb.append("  background: #6c757d;\n");
        sb.append("  color: white;\n");
        sb.append("}\n\n");

        sb.append(".btn-secondary:hover {\n");
        sb.append("  background: #5a6268;\n");
        sb.append("}\n\n");

        sb.append(".btn-filter {\n");
        sb.append("  background: white;\n");
        sb.append("  color: #667eea;\n");
        sb.append("  border: 1px solid #667eea;\n");
        sb.append("}\n\n");

        sb.append(".btn-filter:hover {\n");
        sb.append("  background: #667eea;\n");
        sb.append("  color: white;\n");
        sb.append("}\n\n");

        sb.append(".btn-edit {\n");
        sb.append("  background: #17a2b8;\n");
        sb.append("  color: white;\n");
        sb.append("}\n\n");

        sb.append(".btn-edit:hover {\n");
        sb.append("  background: #138496;\n");
        sb.append("}\n\n");

        // Paginação
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
        sb.append("}\n\n");

        // Modal
        sb.append(".modal-overlay {\n");
        sb.append("  position: fixed;\n");
        sb.append("  top: 0;\n");
        sb.append("  left: 0;\n");
        sb.append("  right: 0;\n");
        sb.append("  bottom: 0;\n");
        sb.append("  background: rgba(0,0,0,0.5);\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  justify-content: center;\n");
        sb.append("  z-index: 1000;\n");
        sb.append("}\n\n");

        sb.append(".modal-content {\n");
        sb.append("  background: white;\n");
        sb.append("  border-radius: 8px;\n");
        sb.append("  width: 90%;\n");
        sb.append("  max-width: 700px;\n");
        sb.append("  max-height: 85vh;\n");
        sb.append("  overflow-y: auto;\n");
        sb.append("  box-shadow: 0 4px 20px rgba(0,0,0,0.15);\n");
        sb.append("}\n\n");

        sb.append(".modal-header {\n");
        sb.append("  display: flex;\n");
        sb.append("  justify-content: space-between;\n");
        sb.append("  align-items: center;\n");
        sb.append("  padding: 1rem 1.5rem;\n");
        sb.append("  border-bottom: 1px solid #eee;\n");
        sb.append("  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n");
        sb.append("  color: white;\n");
        sb.append("  border-radius: 8px 8px 0 0;\n");
        sb.append("}\n\n");

        sb.append(".modal-header h3 {\n");
        sb.append("  margin: 0;\n");
        sb.append("  font-size: 1.25rem;\n");
        sb.append("}\n\n");

        sb.append(".close-btn {\n");
        sb.append("  background: none;\n");
        sb.append("  border: none;\n");
        sb.append("  cursor: pointer;\n");
        sb.append("  color: white;\n");
        sb.append("  padding: 0.25rem;\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  justify-content: center;\n");
        sb.append("  opacity: 0.8;\n");
        sb.append("  transition: opacity 0.2s;\n");
        sb.append("}\n\n");

        sb.append(".close-btn:hover {\n");
        sb.append("  opacity: 1;\n");
        sb.append("}\n\n");

        sb.append(".close-btn svg {\n");
        sb.append("  width: 20px;\n");
        sb.append("  height: 20px;\n");
        sb.append("}\n\n");

        sb.append(".modal-body {\n");
        sb.append("  padding: 1.5rem;\n");
        sb.append("}\n\n");

        sb.append(".modal-footer {\n");
        sb.append("  padding: 1rem 1.5rem;\n");
        sb.append("  border-top: 1px solid #eee;\n");
        sb.append("  display: flex;\n");
        sb.append("  justify-content: flex-end;\n");
        sb.append("  background: #fafafa;\n");
        sb.append("  border-radius: 0 0 8px 8px;\n");
        sb.append("}\n\n");

        sb.append(".detail-grid {\n");
        sb.append("  display: grid;\n");
        sb.append("  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));\n");
        sb.append("  gap: 1rem;\n");
        sb.append("  margin-bottom: 1.5rem;\n");
        sb.append("}\n\n");

        sb.append(".detail-item {\n");
        sb.append("  display: flex;\n");
        sb.append("  flex-direction: column;\n");
        sb.append("  gap: 0.25rem;\n");
        sb.append("}\n\n");

        sb.append(".detail-item label {\n");
        sb.append("  font-size: 0.75rem;\n");
        sb.append("  color: #999;\n");
        sb.append("  text-transform: uppercase;\n");
        sb.append("  font-weight: 600;\n");
        sb.append("}\n\n");

        sb.append(".detail-item span {\n");
        sb.append("  font-size: 0.95rem;\n");
        sb.append("  color: #333;\n");
        sb.append("}\n\n");

        sb.append(".dados-anteriores {\n");
        sb.append("  margin-top: 1rem;\n");
        sb.append("  padding-top: 1rem;\n");
        sb.append("  border-top: 1px solid #eee;\n");
        sb.append("}\n\n");

        sb.append(".dados-anteriores h4 {\n");
        sb.append("  margin: 0 0 1rem 0;\n");
        sb.append("  color: #333;\n");
        sb.append("  font-size: 1rem;\n");
        sb.append("}\n\n");

        sb.append(".details-table {\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("  overflow: hidden;\n");
        sb.append("}\n\n");

        sb.append(".details-table th:first-child {\n");
        sb.append("  width: 35%;\n");
        sb.append("}\n\n");

        sb.append(".no-data-message {\n");
        sb.append("  color: #999;\n");
        sb.append("  text-align: center;\n");
        sb.append("  padding: 1rem;\n");
        sb.append("  background: #f9f9f9;\n");
        sb.append("  border-radius: 4px;\n");
        sb.append("}\n");

        return sb.toString();
    }
}
