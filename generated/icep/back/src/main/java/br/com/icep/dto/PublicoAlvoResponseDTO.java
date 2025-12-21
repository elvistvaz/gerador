package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para PublicoAlvo.
 */
public record PublicoAlvoResponseDTO(
    Integer id,
    @NotNull
    @NotBlank
    @Size(max = 100)
    String nome,
    @NotNull
    @NotBlank
    @Size(max = 30)
    String tipo
) {}
