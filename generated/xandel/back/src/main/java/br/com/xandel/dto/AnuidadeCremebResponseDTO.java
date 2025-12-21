package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de response para AnuidadeCremeb.
 */
public record AnuidadeCremebResponseDTO(
    Integer idAnuidadeCremeb,
    @Size(max = 4)
    String anoExercicio,
    LocalDateTime dataInicio,
    LocalDateTime dataFim,
    BigDecimal valorTotal,
    Integer idEmpresa
) {}
