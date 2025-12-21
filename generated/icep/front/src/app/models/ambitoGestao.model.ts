/**
 * Model: Âmbitos de gestão para indicadores
 * Auto-gerado pelo gerador de código
 */

export interface AmbitoGestao {
  id: number;
  nome: string;
  ordem?: number;
}

export interface AmbitoGestaoRequest {
  nome: string;
  ordem?: number;
}

export interface AmbitoGestaoResponse {
  id: number;
  nome: string;
  ordem?: number;
}

export interface AmbitoGestaoList {
  id: number;
  nome: string;
  ordem: number;
}
