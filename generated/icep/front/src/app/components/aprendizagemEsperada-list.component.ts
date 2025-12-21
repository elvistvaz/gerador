import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AprendizagemEsperadaList } from '../models/aprendizagemEsperada.model';
import { AprendizagemEsperadaService } from '../services/aprendizagemEsperada.service';
import { ComponenteCurricularList } from '../models/componenteCurricular.model';
import { ComponenteCurricularService } from '../services/componenteCurricular.service';
import { ConceitoAprendidoList } from '../models/conceitoAprendido.model';
import { ConceitoAprendidoService } from '../services/conceitoAprendido.service';

@Component({
  selector: 'app-aprendizagemEsperada-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './aprendizagemEsperada-list.component.html',
  styleUrls: ['./aprendizagemEsperada-list.component.css']
})
export class AprendizagemEsperadaListComponent implements OnInit {

  items: AprendizagemEsperadaList[] = [];
  filteredItems: AprendizagemEsperadaList[] = [];
  searchTerm: string = '';
  loading: boolean = false;
  error: string | null = null;
  successMessage: string | null = null;
  parentFilter: { [key: string]: number } = {};
  parentFilterKey: string | null = null;
  parentName: string | null = null;

  // Paginação
  currentPage: number = 0;
  pageSize: number = 20;
  totalElements: number = 0;
  totalPages: number = 0;

  // Ordenação
  sortField: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  componenteCurricularMap: Map<number, string> = new Map();
  conceitoAprendidoMap: Map<number, string> = new Map();

  constructor(
    private service: AprendizagemEsperadaService,
    private componenteCurricularService: ComponenteCurricularService,
    private conceitoAprendidoService: ConceitoAprendidoService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.parentFilter = {};
      this.parentFilterKey = null;
      this.parentName = null;
      for (const key of Object.keys(params)) {
        this.parentFilter[key] = +params[key];
        this.parentFilterKey = key;
      }
      this.loadComponenteCurricular();
      this.loadConceitoAprendido();
      this.loadParentName();
      this.loadItems();
    });
  }

  loadComponenteCurricular(): void {
    this.componenteCurricularService.findAll(0, 10000).subscribe({
      next: (data) => {
        data.content.forEach((item: ComponenteCurricularList) => {
          this.componenteCurricularMap.set(item.id, String(item.nome));
        });
      },
      error: (err) => console.error('Erro ao carregar componenteCurricular:', err)
    });
  }

  loadConceitoAprendido(): void {
    this.conceitoAprendidoService.findAll(0, 10000).subscribe({
      next: (data) => {
        data.content.forEach((item: ConceitoAprendidoList) => {
          this.conceitoAprendidoMap.set(item.id, String(item.nome));
        });
      },
      error: (err) => console.error('Erro ao carregar conceitoAprendido:', err)
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
        case 'componenteCurricularId':
          this.parentName = this.componenteCurricularMap.get(parentId) || null;
          break;
        case 'conceitoAprendidoId':
          this.parentName = this.conceitoAprendidoMap.get(parentId) || null;
          break;
      }
    }, 500);
  }

  loadItems(): void {
    this.loading = true;
    this.error = null;
    const sort = this.sortField ? `${this.sortField},${this.sortDirection}` : undefined;
    this.service.findAll(this.currentPage, this.pageSize, sort).subscribe({
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
      this.router.navigate(['/aprendizagemEsperada/novo'], { queryParams: this.parentFilter });
    } else {
      this.router.navigate(['/aprendizagemEsperada/novo']);
    }
  }

  editar(id: number): void {
    this.router.navigate(['/aprendizagemEsperada/editar', id]);
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
