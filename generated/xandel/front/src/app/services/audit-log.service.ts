import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuditLogList, AuditLogResponse, AuditLogFilter, PageResponse } from '../models/audit-log.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuditLogService {

  private apiUrl = `${environment.apiUrl}/audit-log`;

  constructor(private http: HttpClient) { }

  findAll(filter: AuditLogFilter, page: number = 0, size: number = 10, sort: string = 'dataHora,desc'): Observable<PageResponse<AuditLogList>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort);

    if (filter.entidade) {
      params = params.set('entidade', filter.entidade);
    }
    if (filter.usuario) {
      params = params.set('usuario', filter.usuario);
    }
    if (filter.dataInicio) {
      params = params.set('dataInicio', filter.dataInicio);
    }
    if (filter.dataFim) {
      params = params.set('dataFim', filter.dataFim);
    }

    return this.http.get<PageResponse<AuditLogList>>(this.apiUrl, { params });
  }

  findById(id: number): Observable<AuditLogResponse> {
    return this.http.get<AuditLogResponse>(`${this.apiUrl}/${id}`);
  }

  getEntidades(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/entidades`);
  }

  getUsuarios(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/usuarios`);
  }
}
