import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ComponenteCurricular, ComponenteCurricularRequest, ComponenteCurricularList } from '../models/componenteCurricular.model';
import { environment } from '../../environments/environment';

/**
 * Service: Componentes curriculares
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class ComponenteCurricularService {

  private readonly apiUrl = `${environment.apiUrl}/componente-curriculares`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<ComponenteCurricularList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nome,asc');
    return this.http.get<Page<ComponenteCurricularList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<ComponenteCurricular> {
    return this.http.get<ComponenteCurricular>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: ComponenteCurricularRequest): Observable<ComponenteCurricular> {
    return this.http.post<ComponenteCurricular>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: ComponenteCurricularRequest): Observable<ComponenteCurricular> {
    return this.http.put<ComponenteCurricular>(`${this.apiUrl}/${id}`, data);
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
