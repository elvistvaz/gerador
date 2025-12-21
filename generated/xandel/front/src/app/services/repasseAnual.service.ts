import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RepasseAnual, RepasseAnualRequest, RepasseAnualList, RepasseAnualId } from '../models/repasseAnual.model';
import { environment } from '../../environments/environment';

/**
 * Service: Repasse anual consolidado
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class RepasseAnualService {

  private readonly apiUrl = `${environment.apiUrl}/repasse-anuais`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, idEmpresa?: number | null, sort?: string): Observable<Page<RepasseAnualList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort || 'id.ano,desc');
    if (idEmpresa) {
      params = params.set('idEmpresa', idEmpresa.toString());
    }
    return this.http.get<Page<RepasseAnualList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: RepasseAnualId): Observable<RepasseAnual> {
    return this.http.get<RepasseAnual>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: RepasseAnualRequest): Observable<RepasseAnual> {
    return this.http.post<RepasseAnual>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: RepasseAnualId, data: RepasseAnualRequest): Observable<RepasseAnual> {
    return this.http.put<RepasseAnual>(`${this.apiUrl}/${id}`, data);
  }

  /**
   * Remove um registro por ID.
   */
  delete(id: RepasseAnualId): Observable<void> {
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
