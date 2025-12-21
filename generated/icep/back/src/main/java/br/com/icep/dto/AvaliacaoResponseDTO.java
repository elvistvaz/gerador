package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para Avaliacao.
 */
public record AvaliacaoResponseDTO(
    Integer id,
    @NotNull
    @NotBlank
    @Size(max = 6)
    String avaliacao
) {}
