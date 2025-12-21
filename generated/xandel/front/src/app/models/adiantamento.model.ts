/**
 * Model: Adiantamentos de pagamentos
 * Auto-gerado pelo gerador de código
 */

export interface Adiantamento {
  idAdiantamento: number;
  data: Date | string;
  idEmpresa: number;
  idPessoa: number;
  idCliente: number;
  nf?: number;
  valorBruto?: any;
  retencao?: any;
  valorLiquido?: any;
  valorTaxa?: any;
  valorRepasse?: any;
  idLancamento: number;
  pago?: boolean;
}

export interface AdiantamentoRequest {
  idAdiantamento: number;
  data: Date | string;
  idEmpresa: number;
  idPessoa: number;
  idCliente: number;
  nf?: number;
  valorBruto?: any;
  retencao?: any;
  valorLiquido?: any;
  valorTaxa?: any;
  valorRepasse?: any;
  idLancamento: number;
  pago?: boolean;
}

export interface AdiantamentoResponse {
  idAdiantamento: number;
  data: Date | string;
  idEmpresa: number;
  idPessoa: number;
  idCliente: number;
  nf?: number;
  valorBruto?: any;
  retencao?: any;
  valorLiquido?: any;
  valorTaxa?: any;
  valorRepasse?: any;
  idLancamento: number;
  pago?: boolean;
}

export interface AdiantamentoList {
  idAdiantamento: number;
  data: Date | string;
  idEmpresa: number;
  idPessoa: number;
  idCliente: number;
  nf: number;
  valorBruto: any;
  retencao: any;
  valorLiquido: any;
  valorTaxa: any;
  valorRepasse: any;
  pago: boolean;
}
