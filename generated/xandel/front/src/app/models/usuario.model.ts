/**
 * Model: Cadastro de usuários do sistema
 * Auto-gerado pelo gerador de código
 */

export interface Usuario {
  idUsuario: number;
  usuarioNome: string;
  usuarioLogin: string;
  usuarioSenha: string;
  usuarioNivelAcesso?: number;
  usuarioInativo?: boolean;
  usuarioDataInicio?: Date | string;
}

export interface UsuarioRequest {
  idUsuario: number;
  usuarioNome: string;
  usuarioLogin: string;
  usuarioSenha: string;
  usuarioNivelAcesso?: number;
  usuarioInativo?: boolean;
  usuarioDataInicio?: Date | string;
}

export interface UsuarioResponse {
  idUsuario: number;
  usuarioNome: string;
  usuarioLogin: string;
  usuarioSenha: string;
  usuarioNivelAcesso?: number;
  usuarioInativo?: boolean;
  usuarioDataInicio?: Date | string;
}

export interface UsuarioList {
  idUsuario: number;
  usuarioNome: string;
  usuarioLogin: string;
  usuarioNivelAcesso: number;
  usuarioInativo: boolean;
  usuarioDataInicio: Date | string;
}
