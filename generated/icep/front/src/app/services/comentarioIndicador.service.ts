import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ComentarioIndicador, ComentarioIndicadorRequest, ComentarioIndicadorList } from '../models/comentarioIndicador.model';
import { environment } from '../../environments/environment';

/**
 * Service: Comentários sobre indicadores por município e âmbito
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class ComentarioIndicadorService {

  private readonly apiUrl = `${environment.apiUrl}/comentario-indicadores`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, municipioId?: number | null, avaliacaoId?: number | null, sort?: string): Observable<Page<ComentarioIndicadorList>> {
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
    return this.http.get<Page<ComentarioIndicadorList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<ComentarioIndicador> {
    return this.http.get<ComentarioIndicador>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: ComentarioIndicadorRequest): Observable<ComentarioIndicador> {
    return this.http.post<ComentarioIndicador>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: ComentarioIndicadorRequest): Observable<ComentarioIndicador> {
    return this.http.put<ComentarioIndicador>(`${this.apiUrl}/${id}`, data);
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
