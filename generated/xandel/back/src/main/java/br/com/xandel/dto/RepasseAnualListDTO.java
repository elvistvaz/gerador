package br.com.xandel.dto;

import java.math.BigDecimal;

/**
 * DTO de listagem para RepasseAnual.
 */
public record RepasseAnualListDTO(
    String ano,
    Integer idEmpresa,
    Integer idPessoa,
    BigDecimal janBruto,
    BigDecimal fevBruto,
    BigDecimal marBruto,
    BigDecimal abrBruto,
    BigDecimal maiBruto,
    BigDecimal junBruto
) {}
