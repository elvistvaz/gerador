/**
 * Model: Despesas fixas por empresa
 * Auto-gerado pelo gerador de código
 */

export interface EmpresaDespesaFixa {
  idEmpresa: number;
  idDespesaReceita: number;
  dataLancamento?: Date | string;
  parcelas?: number;
  valorEmpresa?: any;
  inativa?: number;
}

export interface EmpresaDespesaFixaRequest {
  idEmpresa: number;
  idDespesaReceita: number;
  dataLancamento?: Date | string;
  parcelas?: number;
  valorEmpresa?: any;
  inativa?: number;
}

export interface EmpresaDespesaFixaResponse {
  idEmpresa: number;
  idDespesaReceita: number;
  dataLancamento?: Date | string;
  parcelas?: number;
  valorEmpresa?: any;
  inativa?: number;
}

export interface EmpresaDespesaFixaList {
  idEmpresa: number;
  idDespesaReceita: number;
  dataLancamento: Date | string;
  parcelas: number;
  valorEmpresa: any;
  inativa: number;
}

export interface EmpresaDespesaFixaId {
  idEmpresa: number;
  idDespesaReceita: number;
}
