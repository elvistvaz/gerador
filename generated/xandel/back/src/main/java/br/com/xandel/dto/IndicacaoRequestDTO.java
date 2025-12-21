package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de request para Indicacao.
 */
public record IndicacaoRequestDTO(
    Integer idIndicacao,
    LocalDateTime de,
    LocalDateTime ate,
    Integer indicacaoMinima,
    Integer indicacaoMaxima,
    BigDecimal indice,
    BigDecimal tetoIndice,
    Integer grupoIndicados
) {}
