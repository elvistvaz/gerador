package br.com.xandel.dto;

/**
 * DTO de listagem para PlanoRetencao.
 */
public record PlanoRetencaoListDTO(
    Integer idPlanoRetencao,
    String nomePlanoRetencao,
    Boolean inativo,
    Boolean liberaDespesas
) {}
