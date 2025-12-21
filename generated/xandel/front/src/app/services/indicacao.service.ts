import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Indicacao, IndicacaoRequest, IndicacaoList } from '../models/indicacao.model';
import { environment } from '../../environments/environment';

/**
 * Service: Programas de indicação
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class IndicacaoService {

  private readonly apiUrl = `${environment.apiUrl}/indicacoes`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<IndicacaoList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'de,desc');
    return this.http.get<Page<IndicacaoList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<Indicacao> {
    return this.http.get<Indicacao>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: IndicacaoRequest): Observable<Indicacao> {
    return this.http.post<Indicacao>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: IndicacaoRequest): Observable<Indicacao> {
    return this.http.put<Indicacao>(`${this.apiUrl}/${id}`, data);
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
