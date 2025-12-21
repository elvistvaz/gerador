package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de request para NF.
 */
public record NFRequestDTO(
    Integer idEmpresa,
    Integer nf,
    @NotNull
    Integer idCliente,
    @NotNull
    LocalDateTime emissao,
    LocalDateTime vencimento,
    BigDecimal total,
    BigDecimal irrf,
    BigDecimal pis,
    BigDecimal cofins,
    BigDecimal csll,
    BigDecimal iss,
    LocalDateTime baixa,
    BigDecimal valorQuitado,
    Boolean cancelada,
    @Size(max = 50)
    String observacao
) {}
