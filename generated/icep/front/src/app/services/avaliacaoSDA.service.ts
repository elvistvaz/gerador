import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AvaliacaoSDA, AvaliacaoSDARequest, AvaliacaoSDAList } from '../models/avaliacaoSDA.model';
import { environment } from '../../environments/environment';

/**
 * Service: Avaliação de Situações Didáticas Avaliativas
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class AvaliacaoSDAService {

  private readonly apiUrl = `${environment.apiUrl}/avaliacao-s-d-as`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, municipioId?: number | null, avaliacaoId?: number | null, sort?: string): Observable<Page<AvaliacaoSDAList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (sort) {
      params = params.set('sort', sort);
    };
    if (municipioId) {
      params = params.set('municipioId', municipioId.toString());
    }
    if (avaliacaoId) {
      params = params.set('avaliacaoId', avaliacaoId.toString());
    }
    return this.http.get<Page<AvaliacaoSDAList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<AvaliacaoSDA> {
    return this.http.get<AvaliacaoSDA>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: AvaliacaoSDARequest): Observable<AvaliacaoSDA> {
    return this.http.post<AvaliacaoSDA>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: AvaliacaoSDARequest): Observable<AvaliacaoSDA> {
    return this.http.put<AvaliacaoSDA>(`${this.apiUrl}/${id}`, data);
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
