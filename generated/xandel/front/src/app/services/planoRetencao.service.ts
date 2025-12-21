import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PlanoRetencao, PlanoRetencaoRequest, PlanoRetencaoList } from '../models/planoRetencao.model';
import { environment } from '../../environments/environment';

/**
 * Service: Cadastro de planos de retenção
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class PlanoRetencaoService {

  private readonly apiUrl = `${environment.apiUrl}/plano-retencoes`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<PlanoRetencaoList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nomePlanoRetencao,asc');
    return this.http.get<Page<PlanoRetencaoList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<PlanoRetencao> {
    return this.http.get<PlanoRetencao>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: PlanoRetencaoRequest): Observable<PlanoRetencao> {
    return this.http.post<PlanoRetencao>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: PlanoRetencaoRequest): Observable<PlanoRetencao> {
    return this.http.put<PlanoRetencao>(`${this.apiUrl}/${id}`, data);
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
