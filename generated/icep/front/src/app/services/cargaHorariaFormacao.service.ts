import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CargaHorariaFormacao, CargaHorariaFormacaoRequest, CargaHorariaFormacaoList } from '../models/cargaHorariaFormacao.model';
import { environment } from '../../environments/environment';

/**
 * Service: Carga horária de formações por município
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class CargaHorariaFormacaoService {

  private readonly apiUrl = `${environment.apiUrl}/carga-horaria-formacoes`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, municipioId?: number | null, avaliacaoId?: number | null, sort?: string): Observable<Page<CargaHorariaFormacaoList>> {
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
    return this.http.get<Page<CargaHorariaFormacaoList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<CargaHorariaFormacao> {
    return this.http.get<CargaHorariaFormacao>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: CargaHorariaFormacaoRequest): Observable<CargaHorariaFormacao> {
    return this.http.post<CargaHorariaFormacao>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: CargaHorariaFormacaoRequest): Observable<CargaHorariaFormacao> {
    return this.http.put<CargaHorariaFormacao>(`${this.apiUrl}/${id}`, data);
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
