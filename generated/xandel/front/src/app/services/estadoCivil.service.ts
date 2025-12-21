import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EstadoCivil, EstadoCivilRequest, EstadoCivilList } from '../models/estadoCivil.model';
import { environment } from '../../environments/environment';

/**
 * Service: Cadastro de estados civis
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class EstadoCivilService {

  private readonly apiUrl = `${environment.apiUrl}/estado-civis`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<EstadoCivilList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nomeEstadoCivil,asc');
    return this.http.get<Page<EstadoCivilList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<EstadoCivil> {
    return this.http.get<EstadoCivil>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: EstadoCivilRequest): Observable<EstadoCivil> {
    return this.http.post<EstadoCivil>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: EstadoCivilRequest): Observable<EstadoCivil> {
    return this.http.put<EstadoCivil>(`${this.apiUrl}/${id}`, data);
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
