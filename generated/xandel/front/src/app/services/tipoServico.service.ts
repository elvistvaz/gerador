import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TipoServico, TipoServicoRequest, TipoServicoList } from '../models/tipoServico.model';
import { environment } from '../../environments/environment';

/**
 * Service: Cadastro de tipos de serviço
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class TipoServicoService {

  private readonly apiUrl = `${environment.apiUrl}/tipo-servicos`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<TipoServicoList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    params = params.set('sort', sort || 'nomeTipoServico,asc');
    return this.http.get<Page<TipoServicoList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<TipoServico> {
    return this.http.get<TipoServico>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: TipoServicoRequest): Observable<TipoServico> {
    return this.http.post<TipoServico>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: TipoServicoRequest): Observable<TipoServico> {
    return this.http.put<TipoServico>(`${this.apiUrl}/${id}`, data);
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
