package br.com.xandel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de listagem para ImpostoDeRenda.
 */
public record ImpostoDeRendaListDTO(
    LocalDateTime data,
    BigDecimal de,
    BigDecimal ate,
    BigDecimal aliquota,
    BigDecimal valorDeduzir,
    BigDecimal deducaoDependente
) {}
