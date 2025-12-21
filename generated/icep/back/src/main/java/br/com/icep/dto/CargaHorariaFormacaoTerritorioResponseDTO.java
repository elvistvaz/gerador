package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para CargaHorariaFormacaoTerritorio.
 */
public record CargaHorariaFormacaoTerritorioResponseDTO(
    Integer id,
    @NotNull
    Integer avaliacaoId,
    @NotNull
    Integer territorioId,
    @NotNull
    Integer formacaoTerritorioId,
    @NotNull
    Integer cargaHoraria
) {}
