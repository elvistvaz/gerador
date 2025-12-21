import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AnoEscolar, AnoEscolarRequest, AnoEscolarList } from '../models/anoEscolar.model';
import { environment } from '../../environments/environment';

/**
 * Service: Anos escolares
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class AnoEscolarService {

  private readonly apiUrl = `${environment.apiUrl}/ano-escolares`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<AnoEscolarList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nome,asc');
    return this.http.get<Page<AnoEscolarList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<AnoEscolar> {
    return this.http.get<AnoEscolar>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: AnoEscolarRequest): Observable<AnoEscolar> {
    return this.http.post<AnoEscolar>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: AnoEscolarRequest): Observable<AnoEscolar> {
    return this.http.put<AnoEscolar>(`${this.apiUrl}/${id}`, data);
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
