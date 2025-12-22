import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UsuarioRequest } from '../models/usuario.model';
import { Perfil } from '../models/perfil.model';
import { UsuarioService } from '../services/usuario.service';

@Component({
  selector: 'app-usuario-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuario-form.component.html',
  styleUrls: ['./usuario-form.component.css']
})
export class UsuarioFormComponent implements OnInit {

  id: number | null = null;
  isEditing: boolean = false;
  loading: boolean = false;
  saving: boolean = false;
  error: string | null = null;

  perfis: Perfil[] = [];

  form: UsuarioRequest = {
    username: '',
    senha: '',
    nome: '',
    email: '',
    ativo: true,
    perfilId: ''
  };

  constructor(
    private service: UsuarioService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadPerfis();
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.id = +idParam;
      this.isEditing = true;
      this.loadData();
    }
  }

  loadPerfis(): void {
    this.service.getPerfis().subscribe({
      next: (data) => this.perfis = data,
      error: (err) => console.error('Erro ao carregar perfis:', err)
    });
  }

  loadData(): void {
    if (!this.id) return;
    this.loading = true;
    this.service.findById(this.id).subscribe({
      next: (data) => {
        this.form = {
          username: data.username,
          senha: '',
          nome: data.nome,
          email: data.email || '',
          ativo: data.ativo,
          perfilId: data.perfilId
        };
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erro ao carregar usuário';
        this.loading = false;
        console.error(err);
      }
    });
  }

  save(): void {
    this.saving = true;
    this.error = null;

    const request = { ...this.form };
    if (!request.senha) {
      delete request.senha;
    }

    const operation = this.isEditing && this.id
      ? this.service.update(this.id, request)
      : this.service.create(request);

    operation.subscribe({
      next: () => {
        this.router.navigate(['/usuario']);
      },
      error: (err) => {
        this.error = err.error?.message || 'Erro ao salvar usuário';
        this.saving = false;
        console.error(err);
      }
    });
  }

  cancel(): void {
    this.router.navigate(['/usuario']);
  }
}
