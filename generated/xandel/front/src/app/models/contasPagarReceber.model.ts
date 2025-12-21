/**
 * Model: Contas a pagar e receber
 * Auto-gerado pelo gerador de código
 */

export interface ContasPagarReceber {
  idContasPagarReceber: number;
  dataLancamento: Date | string;
  valorOriginal: any;
  valorParcela: any;
  dataVencimento: Date | string;
  dataBaixa?: Date | string;
  valorBaixado?: any;
  idEmpresa?: number;
  idPessoa?: number;
  mesAnoReferencia?: string;
  historico?: string;
  idDespesaReceita?: number;
}

export interface ContasPagarReceberRequest {
  dataLancamento: Date | string;
  valorOriginal: any;
  valorParcela: any;
  dataVencimento: Date | string;
  dataBaixa?: Date | string;
  valorBaixado?: any;
  idEmpresa?: number;
  idPessoa?: number;
  mesAnoReferencia?: string;
  historico?: string;
  idDespesaReceita?: number;
}

export interface ContasPagarReceberResponse {
  idContasPagarReceber: number;
  dataLancamento: Date | string;
  valorOriginal: any;
  valorParcela: any;
  dataVencimento: Date | string;
  dataBaixa?: Date | string;
  valorBaixado?: any;
  idEmpresa?: number;
  idPessoa?: number;
  mesAnoReferencia?: string;
  historico?: string;
  idDespesaReceita?: number;
}

export interface ContasPagarReceberList {
  idContasPagarReceber: number;
  dataLancamento: Date | string;
  valorOriginal: any;
  valorParcela: any;
  dataVencimento: Date | string;
  dataBaixa: Date | string;
  valorBaixado: any;
  idEmpresa: number;
  historico: string;
  idDespesaReceita: number;
}
