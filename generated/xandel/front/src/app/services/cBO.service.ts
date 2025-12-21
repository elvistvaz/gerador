import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CBO, CBORequest, CBOList } from '../models/cBO.model';
import { environment } from '../../environments/environment';

/**
 * Service: Cadastro Brasileiro de Ocupações
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class CBOService {

  private readonly apiUrl = `${environment.apiUrl}/c-b-os`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<CBOList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nomeCBO,asc');
    return this.http.get<Page<CBOList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: string): Observable<CBO> {
    return this.http.get<CBO>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: CBORequest): Observable<CBO> {
    return this.http.post<CBO>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: string, data: CBORequest): Observable<CBO> {
    return this.http.put<CBO>(`${this.apiUrl}/${id}`, data);
  }

  /**
   * Remove um registro por ID.
   */
  delete(id: string): Observable<void> {
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
