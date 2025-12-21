/**
 * Model: Carga horária de formações por território
 * Auto-gerado pelo gerador de código
 */

export interface CargaHorariaFormacaoTerritorio {
  id: number;
  avaliacaoId: number;
  territorioId: number;
  formacaoTerritorioId: number;
  cargaHoraria: number;
}

export interface CargaHorariaFormacaoTerritorioRequest {
  avaliacaoId: number;
  territorioId: number;
  formacaoTerritorioId: number;
  cargaHoraria: number;
}

export interface CargaHorariaFormacaoTerritorioResponse {
  id: number;
  avaliacaoId: number;
  territorioId: number;
  formacaoTerritorioId: number;
  cargaHoraria: number;
}

export interface CargaHorariaFormacaoTerritorioList {
  id: number;
  avaliacaoId: number;
  territorioId: number;
  formacaoTerritorioId: number;
  cargaHoraria: number;
}
