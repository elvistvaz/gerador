/**
 * Model: Cadastro de cidades
 * Auto-gerado pelo gerador de código
 */

export interface Cidade {
  idCidade: number;
  nomeCidade?: string;
  uf?: string;
  ddd?: string;
  iss?: number;
}

export interface CidadeRequest {
  idCidade: number;
  nomeCidade?: string;
  uf?: string;
  ddd?: string;
  iss?: number;
}

export interface CidadeResponse {
  idCidade: number;
  nomeCidade?: string;
  uf?: string;
  ddd?: string;
  iss?: number;
}

export interface CidadeList {
  idCidade: number;
  nomeCidade: string;
  uf: string;
  ddd: string;
  iss: number;
}
