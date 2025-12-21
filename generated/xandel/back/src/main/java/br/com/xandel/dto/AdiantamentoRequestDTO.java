package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de request para Adiantamento.
 */
public record AdiantamentoRequestDTO(
    Integer idAdiantamento,
    @NotNull
    LocalDateTime data,
    @NotNull
    Integer idEmpresa,
    @NotNull
    Integer idPessoa,
    @NotNull
    Integer idCliente,
    Integer nf,
    BigDecimal valorBruto,
    BigDecimal retencao,
    BigDecimal valorLiquido,
    BigDecimal valorTaxa,
    BigDecimal valorRepasse,
    @NotNull
    Integer idLancamento,
    Boolean pago
) {}
