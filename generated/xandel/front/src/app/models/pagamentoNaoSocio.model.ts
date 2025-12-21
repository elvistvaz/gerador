/**
 * Model: Pagamentos para não sócios
 * Auto-gerado pelo gerador de código
 */

export interface PagamentoNaoSocio {
  data: Date | string;
  idEmpresa: number;
  idCliente: number;
  idPessoaNaoSocio: number;
  nf?: number;
  valorBruto?: any;
  retencao?: any;
  valorLiquido?: any;
  valorTaxa?: any;
  valorRepasse?: any;
}

export interface PagamentoNaoSocioRequest {
  data: Date | string;
  idEmpresa: number;
  idCliente: number;
  idPessoaNaoSocio: number;
  nf?: number;
  valorBruto?: any;
  retencao?: any;
  valorLiquido?: any;
  valorTaxa?: any;
  valorRepasse?: any;
}

export interface PagamentoNaoSocioResponse {
  data: Date | string;
  idEmpresa: number;
  idCliente: number;
  idPessoaNaoSocio: number;
  nf?: number;
  valorBruto?: any;
  retencao?: any;
  valorLiquido?: any;
  valorTaxa?: any;
  valorRepasse?: any;
}

export interface PagamentoNaoSocioList {
  data: Date | string;
  idEmpresa: number;
  idCliente: number;
  idPessoaNaoSocio: number;
  nf: number;
  valorBruto: any;
  retencao: any;
  valorLiquido: any;
  valorRepasse: any;
}

export interface PagamentoNaoSocioId {
  data: Date | string;
  idEmpresa: number;
  idCliente: number;
  idPessoaNaoSocio: number;
}
