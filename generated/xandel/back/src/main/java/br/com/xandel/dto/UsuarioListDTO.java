package br.com.xandel.dto;

import java.time.LocalDateTime;

/**
 * DTO de listagem para Usuario.
 */
public record UsuarioListDTO(
    Integer idUsuario,
    String usuarioNome,
    String usuarioLogin,
    Integer usuarioNivelAcesso,
    Boolean usuarioInativo,
    LocalDateTime usuarioDataInicio
) {}
