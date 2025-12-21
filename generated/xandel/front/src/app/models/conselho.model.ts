/**
 * Model: Cadastro de conselhos profissionais
 * Auto-gerado pelo gerador de código
 */

export interface Conselho {
  idConselho: number;
  nomeConselho?: string;
  sigla?: string;
}

export interface ConselhoRequest {
  idConselho: number;
  nomeConselho?: string;
  sigla?: string;
}

export interface ConselhoResponse {
  idConselho: number;
  nomeConselho?: string;
  sigla?: string;
}

export interface ConselhoList {
  idConselho: number;
  nomeConselho: string;
  sigla: string;
}
