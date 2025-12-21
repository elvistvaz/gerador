/**
 * Model: Cadastro de clientes
 * Auto-gerado pelo gerador de código
 */

export interface Cliente {
  idCliente: number;
  nomeCliente: string;
  fantasiaCliente?: string;
  cnpj?: string;
  dataContrato?: Date | string;
  taxaADM?: number;
  endereco?: string;
  cep?: string;
  idBairro?: number;
  idCidade?: number;
  contato?: string;
  fone1?: string;
  fone2?: string;
  email?: string;
  pessoaJuridica?: number;
}

export interface ClienteRequest {
  idCliente: number;
  nomeCliente: string;
  fantasiaCliente?: string;
  cnpj?: string;
  dataContrato?: Date | string;
  taxaADM?: number;
  endereco?: string;
  cep?: string;
  idBairro?: number;
  idCidade?: number;
  contato?: string;
  fone1?: string;
  fone2?: string;
  email?: string;
  pessoaJuridica?: number;
}

export interface ClienteResponse {
  idCliente: number;
  nomeCliente: string;
  fantasiaCliente?: string;
  cnpj?: string;
  dataContrato?: Date | string;
  taxaADM?: number;
  endereco?: string;
  cep?: string;
  idBairro?: number;
  idCidade?: number;
  contato?: string;
  fone1?: string;
  fone2?: string;
  email?: string;
  pessoaJuridica?: number;
}

export interface ClienteList {
  idCliente: number;
  nomeCliente: string;
  fantasiaCliente: string;
  cnpj: string;
  dataContrato: Date | string;
  taxaADM: number;
  contato: string;
  email: string;
}
