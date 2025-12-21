import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AmbitoGestao, AmbitoGestaoRequest, AmbitoGestaoList } from '../models/ambitoGestao.model';
import { environment } from '../../environments/environment';

/**
 * Service: Âmbitos de gestão para indicadores
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class AmbitoGestaoService {

  private readonly apiUrl = `${environment.apiUrl}/ambito-gestoes`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<AmbitoGestaoList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nome,asc');
    return this.http.get<Page<AmbitoGestaoList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<AmbitoGestao> {
    return this.http.get<AmbitoGestao>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: AmbitoGestaoRequest): Observable<AmbitoGestao> {
    return this.http.post<AmbitoGestao>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: AmbitoGestaoRequest): Observable<AmbitoGestao> {
    return this.http.put<AmbitoGestao>(`${this.apiUrl}/${id}`, data);
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
