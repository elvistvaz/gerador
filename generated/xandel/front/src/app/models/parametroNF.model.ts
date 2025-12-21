/**
 * Model: Parâmetros de Nota Fiscal
 * Auto-gerado pelo gerador de código
 */

export interface ParametroNF {
  id?: number;
  cofins?: number;
  pis?: number;
  csll?: number;
  irrf?: number;
  tetoImposto?: number;
  basePisCofinsCsll?: any;
}

export interface ParametroNFRequest {
  cofins?: number;
  pis?: number;
  csll?: number;
  irrf?: number;
  tetoImposto?: number;
  basePisCofinsCsll?: any;
}

export interface ParametroNFResponse {
  id?: number;
  cofins?: number;
  pis?: number;
  csll?: number;
  irrf?: number;
  tetoImposto?: number;
  basePisCofinsCsll?: any;
}

export interface ParametroNFList {
  id: number;
  cofins: number;
  pis: number;
  csll: number;
  irrf: number;
  tetoImposto: number;
  basePisCofinsCsll: any;
}
