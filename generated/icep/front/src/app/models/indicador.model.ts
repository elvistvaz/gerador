/**
 * Model: Indicadores de acompanhamento
 * Auto-gerado pelo gerador de código
 */

export interface Indicador {
  id: number;
  ambitoGestaoId: number;
  descricao: string;
  ordem?: number;
}

export interface IndicadorRequest {
  ambitoGestaoId: number;
  descricao: string;
  ordem?: number;
}

export interface IndicadorResponse {
  id: number;
  ambitoGestaoId: number;
  descricao: string;
  ordem?: number;
}

export interface IndicadorList {
  id: number;
  ambitoGestaoId: number;
  descricao: string;
  ordem: number;
}
