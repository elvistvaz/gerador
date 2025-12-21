import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AvaliacaoSDAList } from '../models/avaliacaoSDA.model';
import { AvaliacaoSDAService } from '../services/avaliacaoSDA.service';
import { SessionService } from '../services/session.service';
import { AnoEscolarList } from '../models/anoEscolar.model';
import { AnoEscolarService } from '../services/anoEscolar.service';
import { AprendizagemEsperadaList } from '../models/aprendizagemEsperada.model';
import { AprendizagemEsperadaService } from '../services/aprendizagemEsperada.service';

@Component({
  selector: 'app-avaliacaoSDA-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './avaliacaoSDA-list.component.html',
  styleUrls: ['./avaliacaoSDA-list.component.css']
})
export class AvaliacaoSDAListComponent implements OnInit {

  items: AvaliacaoSDAList[] = [];
  filteredItems: AvaliacaoSDAList[] = [];
  searchTerm: string = '';
  loading: boolean = false;
  error: string | null = null;
  successMessage: string | null = null;
  parentFilter: { [key: string]: number } = {};
  parentFilterKey: string | null = null;
  parentName: string | null = null;
  municipioNome: string | null = null;
  avaliacaoNome: string | null = null;

  // Paginação
  currentPage: number = 0;
  pageSize: number = 20;
  totalElements: number = 0;
  totalPages: number = 0;

  // Ordenação
  sortField: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  anoEscolarMap: Map<number, string> = new Map();
  aprendizagemEsperadaMap: Map<number, string> = new Map();

  constructor(
    private service: AvaliacaoSDAService,
    private anoEscolarService: AnoEscolarService,
    private aprendizagemEsperadaService: AprendizagemEsperadaService,
    private sessionService: SessionService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.municipioNome = this.sessionService.getMunicipioNome();
    this.avaliacaoNome = this.sessionService.getAvaliacaoNome();
    this.route.queryParams.subscribe(params => {
      this.parentFilter = {};
      this.parentFilterKey = null;
      this.parentName = null;
      for (const key of Object.keys(params)) {
        this.parentFilter[key] = +params[key];
        this.parentFilterKey = key;
      }
      this.loadAnoEscolar();
      this.loadAprendizagemEsperada();
      this.loadParentName();
      this.loadItems();
    });
  }

  loadAnoEscolar(): void {
    this.anoEscolarService.findAll(0, 10000).subscribe({
      next: (data) => {
        data.content.forEach((item: AnoEscolarList) => {
          this.anoEscolarMap.set(item.id, String(item.nome));
        });
      },
      error: (err) => console.error('Erro ao carregar anoEscolar:', err)
    });
  }

  loadAprendizagemEsperada(): void {
    this.aprendizagemEsperadaService.findAll(0, 10000).subscribe({
      next: (data) => {
        data.content.forEach((item: AprendizagemEsperadaList) => {
          this.aprendizagemEsperadaMap.set(item.id, String(item.descricao));
        });
      },
      error: (err) => console.error('Erro ao carregar aprendizagemEsperada:', err)
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
      switch (this.parentFilterKey) {
        case 'anoEscolarId':
          this.parentName = this.anoEscolarMap.get(parentId) || null;
          break;
        case 'aprendizagemEsperadaId':
          this.parentName = this.aprendizagemEsperadaMap.get(parentId) || null;
          break;
      }
    }, 500);
  }

  loadItems(): void {
    this.loading = true;
    this.error = null;
    const sort = this.sortField ? `${this.sortField},${this.sortDirection}` : undefined;
    const municipioId = this.sessionService.getMunicipioId();
    const avaliacaoId = this.sessionService.getAvaliacaoId();
    this.service.findAll(this.currentPage, this.pageSize, municipioId, avaliacaoId, sort).subscribe({
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
      this.router.navigate(['/avaliacaoSDA/novo'], { queryParams: this.parentFilter });
    } else {
      this.router.navigate(['/avaliacaoSDA/novo']);
    }
  }

  editar(id: number): void {
    this.router.navigate(['/avaliacaoSDA/editar', id]);
  }

  delete(id: number): void {
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

  toggleSort(field: string): void {
    if (this.sortField === field) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = field;
      this.sortDirection = 'asc';
    }
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
}
