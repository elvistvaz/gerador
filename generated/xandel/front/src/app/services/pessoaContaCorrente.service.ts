import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PessoaContaCorrente, PessoaContaCorrenteRequest, PessoaContaCorrenteList } from '../models/pessoaContaCorrente.model';
import { environment } from '../../environments/environment';

/**
 * Service: Contas correntes da pessoa
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class PessoaContaCorrenteService {

  private readonly apiUrl = `${environment.apiUrl}/pessoa-conta-correntes`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<PessoaContaCorrenteList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (sort) {
      params = params.set('sort', sort);
    }
    return this.http.get<Page<PessoaContaCorrenteList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<PessoaContaCorrente> {
    return this.http.get<PessoaContaCorrente>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: PessoaContaCorrenteRequest): Observable<PessoaContaCorrente> {
    return this.http.post<PessoaContaCorrente>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: PessoaContaCorrenteRequest): Observable<PessoaContaCorrente> {
    return this.http.put<PessoaContaCorrente>(`${this.apiUrl}/${id}`, data);
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
