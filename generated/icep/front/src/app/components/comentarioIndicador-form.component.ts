import { Component, OnInit, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ComentarioIndicadorRequest, ComentarioIndicadorResponse } from '../models/comentarioIndicador.model';
import { ComentarioIndicadorService } from '../services/comentarioIndicador.service';
import { SessionService } from '../services/session.service';
import { AvaliacaoList } from '../models/avaliacao.model';
import { AvaliacaoService } from '../services/avaliacao.service';
import { MunicipioList } from '../models/municipio.model';
import { MunicipioService } from '../services/municipio.service';
import { AmbitoGestaoList } from '../models/ambitoGestao.model';
import { AmbitoGestaoService } from '../services/ambitoGestao.service';

@Component({
  selector: 'app-comentarioIndicador-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './comentarioIndicador-form.component.html',
  styleUrls: ['./comentarioIndicador-form.component.css']
})
export class ComentarioIndicadorFormComponent implements OnInit {

  item: ComentarioIndicadorRequest = {} as ComentarioIndicadorRequest;
  isEditing: boolean = false;
  loading: boolean = false;
  saving: boolean = false;
  error: string | null = null;
  successMessage: string | null = null;
  private id: any = null;
  private returnQueryParams: { [key: string]: any } = {};

  avaliacaoOptions: AvaliacaoList[] = [];
  municipioOptions: MunicipioList[] = [];
  ambitoGestaoOptions: AmbitoGestaoList[] = [];

  avaliacaoIdDropdownOpen: boolean = false;
  avaliacaoIdSearchTerm: string = '';
  municipioIdDropdownOpen: boolean = false;
  municipioIdSearchTerm: string = '';
  ambitoGestaoIdDropdownOpen: boolean = false;
  ambitoGestaoIdSearchTerm: string = '';

  constructor(
    private service: ComentarioIndicadorService,
    private avaliacaoService: AvaliacaoService,
    private municipioService: MunicipioService,
    private ambitoGestaoService: AmbitoGestaoService,
    private sessionService: SessionService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadAvaliacaoOptions();
    this.loadMunicipioOptions();
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
      // Auto-preencher com o valor da sessão
      const sessionValue = this.sessionService.getMunicipioId();
      if (sessionValue) {
        this.item.municipioId = sessionValue;
      }
    }
  }

  loadAvaliacaoOptions(): void {
    this.avaliacaoService.findAll(0, 1000).subscribe({
      next: (data) => this.avaliacaoOptions = data.content,
      error: (err) => console.error('Erro ao carregar avaliacao:', err)
    });
  }

  loadMunicipioOptions(): void {
    this.municipioService.findAll(0, 1000).subscribe({
      next: (data) => this.municipioOptions = data.content,
      error: (err) => console.error('Erro ao carregar municipio:', err)
    });
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
        this.item = data as ComentarioIndicadorRequest;
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
      this.router.navigate(['/comentarioIndicador'], { queryParams: this.returnQueryParams });
    } else {
      this.router.navigate(['/comentarioIndicador']);
    }
  }

  toggleAvaliacaoIdDropdown(): void {
    this.avaliacaoIdDropdownOpen = !this.avaliacaoIdDropdownOpen;
    if (this.avaliacaoIdDropdownOpen) {
      this.avaliacaoIdSearchTerm = '';
    }
  }

  selectAvaliacaoId(value: any): void {
    this.item.avaliacaoId = value;
    this.avaliacaoIdDropdownOpen = false;
  }

  getSelectedAvaliacaoIdText(): string {
    if (this.item.avaliacaoId === null || this.item.avaliacaoId === undefined) {
      return 'Selecione...';
    }
    const selected = this.avaliacaoOptions.find(opt => opt.id === this.item.avaliacaoId);
    return selected ? String(selected.avaliacao) : 'Selecione...';
  }

  filteredAvaliacaoIdOptions(): any[] {
    if (!this.avaliacaoIdSearchTerm) {
      return this.avaliacaoOptions;
    }
    const term = this.avaliacaoIdSearchTerm.toLowerCase();
    return this.avaliacaoOptions.filter(opt => 
      String(opt.avaliacao).toLowerCase().includes(term)
    );
  }

  toggleMunicipioIdDropdown(): void {
    this.municipioIdDropdownOpen = !this.municipioIdDropdownOpen;
    if (this.municipioIdDropdownOpen) {
      this.municipioIdSearchTerm = '';
    }
  }

  selectMunicipioId(value: any): void {
    this.item.municipioId = value;
    this.municipioIdDropdownOpen = false;
  }

  getSelectedMunicipioIdText(): string {
    if (this.item.municipioId === null || this.item.municipioId === undefined) {
      return 'Selecione...';
    }
    const selected = this.municipioOptions.find(opt => opt.id === this.item.municipioId);
    return selected ? String(selected.nome) : 'Selecione...';
  }

  filteredMunicipioIdOptions(): any[] {
    if (!this.municipioIdSearchTerm) {
      return this.municipioOptions;
    }
    const term = this.municipioIdSearchTerm.toLowerCase();
    return this.municipioOptions.filter(opt => 
      String(opt.nome).toLowerCase().includes(term)
    );
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
      this.avaliacaoIdDropdownOpen = false;
      this.municipioIdDropdownOpen = false;
      this.ambitoGestaoIdDropdownOpen = false;
    }
  }
}
