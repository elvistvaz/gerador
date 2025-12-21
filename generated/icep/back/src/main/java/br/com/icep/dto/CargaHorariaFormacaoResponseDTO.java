package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para CargaHorariaFormacao.
 */
public record CargaHorariaFormacaoResponseDTO(
    Integer id,
    @NotNull
    Integer avaliacaoId,
    @NotNull
    Integer municipioId,
    @NotNull
    Integer formacaoId,
    @NotNull
    Integer cargaHoraria
) {}
