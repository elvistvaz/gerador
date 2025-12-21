package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de response para Indicacao.
 */
public record IndicacaoResponseDTO(
    Integer idIndicacao,
    LocalDateTime de,
    LocalDateTime ate,
    Integer indicacaoMinima,
    Integer indicacaoMaxima,
    BigDecimal indice,
    BigDecimal tetoIndice,
    Integer grupoIndicados
) {}
