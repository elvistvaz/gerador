/**
 * Model: Anuidades do CREMEB
 * Auto-gerado pelo gerador de código
 */

export interface AnuidadeCremeb {
  idAnuidadeCremeb: number;
  anoExercicio?: string;
  dataInicio?: Date | string;
  dataFim?: Date | string;
  valorTotal?: any;
  idEmpresa?: number;
}

export interface AnuidadeCremebRequest {
  idAnuidadeCremeb: number;
  anoExercicio?: string;
  dataInicio?: Date | string;
  dataFim?: Date | string;
  valorTotal?: any;
  idEmpresa?: number;
}

export interface AnuidadeCremebResponse {
  idAnuidadeCremeb: number;
  anoExercicio?: string;
  dataInicio?: Date | string;
  dataFim?: Date | string;
  valorTotal?: any;
  idEmpresa?: number;
}

export interface AnuidadeCremebList {
  idAnuidadeCremeb: number;
  anoExercicio: string;
  dataInicio: Date | string;
  dataFim: Date | string;
  valorTotal: any;
  idEmpresa: number;
}
