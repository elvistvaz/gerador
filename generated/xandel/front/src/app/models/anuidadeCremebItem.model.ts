/**
 * Model: Itens individuais da anuidade CREMEB
 * Auto-gerado pelo gerador de código
 */

export interface AnuidadeCremebItem {
  idAnuidadeCremebItem: number;
  idAnuidadeCremeb?: number;
  idPessoa?: number;
  dataLancamento?: Date | string;
  valorIndividual?: any;
  dataPagamento?: string;
  idLancamento?: number;
}

export interface AnuidadeCremebItemRequest {
  idAnuidadeCremebItem: number;
  idAnuidadeCremeb?: number;
  idPessoa?: number;
  dataLancamento?: Date | string;
  valorIndividual?: any;
  dataPagamento?: string;
  idLancamento?: number;
}

export interface AnuidadeCremebItemResponse {
  idAnuidadeCremebItem: number;
  idAnuidadeCremeb?: number;
  idPessoa?: number;
  dataLancamento?: Date | string;
  valorIndividual?: any;
  dataPagamento?: string;
  idLancamento?: number;
}

export interface AnuidadeCremebItemList {
  idAnuidadeCremebItem: number;
  idPessoa: number;
  dataLancamento: Date | string;
  valorIndividual: any;
  dataPagamento: string;
}
