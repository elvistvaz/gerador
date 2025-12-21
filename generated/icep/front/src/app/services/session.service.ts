import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface SessionContextData {
  avaliacaoId: number | null;
  avaliacaoNome: string | null;
  municipioId: number | null;
  municipioNome: string | null;
}

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private readonly STORAGE_KEY = 'session_context';

  private _avaliacaoIdSubject = new BehaviorSubject<number | null>(null);
  private _municipioIdSubject = new BehaviorSubject<number | null>(null);

  readonly avaliacaoId$: Observable<number | null> = this._avaliacaoIdSubject.asObservable();
  readonly municipioId$: Observable<number | null> = this._municipioIdSubject.asObservable();

  private _avaliacaoNome: string | null = null;
  private _municipioNome: string | null = null;

  constructor() {
    this.loadFromStorage();
  }

  private loadFromStorage(): void {
    try {
      const stored = localStorage.getItem(this.STORAGE_KEY);
      if (stored) {
        const data: SessionContextData = JSON.parse(stored);
        if (data.avaliacaoId) {
          this._avaliacaoIdSubject.next(data.avaliacaoId);
          this._avaliacaoNome = data.avaliacaoNome || null;
        }
        if (data.municipioId) {
          this._municipioIdSubject.next(data.municipioId);
          this._municipioNome = data.municipioNome || null;
        }
      }
    } catch (e) {
      console.error('Erro ao carregar contexto de sessão:', e);
    }
  }

  private saveToStorage(): void {
    const data: SessionContextData = {
      avaliacaoId: this._avaliacaoIdSubject.getValue(),
      avaliacaoNome: this._avaliacaoNome,
      municipioId: this._municipioIdSubject.getValue(),
      municipioNome: this._municipioNome
    };
    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(data));
  }

  getAvaliacaoId(): number | null {
    return this._avaliacaoIdSubject.getValue();
  }

  getAvaliacaoNome(): string | null {
    return this._avaliacaoNome;
  }

  setAvaliacaoId(id: number | null, nome?: string): void {
    this._avaliacaoIdSubject.next(id);
    this._avaliacaoNome = nome || null;
    this.saveToStorage();
  }

  getMunicipioId(): number | null {
    return this._municipioIdSubject.getValue();
  }

  getMunicipioNome(): string | null {
    return this._municipioNome;
  }

  setMunicipioId(id: number | null, nome?: string): void {
    this._municipioIdSubject.next(id);
    this._municipioNome = nome || null;
    this.saveToStorage();
  }

  isContextComplete(): boolean {
    return this._avaliacaoIdSubject.getValue() !== null && this._municipioIdSubject.getValue() !== null;
  }

  clearContext(): void {
    this._avaliacaoIdSubject.next(null);
    this._avaliacaoNome = null;
    this._municipioIdSubject.next(null);
    this._municipioNome = null;
    localStorage.removeItem(this.STORAGE_KEY);
  }
}
