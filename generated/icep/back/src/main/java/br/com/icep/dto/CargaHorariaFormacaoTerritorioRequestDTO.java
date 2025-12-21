package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para CargaHorariaFormacaoTerritorio.
 */
public record CargaHorariaFormacaoTerritorioRequestDTO(
    @NotNull
    Integer avaliacaoId,
    @NotNull
    Integer territorioId,
    @NotNull
    Integer formacaoTerritorioId,
    @NotNull
    Integer cargaHoraria
) {}
