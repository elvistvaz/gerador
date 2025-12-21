/**
 * Model: Tabela de Imposto de Renda
 * Auto-gerado pelo gerador de código
 */

export interface ImpostoDeRenda {
  data: Date | string;
  de: any;
  ate?: any;
  aliquota?: number;
  valorDeduzir?: any;
  deducaoDependente?: any;
}

export interface ImpostoDeRendaRequest {
  data: Date | string;
  de: any;
  ate?: any;
  aliquota?: number;
  valorDeduzir?: any;
  deducaoDependente?: any;
}

export interface ImpostoDeRendaResponse {
  data: Date | string;
  de: any;
  ate?: any;
  aliquota?: number;
  valorDeduzir?: any;
  deducaoDependente?: any;
}

export interface ImpostoDeRendaList {
  data: Date | string;
  de: any;
  ate: any;
  aliquota: number;
  valorDeduzir: any;
  deducaoDependente: any;
}

export interface ImpostoDeRendaId {
  data: Date | string;
  de: any;
}
