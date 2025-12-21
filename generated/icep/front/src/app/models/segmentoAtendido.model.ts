/**
 * Model: Segmentos atendidos por público alvo
 * Auto-gerado pelo gerador de código
 */

export interface SegmentoAtendido {
  id: number;
  publicoAlvoId: number;
  nome: string;
  ordem?: number;
}

export interface SegmentoAtendidoRequest {
  publicoAlvoId: number;
  nome: string;
  ordem?: number;
}

export interface SegmentoAtendidoResponse {
  id: number;
  publicoAlvoId: number;
  nome: string;
  ordem?: number;
}

export interface SegmentoAtendidoList {
  id: number;
  publicoAlvoId: number;
  nome: string;
  ordem: number;
}
