package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de request para ContasPagarReceber.
 */
public record ContasPagarReceberRequestDTO(
    @NotNull
    LocalDateTime dataLancamento,
    @NotNull
    BigDecimal valorOriginal,
    @NotNull
    BigDecimal valorParcela,
    @NotNull
    LocalDateTime dataVencimento,
    LocalDateTime dataBaixa,
    BigDecimal valorBaixado,
    Integer idEmpresa,
    Integer idPessoa,
    @Size(max = 10)
    String mesAnoReferencia,
    @Size(max = 100)
    String historico,
    Integer idDespesaReceita
) {}
