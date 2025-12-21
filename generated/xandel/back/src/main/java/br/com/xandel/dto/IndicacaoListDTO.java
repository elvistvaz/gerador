package br.com.xandel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de listagem para Indicacao.
 */
public record IndicacaoListDTO(
    Integer idIndicacao,
    LocalDateTime de,
    LocalDateTime ate,
    Integer indicacaoMinima,
    Integer indicacaoMaxima,
    BigDecimal indice,
    BigDecimal tetoIndice,
    Integer grupoIndicados
) {}
