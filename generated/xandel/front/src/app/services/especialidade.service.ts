import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Especialidade, EspecialidadeRequest, EspecialidadeList } from '../models/especialidade.model';
import { environment } from '../../environments/environment';

/**
 * Service: Cadastro de especialidades médicas
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class EspecialidadeService {

  private readonly apiUrl = `${environment.apiUrl}/especialidades`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<EspecialidadeList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nomeEspecialidade,asc');
    return this.http.get<Page<EspecialidadeList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<Especialidade> {
    return this.http.get<Especialidade>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: EspecialidadeRequest): Observable<Especialidade> {
    return this.http.post<Especialidade>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: EspecialidadeRequest): Observable<Especialidade> {
    return this.http.put<Especialidade>(`${this.apiUrl}/${id}`, data);
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
