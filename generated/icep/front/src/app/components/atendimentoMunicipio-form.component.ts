import { Component, OnInit, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AtendimentoMunicipioRequest, AtendimentoMunicipioResponse } from '../models/atendimentoMunicipio.model';
import { AtendimentoMunicipioService } from '../services/atendimentoMunicipio.service';
import { SessionService } from '../services/session.service';
import { AvaliacaoList } from '../models/avaliacao.model';
import { AvaliacaoService } from '../services/avaliacao.service';
import { MunicipioList } from '../models/municipio.model';
import { MunicipioService } from '../services/municipio.service';
import { SegmentoAtendidoList } from '../models/segmentoAtendido.model';
import { SegmentoAtendidoService } from '../services/segmentoAtendido.service';

@Component({
  selector: 'app-atendimentoMunicipio-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './atendimentoMunicipio-form.component.html',
  styleUrls: ['./atendimentoMunicipio-form.component.css']
})
export class AtendimentoMunicipioFormComponent implements OnInit {

  item: AtendimentoMunicipioRequest = {} as AtendimentoMunicipioRequest;
  isEditing: boolean = false;
  loading: boolean = false;
  saving: boolean = false;
  error: string | null = null;
  successMessage: string | null = null;
  private id: any = null;
  private returnQueryParams: { [key: string]: any } = {};

  avaliacaoOptions: AvaliacaoList[] = [];
  municipioOptions: MunicipioList[] = [];
  segmentoAtendidoOptions: SegmentoAtendidoList[] = [];

  avaliacaoIdDropdownOpen: boolean = false;
  avaliacaoIdSearchTerm: string = '';
  municipioIdDropdownOpen: boolean = false;
  municipioIdSearchTerm: string = '';
  segmentoAtendidoIdDropdownOpen: boolean = false;
  segmentoAtendidoIdSearchTerm: string = '';

  constructor(
    private service: AtendimentoMunicipioService,
    private avaliacaoService: AvaliacaoService,
    private municipioService: MunicipioService,
    private segmentoAtendidoService: SegmentoAtendidoService,
    private sessionService: SessionService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadAvaliacaoOptions();
    this.loadMunicipioOptions();
    this.loadSegmentoAtendidoOptions();
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

  loadSegmentoAtendidoOptions(): void {
    this.segmentoAtendidoService.findAll(0, 1000).subscribe({
      next: (data) => this.segmentoAtendidoOptions = data.content,
      error: (err) => console.error('Erro ao carregar segmentoAtendido:', err)
    });
  }

  loadItem(): void {
    this.loading = true;
    this.service.findById(this.id).subscribe({
      next: (data) => {
        this.item = data as AtendimentoMunicipioRequest;
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
      this.router.navigate(['/atendimentoMunicipio'], { queryParams: this.returnQueryParams });
    } else {
      this.router.navigate(['/atendimentoMunicipio']);
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

  toggleSegmentoAtendidoIdDropdown(): void {
    this.segmentoAtendidoIdDropdownOpen = !this.segmentoAtendidoIdDropdownOpen;
    if (this.segmentoAtendidoIdDropdownOpen) {
      this.segmentoAtendidoIdSearchTerm = '';
    }
  }

  selectSegmentoAtendidoId(value: any): void {
    this.item.segmentoAtendidoId = value;
    this.segmentoAtendidoIdDropdownOpen = false;
  }

  getSelectedSegmentoAtendidoIdText(): string {
    if (this.item.segmentoAtendidoId === null || this.item.segmentoAtendidoId === undefined) {
      return 'Selecione...';
    }
    const selected = this.segmentoAtendidoOptions.find(opt => opt.id === this.item.segmentoAtendidoId);
    return selected ? String(selected.nome) : 'Selecione...';
  }

  filteredSegmentoAtendidoIdOptions(): any[] {
    if (!this.segmentoAtendidoIdSearchTerm) {
      return this.segmentoAtendidoOptions;
    }
    const term = this.segmentoAtendidoIdSearchTerm.toLowerCase();
    return this.segmentoAtendidoOptions.filter(opt => 
      String(opt.nome).toLowerCase().includes(term)
    );
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.searchable-select')) {
      this.avaliacaoIdDropdownOpen = false;
      this.municipioIdDropdownOpen = false;
      this.segmentoAtendidoIdDropdownOpen = false;
    }
  }
}
