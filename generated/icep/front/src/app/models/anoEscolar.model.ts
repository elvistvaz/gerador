/**
 * Model: Anos escolares
 * Auto-gerado pelo gerador de código
 */

export interface AnoEscolar {
  id: number;
  nome: string;
  etapa: string;
  ordem?: number;
}

export interface AnoEscolarRequest {
  nome: string;
  etapa: string;
  ordem?: number;
}

export interface AnoEscolarResponse {
  id: number;
  nome: string;
  etapa: string;
  ordem?: number;
}

export interface AnoEscolarList {
  id: number;
  nome: string;
  etapa: string;
  ordem: number;
}
