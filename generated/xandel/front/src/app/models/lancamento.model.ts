/**
 * Model: Lançamentos financeiros
 * Auto-gerado pelo gerador de código
 */

export interface Lancamento {
  idLancamento: number;
  data: Date | string;
  idEmpresa: number;
  idCliente: number;
  idPessoa: number;
  nf?: number;
  valorBruto?: any;
  despesas?: any;
  retencao?: any;
  valorLiquido?: any;
  valorTaxa?: any;
  valorRepasse?: any;
  baixa?: Date | string;
  mesAno?: string;
  taxa?: number;
  idTipoServico?: number;
}

export interface LancamentoRequest {
  data: Date | string;
  idEmpresa: number;
  idCliente: number;
  idPessoa: number;
  nf?: number;
  valorBruto?: any;
  despesas?: any;
  retencao?: any;
  valorLiquido?: any;
  valorTaxa?: any;
  valorRepasse?: any;
  baixa?: Date | string;
  mesAno?: string;
  taxa?: number;
  idTipoServico?: number;
}

export interface LancamentoResponse {
  idLancamento: number;
  data: Date | string;
  idEmpresa: number;
  idCliente: number;
  idPessoa: number;
  nf?: number;
  valorBruto?: any;
  despesas?: any;
  retencao?: any;
  valorLiquido?: any;
  valorTaxa?: any;
  valorRepasse?: any;
  baixa?: Date | string;
  mesAno?: string;
  taxa?: number;
  idTipoServico?: number;
}

export interface LancamentoList {
  idLancamento: number;
  data: Date | string;
  idEmpresa: number;
  idCliente: number;
  idPessoa: number;
  nf: number;
  valorBruto: any;
  retencao: any;
  valorLiquido: any;
  valorRepasse: any;
  baixa: Date | string;
}
