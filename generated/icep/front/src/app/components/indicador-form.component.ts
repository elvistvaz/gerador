import { Component, OnInit, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { IndicadorRequest, IndicadorResponse } from '../models/indicador.model';
import { IndicadorService } from '../services/indicador.service';
import { AmbitoGestaoList } from '../models/ambitoGestao.model';
import { AmbitoGestaoService } from '../services/ambitoGestao.service';

@Component({
  selector: 'app-indicador-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './indicador-form.component.html',
  styleUrls: ['./indicador-form.component.css']
})
export class IndicadorFormComponent implements OnInit {

  item: IndicadorRequest = {} as IndicadorRequest;
  isEditing: boolean = false;
  loading: boolean = false;
  saving: boolean = false;
  error: string | null = null;
  successMessage: string | null = null;
  private id: any = null;
  private returnQueryParams: { [key: string]: any } = {};

  ambitoGestaoOptions: AmbitoGestaoList[] = [];

  ambitoGestaoIdDropdownOpen: boolean = false;
  ambitoGestaoIdSearchTerm: string = '';

  constructor(
    private service: IndicadorService,
    private ambitoGestaoService: AmbitoGestaoService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadAmbitoGestaoOptions();
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

  loadAmbitoGestaoOptions(): void {
    this.ambitoGestaoService.findAll(0, 1000).subscribe({
      next: (data) => this.ambitoGestaoOptions = data.content,
      error: (err) => console.error('Erro ao carregar ambitoGestao:', err)
    });
  }

  loadItem(): void {
    this.loading = true;
    this.service.findById(this.id).subscribe({
      next: (data) => {
        this.item = data as IndicadorRequest;
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
      this.router.navigate(['/indicador'], { queryParams: this.returnQueryParams });
    } else {
      this.router.navigate(['/indicador']);
    }
  }

  toggleAmbitoGestaoIdDropdown(): void {
    this.ambitoGestaoIdDropdownOpen = !this.ambitoGestaoIdDropdownOpen;
    if (this.ambitoGestaoIdDropdownOpen) {
      this.ambitoGestaoIdSearchTerm = '';
    }
  }

  selectAmbitoGestaoId(value: any): void {
    this.item.ambitoGestaoId = value;
    this.ambitoGestaoIdDropdownOpen = false;
  }

  getSelectedAmbitoGestaoIdText(): string {
    if (this.item.ambitoGestaoId === null || this.item.ambitoGestaoId === undefined) {
      return 'Selecione...';
    }
    const selected = this.ambitoGestaoOptions.find(opt => opt.id === this.item.ambitoGestaoId);
    return selected ? String(selected.nome) : 'Selecione...';
  }

  filteredAmbitoGestaoIdOptions(): any[] {
    if (!this.ambitoGestaoIdSearchTerm) {
      return this.ambitoGestaoOptions;
    }
    const term = this.ambitoGestaoIdSearchTerm.toLowerCase();
    return this.ambitoGestaoOptions.filter(opt => 
      String(opt.nome).toLowerCase().includes(term)
    );
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.searchable-select')) {
      this.ambitoGestaoIdDropdownOpen = false;
    }
  }
}
