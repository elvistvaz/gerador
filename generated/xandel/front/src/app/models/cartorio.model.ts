/**
 * Model: Cadastro de cartórios
 * Auto-gerado pelo gerador de código
 */

export interface Cartorio {
  idCartorio: number;
  nomeCartorio?: string;
  endereco?: string;
  idBairro?: number;
  idCidade?: number;
  telefone?: string;
}

export interface CartorioRequest {
  idCartorio: number;
  nomeCartorio?: string;
  endereco?: string;
  idBairro?: number;
  idCidade?: number;
  telefone?: string;
}

export interface CartorioResponse {
  idCartorio: number;
  nomeCartorio?: string;
  endereco?: string;
  idBairro?: number;
  idCidade?: number;
  telefone?: string;
}

export interface CartorioList {
  idCartorio: number;
  nomeCartorio: string;
  endereco: string;
  idBairro: number;
  idCidade: number;
  telefone: string;
}
