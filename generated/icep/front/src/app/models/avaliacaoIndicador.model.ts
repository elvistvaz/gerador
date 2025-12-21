/**
 * Model: Avaliação de indicadores por município
 * Auto-gerado pelo gerador de código
 */

export interface AvaliacaoIndicador {
  id: number;
  avaliacaoId: number;
  municipioId: number;
  indicadorId: number;
  valor?: number;
}

export interface AvaliacaoIndicadorRequest {
  avaliacaoId: number;
  municipioId: number;
  indicadorId: number;
  valor?: number;
}

export interface AvaliacaoIndicadorResponse {
  id: number;
  avaliacaoId: number;
  municipioId: number;
  indicadorId: number;
  valor?: number;
}

export interface AvaliacaoIndicadorList {
  id: number;
  avaliacaoId: number;
  municipioId: number;
  indicadorId: number;
  valor: number;
}
