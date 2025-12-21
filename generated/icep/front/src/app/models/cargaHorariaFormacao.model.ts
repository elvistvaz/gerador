/**
 * Model: Carga horária de formações por município
 * Auto-gerado pelo gerador de código
 */

export interface CargaHorariaFormacao {
  id: number;
  avaliacaoId: number;
  municipioId: number;
  formacaoId: number;
  cargaHoraria: number;
}

export interface CargaHorariaFormacaoRequest {
  avaliacaoId: number;
  municipioId: number;
  formacaoId: number;
  cargaHoraria: number;
}

export interface CargaHorariaFormacaoResponse {
  id: number;
  avaliacaoId: number;
  municipioId: number;
  formacaoId: number;
  cargaHoraria: number;
}

export interface CargaHorariaFormacaoList {
  id: number;
  avaliacaoId: number;
  municipioId: number;
  formacaoId: number;
  cargaHoraria: number;
}
