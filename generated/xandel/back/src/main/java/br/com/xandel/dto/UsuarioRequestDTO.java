package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * DTO de request para Usuario.
 */
public record UsuarioRequestDTO(
    Integer idUsuario,
    @NotNull
    @NotBlank
    @Size(max = 60)
    String usuarioNome,
    @NotNull
    @NotBlank
    @Size(max = 30)
    String usuarioLogin,
    @NotNull
    @NotBlank
    @Size(max = 50)
    String usuarioSenha,
    Integer usuarioNivelAcesso,
    Boolean usuarioInativo,
    LocalDateTime usuarioDataInicio
) {}
