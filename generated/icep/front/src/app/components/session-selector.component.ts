import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SessionService } from '../services/session.service';
import { AvaliacaoService } from '../services/avaliacao.service';
import { AvaliacaoList } from '../models/avaliacao.model';
import { MunicipioService } from '../services/municipio.service';
import { MunicipioList } from '../models/municipio.model';

@Component({
  selector: 'app-session-selector',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './session-selector.component.html',
  styleUrls: ['./session-selector.component.css']
})
export class SessionSelectorComponent implements OnInit {

  @Output() contextSelected = new EventEmitter<void>();

  avaliacaoOptions: AvaliacaoList[] = [];
  selectedAvaliacaoId: number | null = null;
  avaliacaoSearchTerm: string = '';
  avaliacaoDropdownOpen: boolean = false;
  municipioOptions: MunicipioList[] = [];
  selectedMunicipioId: number | null = null;
  municipioSearchTerm: string = '';
  municipioDropdownOpen: boolean = false;

  loading: boolean = false;
  error: string | null = null;

  constructor(
    private sessionService: SessionService,
    private avaliacaoService: AvaliacaoService,
    private municipioService: MunicipioService
  ) {}

  ngOnInit(): void {
    this.loadAvaliacaoOptions();
    this.selectedAvaliacaoId = this.sessionService.getAvaliacaoId();
    this.loadMunicipioOptions();
    this.selectedMunicipioId = this.sessionService.getMunicipioId();
  }

  loadAvaliacaoOptions(): void {
    this.loading = true;
    this.avaliacaoService.findAll(0, 10000).subscribe({
      next: (data) => {
        this.avaliacaoOptions = data.content;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erro ao carregar opções';
        this.loading = false;
        console.error(err);
      }
    });
  }

  toggleAvaliacaoDropdown(): void {
    this.avaliacaoDropdownOpen = !this.avaliacaoDropdownOpen;
  }

  selectAvaliacao(id: number | null): void {
    this.selectedAvaliacaoId = id;
    this.avaliacaoDropdownOpen = false;
  }

  filteredAvaliacaoOptions(): AvaliacaoList[] {
    if (!this.avaliacaoSearchTerm) {
      return this.avaliacaoOptions;
    }
    const term = this.avaliacaoSearchTerm.toLowerCase();
    return this.avaliacaoOptions.filter(opt => 
      String(opt.avaliacao).toLowerCase().includes(term)
    );
  }

  getSelectedAvaliacaoText(): string {
    if (!this.selectedAvaliacaoId) {
      return 'Selecione...';
    }
    const selected = this.avaliacaoOptions.find(opt => opt.avaliacaoId === this.selectedAvaliacaoId);
    return selected ? String(selected.avaliacao) : 'Selecione...';
  }

  loadMunicipioOptions(): void {
    this.loading = true;
    this.municipioService.findAll(0, 10000).subscribe({
      next: (data) => {
        this.municipioOptions = data.content;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erro ao carregar opções';
        this.loading = false;
        console.error(err);
      }
    });
  }

  toggleMunicipioDropdown(): void {
    this.municipioDropdownOpen = !this.municipioDropdownOpen;
  }

  selectMunicipio(id: number | null): void {
    this.selectedMunicipioId = id;
    this.municipioDropdownOpen = false;
  }

  filteredMunicipioOptions(): MunicipioList[] {
    if (!this.municipioSearchTerm) {
      return this.municipioOptions;
    }
    const term = this.municipioSearchTerm.toLowerCase();
    return this.municipioOptions.filter(opt => 
      String(opt.nome).toLowerCase().includes(term)
    );
  }

  getSelectedMunicipioText(): string {
    if (!this.selectedMunicipioId) {
      return 'Selecione...';
    }
    const selected = this.municipioOptions.find(opt => opt.municipioId === this.selectedMunicipioId);
    return selected ? String(selected.nome) : 'Selecione...';
  }

  confirmar(): void {
    const avaliacaoSelected = this.avaliacaoOptions.find(opt => opt.avaliacaoId === this.selectedAvaliacaoId);
    this.sessionService.setAvaliacaoId(
      this.selectedAvaliacaoId,
      avaliacaoSelected ? String(avaliacaoSelected.avaliacao) : undefined
    );
    const municipioSelected = this.municipioOptions.find(opt => opt.municipioId === this.selectedMunicipioId);
    this.sessionService.setMunicipioId(
      this.selectedMunicipioId,
      municipioSelected ? String(municipioSelected.nome) : undefined
    );
    this.contextSelected.emit();
  }

  canConfirm(): boolean {
    return this.selectedAvaliacaoId !== null && this.selectedMunicipioId !== null;
  }
}
