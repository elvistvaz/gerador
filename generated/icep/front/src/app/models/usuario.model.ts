export interface Usuario {
  id: number;
  username: string;
  nome: string;
  email: string;
  ativo: boolean;
  perfilId: string;
  perfilNome: string;
  dataCriacao: string;
  ultimoAcesso: string;
}

export interface UsuarioRequest {
  username: string;
  senha?: string;
  nome: string;
  email?: string;
  ativo: boolean;
  perfilId: string;
}

export interface UsuarioList {
  id: number;
  username: string;
  nome: string;
  email: string;
  ativo: boolean;
  perfilNome: string;
}
