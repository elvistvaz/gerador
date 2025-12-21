import { Component, OnInit, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AvaliacaoSDARequest, AvaliacaoSDAResponse } from '../models/avaliacaoSDA.model';
import { AvaliacaoSDAService } from '../services/avaliacaoSDA.service';
import { SessionService } from '../services/session.service';
import { AvaliacaoList } from '../models/avaliacao.model';
import { AvaliacaoService } from '../services/avaliacao.service';
import { MunicipioList } from '../models/municipio.model';
import { MunicipioService } from '../services/municipio.service';
import { AnoEscolarList } from '../models/anoEscolar.model';
import { AnoEscolarService } from '../services/anoEscolar.service';
import { AprendizagemEsperadaList } from '../models/aprendizagemEsperada.model';
import { AprendizagemEsperadaService } from '../services/aprendizagemEsperada.service';

@Component({
  selector: 'app-avaliacaoSDA-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './avaliacaoSDA-form.component.html',
  styleUrls: ['./avaliacaoSDA-form.component.css']
})
export class AvaliacaoSDAFormComponent implements OnInit {

  item: AvaliacaoSDARequest = {} as AvaliacaoSDARequest;
  isEditing: boolean = false;
  loading: boolean = false;
  saving: boolean = false;
  error: string | null = null;
  successMessage: string | null = null;
  private id: any = null;
  private returnQueryParams: { [key: string]: any } = {};

  avaliacaoOptions: AvaliacaoList[] = [];
  municipioOptions: MunicipioList[] = [];
  anoEscolarOptions: AnoEscolarList[] = [];
  aprendizagemEsperadaOptions: AprendizagemEsperadaList[] = [];

  avaliacaoIdDropdownOpen: boolean = false;
  avaliacaoIdSearchTerm: string = '';
  municipioIdDropdownOpen: boolean = false;
  municipioIdSearchTerm: string = '';
  anoEscolarIdDropdownOpen: boolean = false;
  anoEscolarIdSearchTerm: string = '';
  aprendizagemIdDropdownOpen: boolean = false;
  aprendizagemIdSearchTerm: string = '';

  constructor(
    private service: AvaliacaoSDAService,
    private avaliacaoService: AvaliacaoService,
    private municipioService: MunicipioService,
    private anoEscolarService: AnoEscolarService,
    private aprendizagemEsperadaService: AprendizagemEsperadaService,
    private sessionService: SessionService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadAvaliacaoOptions();
    this.loadMunicipioOptions();
    this.loadAnoEscolarOptions();
    this.loadAprendizagemEsperadaOptions();
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

  loadAnoEscolarOptions(): void {
    this.anoEscolarService.findAll(0, 1000).subscribe({
      next: (data) => this.anoEscolarOptions = data.content,
      error: (err) => console.error('Erro ao carregar anoEscolar:', err)
    });
  }

  loadAprendizagemEsperadaOptions(): void {
    this.aprendizagemEsperadaService.findAll(0, 1000).subscribe({
      next: (data) => this.aprendizagemEsperadaOptions = data.content,
      error: (err) => console.error('Erro ao carregar aprendizagemEsperada:', err)
    });
  }

  loadItem(): void {
    this.loading = true;
    this.service.findById(this.id).subscribe({
      next: (data) => {
        this.item = data as AvaliacaoSDARequest;
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
      this.router.navigate(['/avaliacaoSDA'], { queryParams: this.returnQueryParams });
    } else {
      this.router.navigate(['/avaliacaoSDA']);
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

  toggleAnoEscolarIdDropdown(): void {
    this.anoEscolarIdDropdownOpen = !this.anoEscolarIdDropdownOpen;
    if (this.anoEscolarIdDropdownOpen) {
      this.anoEscolarIdSearchTerm = '';
    }
  }

  selectAnoEscolarId(value: any): void {
    this.item.anoEscolarId = value;
    this.anoEscolarIdDropdownOpen = false;
  }

  getSelectedAnoEscolarIdText(): string {
    if (this.item.anoEscolarId === null || this.item.anoEscolarId === undefined) {
      return 'Selecione...';
    }
    const selected = this.anoEscolarOptions.find(opt => opt.id === this.item.anoEscolarId);
    return selected ? String(selected.nome) : 'Selecione...';
  }

  filteredAnoEscolarIdOptions(): any[] {
    if (!this.anoEscolarIdSearchTerm) {
      return this.anoEscolarOptions;
    }
    const term = this.anoEscolarIdSearchTerm.toLowerCase();
    return this.anoEscolarOptions.filter(opt => 
      String(opt.nome).toLowerCase().includes(term)
    );
  }

  toggleAprendizagemIdDropdown(): void {
    this.aprendizagemIdDropdownOpen = !this.aprendizagemIdDropdownOpen;
    if (this.aprendizagemIdDropdownOpen) {
      this.aprendizagemIdSearchTerm = '';
    }
  }

  selectAprendizagemId(value: any): void {
    this.item.aprendizagemId = value;
    this.aprendizagemIdDropdownOpen = false;
  }

  getSelectedAprendizagemIdText(): string {
    if (this.item.aprendizagemId === null || this.item.aprendizagemId === undefined) {
      return 'Selecione...';
    }
    const selected = this.aprendizagemEsperadaOptions.find(opt => opt.id === this.item.aprendizagemId);
    return selected ? String(selected.descricao) : 'Selecione...';
  }

  filteredAprendizagemIdOptions(): any[] {
    if (!this.aprendizagemIdSearchTerm) {
      return this.aprendizagemEsperadaOptions;
    }
    const term = this.aprendizagemIdSearchTerm.toLowerCase();
    return this.aprendizagemEsperadaOptions.filter(opt => 
      String(opt.descricao).toLowerCase().includes(term)
    );
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.searchable-select')) {
      this.avaliacaoIdDropdownOpen = false;
      this.municipioIdDropdownOpen = false;
      this.anoEscolarIdDropdownOpen = false;
      this.aprendizagemIdDropdownOpen = false;
    }
  }
}
