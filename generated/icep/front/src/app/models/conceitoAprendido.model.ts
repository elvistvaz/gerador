/**
 * Model: Conceitos de aprendizagem
 * Auto-gerado pelo gerador de código
 */

export interface ConceitoAprendido {
  id: number;
  nome: string;
  ordem?: number;
}

export interface ConceitoAprendidoRequest {
  nome: string;
  ordem?: number;
}

export interface ConceitoAprendidoResponse {
  id: number;
  nome: string;
  ordem?: number;
}

export interface ConceitoAprendidoList {
  id: number;
  nome: string;
  ordem: number;
}
