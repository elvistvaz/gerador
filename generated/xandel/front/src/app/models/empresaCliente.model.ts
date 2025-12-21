/**
 * Model: Relacionamento empresa-cliente com taxas
 * Auto-gerado pelo gerador de código
 */

export interface EmpresaCliente {
  idEmpresa: number;
  idCliente: number;
  taxa?: number;
  processo?: string;
  taxaISS?: number;
  taxaCOFINS?: number;
  taxaPIS?: number;
  taxaCSLL?: number;
  taxaIRRF?: number;
}

export interface EmpresaClienteRequest {
  idEmpresa: number;
  idCliente: number;
  taxa?: number;
  processo?: string;
  taxaISS?: number;
  taxaCOFINS?: number;
  taxaPIS?: number;
  taxaCSLL?: number;
  taxaIRRF?: number;
}

export interface EmpresaClienteResponse {
  idEmpresa: number;
  idCliente: number;
  taxa?: number;
  processo?: string;
  taxaISS?: number;
  taxaCOFINS?: number;
  taxaPIS?: number;
  taxaCSLL?: number;
  taxaIRRF?: number;
}

export interface EmpresaClienteList {
  idEmpresa: number;
  idCliente: number;
  taxa: number;
  processo: string;
  taxaISS: number;
  taxaCOFINS: number;
  taxaPIS: number;
  taxaCSLL: number;
  taxaIRRF: number;
}

export interface EmpresaClienteId {
  idEmpresa: number;
  idCliente: number;
}
