import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pessoa, PessoaRequest, PessoaList } from '../models/pessoa.model';
import { environment } from '../../environments/environment';

/**
 * Service: Cadastro de pessoas (sócios/médicos)
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class PessoaService {

  private readonly apiUrl = `${environment.apiUrl}/pessoas`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<PessoaList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nome,asc');
    return this.http.get<Page<PessoaList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<Pessoa> {
    return this.http.get<Pessoa>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: PessoaRequest): Observable<Pessoa> {
    return this.http.post<Pessoa>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: PessoaRequest): Observable<Pessoa> {
    return this.http.put<Pessoa>(`${this.apiUrl}/${id}`, data);
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
