import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Lancamento, LancamentoRequest, LancamentoList } from '../models/lancamento.model';
import { environment } from '../../environments/environment';

/**
 * Service: Lançamentos financeiros
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class LancamentoService {

  private readonly apiUrl = `${environment.apiUrl}/lancamentos`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, idEmpresa?: number | null, sort?: string): Observable<Page<LancamentoList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort || 'data,desc');
    if (idEmpresa) {
      params = params.set('idEmpresa', idEmpresa.toString());
    }
    return this.http.get<Page<LancamentoList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<Lancamento> {
    return this.http.get<Lancamento>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: LancamentoRequest): Observable<Lancamento> {
    return this.http.post<Lancamento>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: LancamentoRequest): Observable<Lancamento> {
    return this.http.put<Lancamento>(`${this.apiUrl}/${id}`, data);
  }

  /**
   * Remove um registro por ID.
   */
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

/**
 * Interface auxiliar para paginação do Spring Boot.
 */
export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
