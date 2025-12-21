package br.com.xandel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de listagem para Lancamento.
 */
public record LancamentoListDTO(
    Integer idLancamento,
    LocalDateTime data,
    Integer idEmpresa,
    Integer idCliente,
    Integer idPessoa,
    Integer nf,
    BigDecimal valorBruto,
    BigDecimal retencao,
    BigDecimal valorLiquido,
    BigDecimal valorRepasse,
    LocalDateTime baixa
) {}
