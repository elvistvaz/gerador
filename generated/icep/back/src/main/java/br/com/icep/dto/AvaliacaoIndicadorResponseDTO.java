package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para AvaliacaoIndicador.
 */
public record AvaliacaoIndicadorResponseDTO(
    Integer id,
    @NotNull
    Integer avaliacaoId,
    @NotNull
    Integer municipioId,
    @NotNull
    Integer indicadorId,
    Integer valor
) {}
