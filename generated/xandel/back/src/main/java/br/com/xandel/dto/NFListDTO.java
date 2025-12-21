package br.com.xandel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de listagem para NF.
 */
public record NFListDTO(
    Integer idEmpresa,
    Integer nf,
    Integer idCliente,
    LocalDateTime emissao,
    LocalDateTime vencimento,
    BigDecimal total,
    LocalDateTime baixa,
    BigDecimal valorQuitado,
    Boolean cancelada
) {}
