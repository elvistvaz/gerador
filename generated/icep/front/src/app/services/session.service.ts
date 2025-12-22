import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface SessionContextData {
  avaliacaoId: number | null;
  avaliacaoIdNome: string | null;
  municipioId: number | null;
  municipioIdNome: string | null;
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

  private _avaliacaoIdNome: string | null = null;
  private _municipioIdNome: string | null = null;

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
          this._avaliacaoIdNome = data.avaliacaoIdNome || null;
        }
        if (data.municipioId) {
          this._municipioIdSubject.next(data.municipioId);
          this._municipioIdNome = data.municipioIdNome || null;
        }
      }
    } catch (e) {
      console.error('Erro ao carregar contexto de sessão:', e);
    }
  }

  private saveToStorage(): void {
    const data: SessionContextData = {
      avaliacaoId: this._avaliacaoIdSubject.getValue(),
      avaliacaoIdNome: this._avaliacaoIdNome,
      municipioId: this._municipioIdSubject.getValue(),
      municipioIdNome: this._municipioIdNome
    };
    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(data));
  }

  getAvaliacaoId(): number | null {
    return this._avaliacaoIdSubject.getValue();
  }

  getAvaliacaoIdNome(): string | null {
    return this._avaliacaoIdNome;
  }

  setAvaliacaoId(id: number | null, nome?: string): void {
    this._avaliacaoIdSubject.next(id);
    this._avaliacaoIdNome = nome || null;
    this.saveToStorage();
  }

  getMunicipioId(): number | null {
    return this._municipioIdSubject.getValue();
  }

  getMunicipioIdNome(): string | null {
    return this._municipioIdNome;
  }

  setMunicipioId(id: number | null, nome?: string): void {
    this._municipioIdSubject.next(id);
    this._municipioIdNome = nome || null;
    this.saveToStorage();
  }

  isContextComplete(): boolean {
    return this._avaliacaoIdSubject.getValue() !== null && this._municipioIdSubject.getValue() !== null;
  }

  clearContext(): void {
    this._avaliacaoIdSubject.next(null);
    this._avaliacaoIdNome = null;
    this._municipioIdSubject.next(null);
    this._municipioIdNome = null;
    localStorage.removeItem(this.STORAGE_KEY);
  }
}
