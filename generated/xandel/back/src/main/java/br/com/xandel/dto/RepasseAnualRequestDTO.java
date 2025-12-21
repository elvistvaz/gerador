package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO de request para RepasseAnual.
 */
public record RepasseAnualRequestDTO(
    @Size(max = 4)
    String ano,
    Integer idEmpresa,
    Integer idPessoa,
    BigDecimal janBruto,
    BigDecimal janTaxa,
    BigDecimal fevBruto,
    BigDecimal fevTaxa,
    BigDecimal marBruto,
    BigDecimal marTaxa,
    BigDecimal abrBruto,
    BigDecimal abrTaxa,
    BigDecimal maiBruto,
    BigDecimal maiTaxa,
    BigDecimal junBruto,
    BigDecimal junTaxa,
    BigDecimal julBruto,
    BigDecimal julTaxa,
    BigDecimal agoBruto,
    BigDecimal agoTaxa,
    BigDecimal setBruto,
    BigDecimal setTaxa,
    BigDecimal outBruto,
    BigDecimal outTaxa,
    BigDecimal novBruto,
    BigDecimal novTaxa,
    BigDecimal dezBruto,
    BigDecimal dezTaxa
) {}
