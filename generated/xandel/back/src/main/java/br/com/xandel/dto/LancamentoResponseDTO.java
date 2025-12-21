package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de response para Lancamento.
 */
public record LancamentoResponseDTO(
    Integer idLancamento,
    @NotNull
    LocalDateTime data,
    @NotNull
    Integer idEmpresa,
    @NotNull
    Integer idCliente,
    @NotNull
    Integer idPessoa,
    Integer nf,
    BigDecimal valorBruto,
    BigDecimal despesas,
    BigDecimal retencao,
    BigDecimal valorLiquido,
    BigDecimal valorTaxa,
    BigDecimal valorRepasse,
    LocalDateTime baixa,
    @Size(max = 6)
    String mesAno,
    BigDecimal taxa,
    Integer idTipoServico
) {}
