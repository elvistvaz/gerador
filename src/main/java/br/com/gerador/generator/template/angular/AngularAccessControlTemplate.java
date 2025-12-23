package br.com.gerador.generator.template.angular;

/**
 * Template para geração de componentes Angular de Controle de Acesso.
 * Gera componentes para gestão de Usuários e visualização de Perfis.
 */
public class AngularAccessControlTemplate {

    // ==================== MODELS ====================

    /**
     * Gera o model de Perfil (separado para evitar conflito com entidade Usuario do metamodelo).
     */
    public String generatePerfilModel() {
        StringBuilder sb = new StringBuilder();

        sb.append("/**\n");
        sb.append(" * Interface de Perfil para controle de acesso.\n");
        sb.append(" * Auto-gerado pelo gerador de código.\n");
        sb.append(" */\n");
        sb.append("export interface Perfil {\n");
        sb.append("  id: string;\n");
        sb.append("  nome: string;\n");
        sb.append("  descricao: string;\n");
        sb.append("  ativo: boolean;\n");
        sb.append("  permissoes: string[];\n");
        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o model de Usuario (usado apenas se não houver entidade Usuario no metamodelo).
     */
    public String generateUsuarioModel() {
        StringBuilder sb = new StringBuilder();

        sb.append("export interface Usuario {\n");
        sb.append("  id: number;\n");
        sb.append("  username: string;\n");
        sb.append("  nome: string;\n");
        sb.append("  email: string;\n");
        sb.append("  ativo: boolean;\n");
        sb.append("  perfilId: string;\n");
        sb.append("  perfilNome: string;\n");
        sb.append("  dataCriacao: string;\n");
        sb.append("  ultimoAcesso: string;\n");
        sb.append("}\n\n");

        sb.append("export interface UsuarioRequest {\n");
        sb.append("  username: string;\n");
        sb.append("  senha?: string;\n");
        sb.append("  nome: string;\n");
        sb.append("  email?: string;\n");
        sb.append("  ativo: boolean;\n");
        sb.append("  perfilId: string;\n");
        sb.append("}\n\n");

        sb.append("export interface UsuarioList {\n");
        sb.append("  id: number;\n");
        sb.append("  username: string;\n");
        sb.append("  nome: string;\n");
        sb.append("  email: string;\n");
        sb.append("  ativo: boolean;\n");
        sb.append("  perfilNome: string;\n");
        sb.append("}\n");

        return sb.toString();
    }

    // ==================== SERVICE ====================

    /**
     * Gera o service de Usuario.
     */
    public String generateUsuarioService() {
        StringBuilder sb = new StringBuilder();

        sb.append("import { Injectable } from '@angular/core';\n");
        sb.append("import { HttpClient, HttpParams } from '@angular/common/http';\n");
        sb.append("import { Observable } from 'rxjs';\n");
        sb.append("import { Usuario, UsuarioRequest, UsuarioList } from '../models/usuario.model';\n");
        sb.append("import { Perfil } from '../models/perfil.model';\n");
        sb.append("import { environment } from '../../environments/environment';\n\n");

        sb.append("@Injectable({\n");
        sb.append("  providedIn: 'root'\n");
        sb.append("})\n");
        sb.append("export class UsuarioService {\n\n");

        sb.append("  private readonly apiUrl = `${environment.apiUrl}/usuarios`;\n\n");

        sb.append("  constructor(private http: HttpClient) {}\n\n");

        sb.append("  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<UsuarioList>> {\n");
        sb.append("    let params = new HttpParams()\n");
        sb.append("      .set('page', page.toString())\n");
        sb.append("      .set('size', size.toString());\n");
        sb.append("    if (sort) {\n");
        sb.append("      params = params.set('sort', sort);\n");
        sb.append("    }\n");
        sb.append("    return this.http.get<Page<UsuarioList>>(this.apiUrl, { params });\n");
        sb.append("  }\n\n");

        sb.append("  findById(id: number): Observable<Usuario> {\n");
        sb.append("    return this.http.get<Usuario>(`${this.apiUrl}/${id}`);\n");
        sb.append("  }\n\n");

        sb.append("  create(data: UsuarioRequest): Observable<Usuario> {\n");
        sb.append("    return this.http.post<Usuario>(this.apiUrl, data);\n");
        sb.append("  }\n\n");

        sb.append("  update(id: number, data: UsuarioRequest): Observable<Usuario> {\n");
        sb.append("    return this.http.put<Usuario>(`${this.apiUrl}/${id}`, data);\n");
        sb.append("  }\n\n");

        sb.append("  delete(id: number): Observable<void> {\n");
        sb.append("    return this.http.delete<void>(`${this.apiUrl}/${id}`);\n");
        sb.append("  }\n\n");

        sb.append("  getPerfis(): Observable<Perfil[]> {\n");
        sb.append("    return this.http.get<Perfil[]>(`${this.apiUrl}/perfis`);\n");
        sb.append("  }\n");

        sb.append("}\n\n");

        sb.append("export interface Page<T> {\n");
        sb.append("  content: T[];\n");
        sb.append("  totalElements: number;\n");
        sb.append("  totalPages: number;\n");
        sb.append("  size: number;\n");
        sb.append("  number: number;\n");
        sb.append("}\n");

        return sb.toString();
    }

    // ==================== USUARIO LIST COMPONENT ====================

    /**
     * Gera o TypeScript do componente de lista de usuários.
     */
    public String generateUsuarioListComponentTs() {
        StringBuilder sb = new StringBuilder();

        sb.append("import { Component, OnInit } from '@angular/core';\n");
        sb.append("import { CommonModule } from '@angular/common';\n");
        sb.append("import { FormsModule } from '@angular/forms';\n");
        sb.append("import { Router } from '@angular/router';\n");
        sb.append("import { UsuarioList } from '../models/usuario.model';\n");
        sb.append("import { UsuarioService } from '../services/usuario.service';\n\n");

        sb.append("@Component({\n");
        sb.append("  selector: 'app-usuario-list',\n");
        sb.append("  standalone: true,\n");
        sb.append("  imports: [CommonModule, FormsModule],\n");
        sb.append("  templateUrl: './usuario-list.component.html',\n");
        sb.append("  styleUrls: ['./usuario-list.component.css']\n");
        sb.append("})\n");
        sb.append("export class UsuarioListComponent implements OnInit {\n\n");

        sb.append("  items: UsuarioList[] = [];\n");
        sb.append("  filteredItems: UsuarioList[] = [];\n");
        sb.append("  searchTerm: string = '';\n");
        sb.append("  loading: boolean = false;\n");
        sb.append("  error: string | null = null;\n");
        sb.append("  successMessage: string | null = null;\n\n");

        sb.append("  // Paginação\n");
        sb.append("  currentPage: number = 0;\n");
        sb.append("  pageSize: number = 20;\n");
        sb.append("  totalElements: number = 0;\n");
        sb.append("  totalPages: number = 0;\n\n");

        sb.append("  // Ordenação\n");
        sb.append("  sortField: string = '';\n");
        sb.append("  sortDirection: 'asc' | 'desc' = 'asc';\n\n");

        sb.append("  constructor(\n");
        sb.append("    private service: UsuarioService,\n");
        sb.append("    private router: Router\n");
        sb.append("  ) {}\n\n");

        sb.append("  ngOnInit(): void {\n");
        sb.append("    this.loadItems();\n");
        sb.append("  }\n\n");

        sb.append("  loadItems(): void {\n");
        sb.append("    this.loading = true;\n");
        sb.append("    this.error = null;\n");
        sb.append("    const sort = this.sortField ? `${this.sortField},${this.sortDirection}` : undefined;\n");
        sb.append("    this.service.findAll(this.currentPage, this.pageSize, sort).subscribe({\n");
        sb.append("      next: (data) => {\n");
        sb.append("        this.items = data.content;\n");
        sb.append("        this.filteredItems = data.content;\n");
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
        sb.append("    }\n");
        sb.append("    const term = this.searchTerm.toLowerCase();\n");
        sb.append("    this.filteredItems = this.items.filter(item =>\n");
        sb.append("      item.username.toLowerCase().includes(term) ||\n");
        sb.append("      item.nome.toLowerCase().includes(term) ||\n");
        sb.append("      (item.email && item.email.toLowerCase().includes(term)) ||\n");
        sb.append("      (item.perfilNome && item.perfilNome.toLowerCase().includes(term))\n");
        sb.append("    );\n");
        sb.append("  }\n\n");

        sb.append("  novo(): void {\n");
        sb.append("    this.router.navigate(['/usuario/novo']);\n");
        sb.append("  }\n\n");

        sb.append("  editar(id: number): void {\n");
        sb.append("    this.router.navigate(['/usuario/editar', id]);\n");
        sb.append("  }\n\n");

        sb.append("  delete(id: number): void {\n");
        sb.append("    if (!confirm('Deseja realmente excluir este usuário?')) {\n");
        sb.append("      return;\n");
        sb.append("    }\n");
        sb.append("    this.error = null;\n");
        sb.append("    this.successMessage = null;\n");
        sb.append("    this.service.delete(id).subscribe({\n");
        sb.append("      next: () => {\n");
        sb.append("        this.successMessage = 'Usuário excluído com sucesso!';\n");
        sb.append("        this.loadItems();\n");
        sb.append("        setTimeout(() => this.successMessage = null, 3000);\n");
        sb.append("      },\n");
        sb.append("      error: (err) => {\n");
        sb.append("        this.error = err.error?.message || 'Erro ao excluir usuário';\n");
        sb.append("        console.error(err);\n");
        sb.append("        setTimeout(() => this.error = null, 5000);\n");
        sb.append("      }\n");
        sb.append("    });\n");
        sb.append("  }\n\n");

        // Métodos de paginação
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
        sb.append("  }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o HTML do componente de lista de usuários.
     */
    public String generateUsuarioListComponentHtml() {
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"container\">\n");
        sb.append("  <div class=\"header\">\n");
        sb.append("    <h2>Usuários</h2>\n");
        sb.append("    <div class=\"header-actions\">\n");
        sb.append("      <div class=\"search-box\">\n");
        sb.append("        <svg class=\"search-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("          <circle cx=\"11\" cy=\"11\" r=\"8\"></circle>\n");
        sb.append("          <line x1=\"21\" y1=\"21\" x2=\"16.65\" y2=\"16.65\"></line>\n");
        sb.append("        </svg>\n");
        sb.append("        <input type=\"text\" [(ngModel)]=\"searchTerm\" (input)=\"search()\" placeholder=\"Buscar...\" class=\"search-input\"/>\n");
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

        sb.append("  <div *ngIf=\"loading\" class=\"loading\">Carregando...</div>\n");
        sb.append("  <div *ngIf=\"successMessage\" class=\"success\">{{ successMessage }}</div>\n");
        sb.append("  <div *ngIf=\"error\" class=\"error\">{{ error }}</div>\n\n");

        sb.append("  <div *ngIf=\"!loading && !error\" class=\"table-container\">\n");
        sb.append("    <table class=\"data-table\">\n");
        sb.append("      <thead>\n");
        sb.append("        <tr>\n");
        sb.append("          <th>#</th>\n");
        sb.append("          <th class=\"sortable\" (click)=\"toggleSort('username')\">\n");
        sb.append("            <span class=\"th-content\">Username\n");
        sb.append("              <span class=\"sort-icon\" *ngIf=\"sortField === 'username'\">\n");
        sb.append("                <svg *ngIf=\"sortDirection === 'asc'\" viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 14l5-5 5 5z\"/></svg>\n");
        sb.append("                <svg *ngIf=\"sortDirection === 'desc'\" viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 10l5 5 5-5z\"/></svg>\n");
        sb.append("              </span>\n");
        sb.append("              <span class=\"sort-icon inactive\" *ngIf=\"sortField !== 'username'\">\n");
        sb.append("                <svg viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 10l5-5 5 5M7 14l5 5 5-5\" stroke=\"currentColor\" fill=\"none\" stroke-width=\"1.5\"/></svg>\n");
        sb.append("              </span>\n");
        sb.append("            </span>\n");
        sb.append("          </th>\n");
        sb.append("          <th class=\"sortable\" (click)=\"toggleSort('nome')\">\n");
        sb.append("            <span class=\"th-content\">Nome\n");
        sb.append("              <span class=\"sort-icon\" *ngIf=\"sortField === 'nome'\">\n");
        sb.append("                <svg *ngIf=\"sortDirection === 'asc'\" viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 14l5-5 5 5z\"/></svg>\n");
        sb.append("                <svg *ngIf=\"sortDirection === 'desc'\" viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 10l5 5 5-5z\"/></svg>\n");
        sb.append("              </span>\n");
        sb.append("              <span class=\"sort-icon inactive\" *ngIf=\"sortField !== 'nome'\">\n");
        sb.append("                <svg viewBox=\"0 0 24 24\" fill=\"currentColor\"><path d=\"M7 10l5-5 5 5M7 14l5 5 5-5\" stroke=\"currentColor\" fill=\"none\" stroke-width=\"1.5\"/></svg>\n");
        sb.append("              </span>\n");
        sb.append("            </span>\n");
        sb.append("          </th>\n");
        sb.append("          <th>E-mail</th>\n");
        sb.append("          <th>Perfil</th>\n");
        sb.append("          <th>Ativo</th>\n");
        sb.append("          <th class=\"actions-column\">Ações</th>\n");
        sb.append("        </tr>\n");
        sb.append("      </thead>\n");
        sb.append("      <tbody>\n");
        sb.append("        <tr *ngFor=\"let item of filteredItems\">\n");
        sb.append("          <td>{{ item.id }}</td>\n");
        sb.append("          <td>{{ item.username }}</td>\n");
        sb.append("          <td>{{ item.nome }}</td>\n");
        sb.append("          <td>{{ item.email }}</td>\n");
        sb.append("          <td>{{ item.perfilNome }}</td>\n");
        sb.append("          <td><span [class]=\"item.ativo ? 'badge-success' : 'badge-danger'\">{{ item.ativo ? 'Sim' : 'Não' }}</span></td>\n");
        sb.append("          <td class=\"actions-column\">\n");
        sb.append("            <button class=\"btn btn-edit btn-sm\" (click)=\"editar(item.id)\" title=\"Editar\">\n");
        sb.append("              <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("                <path d=\"M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7\"></path>\n");
        sb.append("                <path d=\"M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z\"></path>\n");
        sb.append("              </svg>\n");
        sb.append("              <span>Editar</span>\n");
        sb.append("            </button>\n");
        sb.append("            <button class=\"btn btn-danger btn-sm\" (click)=\"delete(item.id)\" title=\"Excluir\">\n");
        sb.append("              <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("                <polyline points=\"3 6 5 6 21 6\"></polyline>\n");
        sb.append("                <path d=\"M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2\"></path>\n");
        sb.append("              </svg>\n");
        sb.append("              <span>Excluir</span>\n");
        sb.append("            </button>\n");
        sb.append("          </td>\n");
        sb.append("        </tr>\n");
        sb.append("        <tr *ngIf=\"filteredItems.length === 0\">\n");
        sb.append("          <td colspan=\"7\" class=\"no-data\">Nenhum usuário encontrado</td>\n");
        sb.append("        </tr>\n");
        sb.append("      </tbody>\n");
        sb.append("    </table>\n\n");

        // Paginação
        sb.append("    <!-- Paginação -->\n");
        sb.append("    <div class=\"pagination-container\" *ngIf=\"totalPages > 1\">\n");
        sb.append("      <div class=\"pagination-info\">Mostrando {{ currentPage * pageSize + 1 }} - {{ getLastItemIndex() }} de {{ totalElements }} registros</div>\n");
        sb.append("      <div class=\"pagination-controls\">\n");
        sb.append("        <button class=\"btn btn-pagination\" (click)=\"firstPage()\" [disabled]=\"currentPage === 0\" title=\"Primeira página\">\n");
        sb.append("          <svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><polyline points=\"11 17 6 12 11 7\"></polyline><polyline points=\"18 17 13 12 18 7\"></polyline></svg>\n");
        sb.append("        </button>\n");
        sb.append("        <button class=\"btn btn-pagination\" (click)=\"previousPage()\" [disabled]=\"currentPage === 0\" title=\"Página anterior\">\n");
        sb.append("          <svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\"><polyline points=\"15 18 9 12 15 6\"></polyline></svg>\n");
        sb.append("        </button>\n");
        sb.append("        <span class=\"page-numbers\">\n");
        sb.append("          <button *ngFor=\"let page of getPageNumbers()\" class=\"btn btn-page\" [class.active]=\"page === currentPage\" (click)=\"goToPage(page)\">{{ page + 1 }}</button>\n");
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

    /**
     * Gera o CSS do componente de lista de usuários.
     */
    public String generateUsuarioListComponentCss() {
        return generateListComponentCss();
    }

    // ==================== USUARIO FORM COMPONENT ====================

    /**
     * Gera o TypeScript do componente de formulário de usuário.
     */
    public String generateUsuarioFormComponentTs() {
        StringBuilder sb = new StringBuilder();

        sb.append("import { Component, OnInit } from '@angular/core';\n");
        sb.append("import { CommonModule } from '@angular/common';\n");
        sb.append("import { FormsModule } from '@angular/forms';\n");
        sb.append("import { Router, ActivatedRoute } from '@angular/router';\n");
        sb.append("import { UsuarioRequest } from '../models/usuario.model';\n");
        sb.append("import { Perfil } from '../models/perfil.model';\n");
        sb.append("import { UsuarioService } from '../services/usuario.service';\n\n");

        sb.append("@Component({\n");
        sb.append("  selector: 'app-usuario-form',\n");
        sb.append("  standalone: true,\n");
        sb.append("  imports: [CommonModule, FormsModule],\n");
        sb.append("  templateUrl: './usuario-form.component.html',\n");
        sb.append("  styleUrls: ['./usuario-form.component.css']\n");
        sb.append("})\n");
        sb.append("export class UsuarioFormComponent implements OnInit {\n\n");

        sb.append("  id: number | null = null;\n");
        sb.append("  isEditing: boolean = false;\n");
        sb.append("  loading: boolean = false;\n");
        sb.append("  saving: boolean = false;\n");
        sb.append("  error: string | null = null;\n\n");

        sb.append("  perfis: Perfil[] = [];\n\n");

        sb.append("  form: UsuarioRequest = {\n");
        sb.append("    username: '',\n");
        sb.append("    senha: '',\n");
        sb.append("    nome: '',\n");
        sb.append("    email: '',\n");
        sb.append("    ativo: true,\n");
        sb.append("    perfilId: ''\n");
        sb.append("  };\n\n");

        sb.append("  constructor(\n");
        sb.append("    private service: UsuarioService,\n");
        sb.append("    private router: Router,\n");
        sb.append("    private route: ActivatedRoute\n");
        sb.append("  ) {}\n\n");

        sb.append("  ngOnInit(): void {\n");
        sb.append("    this.loadPerfis();\n");
        sb.append("    const idParam = this.route.snapshot.paramMap.get('id');\n");
        sb.append("    if (idParam) {\n");
        sb.append("      this.id = +idParam;\n");
        sb.append("      this.isEditing = true;\n");
        sb.append("      this.loadData();\n");
        sb.append("    }\n");
        sb.append("  }\n\n");

        sb.append("  loadPerfis(): void {\n");
        sb.append("    this.service.getPerfis().subscribe({\n");
        sb.append("      next: (data) => this.perfis = data,\n");
        sb.append("      error: (err) => console.error('Erro ao carregar perfis:', err)\n");
        sb.append("    });\n");
        sb.append("  }\n\n");

        sb.append("  loadData(): void {\n");
        sb.append("    if (!this.id) return;\n");
        sb.append("    this.loading = true;\n");
        sb.append("    this.service.findById(this.id).subscribe({\n");
        sb.append("      next: (data) => {\n");
        sb.append("        this.form = {\n");
        sb.append("          username: data.username,\n");
        sb.append("          senha: '',\n");
        sb.append("          nome: data.nome,\n");
        sb.append("          email: data.email || '',\n");
        sb.append("          ativo: data.ativo,\n");
        sb.append("          perfilId: data.perfilId\n");
        sb.append("        };\n");
        sb.append("        this.loading = false;\n");
        sb.append("      },\n");
        sb.append("      error: (err) => {\n");
        sb.append("        this.error = 'Erro ao carregar usuário';\n");
        sb.append("        this.loading = false;\n");
        sb.append("        console.error(err);\n");
        sb.append("      }\n");
        sb.append("    });\n");
        sb.append("  }\n\n");

        sb.append("  save(): void {\n");
        sb.append("    this.saving = true;\n");
        sb.append("    this.error = null;\n\n");
        sb.append("    const request = { ...this.form };\n");
        sb.append("    if (!request.senha) {\n");
        sb.append("      delete request.senha;\n");
        sb.append("    }\n\n");
        sb.append("    const operation = this.isEditing && this.id\n");
        sb.append("      ? this.service.update(this.id, request)\n");
        sb.append("      : this.service.create(request);\n\n");
        sb.append("    operation.subscribe({\n");
        sb.append("      next: () => {\n");
        sb.append("        this.router.navigate(['/usuario']);\n");
        sb.append("      },\n");
        sb.append("      error: (err) => {\n");
        sb.append("        this.error = err.error?.message || 'Erro ao salvar usuário';\n");
        sb.append("        this.saving = false;\n");
        sb.append("        console.error(err);\n");
        sb.append("      }\n");
        sb.append("    });\n");
        sb.append("  }\n\n");

        sb.append("  cancel(): void {\n");
        sb.append("    this.router.navigate(['/usuario']);\n");
        sb.append("  }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o HTML do componente de formulário de usuário.
     */
    public String generateUsuarioFormComponentHtml() {
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"container\">\n");
        sb.append("  <div class=\"header\">\n");
        sb.append("    <h2>{{ isEditing ? 'Editar Usuário' : 'Novo Usuário' }}</h2>\n");
        sb.append("  </div>\n\n");

        sb.append("  <div *ngIf=\"loading\" class=\"loading\">Carregando...</div>\n");
        sb.append("  <div *ngIf=\"error\" class=\"error\">{{ error }}</div>\n\n");

        sb.append("  <div *ngIf=\"!loading\" class=\"form-container\">\n");
        sb.append("    <form (ngSubmit)=\"save()\">\n");
        sb.append("      <div class=\"form-group\">\n");
        sb.append("        <label for=\"username\">\n");
        sb.append("          <svg class=\"label-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("            <path d=\"M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2\"></path>\n");
        sb.append("            <circle cx=\"12\" cy=\"7\" r=\"4\"></circle>\n");
        sb.append("          </svg>\n");
        sb.append("          Username <span class=\"required\">*</span>\n");
        sb.append("        </label>\n");
        sb.append("        <input type=\"text\" id=\"username\" [(ngModel)]=\"form.username\" name=\"username\" required [disabled]=\"isEditing\" placeholder=\"Digite o username\">\n");
        sb.append("      </div>\n\n");

        sb.append("      <div class=\"form-group\">\n");
        sb.append("        <label for=\"senha\">\n");
        sb.append("          <svg class=\"label-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("            <rect x=\"3\" y=\"11\" width=\"18\" height=\"11\" rx=\"2\" ry=\"2\"></rect>\n");
        sb.append("            <path d=\"M7 11V7a5 5 0 0 1 10 0v4\"></path>\n");
        sb.append("          </svg>\n");
        sb.append("          Senha {{ isEditing ? '(deixe em branco para manter)' : '' }} <span class=\"required\" *ngIf=\"!isEditing\">*</span>\n");
        sb.append("        </label>\n");
        sb.append("        <input type=\"password\" id=\"senha\" [(ngModel)]=\"form.senha\" name=\"senha\" [required]=\"!isEditing\" placeholder=\"{{ isEditing ? 'Deixe em branco para manter a senha atual' : 'Digite a senha' }}\">\n");
        sb.append("      </div>\n\n");

        sb.append("      <div class=\"form-group\">\n");
        sb.append("        <label for=\"nome\">\n");
        sb.append("          <svg class=\"label-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("            <path d=\"M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2\"></path>\n");
        sb.append("            <circle cx=\"9\" cy=\"7\" r=\"4\"></circle>\n");
        sb.append("            <path d=\"M23 21v-2a4 4 0 0 0-3-3.87\"></path>\n");
        sb.append("            <path d=\"M16 3.13a4 4 0 0 1 0 7.75\"></path>\n");
        sb.append("          </svg>\n");
        sb.append("          Nome <span class=\"required\">*</span>\n");
        sb.append("        </label>\n");
        sb.append("        <input type=\"text\" id=\"nome\" [(ngModel)]=\"form.nome\" name=\"nome\" required placeholder=\"Digite o nome completo\">\n");
        sb.append("      </div>\n\n");

        sb.append("      <div class=\"form-group\">\n");
        sb.append("        <label for=\"email\">\n");
        sb.append("          <svg class=\"label-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("            <path d=\"M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z\"></path>\n");
        sb.append("            <polyline points=\"22,6 12,13 2,6\"></polyline>\n");
        sb.append("          </svg>\n");
        sb.append("          E-mail\n");
        sb.append("        </label>\n");
        sb.append("        <input type=\"email\" id=\"email\" [(ngModel)]=\"form.email\" name=\"email\" placeholder=\"Digite o e-mail\">\n");
        sb.append("      </div>\n\n");

        sb.append("      <div class=\"form-group\">\n");
        sb.append("        <label for=\"perfilId\">\n");
        sb.append("          <svg class=\"label-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("            <path d=\"M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z\"></path>\n");
        sb.append("          </svg>\n");
        sb.append("          Perfil <span class=\"required\">*</span>\n");
        sb.append("        </label>\n");
        sb.append("        <select id=\"perfilId\" [(ngModel)]=\"form.perfilId\" name=\"perfilId\" required>\n");
        sb.append("          <option value=\"\">Selecione o perfil...</option>\n");
        sb.append("          <option *ngFor=\"let perfil of perfis\" [value]=\"perfil.id\">{{ perfil.nome }}</option>\n");
        sb.append("        </select>\n");
        sb.append("      </div>\n\n");

        sb.append("      <div class=\"form-group checkbox-group\">\n");
        sb.append("        <label class=\"checkbox-label\">\n");
        sb.append("          <input type=\"checkbox\" [(ngModel)]=\"form.ativo\" name=\"ativo\">\n");
        sb.append("          <span class=\"checkbox-custom\"></span>\n");
        sb.append("          <span class=\"checkbox-text\">Usuário Ativo</span>\n");
        sb.append("        </label>\n");
        sb.append("      </div>\n\n");

        sb.append("      <div class=\"form-actions\">\n");
        sb.append("        <button type=\"button\" class=\"btn btn-secondary\" (click)=\"cancel()\">\n");
        sb.append("          <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("            <line x1=\"18\" y1=\"6\" x2=\"6\" y2=\"18\"></line>\n");
        sb.append("            <line x1=\"6\" y1=\"6\" x2=\"18\" y2=\"18\"></line>\n");
        sb.append("          </svg>\n");
        sb.append("          <span>Cancelar</span>\n");
        sb.append("        </button>\n");
        sb.append("        <button type=\"submit\" class=\"btn btn-primary\" [disabled]=\"saving\">\n");
        sb.append("          <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" *ngIf=\"!saving\">\n");
        sb.append("            <path d=\"M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z\"></path>\n");
        sb.append("            <polyline points=\"17 21 17 13 7 13 7 21\"></polyline>\n");
        sb.append("            <polyline points=\"7 3 7 8 15 8\"></polyline>\n");
        sb.append("          </svg>\n");
        sb.append("          <span>{{ saving ? 'Salvando...' : 'Salvar' }}</span>\n");
        sb.append("        </button>\n");
        sb.append("      </div>\n");
        sb.append("    </form>\n");
        sb.append("  </div>\n");
        sb.append("</div>\n");

        return sb.toString();
    }

    /**
     * Gera o CSS do componente de formulário de usuário.
     */
    public String generateUsuarioFormComponentCss() {
        return generateFormComponentCss();
    }

    // ==================== PERFIL LIST COMPONENT ====================

    /**
     * Gera o TypeScript do componente de lista de perfis.
     */
    public String generatePerfilListComponentTs() {
        StringBuilder sb = new StringBuilder();

        sb.append("import { Component, OnInit } from '@angular/core';\n");
        sb.append("import { CommonModule } from '@angular/common';\n");
        sb.append("import { FormsModule } from '@angular/forms';\n");
        sb.append("import { Perfil } from '../models/perfil.model';\n");
        sb.append("import { UsuarioService } from '../services/usuario.service';\n\n");

        sb.append("@Component({\n");
        sb.append("  selector: 'app-perfil-list',\n");
        sb.append("  standalone: true,\n");
        sb.append("  imports: [CommonModule, FormsModule],\n");
        sb.append("  templateUrl: './perfil-list.component.html',\n");
        sb.append("  styleUrls: ['./perfil-list.component.css']\n");
        sb.append("})\n");
        sb.append("export class PerfilListComponent implements OnInit {\n\n");

        sb.append("  items: Perfil[] = [];\n");
        sb.append("  loading: boolean = false;\n");
        sb.append("  error: string | null = null;\n");
        sb.append("  expandedPerfil: string | null = null;\n\n");

        sb.append("  constructor(private service: UsuarioService) {}\n\n");

        sb.append("  ngOnInit(): void {\n");
        sb.append("    this.loadItems();\n");
        sb.append("  }\n\n");

        sb.append("  loadItems(): void {\n");
        sb.append("    this.loading = true;\n");
        sb.append("    this.error = null;\n");
        sb.append("    this.service.getPerfis().subscribe({\n");
        sb.append("      next: (data) => {\n");
        sb.append("        this.items = data;\n");
        sb.append("        this.loading = false;\n");
        sb.append("      },\n");
        sb.append("      error: (err) => {\n");
        sb.append("        this.error = 'Erro ao carregar dados';\n");
        sb.append("        this.loading = false;\n");
        sb.append("        console.error(err);\n");
        sb.append("      }\n");
        sb.append("    });\n");
        sb.append("  }\n\n");

        sb.append("  toggleExpand(perfilId: string): void {\n");
        sb.append("    this.expandedPerfil = this.expandedPerfil === perfilId ? null : perfilId;\n");
        sb.append("  }\n\n");

        sb.append("  formatPermission(permission: string): string {\n");
        sb.append("    const parts = permission.split(':');\n");
        sb.append("    if (parts.length !== 2) return permission;\n");
        sb.append("    const module = parts[0];\n");
        sb.append("    const access = parts[1];\n");
        sb.append("    const accessLabel = access === 'full' ? 'Acesso Total' : \n");
        sb.append("                        access === 'readonly' ? 'Somente Leitura' : access;\n");
        sb.append("    return `${module} - ${accessLabel}`;\n");
        sb.append("  }\n");

        sb.append("}\n");

        return sb.toString();
    }

    /**
     * Gera o HTML do componente de lista de perfis.
     */
    public String generatePerfilListComponentHtml() {
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"container\">\n");
        sb.append("  <div class=\"header\">\n");
        sb.append("    <h2>Perfis de Acesso</h2>\n");
        sb.append("  </div>\n\n");

        sb.append("  <div *ngIf=\"loading\" class=\"loading\">Carregando...</div>\n");
        sb.append("  <div *ngIf=\"error\" class=\"error\">{{ error }}</div>\n\n");

        sb.append("  <div class=\"info-box\" *ngIf=\"!loading\">\n");
        sb.append("    <svg class=\"info-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("      <circle cx=\"12\" cy=\"12\" r=\"10\"></circle>\n");
        sb.append("      <line x1=\"12\" y1=\"16\" x2=\"12\" y2=\"12\"></line>\n");
        sb.append("      <line x1=\"12\" y1=\"8\" x2=\"12.01\" y2=\"8\"></line>\n");
        sb.append("    </svg>\n");
        sb.append("    <span>Os perfis são definidos na configuração do sistema e não podem ser editados por esta interface.</span>\n");
        sb.append("  </div>\n\n");

        sb.append("  <div *ngIf=\"!loading && items.length > 0\" class=\"table-container\">\n");
        sb.append("    <table class=\"data-table\">\n");
        sb.append("      <thead>\n");
        sb.append("        <tr>\n");
        sb.append("          <th>ID</th>\n");
        sb.append("          <th>Nome</th>\n");
        sb.append("          <th>Descrição</th>\n");
        sb.append("          <th>Ativo</th>\n");
        sb.append("          <th>Permissões</th>\n");
        sb.append("        </tr>\n");
        sb.append("      </thead>\n");
        sb.append("      <tbody>\n");
        sb.append("        <tr *ngFor=\"let item of items\">\n");
        sb.append("          <td>{{ item.id }}</td>\n");
        sb.append("          <td>{{ item.nome }}</td>\n");
        sb.append("          <td>{{ item.descricao }}</td>\n");
        sb.append("          <td>\n");
        sb.append("            <span [class]=\"item.ativo ? 'badge-success' : 'badge-danger'\">{{ item.ativo ? 'Sim' : 'Não' }}</span>\n");
        sb.append("          </td>\n");
        sb.append("          <td>\n");
        sb.append("            <button class=\"btn btn-sm btn-toggle\" (click)=\"toggleExpand(item.id)\">\n");
        sb.append("              <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" *ngIf=\"expandedPerfil !== item.id\">\n");
        sb.append("                <polyline points=\"6 9 12 15 18 9\"></polyline>\n");
        sb.append("              </svg>\n");
        sb.append("              <svg class=\"icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" *ngIf=\"expandedPerfil === item.id\">\n");
        sb.append("                <polyline points=\"18 15 12 9 6 15\"></polyline>\n");
        sb.append("              </svg>\n");
        sb.append("              <span>{{ expandedPerfil === item.id ? 'Ocultar' : 'Ver' }} ({{ item.permissoes.length || 0 }})</span>\n");
        sb.append("            </button>\n");
        sb.append("            <div class=\"permissions-list\" *ngIf=\"expandedPerfil === item.id\">\n");
        sb.append("              <ul>\n");
        sb.append("                <li *ngFor=\"let perm of item.permissoes\">\n");
        sb.append("                  <svg class=\"perm-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("                    <polyline points=\"20 6 9 17 4 12\"></polyline>\n");
        sb.append("                  </svg>\n");
        sb.append("                  {{ formatPermission(perm) }}\n");
        sb.append("                </li>\n");
        sb.append("              </ul>\n");
        sb.append("            </div>\n");
        sb.append("          </td>\n");
        sb.append("        </tr>\n");
        sb.append("      </tbody>\n");
        sb.append("    </table>\n");
        sb.append("  </div>\n\n");

        sb.append("  <div class=\"no-data\" *ngIf=\"!loading && items.length === 0\">\n");
        sb.append("    <svg class=\"empty-icon\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\">\n");
        sb.append("      <path d=\"M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z\"></path>\n");
        sb.append("    </svg>\n");
        sb.append("    <span>Nenhum perfil encontrado.</span>\n");
        sb.append("  </div>\n");

        sb.append("</div>\n");

        return sb.toString();
    }

    /**
     * Gera o CSS do componente de lista de perfis.
     */
    public String generatePerfilListComponentCss() {
        StringBuilder sb = new StringBuilder();
        sb.append(generateListComponentCss());

        sb.append("\n.info-box {\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 0.75rem;\n");
        sb.append("  background: linear-gradient(135deg, #e7f3fe 0%, #f0f7ff 100%);\n");
        sb.append("  border-left: 4px solid #667eea;\n");
        sb.append("  padding: 1rem 1.25rem;\n");
        sb.append("  margin-bottom: 1.5rem;\n");
        sb.append("  border-radius: 0 8px 8px 0;\n");
        sb.append("  color: #1565c0;\n");
        sb.append("}\n\n");

        sb.append(".info-icon {\n");
        sb.append("  width: 24px;\n");
        sb.append("  height: 24px;\n");
        sb.append("  color: #667eea;\n");
        sb.append("  flex-shrink: 0;\n");
        sb.append("}\n\n");

        sb.append(".btn-toggle {\n");
        sb.append("  background: #f0f0f0;\n");
        sb.append("  border: 1px solid #ddd;\n");
        sb.append("  color: #555;\n");
        sb.append("}\n\n");

        sb.append(".btn-toggle:hover {\n");
        sb.append("  background: #e0e0e0;\n");
        sb.append("}\n\n");

        sb.append(".permissions-list {\n");
        sb.append("  margin-top: 0.75rem;\n");
        sb.append("  padding: 0.75rem 1rem;\n");
        sb.append("  background: linear-gradient(135deg, #f8f9fa 0%, #fff 100%);\n");
        sb.append("  border-radius: 6px;\n");
        sb.append("  border: 1px solid #eee;\n");
        sb.append("}\n\n");

        sb.append(".permissions-list ul {\n");
        sb.append("  list-style: none;\n");
        sb.append("  padding: 0;\n");
        sb.append("  margin: 0;\n");
        sb.append("}\n\n");

        sb.append(".permissions-list li {\n");
        sb.append("  display: flex;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 0.5rem;\n");
        sb.append("  padding: 0.35rem 0;\n");
        sb.append("  font-size: 0.9em;\n");
        sb.append("  color: #495057;\n");
        sb.append("}\n\n");

        sb.append(".perm-icon {\n");
        sb.append("  width: 16px;\n");
        sb.append("  height: 16px;\n");
        sb.append("  color: #28a745;\n");
        sb.append("  flex-shrink: 0;\n");
        sb.append("}\n\n");

        sb.append(".no-data {\n");
        sb.append("  display: flex;\n");
        sb.append("  flex-direction: column;\n");
        sb.append("  align-items: center;\n");
        sb.append("  gap: 1rem;\n");
        sb.append("  padding: 3rem;\n");
        sb.append("  text-align: center;\n");
        sb.append("  color: #999;\n");
        sb.append("  background: white;\n");
        sb.append("  border-radius: 8px;\n");
        sb.append("  box-shadow: 0 2px 4px rgba(0,0,0,0.1);\n");
        sb.append("}\n\n");

        sb.append(".empty-icon {\n");
        sb.append("  width: 48px;\n");
        sb.append("  height: 48px;\n");
        sb.append("  color: #ccc;\n");
        sb.append("}\n");

        return sb.toString();
    }

    // ==================== SHARED CSS ====================

    /**
     * Gera o CSS comum para componentes de lista (padrão moderno com gradientes).
     */
    private String generateListComponentCss() {
        StringBuilder sb = new StringBuilder();

        sb.append(".container { padding: 0; }\n\n");

        sb.append(".header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem; }\n");
        sb.append(".header h2 { margin: 0; color: #333; }\n\n");

        sb.append(".header-actions { display: flex; align-items: center; gap: 1rem; }\n\n");

        sb.append(".search-box { position: relative; flex: 1; max-width: 300px; }\n");
        sb.append(".search-icon { position: absolute; left: 10px; top: 50%; transform: translateY(-50%); width: 18px; height: 18px; color: #999; }\n");
        sb.append(".search-input { width: 100%; padding: 0.5rem 1rem 0.5rem 36px; border: 1px solid #ddd; border-radius: 4px; font-size: 14px; }\n");
        sb.append(".search-input:focus { outline: none; border-color: #667eea; }\n\n");

        sb.append(".loading, .error { padding: 2rem; text-align: center; border-radius: 4px; }\n");
        sb.append(".loading { background: #f0f0f0; color: #666; }\n");
        sb.append(".error { background: #fee; color: #c33; }\n");
        sb.append(".success { padding: 1rem; text-align: center; border-radius: 4px; background: #d4edda; color: #155724; border: 1px solid #c3e6cb; margin-bottom: 1rem; }\n\n");

        sb.append(".table-container { background: white; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); overflow: hidden; }\n\n");

        sb.append(".data-table { width: 100%; border-collapse: collapse; }\n");
        sb.append(".data-table thead { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; }\n");
        sb.append(".data-table th { padding: 1rem; text-align: left; font-weight: 600; }\n");
        sb.append(".data-table th.sortable { cursor: pointer; user-select: none; transition: background 0.2s; }\n");
        sb.append(".data-table th.sortable:hover { background: rgba(255, 255, 255, 0.1); }\n");
        sb.append(".th-content { display: flex; align-items: center; gap: 0.5rem; }\n");
        sb.append(".sort-icon { display: flex; align-items: center; }\n");
        sb.append(".sort-icon svg { width: 16px; height: 16px; }\n");
        sb.append(".sort-icon.inactive { opacity: 0.4; }\n");
        sb.append(".data-table td { padding: 0.75rem 1rem; border-bottom: 1px solid #eee; }\n");
        sb.append(".data-table tbody tr:hover { background: #f8f9fa; }\n\n");

        sb.append(".actions-column { width: 180px; text-align: center; }\n");
        sb.append("td.actions-column { display: flex; justify-content: center; align-items: center; gap: 0.5rem; }\n");
        sb.append(".no-data { text-align: center; color: #999; padding: 2rem !important; }\n\n");

        sb.append(".badge-success { background-color: #28a745; color: white; padding: 4px 8px; border-radius: 4px; font-size: 12px; }\n");
        sb.append(".badge-danger { background-color: #dc3545; color: white; padding: 4px 8px; border-radius: 4px; font-size: 12px; }\n\n");

        sb.append(".btn { display: inline-flex; align-items: center; gap: 0.4rem; padding: 0.5rem 1rem; border: none; border-radius: 4px; cursor: pointer; font-size: 14px; font-weight: 500; transition: all 0.2s; }\n");
        sb.append(".btn .icon { width: 16px; height: 16px; flex-shrink: 0; }\n");
        sb.append(".btn-sm { padding: 0.35rem 0.6rem; font-size: 12px; gap: 0.3rem; }\n");
        sb.append(".btn-sm .icon { width: 14px; height: 14px; }\n\n");

        sb.append(".btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; }\n");
        sb.append(".btn-primary:hover { background: linear-gradient(135deg, #5a6fd6 0%, #6a4190 100%); }\n");
        sb.append(".btn-edit { background: #17a2b8; color: white; }\n");
        sb.append(".btn-edit:hover { background: #138496; }\n");
        sb.append(".btn-danger { background: #dc3545; color: white; }\n");
        sb.append(".btn-danger:hover { background: #c82333; }\n\n");

        // Paginação
        sb.append(".pagination-container { display: flex; justify-content: space-between; align-items: center; padding: 1rem; border-top: 1px solid #eee; background: #fafafa; }\n");
        sb.append(".pagination-info { color: #666; font-size: 14px; }\n");
        sb.append(".pagination-controls { display: flex; align-items: center; gap: 0.5rem; }\n");
        sb.append(".btn-pagination { display: flex; align-items: center; justify-content: center; width: 32px; height: 32px; padding: 0; border: 1px solid #ddd; border-radius: 4px; background: white; cursor: pointer; transition: all 0.2s; }\n");
        sb.append(".btn-pagination:hover:not(:disabled) { background: #f0f0f0; border-color: #ccc; }\n");
        sb.append(".btn-pagination:disabled { opacity: 0.5; cursor: not-allowed; }\n");
        sb.append(".btn-pagination svg { width: 16px; height: 16px; color: #666; }\n");
        sb.append(".page-numbers { display: flex; gap: 0.25rem; }\n");
        sb.append(".btn-page { min-width: 32px; height: 32px; padding: 0 0.5rem; border: 1px solid #ddd; border-radius: 4px; background: white; cursor: pointer; font-size: 14px; transition: all 0.2s; }\n");
        sb.append(".btn-page:hover { background: #f0f0f0; border-color: #ccc; }\n");
        sb.append(".btn-page.active { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; border-color: transparent; }\n");
        sb.append(".page-size-select { padding: 0.4rem 0.75rem; border: 1px solid #ddd; border-radius: 4px; font-size: 14px; background: white; cursor: pointer; margin-left: 1rem; }\n");
        sb.append(".page-size-select:focus { outline: none; border-color: #667eea; }\n");

        return sb.toString();
    }

    /**
     * Gera o CSS comum para componentes de formulário (padrão moderno com gradientes).
     */
    private String generateFormComponentCss() {
        StringBuilder sb = new StringBuilder();

        sb.append(".container { padding: 0; max-width: 600px; }\n\n");

        sb.append(".header { margin-bottom: 1.5rem; }\n");
        sb.append(".header h2 { margin: 0; color: #333; }\n\n");

        sb.append(".loading, .error { padding: 2rem; text-align: center; border-radius: 4px; }\n");
        sb.append(".loading { background: #f0f0f0; color: #666; }\n");
        sb.append(".error { background: #fee; color: #c33; margin-bottom: 1rem; }\n\n");

        sb.append(".form-container { background: white; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); padding: 1.5rem; }\n\n");

        sb.append(".form-group { margin-bottom: 1.25rem; }\n");
        sb.append(".form-group label { display: flex; align-items: center; gap: 0.5rem; margin-bottom: 0.5rem; font-weight: 500; color: #333; }\n");
        sb.append(".label-icon { width: 18px; height: 18px; color: #667eea; }\n");
        sb.append(".required { color: #dc3545; }\n\n");

        sb.append(".form-group input, .form-group select, .form-group textarea { width: 100%; padding: 0.75rem 1rem; border: 1px solid #ddd; border-radius: 6px; font-size: 14px; transition: border-color 0.2s, box-shadow 0.2s; }\n");
        sb.append(".form-group input:focus, .form-group select:focus, .form-group textarea:focus { outline: none; border-color: #667eea; box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.15); }\n");
        sb.append(".form-group input:disabled { background-color: #f5f5f5; cursor: not-allowed; }\n");
        sb.append(".form-group input::placeholder { color: #aaa; }\n\n");

        sb.append(".checkbox-group { margin-top: 1.5rem; }\n");
        sb.append(".checkbox-label { display: flex; align-items: center; gap: 0.75rem; cursor: pointer; font-weight: normal !important; }\n");
        sb.append(".checkbox-label input[type=\"checkbox\"] { width: auto; display: none; }\n");
        sb.append(".checkbox-custom { width: 22px; height: 22px; border: 2px solid #ddd; border-radius: 4px; display: flex; align-items: center; justify-content: center; transition: all 0.2s; }\n");
        sb.append(".checkbox-label input[type=\"checkbox\"]:checked + .checkbox-custom { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-color: #667eea; }\n");
        sb.append(".checkbox-label input[type=\"checkbox\"]:checked + .checkbox-custom::after { content: ''; width: 6px; height: 10px; border: solid white; border-width: 0 2px 2px 0; transform: rotate(45deg); margin-bottom: 2px; }\n");
        sb.append(".checkbox-text { color: #333; font-size: 14px; }\n\n");

        sb.append(".form-actions { display: flex; gap: 0.75rem; margin-top: 2rem; padding-top: 1.5rem; border-top: 1px solid #eee; }\n\n");

        sb.append(".btn { display: inline-flex; align-items: center; gap: 0.5rem; padding: 0.75rem 1.25rem; border: none; border-radius: 6px; cursor: pointer; font-size: 14px; font-weight: 500; transition: all 0.2s; }\n");
        sb.append(".btn .icon { width: 18px; height: 18px; }\n");
        sb.append(".btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; }\n");
        sb.append(".btn-primary:hover { background: linear-gradient(135deg, #5a6fd6 0%, #6a4190 100%); transform: translateY(-1px); }\n");
        sb.append(".btn-primary:disabled { background: #aaa; cursor: not-allowed; transform: none; }\n");
        sb.append(".btn-secondary { background: #6c757d; color: white; }\n");
        sb.append(".btn-secondary:hover { background: #545b62; }\n");

        return sb.toString();
    }
}
