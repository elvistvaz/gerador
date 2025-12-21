import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cidade, CidadeRequest, CidadeList } from '../models/cidade.model';
import { environment } from '../../environments/environment';

/**
 * Service: Cadastro de cidades
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class CidadeService {

  private readonly apiUrl = `${environment.apiUrl}/cidades`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<CidadeList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nomeCidade,asc');
    return this.http.get<Page<CidadeList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<Cidade> {
    return this.http.get<Cidade>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: CidadeRequest): Observable<Cidade> {
    return this.http.post<Cidade>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: CidadeRequest): Observable<Cidade> {
    return this.http.put<Cidade>(`${this.apiUrl}/${id}`, data);
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
