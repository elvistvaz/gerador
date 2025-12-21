/**
 * Model: Cadastro de municípios atendidos
 * Auto-gerado pelo gerador de código
 */

export interface Municipio {
  id: number;
  territorioId: number;
  nome: string;
  uf: string;
  quantidadeEscolas?: number;
}

export interface MunicipioRequest {
  territorioId: number;
  nome: string;
  uf: string;
  quantidadeEscolas?: number;
}

export interface MunicipioResponse {
  id: number;
  territorioId: number;
  nome: string;
  uf: string;
  quantidadeEscolas?: number;
}

export interface MunicipioList {
  id: number;
  territorioId: number;
  nome: string;
  uf: string;
  quantidadeEscolas: number;
}
