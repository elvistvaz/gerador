/**
 * Model: Filiais do cliente
 * Auto-gerado pelo gerador de código
 */

export interface ClienteFilial {
  idClienteFilial: number;
  idCliente: number;
  nomeFilial: string;
}

export interface ClienteFilialRequest {
  idCliente: number;
  nomeFilial: string;
}

export interface ClienteFilialResponse {
  idClienteFilial: number;
  idCliente: number;
  nomeFilial: string;
}

export interface ClienteFilialList {
  idClienteFilial: number;
  nomeFilial: string;
}
