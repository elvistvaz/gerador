import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmpresaCliente, EmpresaClienteRequest, EmpresaClienteList, EmpresaClienteId } from '../models/empresaCliente.model';
import { environment } from '../../environments/environment';

/**
 * Service: Relacionamento empresa-cliente com taxas
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class EmpresaClienteService {

  private readonly apiUrl = `${environment.apiUrl}/empresa-clientes`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<EmpresaClienteList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (sort) {
      params = params.set('sort', sort);
    }
    return this.http.get<Page<EmpresaClienteList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: EmpresaClienteId): Observable<EmpresaCliente> {
    return this.http.get<EmpresaCliente>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: EmpresaClienteRequest): Observable<EmpresaCliente> {
    return this.http.post<EmpresaCliente>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: EmpresaClienteId, data: EmpresaClienteRequest): Observable<EmpresaCliente> {
    return this.http.put<EmpresaCliente>(`${this.apiUrl}/${id}`, data);
  }

  /**
   * Remove um registro por ID.
   */
  delete(id: EmpresaClienteId): Observable<void> {
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
