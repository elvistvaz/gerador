import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConceitoAprendido, ConceitoAprendidoRequest, ConceitoAprendidoList } from '../models/conceitoAprendido.model';
import { environment } from '../../environments/environment';

/**
 * Service: Conceitos de aprendizagem
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class ConceitoAprendidoService {

  private readonly apiUrl = `${environment.apiUrl}/conceito-aprendidos`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<ConceitoAprendidoList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nome,asc');
    return this.http.get<Page<ConceitoAprendidoList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<ConceitoAprendido> {
    return this.http.get<ConceitoAprendido>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: ConceitoAprendidoRequest): Observable<ConceitoAprendido> {
    return this.http.post<ConceitoAprendido>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: ConceitoAprendidoRequest): Observable<ConceitoAprendido> {
    return this.http.put<ConceitoAprendido>(`${this.apiUrl}/${id}`, data);
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
