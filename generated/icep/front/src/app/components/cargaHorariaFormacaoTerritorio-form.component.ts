import { Component, OnInit, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { CargaHorariaFormacaoTerritorioRequest, CargaHorariaFormacaoTerritorioResponse } from '../models/cargaHorariaFormacaoTerritorio.model';
import { CargaHorariaFormacaoTerritorioService } from '../services/cargaHorariaFormacaoTerritorio.service';
import { SessionService } from '../services/session.service';
import { AvaliacaoList } from '../models/avaliacao.model';
import { AvaliacaoService } from '../services/avaliacao.service';
import { TerritorioList } from '../models/territorio.model';
import { TerritorioService } from '../services/territorio.service';
import { FormacaoTerritorioList } from '../models/formacaoTerritorio.model';
import { FormacaoTerritorioService } from '../services/formacaoTerritorio.service';

@Component({
  selector: 'app-cargaHorariaFormacaoTerritorio-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cargaHorariaFormacaoTerritorio-form.component.html',
  styleUrls: ['./cargaHorariaFormacaoTerritorio-form.component.css']
})
export class CargaHorariaFormacaoTerritorioFormComponent implements OnInit {

  item: CargaHorariaFormacaoTerritorioRequest = {} as CargaHorariaFormacaoTerritorioRequest;
  isEditing: boolean = false;
  loading: boolean = false;
  saving: boolean = false;
  error: string | null = null;
  successMessage: string | null = null;
  private id: any = null;
  private returnQueryParams: { [key: string]: any } = {};

  avaliacaoOptions: AvaliacaoList[] = [];
  territorioOptions: TerritorioList[] = [];
  formacaoTerritorioOptions: FormacaoTerritorioList[] = [];

  avaliacaoIdDropdownOpen: boolean = false;
  avaliacaoIdSearchTerm: string = '';
  territorioIdDropdownOpen: boolean = false;
  territorioIdSearchTerm: string = '';
  formacaoTerritorioIdDropdownOpen: boolean = false;
  formacaoTerritorioIdSearchTerm: string = '';

  constructor(
    private service: CargaHorariaFormacaoTerritorioService,
    private avaliacaoService: AvaliacaoService,
    private territorioService: TerritorioService,
    private formacaoTerritorioService: FormacaoTerritorioService,
    private sessionService: SessionService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadAvaliacaoOptions();
    this.loadTerritorioOptions();
    this.loadFormacaoTerritorioOptions();
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
      const sessionValue = this.sessionService.getAvaliacaoId();
      if (sessionValue) {
        this.item.avaliacaoId = sessionValue;
      }
    }
  }

  loadAvaliacaoOptions(): void {
    this.avaliacaoService.findAll(0, 1000).subscribe({
      next: (data) => this.avaliacaoOptions = data.content,
      error: (err) => console.error('Erro ao carregar avaliacao:', err)
    });
  }

  loadTerritorioOptions(): void {
    this.territorioService.findAll(0, 1000).subscribe({
      next: (data) => this.territorioOptions = data.content,
      error: (err) => console.error('Erro ao carregar territorio:', err)
    });
  }

  loadFormacaoTerritorioOptions(): void {
    this.formacaoTerritorioService.findAll(0, 1000).subscribe({
      next: (data) => this.formacaoTerritorioOptions = data.content,
      error: (err) => console.error('Erro ao carregar formacaoTerritorio:', err)
    });
  }

  loadItem(): void {
    this.loading = true;
    this.service.findById(this.id).subscribe({
      next: (data) => {
        this.item = data as CargaHorariaFormacaoTerritorioRequest;
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
      this.router.navigate(['/cargaHorariaFormacaoTerritorio'], { queryParams: this.returnQueryParams });
    } else {
      this.router.navigate(['/cargaHorariaFormacaoTerritorio']);
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

  toggleFormacaoTerritorioIdDropdown(): void {
    this.formacaoTerritorioIdDropdownOpen = !this.formacaoTerritorioIdDropdownOpen;
    if (this.formacaoTerritorioIdDropdownOpen) {
      this.formacaoTerritorioIdSearchTerm = '';
    }
  }

  selectFormacaoTerritorioId(value: any): void {
    this.item.formacaoTerritorioId = value;
    this.formacaoTerritorioIdDropdownOpen = false;
  }

  getSelectedFormacaoTerritorioIdText(): string {
    if (this.item.formacaoTerritorioId === null || this.item.formacaoTerritorioId === undefined) {
      return 'Selecione...';
    }
    const selected = this.formacaoTerritorioOptions.find(opt => opt.id === this.item.formacaoTerritorioId);
    return selected ? String(selected.nome) : 'Selecione...';
  }

  filteredFormacaoTerritorioIdOptions(): any[] {
    if (!this.formacaoTerritorioIdSearchTerm) {
      return this.formacaoTerritorioOptions;
    }
    const term = this.formacaoTerritorioIdSearchTerm.toLowerCase();
    return this.formacaoTerritorioOptions.filter(opt => 
      String(opt.nome).toLowerCase().includes(term)
    );
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.searchable-select')) {
      this.avaliacaoIdDropdownOpen = false;
      this.territorioIdDropdownOpen = false;
      this.formacaoTerritorioIdDropdownOpen = false;
    }
  }
}
