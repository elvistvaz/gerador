/**
 * Model: Sócios da empresa
 * Auto-gerado pelo gerador de código
 */

export interface EmpresaSocio {
  idEmpresa: number;
  idPessoa: number;
  dataAdesao?: Date | string;
  numeroQuotas?: number;
  valorQuota?: any;
  dataSaida?: Date | string;
  valorCremeb?: any;
  novoModelo: boolean;
  taxaConsulta?: number;
  taxaProcedimento?: number;
}

export interface EmpresaSocioRequest {
  idEmpresa: number;
  idPessoa: number;
  dataAdesao?: Date | string;
  numeroQuotas?: number;
  valorQuota?: any;
  dataSaida?: Date | string;
  valorCremeb?: any;
  novoModelo: boolean;
  taxaConsulta?: number;
  taxaProcedimento?: number;
}

export interface EmpresaSocioResponse {
  idEmpresa: number;
  idPessoa: number;
  dataAdesao?: Date | string;
  numeroQuotas?: number;
  valorQuota?: any;
  dataSaida?: Date | string;
  valorCremeb?: any;
  novoModelo: boolean;
  taxaConsulta?: number;
  taxaProcedimento?: number;
}

export interface EmpresaSocioList {
  idEmpresa: number;
  idPessoa: number;
  dataAdesao: Date | string;
  numeroQuotas: number;
  valorQuota: any;
  dataSaida: Date | string;
  valorCremeb: any;
  novoModelo: boolean;
  taxaConsulta: number;
  taxaProcedimento: number;
}

export interface EmpresaSocioId {
  idEmpresa: number;
  idPessoa: number;
}
