export interface AuditLogList {
  id: number;
  acao: string;
  entidade: string;
  chave: string;
  usuario: string;
  dataHora: string;
}

export interface AuditLogResponse {
  id: number;
  acao: string;
  entidade: string;
  chave: string;
  usuario: string;
  dataHora: string;
  dadosAnteriores: string;
}

export interface AuditLogFilter {
  entidade?: string;
  usuario?: string;
  dataInicio?: string;
  dataFim?: string;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
