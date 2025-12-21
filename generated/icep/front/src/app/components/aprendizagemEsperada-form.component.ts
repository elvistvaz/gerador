import { Component, OnInit, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AprendizagemEsperadaRequest, AprendizagemEsperadaResponse } from '../models/aprendizagemEsperada.model';
import { AprendizagemEsperadaService } from '../services/aprendizagemEsperada.service';
import { ComponenteCurricularList } from '../models/componenteCurricular.model';
import { ComponenteCurricularService } from '../services/componenteCurricular.service';
import { ConceitoAprendidoList } from '../models/conceitoAprendido.model';
import { ConceitoAprendidoService } from '../services/conceitoAprendido.service';

@Component({
  selector: 'app-aprendizagemEsperada-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './aprendizagemEsperada-form.component.html',
  styleUrls: ['./aprendizagemEsperada-form.component.css']
})
export class AprendizagemEsperadaFormComponent implements OnInit {

  item: AprendizagemEsperadaRequest = {} as AprendizagemEsperadaRequest;
  isEditing: boolean = false;
  loading: boolean = false;
  saving: boolean = false;
  error: string | null = null;
  successMessage: string | null = null;
  private id: any = null;
  private returnQueryParams: { [key: string]: any } = {};

  componenteCurricularOptions: ComponenteCurricularList[] = [];
  conceitoAprendidoOptions: ConceitoAprendidoList[] = [];

  componenteIdDropdownOpen: boolean = false;
  componenteIdSearchTerm: string = '';
  conceitoAprendidoIdDropdownOpen: boolean = false;
  conceitoAprendidoIdSearchTerm: string = '';

  constructor(
    private service: AprendizagemEsperadaService,
    private componenteCurricularService: ComponenteCurricularService,
    private conceitoAprendidoService: ConceitoAprendidoService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadComponenteCurricularOptions();
    this.loadConceitoAprendidoOptions();
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

  loadComponenteCurricularOptions(): void {
    this.componenteCurricularService.findAll(0, 1000).subscribe({
      next: (data) => this.componenteCurricularOptions = data.content,
      error: (err) => console.error('Erro ao carregar componenteCurricular:', err)
    });
  }

  loadConceitoAprendidoOptions(): void {
    this.conceitoAprendidoService.findAll(0, 1000).subscribe({
      next: (data) => this.conceitoAprendidoOptions = data.content,
      error: (err) => console.error('Erro ao carregar conceitoAprendido:', err)
    });
  }

  loadItem(): void {
    this.loading = true;
    this.service.findById(this.id).subscribe({
      next: (data) => {
        this.item = data as AprendizagemEsperadaRequest;
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
      this.router.navigate(['/aprendizagemEsperada'], { queryParams: this.returnQueryParams });
    } else {
      this.router.navigate(['/aprendizagemEsperada']);
    }
  }

  toggleComponenteIdDropdown(): void {
    this.componenteIdDropdownOpen = !this.componenteIdDropdownOpen;
    if (this.componenteIdDropdownOpen) {
      this.componenteIdSearchTerm = '';
    }
  }

  selectComponenteId(value: any): void {
    this.item.componenteId = value;
    this.componenteIdDropdownOpen = false;
  }

  getSelectedComponenteIdText(): string {
    if (this.item.componenteId === null || this.item.componenteId === undefined) {
      return 'Selecione...';
    }
    const selected = this.componenteCurricularOptions.find(opt => opt.id === this.item.componenteId);
    return selected ? String(selected.nome) : 'Selecione...';
  }

  filteredComponenteIdOptions(): any[] {
    if (!this.componenteIdSearchTerm) {
      return this.componenteCurricularOptions;
    }
    const term = this.componenteIdSearchTerm.toLowerCase();
    return this.componenteCurricularOptions.filter(opt => 
      String(opt.nome).toLowerCase().includes(term)
    );
  }

  toggleConceitoAprendidoIdDropdown(): void {
    this.conceitoAprendidoIdDropdownOpen = !this.conceitoAprendidoIdDropdownOpen;
    if (this.conceitoAprendidoIdDropdownOpen) {
      this.conceitoAprendidoIdSearchTerm = '';
    }
  }

  selectConceitoAprendidoId(value: any): void {
    this.item.conceitoAprendidoId = value;
    this.conceitoAprendidoIdDropdownOpen = false;
  }

  getSelectedConceitoAprendidoIdText(): string {
    if (this.item.conceitoAprendidoId === null || this.item.conceitoAprendidoId === undefined) {
      return 'Selecione...';
    }
    const selected = this.conceitoAprendidoOptions.find(opt => opt.id === this.item.conceitoAprendidoId);
    return selected ? String(selected.nome) : 'Selecione...';
  }

  filteredConceitoAprendidoIdOptions(): any[] {
    if (!this.conceitoAprendidoIdSearchTerm) {
      return this.conceitoAprendidoOptions;
    }
    const term = this.conceitoAprendidoIdSearchTerm.toLowerCase();
    return this.conceitoAprendidoOptions.filter(opt => 
      String(opt.nome).toLowerCase().includes(term)
    );
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.searchable-select')) {
      this.componenteIdDropdownOpen = false;
      this.conceitoAprendidoIdDropdownOpen = false;
    }
  }
}
