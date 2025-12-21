import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AnuidadeCremeb, AnuidadeCremebRequest, AnuidadeCremebList } from '../models/anuidadeCremeb.model';
import { environment } from '../../environments/environment';

/**
 * Service: Anuidades do CREMEB
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class AnuidadeCremebService {

  private readonly apiUrl = `${environment.apiUrl}/anuidade-cremebs`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, idEmpresa?: number | null, sort?: string): Observable<Page<AnuidadeCremebList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort || 'anoExercicio,desc');
    if (idEmpresa) {
      params = params.set('idEmpresa', idEmpresa.toString());
    }
    return this.http.get<Page<AnuidadeCremebList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<AnuidadeCremeb> {
    return this.http.get<AnuidadeCremeb>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: AnuidadeCremebRequest): Observable<AnuidadeCremeb> {
    return this.http.post<AnuidadeCremeb>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: AnuidadeCremebRequest): Observable<AnuidadeCremeb> {
    return this.http.put<AnuidadeCremeb>(`${this.apiUrl}/${id}`, data);
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
