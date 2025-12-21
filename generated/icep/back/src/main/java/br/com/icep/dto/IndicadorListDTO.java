package br.com.icep.dto;

/**
 * DTO de listagem para Indicador.
 */
public record IndicadorListDTO(
    Integer id,
    Integer ambitoGestaoId,
    String descricao,
    Integer ordem
) {}
