package br.com.xandel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de listagem para PagamentoNaoSocio.
 */
public record PagamentoNaoSocioListDTO(
    LocalDateTime data,
    Integer idEmpresa,
    Integer idCliente,
    Integer idPessoaNaoSocio,
    Integer nf,
    BigDecimal valorBruto,
    BigDecimal retencao,
    BigDecimal valorLiquido,
    BigDecimal valorRepasse
) {}
