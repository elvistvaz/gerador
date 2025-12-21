package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de response para PagamentoNaoSocio.
 */
public record PagamentoNaoSocioResponseDTO(
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
