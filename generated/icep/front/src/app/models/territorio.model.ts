/**
 * Model: Cadastro de territórios
 * Auto-gerado pelo gerador de código
 */

export interface Territorio {
  id: number;
  nome: string;
  descricao?: string;
}

export interface TerritorioRequest {
  nome: string;
  descricao?: string;
}

export interface TerritorioResponse {
  id: number;
  nome: string;
  descricao?: string;
}

export interface TerritorioList {
  id: number;
  nome: string;
  descricao: string;
}
