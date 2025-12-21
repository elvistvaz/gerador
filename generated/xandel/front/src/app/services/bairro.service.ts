import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Bairro, BairroRequest, BairroList } from '../models/bairro.model';
import { environment } from '../../environments/environment';

/**
 * Service: Cadastro de bairros
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class BairroService {

  private readonly apiUrl = `${environment.apiUrl}/bairros`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<BairroList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nomeBairro,asc');
    return this.http.get<Page<BairroList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<Bairro> {
    return this.http.get<Bairro>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: BairroRequest): Observable<Bairro> {
    return this.http.post<Bairro>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: BairroRequest): Observable<Bairro> {
    return this.http.put<Bairro>(`${this.apiUrl}/${id}`, data);
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
