package br.com.icep.dto;

/**
 * DTO de listagem para CargaHorariaFormacaoTerritorio.
 */
public record CargaHorariaFormacaoTerritorioListDTO(
    Integer id,
    Integer avaliacaoId,
    Integer territorioId,
    Integer formacaoTerritorioId,
    Integer cargaHoraria
) {}
