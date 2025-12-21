package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO de response para ParametroNF.
 */
public record ParametroNFResponseDTO(
    Long id,
    BigDecimal cofins,
    BigDecimal pis,
    BigDecimal csll,
    BigDecimal irrf,
    BigDecimal tetoImposto,
    BigDecimal basePisCofinsCsll
) {}
