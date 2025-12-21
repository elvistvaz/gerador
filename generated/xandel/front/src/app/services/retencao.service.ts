import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Retencao, RetencaoRequest, RetencaoList, RetencaoId } from '../models/retencao.model';
import { environment } from '../../environments/environment';

/**
 * Service: Faixas de retenção por plano
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class RetencaoService {

  private readonly apiUrl = `${environment.apiUrl}/retencoes`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<RetencaoList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (sort) {
      params = params.set('sort', sort);
    }
    return this.http.get<Page<RetencaoList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: RetencaoId): Observable<Retencao> {
    return this.http.get<Retencao>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: RetencaoRequest): Observable<Retencao> {
    return this.http.post<Retencao>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: RetencaoId, data: RetencaoRequest): Observable<Retencao> {
    return this.http.put<Retencao>(`${this.apiUrl}/${id}`, data);
  }

  /**
   * Remove um registro por ID.
   */
  delete(id: RetencaoId): Observable<void> {
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
