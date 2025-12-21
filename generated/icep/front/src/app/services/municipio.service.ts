import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Municipio, MunicipioRequest, MunicipioList } from '../models/municipio.model';
import { environment } from '../../environments/environment';

/**
 * Service: Cadastro de municípios atendidos
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class MunicipioService {

  private readonly apiUrl = `${environment.apiUrl}/municipios`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<MunicipioList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nome,asc');
    return this.http.get<Page<MunicipioList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<Municipio> {
    return this.http.get<Municipio>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: MunicipioRequest): Observable<Municipio> {
    return this.http.post<Municipio>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: MunicipioRequest): Observable<Municipio> {
    return this.http.put<Municipio>(`${this.apiUrl}/${id}`, data);
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
