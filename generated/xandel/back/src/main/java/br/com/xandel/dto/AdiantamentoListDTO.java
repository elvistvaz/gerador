package br.com.xandel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de listagem para Adiantamento.
 */
public record AdiantamentoListDTO(
    Integer idAdiantamento,
    LocalDateTime data,
    Integer idEmpresa,
    Integer idPessoa,
    Integer idCliente,
    Integer nf,
    BigDecimal valorBruto,
    BigDecimal retencao,
    BigDecimal valorLiquido,
    BigDecimal valorTaxa,
    BigDecimal valorRepasse,
    Boolean pago
) {}
