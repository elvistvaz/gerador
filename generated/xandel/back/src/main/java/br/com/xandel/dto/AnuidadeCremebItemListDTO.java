package br.com.xandel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de listagem para AnuidadeCremebItem.
 */
public record AnuidadeCremebItemListDTO(
    Integer idAnuidadeCremebItem,
    Integer idPessoa,
    LocalDateTime dataLancamento,
    BigDecimal valorIndividual,
    String dataPagamento
) {}
