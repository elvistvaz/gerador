/**
 * Model: Cadastro de empresas
 * Auto-gerado pelo gerador de código
 */

export interface Empresa {
  idEmpresa: number;
  nomeEmpresa: string;
  fantasiaEmpresa?: string;
  cnpj?: string;
  taxaRetencao: number;
  inativa?: boolean;
}

export interface EmpresaRequest {
  idEmpresa: number;
  nomeEmpresa: string;
  fantasiaEmpresa?: string;
  cnpj?: string;
  taxaRetencao: number;
  inativa?: boolean;
}

export interface EmpresaResponse {
  idEmpresa: number;
  nomeEmpresa: string;
  fantasiaEmpresa?: string;
  cnpj?: string;
  taxaRetencao: number;
  inativa?: boolean;
}

export interface EmpresaList {
  idEmpresa: number;
  nomeEmpresa: string;
  fantasiaEmpresa: string;
  cnpj: string;
  taxaRetencao: number;
  inativa: boolean;
}
