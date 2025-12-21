/**
 * Model: Tipos de formação
 * Auto-gerado pelo gerador de código
 */

export interface Formacao {
  id: number;
  nome: string;
  ordem?: number;
}

export interface FormacaoRequest {
  nome: string;
  ordem?: number;
}

export interface FormacaoResponse {
  id: number;
  nome: string;
  ordem?: number;
}

export interface FormacaoList {
  id: number;
  nome: string;
  ordem: number;
}
