package br.com.icep.dto;

/**
 * DTO de listagem para AtendimentoMunicipio.
 */
public record AtendimentoMunicipioListDTO(
    Integer id,
    Integer avaliacaoId,
    Integer municipioId,
    Integer segmentoAtendidoId,
    Integer quantidade
) {}
