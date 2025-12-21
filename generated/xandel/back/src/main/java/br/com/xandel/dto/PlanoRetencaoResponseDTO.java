package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para PlanoRetencao.
 */
public record PlanoRetencaoResponseDTO(
    Integer idPlanoRetencao,
    @Size(max = 50)
    String nomePlanoRetencao,
    Boolean inativo,
    Boolean liberaDespesas
) {}
