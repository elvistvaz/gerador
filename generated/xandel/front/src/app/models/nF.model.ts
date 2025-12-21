/**
 * Model: Notas fiscais
 * Auto-gerado pelo gerador de código
 */

export interface NF {
  idEmpresa: number;
  nf: number;
  idCliente: number;
  emissao: Date | string;
  vencimento?: Date | string;
  total?: any;
  irrf?: any;
  pis?: any;
  cofins?: any;
  csll?: any;
  iss?: any;
  baixa?: Date | string;
  valorQuitado?: any;
  cancelada?: boolean;
  observacao?: string;
}

export interface NFRequest {
  idEmpresa: number;
  nf: number;
  idCliente: number;
  emissao: Date | string;
  vencimento?: Date | string;
  total?: any;
  irrf?: any;
  pis?: any;
  cofins?: any;
  csll?: any;
  iss?: any;
  baixa?: Date | string;
  valorQuitado?: any;
  cancelada?: boolean;
  observacao?: string;
}

export interface NFResponse {
  idEmpresa: number;
  nf: number;
  idCliente: number;
  emissao: Date | string;
  vencimento?: Date | string;
  total?: any;
  irrf?: any;
  pis?: any;
  cofins?: any;
  csll?: any;
  iss?: any;
  baixa?: Date | string;
  valorQuitado?: any;
  cancelada?: boolean;
  observacao?: string;
}

export interface NFList {
  idEmpresa: number;
  nf: number;
  idCliente: number;
  emissao: Date | string;
  vencimento: Date | string;
  total: any;
  baixa: Date | string;
  valorQuitado: any;
  cancelada: boolean;
}

export interface NFId {
  idEmpresa: number;
  nf: number;
}
