import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MedicoEspecialidade, MedicoEspecialidadeRequest, MedicoEspecialidadeList, MedicoEspecialidadeId } from '../models/medicoEspecialidade.model';
import { environment } from '../../environments/environment';

/**
 * Service: Relacionamento médico-especialidade
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class MedicoEspecialidadeService {

  private readonly apiUrl = `${environment.apiUrl}/medico-especialidades`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<MedicoEspecialidadeList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (sort) {
      params = params.set('sort', sort);
    }
    return this.http.get<Page<MedicoEspecialidadeList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: MedicoEspecialidadeId): Observable<MedicoEspecialidade> {
    return this.http.get<MedicoEspecialidade>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: MedicoEspecialidadeRequest): Observable<MedicoEspecialidade> {
    return this.http.post<MedicoEspecialidade>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: MedicoEspecialidadeId, data: MedicoEspecialidadeRequest): Observable<MedicoEspecialidade> {
    return this.http.put<MedicoEspecialidade>(`${this.apiUrl}/${id}`, data);
  }

  /**
   * Remove um registro por ID.
   */
  delete(id: MedicoEspecialidadeId): Observable<void> {
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
