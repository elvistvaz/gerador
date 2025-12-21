/**
 * Model: Parâmetros de configuração de e-mail
 * Auto-gerado pelo gerador de código
 */

export interface ParametroEmail {
  idParametro: number;
  assuntoEmail?: string;
  smtp?: string;
  contaEmail?: string;
  senhaEmail?: string;
}

export interface ParametroEmailRequest {
  idParametro: number;
  assuntoEmail?: string;
  smtp?: string;
  contaEmail?: string;
  senhaEmail?: string;
}

export interface ParametroEmailResponse {
  idParametro: number;
  assuntoEmail?: string;
  smtp?: string;
  contaEmail?: string;
  senhaEmail?: string;
}

export interface ParametroEmailList {
  idParametro: number;
  assuntoEmail: string;
  smtp: string;
  contaEmail: string;
}
