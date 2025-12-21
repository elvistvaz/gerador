/**
 * Model: Programas de indicação
 * Auto-gerado pelo gerador de código
 */

export interface Indicacao {
  idIndicacao: number;
  de?: Date | string;
  ate?: Date | string;
  indicacaoMinima?: number;
  indicacaoMaxima?: number;
  indice?: number;
  tetoIndice?: number;
  grupoIndicados?: number;
}

export interface IndicacaoRequest {
  idIndicacao: number;
  de?: Date | string;
  ate?: Date | string;
  indicacaoMinima?: number;
  indicacaoMaxima?: number;
  indice?: number;
  tetoIndice?: number;
  grupoIndicados?: number;
}

export interface IndicacaoResponse {
  idIndicacao: number;
  de?: Date | string;
  ate?: Date | string;
  indicacaoMinima?: number;
  indicacaoMaxima?: number;
  indice?: number;
  tetoIndice?: number;
  grupoIndicados?: number;
}

export interface IndicacaoList {
  idIndicacao: number;
  de: Date | string;
  ate: Date | string;
  indicacaoMinima: number;
  indicacaoMaxima: number;
  indice: number;
  tetoIndice: number;
  grupoIndicados: number;
}
