import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Operadora, OperadoraRequest, OperadoraList } from '../models/operadora.model';
import { environment } from '../../environments/environment';

/**
 * Service: Cadastro de operadoras
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class OperadoraService {

  private readonly apiUrl = `${environment.apiUrl}/operadoras`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<OperadoraList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nomeOperadora,asc');
    return this.http.get<Page<OperadoraList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<Operadora> {
    return this.http.get<Operadora>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: OperadoraRequest): Observable<Operadora> {
    return this.http.post<Operadora>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: OperadoraRequest): Observable<Operadora> {
    return this.http.put<Operadora>(`${this.apiUrl}/${id}`, data);
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
