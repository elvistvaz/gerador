package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de request para EmpresaDespesaFixa.
 */
public record EmpresaDespesaFixaRequestDTO(
    Integer idEmpresa,
    Integer idDespesaReceita,
    LocalDateTime dataLancamento,
    Integer parcelas,
    BigDecimal valorEmpresa,
    Integer inativa
) {}
