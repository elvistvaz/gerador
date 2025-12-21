import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmpresaDespesaFixa, EmpresaDespesaFixaRequest, EmpresaDespesaFixaList, EmpresaDespesaFixaId } from '../models/empresaDespesaFixa.model';
import { environment } from '../../environments/environment';

/**
 * Service: Despesas fixas por empresa
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class EmpresaDespesaFixaService {

  private readonly apiUrl = `${environment.apiUrl}/empresa-despesa-fixas`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<EmpresaDespesaFixaList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (sort) {
      params = params.set('sort', sort);
    }
    return this.http.get<Page<EmpresaDespesaFixaList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: EmpresaDespesaFixaId): Observable<EmpresaDespesaFixa> {
    return this.http.get<EmpresaDespesaFixa>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: EmpresaDespesaFixaRequest): Observable<EmpresaDespesaFixa> {
    return this.http.post<EmpresaDespesaFixa>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: EmpresaDespesaFixaId, data: EmpresaDespesaFixaRequest): Observable<EmpresaDespesaFixa> {
    return this.http.put<EmpresaDespesaFixa>(`${this.apiUrl}/${id}`, data);
  }

  /**
   * Remove um registro por ID.
   */
  delete(id: EmpresaDespesaFixaId): Observable<void> {
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
