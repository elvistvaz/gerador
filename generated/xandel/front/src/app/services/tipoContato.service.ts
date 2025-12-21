import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TipoContato, TipoContatoRequest, TipoContatoList } from '../models/tipoContato.model';
import { environment } from '../../environments/environment';

/**
 * Service: Cadastro de tipos de contato
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class TipoContatoService {

  private readonly apiUrl = `${environment.apiUrl}/tipo-contatos`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<TipoContatoList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nomeTipoContato,asc');
    return this.http.get<Page<TipoContatoList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<TipoContato> {
    return this.http.get<TipoContato>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: TipoContatoRequest): Observable<TipoContato> {
    return this.http.post<TipoContato>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: TipoContatoRequest): Observable<TipoContato> {
    return this.http.put<TipoContato>(`${this.apiUrl}/${id}`, data);
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
