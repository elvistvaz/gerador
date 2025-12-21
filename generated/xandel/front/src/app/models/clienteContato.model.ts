/**
 * Model: Contatos do cliente
 * Auto-gerado pelo gerador de código
 */

export interface ClienteContato {
  idClienteContato: number;
  idCliente: number;
  idTipoContato: number;
  descricao: string;
}

export interface ClienteContatoRequest {
  idCliente: number;
  idTipoContato: number;
  descricao: string;
}

export interface ClienteContatoResponse {
  idClienteContato: number;
  idCliente: number;
  idTipoContato: number;
  descricao: string;
}

export interface ClienteContatoList {
  idClienteContato: number;
  idTipoContato: number;
  descricao: string;
}
