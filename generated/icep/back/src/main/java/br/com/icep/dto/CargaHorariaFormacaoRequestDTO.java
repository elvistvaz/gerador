package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para CargaHorariaFormacao.
 */
public record CargaHorariaFormacaoRequestDTO(
    @NotNull
    Integer avaliacaoId,
    @NotNull
    Integer municipioId,
    @NotNull
    Integer formacaoId,
    @NotNull
    Integer cargaHoraria
) {}
