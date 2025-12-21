/**
 * Model: Atendimentos por município e segmento
 * Auto-gerado pelo gerador de código
 */

export interface AtendimentoMunicipio {
  id: number;
  avaliacaoId: number;
  municipioId: number;
  segmentoAtendidoId: number;
  quantidade: number;
}

export interface AtendimentoMunicipioRequest {
  avaliacaoId: number;
  municipioId: number;
  segmentoAtendidoId: number;
  quantidade: number;
}

export interface AtendimentoMunicipioResponse {
  id: number;
  avaliacaoId: number;
  municipioId: number;
  segmentoAtendidoId: number;
  quantidade: number;
}

export interface AtendimentoMunicipioList {
  id: number;
  avaliacaoId: number;
  municipioId: number;
  segmentoAtendidoId: number;
  quantidade: number;
}
