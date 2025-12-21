import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Empresa, EmpresaRequest, EmpresaList } from '../models/empresa.model';
import { environment } from '../../environments/environment';

/**
 * Service: Cadastro de empresas
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class EmpresaService {

  private readonly apiUrl = `${environment.apiUrl}/empresas`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<EmpresaList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nomeEmpresa,asc');
    return this.http.get<Page<EmpresaList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<Empresa> {
    return this.http.get<Empresa>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: EmpresaRequest): Observable<Empresa> {
    return this.http.post<Empresa>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: EmpresaRequest): Observable<Empresa> {
    return this.http.put<Empresa>(`${this.apiUrl}/${id}`, data);
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
