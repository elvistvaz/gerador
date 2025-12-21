/**
 * Model: Aprendizagens esperadas por componente curricular e conceito
 * Auto-gerado pelo gerador de código
 */

export interface AprendizagemEsperada {
  id: number;
  componenteId: number;
  conceitoAprendidoId: number;
  descricao: string;
  codigo: string;
}

export interface AprendizagemEsperadaRequest {
  componenteId: number;
  conceitoAprendidoId: number;
  descricao: string;
  codigo: string;
}

export interface AprendizagemEsperadaResponse {
  id: number;
  componenteId: number;
  conceitoAprendidoId: number;
  descricao: string;
  codigo: string;
}

export interface AprendizagemEsperadaList {
  id: number;
  componenteId: number;
  conceitoAprendidoId: number;
  descricao: string;
  codigo: string;
}
