import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface SessionContextData {
  idEmpresa: number | null;
  idEmpresaNome: string | null;
}

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private readonly STORAGE_KEY = 'session_context';

  private _idEmpresaSubject = new BehaviorSubject<number | null>(null);

  readonly idEmpresa$: Observable<number | null> = this._idEmpresaSubject.asObservable();

  private _idEmpresaNome: string | null = null;

  constructor() {
    this.loadFromStorage();
  }

  private loadFromStorage(): void {
    try {
      const stored = localStorage.getItem(this.STORAGE_KEY);
      if (stored) {
        const data: SessionContextData = JSON.parse(stored);
        if (data.idEmpresa) {
          this._idEmpresaSubject.next(data.idEmpresa);
          this._idEmpresaNome = data.idEmpresaNome || null;
        }
      }
    } catch (e) {
      console.error('Erro ao carregar contexto de sessão:', e);
    }
  }

  private saveToStorage(): void {
    const data: SessionContextData = {
      idEmpresa: this._idEmpresaSubject.getValue(),
      idEmpresaNome: this._idEmpresaNome
    };
    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(data));
  }

  getIdEmpresa(): number | null {
    return this._idEmpresaSubject.getValue();
  }

  getIdEmpresaNome(): string | null {
    return this._idEmpresaNome;
  }

  setIdEmpresa(id: number | null, nome?: string): void {
    this._idEmpresaSubject.next(id);
    this._idEmpresaNome = nome || null;
    this.saveToStorage();
  }

  isContextComplete(): boolean {
    return this._idEmpresaSubject.getValue() !== null;
  }

  clearContext(): void {
    this._idEmpresaSubject.next(null);
    this._idEmpresaNome = null;
    localStorage.removeItem(this.STORAGE_KEY);
  }
}
