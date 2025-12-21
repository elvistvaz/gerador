import { Component, OnInit, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { SegmentoAtendidoRequest, SegmentoAtendidoResponse } from '../models/segmentoAtendido.model';
import { SegmentoAtendidoService } from '../services/segmentoAtendido.service';
import { PublicoAlvoList } from '../models/publicoAlvo.model';
import { PublicoAlvoService } from '../services/publicoAlvo.service';

@Component({
  selector: 'app-segmentoAtendido-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './segmentoAtendido-form.component.html',
  styleUrls: ['./segmentoAtendido-form.component.css']
})
export class SegmentoAtendidoFormComponent implements OnInit {

  item: SegmentoAtendidoRequest = {} as SegmentoAtendidoRequest;
  isEditing: boolean = false;
  loading: boolean = false;
  saving: boolean = false;
  error: string | null = null;
  successMessage: string | null = null;
  private id: any = null;
  private returnQueryParams: { [key: string]: any } = {};

  publicoAlvoOptions: PublicoAlvoList[] = [];

  publicoAlvoIdDropdownOpen: boolean = false;
  publicoAlvoIdSearchTerm: string = '';

  constructor(
    private service: SegmentoAtendidoService,
    private publicoAlvoService: PublicoAlvoService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadPublicoAlvoOptions();
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

  loadPublicoAlvoOptions(): void {
    this.publicoAlvoService.findAll(0, 1000).subscribe({
      next: (data) => this.publicoAlvoOptions = data.content,
      error: (err) => console.error('Erro ao carregar publicoAlvo:', err)
    });
  }

  loadItem(): void {
    this.loading = true;
    this.service.findById(this.id).subscribe({
      next: (data) => {
        this.item = data as SegmentoAtendidoRequest;
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
      this.router.navigate(['/segmentoAtendido'], { queryParams: this.returnQueryParams });
    } else {
      this.router.navigate(['/segmentoAtendido']);
    }
  }

  togglePublicoAlvoIdDropdown(): void {
    this.publicoAlvoIdDropdownOpen = !this.publicoAlvoIdDropdownOpen;
    if (this.publicoAlvoIdDropdownOpen) {
      this.publicoAlvoIdSearchTerm = '';
    }
  }

  selectPublicoAlvoId(value: any): void {
    this.item.publicoAlvoId = value;
    this.publicoAlvoIdDropdownOpen = false;
  }

  getSelectedPublicoAlvoIdText(): string {
    if (this.item.publicoAlvoId === null || this.item.publicoAlvoId === undefined) {
      return 'Selecione...';
    }
    const selected = this.publicoAlvoOptions.find(opt => opt.id === this.item.publicoAlvoId);
    return selected ? String(selected.nome) : 'Selecione...';
  }

  filteredPublicoAlvoIdOptions(): any[] {
    if (!this.publicoAlvoIdSearchTerm) {
      return this.publicoAlvoOptions;
    }
    const term = this.publicoAlvoIdSearchTerm.toLowerCase();
    return this.publicoAlvoOptions.filter(opt => 
      String(opt.nome).toLowerCase().includes(term)
    );
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.searchable-select')) {
      this.publicoAlvoIdDropdownOpen = false;
    }
  }
}
