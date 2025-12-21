import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PublicoAlvo, PublicoAlvoRequest, PublicoAlvoList } from '../models/publicoAlvo.model';
import { environment } from '../../environments/environment';

/**
 * Service: Público alvo do programa
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class PublicoAlvoService {

  private readonly apiUrl = `${environment.apiUrl}/publico-alvos`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<PublicoAlvoList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nome,asc');
    return this.http.get<Page<PublicoAlvoList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<PublicoAlvo> {
    return this.http.get<PublicoAlvo>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: PublicoAlvoRequest): Observable<PublicoAlvo> {
    return this.http.post<PublicoAlvo>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: PublicoAlvoRequest): Observable<PublicoAlvo> {
    return this.http.put<PublicoAlvo>(`${this.apiUrl}/${id}`, data);
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
