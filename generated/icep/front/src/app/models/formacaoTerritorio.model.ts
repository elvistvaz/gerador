/**
 * Model: Tipos de formação por território
 * Auto-gerado pelo gerador de código
 */

export interface FormacaoTerritorio {
  id: number;
  nome: string;
  ordem?: number;
}

export interface FormacaoTerritorioRequest {
  nome: string;
  ordem?: number;
}

export interface FormacaoTerritorioResponse {
  id: number;
  nome: string;
  ordem?: number;
}

export interface FormacaoTerritorioList {
  id: number;
  nome: string;
  ordem: number;
}
