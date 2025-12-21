import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AvaliacaoIndicador, AvaliacaoIndicadorRequest, AvaliacaoIndicadorList } from '../models/avaliacaoIndicador.model';
import { environment } from '../../environments/environment';

/**
 * Service: Avaliação de indicadores por município
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class AvaliacaoIndicadorService {

  private readonly apiUrl = `${environment.apiUrl}/avaliacao-indicadores`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, municipioId?: number | null, avaliacaoId?: number | null, sort?: string): Observable<Page<AvaliacaoIndicadorList>> {
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
    return this.http.get<Page<AvaliacaoIndicadorList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<AvaliacaoIndicador> {
    return this.http.get<AvaliacaoIndicador>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: AvaliacaoIndicadorRequest): Observable<AvaliacaoIndicador> {
    return this.http.post<AvaliacaoIndicador>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: AvaliacaoIndicadorRequest): Observable<AvaliacaoIndicador> {
    return this.http.put<AvaliacaoIndicador>(`${this.apiUrl}/${id}`, data);
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
