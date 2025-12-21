import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Banco, BancoRequest, BancoList } from '../models/banco.model';
import { environment } from '../../environments/environment';

/**
 * Service: Cadastro de bancos
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class BancoService {

  private readonly apiUrl = `${environment.apiUrl}/bancos`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<BancoList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nomeBanco,asc');
    return this.http.get<Page<BancoList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: string): Observable<Banco> {
    return this.http.get<Banco>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: BancoRequest): Observable<Banco> {
    return this.http.post<Banco>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: string, data: BancoRequest): Observable<Banco> {
    return this.http.put<Banco>(`${this.apiUrl}/${id}`, data);
  }

  /**
   * Remove um registro por ID.
   */
  delete(id: string): Observable<void> {
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
