import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ImpostoDeRenda, ImpostoDeRendaRequest, ImpostoDeRendaList, ImpostoDeRendaId } from '../models/impostoDeRenda.model';
import { environment } from '../../environments/environment';

/**
 * Service: Tabela de Imposto de Renda
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class ImpostoDeRendaService {

  private readonly apiUrl = `${environment.apiUrl}/imposto-de-rendas`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<ImpostoDeRendaList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'id.data,desc');
    return this.http.get<Page<ImpostoDeRendaList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: ImpostoDeRendaId): Observable<ImpostoDeRenda> {
    return this.http.get<ImpostoDeRenda>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: ImpostoDeRendaRequest): Observable<ImpostoDeRenda> {
    return this.http.post<ImpostoDeRenda>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: ImpostoDeRendaId, data: ImpostoDeRendaRequest): Observable<ImpostoDeRenda> {
    return this.http.put<ImpostoDeRenda>(`${this.apiUrl}/${id}`, data);
  }

  /**
   * Remove um registro por ID.
   */
  delete(id: ImpostoDeRendaId): Observable<void> {
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
