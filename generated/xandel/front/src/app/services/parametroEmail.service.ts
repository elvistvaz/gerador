import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ParametroEmail, ParametroEmailRequest, ParametroEmailList } from '../models/parametroEmail.model';
import { environment } from '../../environments/environment';

/**
 * Service: Parâmetros de configuração de e-mail
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class ParametroEmailService {

  private readonly apiUrl = `${environment.apiUrl}/parametro-emais`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<ParametroEmailList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (sort) {
      params = params.set('sort', sort);
    }
    return this.http.get<Page<ParametroEmailList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<ParametroEmail> {
    return this.http.get<ParametroEmail>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: ParametroEmailRequest): Observable<ParametroEmail> {
    return this.http.post<ParametroEmail>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: ParametroEmailRequest): Observable<ParametroEmail> {
    return this.http.put<ParametroEmail>(`${this.apiUrl}/${id}`, data);
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
