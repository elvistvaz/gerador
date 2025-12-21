package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de response para AnuidadeCremebItem.
 */
public record AnuidadeCremebItemResponseDTO(
    Integer idAnuidadeCremebItem,
    Integer idAnuidadeCremeb,
    Integer idPessoa,
    LocalDateTime dataLancamento,
    BigDecimal valorIndividual,
    @Size(max = 10)
    String dataPagamento,
    Integer idLancamento
) {}
