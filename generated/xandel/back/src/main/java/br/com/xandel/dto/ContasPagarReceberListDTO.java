package br.com.xandel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de listagem para ContasPagarReceber.
 */
public record ContasPagarReceberListDTO(
    Integer idContasPagarReceber,
    LocalDateTime dataLancamento,
    BigDecimal valorOriginal,
    BigDecimal valorParcela,
    LocalDateTime dataVencimento,
    LocalDateTime dataBaixa,
    BigDecimal valorBaixado,
    Integer idEmpresa,
    String historico,
    Integer idDespesaReceita
) {}
