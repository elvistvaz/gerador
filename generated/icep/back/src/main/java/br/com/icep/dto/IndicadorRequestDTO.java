package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para Indicador.
 */
public record IndicadorRequestDTO(
    @NotNull
    Integer ambitoGestaoId,
    @NotNull
    @NotBlank
    @Size(max = 500)
    String descricao,
    Integer ordem
) {}
