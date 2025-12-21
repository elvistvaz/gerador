import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PagamentoNaoSocio, PagamentoNaoSocioRequest, PagamentoNaoSocioList, PagamentoNaoSocioId } from '../models/pagamentoNaoSocio.model';
import { environment } from '../../environments/environment';

/**
 * Service: Pagamentos para não sócios
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class PagamentoNaoSocioService {

  private readonly apiUrl = `${environment.apiUrl}/pagamento-nao-socios`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, idEmpresa?: number | null, sort?: string): Observable<Page<PagamentoNaoSocioList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort || 'id.data,desc');
    if (idEmpresa) {
      params = params.set('idEmpresa', idEmpresa.toString());
    }
    return this.http.get<Page<PagamentoNaoSocioList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: PagamentoNaoSocioId): Observable<PagamentoNaoSocio> {
    return this.http.get<PagamentoNaoSocio>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: PagamentoNaoSocioRequest): Observable<PagamentoNaoSocio> {
    return this.http.post<PagamentoNaoSocio>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: PagamentoNaoSocioId, data: PagamentoNaoSocioRequest): Observable<PagamentoNaoSocio> {
    return this.http.put<PagamentoNaoSocio>(`${this.apiUrl}/${id}`, data);
  }

  /**
   * Remove um registro por ID.
   */
  delete(id: PagamentoNaoSocioId): Observable<void> {
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
