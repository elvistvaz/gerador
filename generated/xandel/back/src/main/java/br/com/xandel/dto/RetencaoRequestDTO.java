package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO de request para Retencao.
 */
public record RetencaoRequestDTO(
    Integer idPlanoRetencao,
    BigDecimal ate,
    @NotNull
    BigDecimal reter
) {}
