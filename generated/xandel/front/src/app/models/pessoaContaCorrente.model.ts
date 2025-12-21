/**
 * Model: Contas correntes da pessoa
 * Auto-gerado pelo gerador de código
 */

export interface PessoaContaCorrente {
  idContaCorrente: number;
  idPessoa?: number;
  idBanco?: string;
  agencia?: string;
  contaCorrente?: string;
  dvContaCorrente?: string;
  ativa?: boolean;
  contaPoupanca?: boolean;
  nomeDependente?: string;
  contaPadrao?: boolean;
  pix?: string;
}

export interface PessoaContaCorrenteRequest {
  idPessoa?: number;
  idBanco?: string;
  agencia?: string;
  contaCorrente?: string;
  dvContaCorrente?: string;
  ativa?: boolean;
  contaPoupanca?: boolean;
  nomeDependente?: string;
  contaPadrao?: boolean;
  pix?: string;
}

export interface PessoaContaCorrenteResponse {
  idContaCorrente: number;
  idPessoa?: number;
  idBanco?: string;
  agencia?: string;
  contaCorrente?: string;
  dvContaCorrente?: string;
  ativa?: boolean;
  contaPoupanca?: boolean;
  nomeDependente?: string;
  contaPadrao?: boolean;
  pix?: string;
}

export interface PessoaContaCorrenteList {
  idContaCorrente: number;
  idBanco: string;
  agencia: string;
  contaCorrente: string;
  dvContaCorrente: string;
  ativa: boolean;
  contaPoupanca: boolean;
  contaPadrao: boolean;
  pix: string;
}
