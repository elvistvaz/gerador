package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para AtendimentoMunicipio.
 */
public record AtendimentoMunicipioRequestDTO(
    @NotNull
    Integer avaliacaoId,
    @NotNull
    Integer municipioId,
    @NotNull
    Integer segmentoAtendidoId,
    @NotNull
    Integer quantidade
) {}
