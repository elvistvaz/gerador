import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AtendimentoMunicipio, AtendimentoMunicipioRequest, AtendimentoMunicipioList } from '../models/atendimentoMunicipio.model';
import { environment } from '../../environments/environment';

/**
 * Service: Atendimentos por município e segmento
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class AtendimentoMunicipioService {

  private readonly apiUrl = `${environment.apiUrl}/atendimento-municipios`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, municipioId?: number | null, avaliacaoId?: number | null, sort?: string): Observable<Page<AtendimentoMunicipioList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (sort) {
      params = params.set('sort', sort);
    };
    if (municipioId) {
      params = params.set('municipioId', municipioId.toString());
    }
    if (avaliacaoId) {
      params = params.set('avaliacaoId', avaliacaoId.toString());
    }
    return this.http.get<Page<AtendimentoMunicipioList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<AtendimentoMunicipio> {
    return this.http.get<AtendimentoMunicipio>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: AtendimentoMunicipioRequest): Observable<AtendimentoMunicipio> {
    return this.http.post<AtendimentoMunicipio>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: AtendimentoMunicipioRequest): Observable<AtendimentoMunicipio> {
    return this.http.put<AtendimentoMunicipio>(`${this.apiUrl}/${id}`, data);
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
