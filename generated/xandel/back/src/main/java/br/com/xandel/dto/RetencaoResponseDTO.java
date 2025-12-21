package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO de response para Retencao.
 */
public record RetencaoResponseDTO(
    Integer idPlanoRetencao,
    BigDecimal ate,
    @NotNull
    BigDecimal reter
) {}
