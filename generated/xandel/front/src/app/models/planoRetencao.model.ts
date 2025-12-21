/**
 * Model: Cadastro de planos de retenção
 * Auto-gerado pelo gerador de código
 */

export interface PlanoRetencao {
  idPlanoRetencao: number;
  nomePlanoRetencao?: string;
  inativo?: boolean;
  liberaDespesas?: boolean;
}

export interface PlanoRetencaoRequest {
  idPlanoRetencao: number;
  nomePlanoRetencao?: string;
  inativo?: boolean;
  liberaDespesas?: boolean;
}

export interface PlanoRetencaoResponse {
  idPlanoRetencao: number;
  nomePlanoRetencao?: string;
  inativo?: boolean;
  liberaDespesas?: boolean;
}

export interface PlanoRetencaoList {
  idPlanoRetencao: number;
  nomePlanoRetencao: string;
  inativo: boolean;
  liberaDespesas: boolean;
}
