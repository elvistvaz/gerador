import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmpresaSocio, EmpresaSocioRequest, EmpresaSocioList, EmpresaSocioId } from '../models/empresaSocio.model';
import { environment } from '../../environments/environment';

/**
 * Service: Sócios da empresa
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class EmpresaSocioService {

  private readonly apiUrl = `${environment.apiUrl}/empresa-socios`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<EmpresaSocioList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (sort) {
      params = params.set('sort', sort);
    }
    return this.http.get<Page<EmpresaSocioList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: EmpresaSocioId): Observable<EmpresaSocio> {
    return this.http.get<EmpresaSocio>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: EmpresaSocioRequest): Observable<EmpresaSocio> {
    return this.http.post<EmpresaSocio>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: EmpresaSocioId, data: EmpresaSocioRequest): Observable<EmpresaSocio> {
    return this.http.put<EmpresaSocio>(`${this.apiUrl}/${id}`, data);
  }

  /**
   * Remove um registro por ID.
   */
  delete(id: EmpresaSocioId): Observable<void> {
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
