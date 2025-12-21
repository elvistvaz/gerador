package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para PublicoAlvo.
 */
public record PublicoAlvoRequestDTO(
    @NotNull
    @NotBlank
    @Size(max = 100)
    String nome,
    @NotNull
    @NotBlank
    @Size(max = 30)
    String tipo
) {}
