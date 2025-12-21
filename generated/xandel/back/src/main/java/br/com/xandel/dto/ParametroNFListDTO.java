package br.com.xandel.dto;

import java.math.BigDecimal;

/**
 * DTO de listagem para ParametroNF.
 */
public record ParametroNFListDTO(
    Long id,
    BigDecimal cofins,
    BigDecimal pis,
    BigDecimal csll,
    BigDecimal irrf,
    BigDecimal tetoImposto,
    BigDecimal basePisCofinsCsll
) {}
