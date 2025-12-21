/**
 * Model: Cadastro de pessoas (sócios/médicos)
 * Auto-gerado pelo gerador de código
 */

export interface Pessoa {
  idPessoa: number;
  nome?: string;
  cpf?: string;
  idConselho?: number;
  numeroConselho?: number;
  nascimento?: Date | string;
  rg?: string;
  email?: string;
  telefone?: string;
  celular?: string;
  endereco?: string;
  idBairro?: number;
  idCidade?: number;
  cep?: string;
  idEstadoCivil?: number;
  dataAdesao?: Date | string;
  dataInativo?: Date | string;
  idBanco?: string;
  agencia?: string;
  conta?: string;
  idPlanoRetencao?: number;
  idOperadora?: number;
}

export interface PessoaRequest {
  idPessoa: number;
  nome?: string;
  cpf?: string;
  idConselho?: number;
  numeroConselho?: number;
  nascimento?: Date | string;
  rg?: string;
  email?: string;
  telefone?: string;
  celular?: string;
  endereco?: string;
  idBairro?: number;
  idCidade?: number;
  cep?: string;
  idEstadoCivil?: number;
  dataAdesao?: Date | string;
  dataInativo?: Date | string;
  idBanco?: string;
  agencia?: string;
  conta?: string;
  idPlanoRetencao?: number;
  idOperadora?: number;
}

export interface PessoaResponse {
  idPessoa: number;
  nome?: string;
  cpf?: string;
  idConselho?: number;
  numeroConselho?: number;
  nascimento?: Date | string;
  rg?: string;
  email?: string;
  telefone?: string;
  celular?: string;
  endereco?: string;
  idBairro?: number;
  idCidade?: number;
  cep?: string;
  idEstadoCivil?: number;
  dataAdesao?: Date | string;
  dataInativo?: Date | string;
  idBanco?: string;
  agencia?: string;
  conta?: string;
  idPlanoRetencao?: number;
  idOperadora?: number;
}

export interface PessoaList {
  idPessoa: number;
  nome: string;
  cpf: string;
  idConselho: number;
  numeroConselho: number;
  nascimento: Date | string;
  email: string;
  celular: string;
  dataAdesao: Date | string;
  dataInativo: Date | string;
}
