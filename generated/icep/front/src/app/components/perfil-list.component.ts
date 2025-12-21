import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Perfil } from '../models/usuario.model';
import { UsuarioService } from '../services/usuario.service';

@Component({
  selector: 'app-perfil-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './perfil-list.component.html',
  styleUrls: ['./perfil-list.component.css']
})
export class PerfilListComponent implements OnInit {

  items: Perfil[] = [];
  loading: boolean = false;
  error: string | null = null;
  expandedPerfil: string | null = null;

  constructor(private service: UsuarioService) {}

  ngOnInit(): void {
    this.loadItems();
  }

  loadItems(): void {
    this.loading = true;
    this.error = null;
    this.service.getPerfis().subscribe({
      next: (data) => {
        this.items = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erro ao carregar dados';
        this.loading = false;
        console.error(err);
      }
    });
  }

  toggleExpand(perfilId: string): void {
    this.expandedPerfil = this.expandedPerfil === perfilId ? null : perfilId;
  }

  formatPermission(permission: string): string {
    const parts = permission.split(':');
    if (parts.length !== 2) return permission;
    const module = parts[0];
    const access = parts[1];
    const accessLabel = access === 'full' ? 'Acesso Total' : 
                        access === 'readonly' ? 'Somente Leitura' : access;
    return `${module} - ${accessLabel}`;
  }
}
