import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ContasPagarReceber, ContasPagarReceberRequest, ContasPagarReceberList } from '../models/contasPagarReceber.model';
import { environment } from '../../environments/environment';

/**
 * Service: Contas a pagar e receber
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class ContasPagarReceberService {

  private readonly apiUrl = `${environment.apiUrl}/contas-pagar-receberes`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, idEmpresa?: number | null, sort?: string): Observable<Page<ContasPagarReceberList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort || 'dataVencimento,asc');
    if (idEmpresa) {
      params = params.set('idEmpresa', idEmpresa.toString());
    }
    return this.http.get<Page<ContasPagarReceberList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<ContasPagarReceber> {
    return this.http.get<ContasPagarReceber>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: ContasPagarReceberRequest): Observable<ContasPagarReceber> {
    return this.http.post<ContasPagarReceber>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: ContasPagarReceberRequest): Observable<ContasPagarReceber> {
    return this.http.put<ContasPagarReceber>(`${this.apiUrl}/${id}`, data);
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
