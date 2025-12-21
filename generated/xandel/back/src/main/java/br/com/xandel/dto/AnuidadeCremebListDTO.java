package br.com.xandel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de listagem para AnuidadeCremeb.
 */
public record AnuidadeCremebListDTO(
    Integer idAnuidadeCremeb,
    String anoExercicio,
    LocalDateTime dataInicio,
    LocalDateTime dataFim,
    BigDecimal valorTotal,
    Integer idEmpresa
) {}
