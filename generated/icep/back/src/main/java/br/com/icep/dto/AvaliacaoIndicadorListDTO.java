package br.com.icep.dto;

/**
 * DTO de listagem para AvaliacaoIndicador.
 */
public record AvaliacaoIndicadorListDTO(
    Integer id,
    Integer avaliacaoId,
    Integer municipioId,
    Integer indicadorId,
    Integer valor
) {}
