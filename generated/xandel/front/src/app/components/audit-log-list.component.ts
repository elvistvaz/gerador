import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuditLogService } from '../services/audit-log.service';
import { AuditLogList, AuditLogResponse, AuditLogFilter } from '../models/audit-log.model';

@Component({
  selector: 'app-audit-log-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './audit-log-list.component.html',
  styleUrls: ['./audit-log-list.component.css']
})
export class AuditLogListComponent implements OnInit {

  logs: AuditLogList[] = [];
  entidades: string[] = [];
  usuarios: string[] = [];
  totalElements = 0;
  totalPages = 0;
  currentPage = 0;
  pageSize = 10;
  loading = false;
  error = '';
  showFilters = false;

  // Ordenação
  sortField = 'dataHora';
  sortDirection: 'asc' | 'desc' = 'desc';

  // Filtros
  filter: AuditLogFilter = {};
  dataInicioStr = '';
  dataFimStr = '';

  // Modal
  showModal = false;
  selectedLog: AuditLogResponse | null = null;
  dadosAnterioresObj: { [key: string]: any } = {};

  constructor(private auditLogService: AuditLogService) { }

  ngOnInit(): void {
    this.loadEntidades();
    this.loadUsuarios();
    this.loadLogs();
  }

  loadEntidades(): void {
    this.auditLogService.getEntidades().subscribe({
      next: (data) => this.entidades = data,
      error: (err) => console.error('Erro ao carregar entidades:', err)
    });
  }

  loadUsuarios(): void {
    this.auditLogService.getUsuarios().subscribe({
      next: (data) => this.usuarios = data,
      error: (err) => console.error('Erro ao carregar usuários:', err)
    });
  }

  loadLogs(): void {
    this.loading = true;
    this.error = '';
    
    const filterToSend: AuditLogFilter = { ...this.filter };
    if (this.dataInicioStr) {
      filterToSend.dataInicio = new Date(this.dataInicioStr).toISOString();
    }
    if (this.dataFimStr) {
      filterToSend.dataFim = new Date(this.dataFimStr).toISOString();
    }
    
    const sort = `${this.sortField},${this.sortDirection}`;
    this.auditLogService.findAll(filterToSend, this.currentPage, this.pageSize, sort).subscribe({
      next: (response) => {
        this.logs = response.content;
        this.totalElements = response.totalElements;
        this.totalPages = response.totalPages;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar logs:', err);
        this.error = 'Erro ao carregar registros';
        this.loading = false;
      }
    });
  }

  toggleSort(field: string): void {
    if (this.sortField === field) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = field;
      this.sortDirection = 'asc';
    }
    this.loadLogs();
  }

  toggleFilters(): void {
    this.showFilters = !this.showFilters;
  }

  aplicarFiltro(): void {
    this.currentPage = 0;
    this.loadLogs();
  }

  limparFiltro(): void {
    this.filter = {};
    this.dataInicioStr = '';
    this.dataFimStr = '';
    this.currentPage = 0;
    this.loadLogs();
  }

  firstPage(): void {
    this.currentPage = 0;
    this.loadLogs();
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadLogs();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadLogs();
    }
  }

  lastPage(): void {
    this.currentPage = this.totalPages - 1;
    this.loadLogs();
  }

  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadLogs();
    }
  }

  getPageNumbers(): number[] {
    const pages: number[] = [];
    const start = Math.max(0, this.currentPage - 2);
    const end = Math.min(this.totalPages, start + 5);
    for (let i = start; i < end; i++) {
      pages.push(i);
    }
    return pages;
  }

  getLastItemIndex(): number {
    return Math.min((this.currentPage + 1) * this.pageSize, this.totalElements);
  }

  onPageSizeChange(): void {
    this.currentPage = 0;
    this.loadLogs();
  }

  exibirDetalhes(log: AuditLogList): void {
    this.auditLogService.findById(log.id).subscribe({
      next: (response) => {
        this.selectedLog = response;
        if (response.dadosAnteriores) {
          try {
            this.dadosAnterioresObj = JSON.parse(response.dadosAnteriores);
          } catch {
            this.dadosAnterioresObj = {};
          }
        } else {
          this.dadosAnterioresObj = {};
        }
        this.showModal = true;
      },
      error: (err) => console.error('Erro ao carregar detalhes:', err)
    });
  }

  fecharModal(): void {
    this.showModal = false;
    this.selectedLog = null;
    this.dadosAnterioresObj = {};
  }

  getObjectKeys(obj: any): string[] {
    return Object.keys(obj || {});
  }

  formatDate(dateStr: string): string {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    return date.toLocaleString('pt-BR');
  }

  getAcaoClass(acao: string): string {
    switch (acao) {
      case 'CREATE': return 'badge-success';
      case 'UPDATE': return 'badge-warning';
      case 'DELETE': return 'badge-danger';
      default: return 'badge-secondary';
    }
  }
}
