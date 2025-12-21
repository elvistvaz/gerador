package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de request para PagamentoNaoSocio.
 */
public record PagamentoNaoSocioRequestDTO(
    LocalDateTime data,
    Integer idEmpresa,
    Integer idCliente,
    Integer idPessoaNaoSocio,
    Integer nf,
    BigDecimal valorBruto,
    BigDecimal retencao,
    BigDecimal valorLiquido,
    BigDecimal valorTaxa,
    BigDecimal valorRepasse
) {}
