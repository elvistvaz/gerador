package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para Avaliacao.
 */
public record AvaliacaoRequestDTO(
    @NotNull
    @NotBlank
    @Size(max = 6)
    String avaliacao
) {}
