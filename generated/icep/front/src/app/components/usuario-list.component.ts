import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UsuarioList } from '../models/usuario.model';
import { UsuarioService } from '../services/usuario.service';

@Component({
  selector: 'app-usuario-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuario-list.component.html',
  styleUrls: ['./usuario-list.component.css']
})
export class UsuarioListComponent implements OnInit {

  items: UsuarioList[] = [];
  filteredItems: UsuarioList[] = [];
  searchTerm: string = '';
  loading: boolean = false;
  error: string | null = null;
  successMessage: string | null = null;

  // Paginação
  currentPage: number = 0;
  pageSize: number = 20;
  totalElements: number = 0;
  totalPages: number = 0;

  // Ordenação
  sortField: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  constructor(
    private service: UsuarioService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadItems();
  }

  loadItems(): void {
    this.loading = true;
    this.error = null;
    const sort = this.sortField ? `${this.sortField},${this.sortDirection}` : undefined;
    this.service.findAll(this.currentPage, this.pageSize, sort).subscribe({
      next: (data) => {
        this.items = data.content;
        this.filteredItems = data.content;
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
      item.username.toLowerCase().includes(term) ||
      item.nome.toLowerCase().includes(term) ||
      (item.email && item.email.toLowerCase().includes(term)) ||
      (item.perfilNome && item.perfilNome.toLowerCase().includes(term))
    );
  }

  novo(): void {
    this.router.navigate(['/usuario/novo']);
  }

  editar(id: number): void {
    this.router.navigate(['/usuario/editar', id]);
  }

  delete(id: number): void {
    if (!confirm('Deseja realmente excluir este usuário?')) {
      return;
    }
    this.error = null;
    this.successMessage = null;
    this.service.delete(id).subscribe({
      next: () => {
        this.successMessage = 'Usuário excluído com sucesso!';
        this.loadItems();
        setTimeout(() => this.successMessage = null, 3000);
      },
      error: (err) => {
        this.error = err.error?.message || 'Erro ao excluir usuário';
        console.error(err);
        setTimeout(() => this.error = null, 5000);
      }
    });
  }

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
}
