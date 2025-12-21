package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * DTO de response para Usuario.
 */
public record UsuarioResponseDTO(
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
