import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { PagamentoNaoSocioList, PagamentoNaoSocioId } from '../models/pagamentoNaoSocio.model';
import { PagamentoNaoSocioService } from '../services/pagamentoNaoSocio.service';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-pagamentoNaoSocio-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './pagamentoNaoSocio-list.component.html',
  styleUrls: ['./pagamentoNaoSocio-list.component.css']
})
export class PagamentoNaoSocioListComponent implements OnInit {

  items: PagamentoNaoSocioList[] = [];
  filteredItems: PagamentoNaoSocioList[] = [];
  searchTerm: string = '';
  loading: boolean = false;
  error: string | null = null;
  successMessage: string | null = null;
  parentFilter: { [key: string]: number } = {};
  parentFilterKey: string | null = null;
  parentName: string | null = null;
  idEmpresaNome: string | null = null;

  // Paginação
  currentPage: number = 0;
  pageSize: number = 20;
  totalElements: number = 0;
  totalPages: number = 0;

  constructor(
    private service: PagamentoNaoSocioService,
    private sessionService: SessionService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.idEmpresaNome = this.sessionService.getIdEmpresaNome();
    this.route.queryParams.subscribe(params => {
      this.parentFilter = {};
      this.parentFilterKey = null;
      this.parentName = null;
      for (const key of Object.keys(params)) {
        this.parentFilter[key] = +params[key];
        this.parentFilterKey = key;
      }
      this.loadParentName();
      this.loadItems();
    });
  }

  loadParentName(): void {
    if (!this.parentFilterKey) {
      this.parentName = null;
      return;
    }
    const parentId = this.parentFilter[this.parentFilterKey];
    // Aguardar um pouco para os maps serem carregados
    setTimeout(() => {
    }, 500);
  }

  loadItems(): void {
    this.loading = true;
    this.error = null;
    const idEmpresa = this.sessionService.getIdEmpresa();
    this.service.findAll(this.currentPage, this.pageSize, idEmpresa).subscribe({
      next: (data) => {
        let items = data.content;
        // Aplicar filtro de navegação mestre-detalhe (parentFilter)
        if (Object.keys(this.parentFilter).length > 0) {
          items = items.filter((item: any) => {
            for (const key of Object.keys(this.parentFilter)) {
              if (item[key] !== this.parentFilter[key]) return false;
            }
            return true;
          });
        }
        this.items = items;
        this.filteredItems = items;
        // Usar valores de paginação do servidor
        this.totalElements = data.totalElements;
        this.totalPages = data.totalPages;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erro ao carregar dados';
        this.loading = false;
        console.error(err);
      }
    });
  }

  search(): void {
    if (!this.searchTerm) {
      this.filteredItems = this.items;
      return;
    }

    const term = this.searchTerm.toLowerCase();
    this.filteredItems = this.items.filter(item =>
      JSON.stringify(item).toLowerCase().includes(term)
    );
  }

  novo(): void {
    // Passar o parentFilter como queryParams para o formulário pré-selecionar o FK
    if (Object.keys(this.parentFilter).length > 0) {
      this.router.navigate(['/pagamentoNaoSocio/novo'], { queryParams: this.parentFilter });
    } else {
      this.router.navigate(['/pagamentoNaoSocio/novo']);
    }
  }

  editar(id: PagamentoNaoSocioId): void {
    const idParam = Object.values(id).join(',');
    this.router.navigate(['/pagamentoNaoSocio/editar', idParam]);
  }

  delete(id: PagamentoNaoSocioId): void {
    if (!confirm('Deseja realmente excluir este registro?')) {
      return;
    }

    this.error = null;
    this.successMessage = null;
    this.service.delete(id).subscribe({
      next: () => {
        this.successMessage = 'Registro excluído com sucesso!';
        this.loadItems();
        setTimeout(() => this.successMessage = null, 3000);
      },
      error: (err) => {
        this.error = err.error?.message || 'Erro ao excluir registro';
        console.error(err);
        setTimeout(() => this.error = null, 5000);
      }
    });
  }

  // Métodos de paginação
  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadItems();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadItems();
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadItems();
    }
  }

  firstPage(): void {
    this.currentPage = 0;
    this.loadItems();
  }

  lastPage(): void {
    this.currentPage = this.totalPages - 1;
    this.loadItems();
  }

  onPageSizeChange(): void {
    this.currentPage = 0;
    this.loadItems();
  }

  getPageNumbers(): number[] {
    const pages: number[] = [];
    const maxVisiblePages = 5;
    let start = Math.max(0, this.currentPage - Math.floor(maxVisiblePages / 2));
    let end = Math.min(this.totalPages, start + maxVisiblePages);
    if (end - start < maxVisiblePages) {
      start = Math.max(0, end - maxVisiblePages);
    }
    for (let i = start; i < end; i++) {
      pages.push(i);
    }
    return pages;
  }

  getLastItemIndex(): number {
    return Math.min((this.currentPage + 1) * this.pageSize, this.totalElements);
  }

  /**
   * Formata número inteiro com separador de milhar (ponto).
   */
  formatInt(value: number | null | undefined): string {
    if (value === null || value === undefined) return '';
    return value.toLocaleString('pt-BR', { maximumFractionDigits: 0 });
  }

  /**
   * Formata número decimal com separador de milhar (ponto) e decimal (vírgula).
   */
  formatDecimal(value: number | null | undefined, decimals: number = 2): string {
    if (value === null || value === undefined) return '';
    return value.toLocaleString('pt-BR', { minimumFractionDigits: decimals, maximumFractionDigits: decimals });
  }

  /**
   * Formata data no padrão brasileiro (dd/mm/aaaa).
   */
  formatDate(value: Date | string | null | undefined): string {
    if (value === null || value === undefined) return '';
    const date = typeof value === 'string' ? new Date(value + 'T00:00:00') : value;
    return date.toLocaleDateString('pt-BR');
  }
}
