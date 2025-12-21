import { Component, OnInit, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MunicipioRequest, MunicipioResponse } from '../models/municipio.model';
import { MunicipioService } from '../services/municipio.service';
import { TerritorioList } from '../models/territorio.model';
import { TerritorioService } from '../services/territorio.service';

@Component({
  selector: 'app-municipio-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './municipio-form.component.html',
  styleUrls: ['./municipio-form.component.css']
})
export class MunicipioFormComponent implements OnInit {

  item: MunicipioRequest = {} as MunicipioRequest;
  isEditing: boolean = false;
  loading: boolean = false;
  saving: boolean = false;
  error: string | null = null;
  successMessage: string | null = null;
  private id: any = null;
  private returnQueryParams: { [key: string]: any } = {};

  territorioOptions: TerritorioList[] = [];

  territorioIdDropdownOpen: boolean = false;
  territorioIdSearchTerm: string = '';

  constructor(
    private service: MunicipioService,
    private territorioService: TerritorioService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadTerritorioOptions();
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditing = true;
      this.id = Number(idParam);
      this.loadItem();
    } else {
      // Pré-selecionar campos FK vindos de navegação mestre-detalhe
      this.route.queryParams.subscribe(params => {
        this.returnQueryParams = { ...params };
        for (const key of Object.keys(params)) {
          if ((this.item as any).hasOwnProperty(key) || key.endsWith('Id')) {
            (this.item as any)[key] = +params[key];
          }
        }
      });
    }
  }

  loadTerritorioOptions(): void {
    this.territorioService.findAll(0, 1000).subscribe({
      next: (data) => this.territorioOptions = data.content,
      error: (err) => console.error('Erro ao carregar territorio:', err)
    });
  }

  loadItem(): void {
    this.loading = true;
    this.service.findById(this.id).subscribe({
      next: (data) => {
        this.item = data as MunicipioRequest;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erro ao carregar registro';
        this.loading = false;
        console.error(err);
      }
    });
  }

  salvar(): void {
    this.saving = true;
    this.error = null;
    this.successMessage = null;

    const operation = this.isEditing
      ? this.service.update(this.id, this.item)
      : this.service.create(this.item);

    operation.subscribe({
      next: () => {
        this.successMessage = this.isEditing ? 'Registro atualizado com sucesso!' : 'Registro criado com sucesso!';
        setTimeout(() => {
          this.navegarParaLista();
        }, 1500);
      },
      error: (err) => {
        this.error = err.error?.message || 'Erro ao salvar registro';
        this.saving = false;
        console.error(err);
      }
    });
  }

  cancelar(): void {
    this.navegarParaLista();
  }

  private navegarParaLista(): void {
    if (Object.keys(this.returnQueryParams).length > 0) {
      this.router.navigate(['/municipio'], { queryParams: this.returnQueryParams });
    } else {
      this.router.navigate(['/municipio']);
    }
  }

  toggleTerritorioIdDropdown(): void {
    this.territorioIdDropdownOpen = !this.territorioIdDropdownOpen;
    if (this.territorioIdDropdownOpen) {
      this.territorioIdSearchTerm = '';
    }
  }

  selectTerritorioId(value: any): void {
    this.item.territorioId = value;
    this.territorioIdDropdownOpen = false;
  }

  getSelectedTerritorioIdText(): string {
    if (this.item.territorioId === null || this.item.territorioId === undefined) {
      return 'Selecione...';
    }
    const selected = this.territorioOptions.find(opt => opt.id === this.item.territorioId);
    return selected ? String(selected.nome) : 'Selecione...';
  }

  filteredTerritorioIdOptions(): any[] {
    if (!this.territorioIdSearchTerm) {
      return this.territorioOptions;
    }
    const term = this.territorioIdSearchTerm.toLowerCase();
    return this.territorioOptions.filter(opt => 
      String(opt.nome).toLowerCase().includes(term)
    );
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.searchable-select')) {
      this.territorioIdDropdownOpen = false;
    }
  }
}
