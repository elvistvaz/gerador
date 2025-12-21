import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PessoaContaRecebimento, PessoaContaRecebimentoRequest, PessoaContaRecebimentoList, PessoaContaRecebimentoId } from '../models/pessoaContaRecebimento.model';
import { environment } from '../../environments/environment';

/**
 * Service: Conta de recebimento por pessoa e cliente
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class PessoaContaRecebimentoService {

  private readonly apiUrl = `${environment.apiUrl}/pessoa-conta-recebimentos`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<PessoaContaRecebimentoList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (sort) {
      params = params.set('sort', sort);
    }
    return this.http.get<Page<PessoaContaRecebimentoList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: PessoaContaRecebimentoId): Observable<PessoaContaRecebimento> {
    return this.http.get<PessoaContaRecebimento>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: PessoaContaRecebimentoRequest): Observable<PessoaContaRecebimento> {
    return this.http.post<PessoaContaRecebimento>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: PessoaContaRecebimentoId, data: PessoaContaRecebimentoRequest): Observable<PessoaContaRecebimento> {
    return this.http.put<PessoaContaRecebimento>(`${this.apiUrl}/${id}`, data);
  }

  /**
   * Remove um registro por ID.
   */
  delete(id: PessoaContaRecebimentoId): Observable<void> {
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
