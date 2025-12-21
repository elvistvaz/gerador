import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AnuidadeCremebItem, AnuidadeCremebItemRequest, AnuidadeCremebItemList } from '../models/anuidadeCremebItem.model';
import { environment } from '../../environments/environment';

/**
 * Service: Itens individuais da anuidade CREMEB
 * Auto-gerado pelo gerador de código
 */
@Injectable({
  providedIn: 'root'
})
export class AnuidadeCremebItemService {

  private readonly apiUrl = `${environment.apiUrl}/anuidade-cremeb-itens`;

  constructor(private http: HttpClient) {}

  /**
   * Lista todos os registros com paginação.
   */
  findAll(page: number = 0, size: number = 20, sort?: string): Observable<Page<AnuidadeCremebItemList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (sort) {
      params = params.set('sort', sort);
    }
    return this.http.get<Page<AnuidadeCremebItemList>>(this.apiUrl, { params });
  }

  /**
   * Busca um registro por ID.
   */
  findById(id: number): Observable<AnuidadeCremebItem> {
    return this.http.get<AnuidadeCremebItem>(`${this.apiUrl}/${id}`);
  }

  /**
   * Cria um novo registro.
   */
  create(data: AnuidadeCremebItemRequest): Observable<AnuidadeCremebItem> {
    return this.http.post<AnuidadeCremebItem>(this.apiUrl, data);
  }

  /**
   * Atualiza um registro existente.
   */
  update(id: number, data: AnuidadeCremebItemRequest): Observable<AnuidadeCremebItem> {
    return this.http.put<AnuidadeCremebItem>(`${this.apiUrl}/${id}`, data);
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
