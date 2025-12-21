package br.com.xandel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de listagem para EmpresaDespesaFixa.
 */
public record EmpresaDespesaFixaListDTO(
    Integer idEmpresa,
    Integer idDespesaReceita,
    LocalDateTime dataLancamento,
    Integer parcelas,
    BigDecimal valorEmpresa,
    Integer inativa
) {}
