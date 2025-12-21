/**
 * Model: Avaliação de Situações Didáticas Avaliativas
 * Auto-gerado pelo gerador de código
 */

export interface AvaliacaoSDA {
  id: number;
  avaliacaoId: number;
  municipioId: number;
  anoEscolarId: number;
  aprendizagemId: number;
  nivel: number;
  peso: number;
  quantidadeAlunos: number;
}

export interface AvaliacaoSDARequest {
  avaliacaoId: number;
  municipioId: number;
  anoEscolarId: number;
  aprendizagemId: number;
  nivel: number;
  peso: number;
  quantidadeAlunos: number;
}

export interface AvaliacaoSDAResponse {
  id: number;
  avaliacaoId: number;
  municipioId: number;
  anoEscolarId: number;
  aprendizagemId: number;
  nivel: number;
  peso: number;
  quantidadeAlunos: number;
}

export interface AvaliacaoSDAList {
  id: number;
  avaliacaoId: number;
  municipioId: number;
  anoEscolarId: number;
  aprendizagemId: number;
  nivel: number;
  peso: number;
  quantidadeAlunos: number;
}
