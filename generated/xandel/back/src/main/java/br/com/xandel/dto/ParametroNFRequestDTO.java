package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO de request para ParametroNF.
 */
public record ParametroNFRequestDTO(
    BigDecimal cofins,
    BigDecimal pis,
    BigDecimal csll,
    BigDecimal irrf,
    BigDecimal tetoImposto,
    BigDecimal basePisCofinsCsll
) {}
