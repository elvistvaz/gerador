package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para PlanoRetencao.
 */
public record PlanoRetencaoRequestDTO(
    Integer idPlanoRetencao,
    @Size(max = 50)
    String nomePlanoRetencao,
    Boolean inativo,
    Boolean liberaDespesas
) {}
