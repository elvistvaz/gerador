package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de request para ImpostoDeRenda.
 */
public record ImpostoDeRendaRequestDTO(
    LocalDateTime data,
    BigDecimal de,
    BigDecimal ate,
    BigDecimal aliquota,
    BigDecimal valorDeduzir,
    BigDecimal deducaoDependente
) {}
