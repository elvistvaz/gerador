import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DespesaReceita, DespesaReceitaRequest, DespesaReceitaList } from '../models/despesaReceita.model';
import { environment } from '../../environments/environment';

/**
 * Service: Cadastro de despesas e receitas
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class DespesaReceitaService {

  private readonly apiUrl = `${environment.apiUrl}/despesa-receitas`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<DespesaReceitaList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nomeDespesaReceita,asc');
    return this.http.get<Page<DespesaReceitaList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<DespesaReceita> {
    return this.http.get<DespesaReceita>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: DespesaReceitaRequest): Observable<DespesaReceita> {
    return this.http.post<DespesaReceita>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: DespesaReceitaRequest): Observable<DespesaReceita> {
    return this.http.put<DespesaReceita>(`${this.apiUrl}/${id}`, data);
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
