package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para AvaliacaoIndicador.
 */
public record AvaliacaoIndicadorRequestDTO(
    @NotNull
    Integer avaliacaoId,
    @NotNull
    Integer municipioId,
    @NotNull
    Integer indicadorId,
    Integer valor
) {}
