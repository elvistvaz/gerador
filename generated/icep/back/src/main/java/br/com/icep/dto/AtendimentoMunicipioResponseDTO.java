package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para AtendimentoMunicipio.
 */
public record AtendimentoMunicipioResponseDTO(
    Integer id,
    @NotNull
    Integer avaliacaoId,
    @NotNull
    Integer municipioId,
    @NotNull
    Integer segmentoAtendidoId,
    @NotNull
    Integer quantidade
) {}
