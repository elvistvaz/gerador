package br.com.xandel.dto;

import java.math.BigDecimal;

/**
 * DTO de listagem para Retencao.
 */
public record RetencaoListDTO(
    Integer idPlanoRetencao,
    BigDecimal ate,
    BigDecimal reter
) {}
