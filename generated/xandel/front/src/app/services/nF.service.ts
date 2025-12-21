import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NF, NFRequest, NFList, NFId } from '../models/nF.model';
import { environment } from '../../environments/environment';

/**
 * Service: Notas fiscais
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class NFService {

  private readonly apiUrl = `${environment.apiUrl}/n-fs`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, idEmpresa?: number | null, sort?: string): Observable<Page<NFList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort || 'emissao,desc');
    if (idEmpresa) {
      params = params.set('idEmpresa', idEmpresa.toString());
    }
    return this.http.get<Page<NFList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: NFId): Observable<NF> {
    return this.http.get<NF>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: NFRequest): Observable<NF> {
    return this.http.post<NF>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: NFId, data: NFRequest): Observable<NF> {
    return this.http.put<NF>(`${this.apiUrl}/${id}`, data);
  }

  /**
   * Remove um registro por ID.
   */
  delete(id: NFId): Observable<void> {
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
