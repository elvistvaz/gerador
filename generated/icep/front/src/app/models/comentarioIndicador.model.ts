/**
 * Model: Comentários sobre indicadores por município e âmbito
 * Auto-gerado pelo gerador de código
 */

export interface ComentarioIndicador {
  id: number;
  avaliacaoId: number;
  municipioId: number;
  ambitoGestaoId: number;
  comentario: string;
}

export interface ComentarioIndicadorRequest {
  avaliacaoId: number;
  municipioId: number;
  ambitoGestaoId: number;
  comentario: string;
}

export interface ComentarioIndicadorResponse {
  id: number;
  avaliacaoId: number;
  municipioId: number;
  ambitoGestaoId: number;
  comentario: string;
}

export interface ComentarioIndicadorList {
  id: number;
  avaliacaoId: number;
  municipioId: number;
  ambitoGestaoId: number;
  comentario: string;
}
