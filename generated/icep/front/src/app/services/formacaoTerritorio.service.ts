import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FormacaoTerritorio, FormacaoTerritorioRequest, FormacaoTerritorioList } from '../models/formacaoTerritorio.model';
import { environment } from '../../environments/environment';

/**
 * Service: Tipos de formação por território
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class FormacaoTerritorioService {

  private readonly apiUrl = `${environment.apiUrl}/formacao-territorios`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<FormacaoTerritorioList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'ordem,asc');
    return this.http.get<Page<FormacaoTerritorioList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<FormacaoTerritorio> {
    return this.http.get<FormacaoTerritorio>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: FormacaoTerritorioRequest): Observable<FormacaoTerritorio> {
    return this.http.post<FormacaoTerritorio>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: FormacaoTerritorioRequest): Observable<FormacaoTerritorio> {
    return this.http.put<FormacaoTerritorio>(`${this.apiUrl}/${id}`, data);
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
