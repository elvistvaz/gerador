import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SessionService } from '../services/session.service';
import { EmpresaService } from '../services/empresa.service';
import { EmpresaList } from '../models/empresa.model';

@Component({
  selector: 'app-session-selector',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './session-selector.component.html',
  styleUrls: ['./session-selector.component.css']
})
export class SessionSelectorComponent implements OnInit {

  @Output() contextSelected = new EventEmitter<void>();

  empresaOptions: EmpresaList[] = [];
  selectedEmpresaId: number | null = null;
  empresaSearchTerm: string = '';
  empresaDropdownOpen: boolean = false;

  loading: boolean = false;
  error: string | null = null;

  constructor(
    private sessionService: SessionService,
    private empresaService: EmpresaService
  ) {}

  ngOnInit(): void {
    this.loadEmpresaOptions();
    this.selectedEmpresaId = this.sessionService.getIdEmpresa();
  }

  loadEmpresaOptions(): void {
    this.loading = true;
    this.empresaService.findAll(0, 10000).subscribe({
      next: (data) => {
        this.empresaOptions = data.content;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erro ao carregar opções';
        this.loading = false;
        console.error(err);
      }
    });
  }

  toggleEmpresaDropdown(): void {
    this.empresaDropdownOpen = !this.empresaDropdownOpen;
  }

  selectEmpresa(id: number | null): void {
    this.selectedEmpresaId = id;
    this.empresaDropdownOpen = false;
  }

  filteredEmpresaOptions(): EmpresaList[] {
    if (!this.empresaSearchTerm) {
      return this.empresaOptions;
    }
    const term = this.empresaSearchTerm.toLowerCase();
    return this.empresaOptions.filter(opt => 
      String(opt.nomeEmpresa).toLowerCase().includes(term)
    );
  }

  getSelectedEmpresaText(): string {
    if (!this.selectedEmpresaId) {
      return 'Selecione...';
    }
    const selected = this.empresaOptions.find(opt => opt.idEmpresa === this.selectedEmpresaId);
    return selected ? String(selected.nomeEmpresa) : 'Selecione...';
  }

  confirmar(): void {
    const empresaSelected = this.empresaOptions.find(opt => opt.idEmpresa === this.selectedEmpresaId);
    this.sessionService.setIdEmpresa(
      this.selectedEmpresaId,
      empresaSelected ? String(empresaSelected.nomeEmpresa) : undefined
    );
    this.contextSelected.emit();
  }

  canConfirm(): boolean {
    return this.selectedEmpresaId !== null;
  }
}
