import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CargaHorariaFormacaoTerritorio, CargaHorariaFormacaoTerritorioRequest, CargaHorariaFormacaoTerritorioList } from '../models/cargaHorariaFormacaoTerritorio.model';
import { environment } from '../../environments/environment';

/**
 * Service: Carga horária de formações por território
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class CargaHorariaFormacaoTerritorioService {

  private readonly apiUrl = `${environment.apiUrl}/carga-horaria-formacao-territorios`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, avaliacaoId?: number | null, sort?: string): Observable<Page<CargaHorariaFormacaoTerritorioList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort || 'id,asc');
    if (avaliacaoId) {
      params = params.set('avaliacaoId', avaliacaoId.toString());
    }
    return this.http.get<Page<CargaHorariaFormacaoTerritorioList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<CargaHorariaFormacaoTerritorio> {
    return this.http.get<CargaHorariaFormacaoTerritorio>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: CargaHorariaFormacaoTerritorioRequest): Observable<CargaHorariaFormacaoTerritorio> {
    return this.http.post<CargaHorariaFormacaoTerritorio>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: CargaHorariaFormacaoTerritorioRequest): Observable<CargaHorariaFormacaoTerritorio> {
    return this.http.put<CargaHorariaFormacaoTerritorio>(`${this.apiUrl}/${id}`, data);
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
