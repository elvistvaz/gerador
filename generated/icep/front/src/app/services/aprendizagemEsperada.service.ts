import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AprendizagemEsperada, AprendizagemEsperadaRequest, AprendizagemEsperadaList } from '../models/aprendizagemEsperada.model';
import { environment } from '../../environments/environment';

/**
 * Service: Aprendizagens esperadas por componente curricular e conceito
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class AprendizagemEsperadaService {

  private readonly apiUrl = `${environment.apiUrl}/aprendizagem-esperadas`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<AprendizagemEsperadaList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (sort) {
      params = params.set('sort', sort);
    }
    return this.http.get<Page<AprendizagemEsperadaList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<AprendizagemEsperada> {
    return this.http.get<AprendizagemEsperada>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: AprendizagemEsperadaRequest): Observable<AprendizagemEsperada> {
    return this.http.post<AprendizagemEsperada>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: AprendizagemEsperadaRequest): Observable<AprendizagemEsperada> {
    return this.http.put<AprendizagemEsperada>(`${this.apiUrl}/${id}`, data);
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
