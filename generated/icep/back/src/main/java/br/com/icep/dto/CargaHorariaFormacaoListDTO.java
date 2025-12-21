package br.com.icep.dto;

/**
 * DTO de listagem para CargaHorariaFormacao.
 */
public record CargaHorariaFormacaoListDTO(
    Integer id,
    Integer avaliacaoId,
    Integer municipioId,
    Integer formacaoId,
    Integer cargaHoraria
) {}
