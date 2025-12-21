import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Formacao, FormacaoRequest, FormacaoList } from '../models/formacao.model';
import { environment } from '../../environments/environment';

/**
 * Service: Tipos de formação
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class FormacaoService {

  private readonly apiUrl = `${environment.apiUrl}/formacoes`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<FormacaoList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'ordem,asc');
    return this.http.get<Page<FormacaoList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<Formacao> {
    return this.http.get<Formacao>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: FormacaoRequest): Observable<Formacao> {
    return this.http.post<Formacao>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: FormacaoRequest): Observable<Formacao> {
    return this.http.put<Formacao>(`${this.apiUrl}/${id}`, data);
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
