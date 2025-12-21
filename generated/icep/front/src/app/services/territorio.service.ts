import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Territorio, TerritorioRequest, TerritorioList } from '../models/territorio.model';
import { environment } from '../../environments/environment';

/**
 * Service: Cadastro de territórios
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class TerritorioService {

  private readonly apiUrl = `${environment.apiUrl}/territorios`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<TerritorioList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nome,asc');
    return this.http.get<Page<TerritorioList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<Territorio> {
    return this.http.get<Territorio>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: TerritorioRequest): Observable<Territorio> {
    return this.http.post<Territorio>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: TerritorioRequest): Observable<Territorio> {
    return this.http.put<Territorio>(`${this.apiUrl}/${id}`, data);
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
