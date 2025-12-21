/**
 * Model: Cadastro de despesas e receitas
 * Auto-gerado pelo gerador de código
 */

export interface DespesaReceita {
  idDespesaReceita: number;
  siglaDespesaReceita: string;
  nomeDespesaReceita: string;
  despesa: number;
  temRateio: number;
  inativa: number;
  valor?: any;
  parcelas?: number;
}

export interface DespesaReceitaRequest {
  idDespesaReceita: number;
  siglaDespesaReceita: string;
  nomeDespesaReceita: string;
  despesa: number;
  temRateio: number;
  inativa: number;
  valor?: any;
  parcelas?: number;
}

export interface DespesaReceitaResponse {
  idDespesaReceita: number;
  siglaDespesaReceita: string;
  nomeDespesaReceita: string;
  despesa: number;
  temRateio: number;
  inativa: number;
  valor?: any;
  parcelas?: number;
}

export interface DespesaReceitaList {
  idDespesaReceita: number;
  siglaDespesaReceita: string;
  nomeDespesaReceita: string;
  despesa: number;
  temRateio: number;
  inativa: number;
  valor: any;
  parcelas: number;
}
