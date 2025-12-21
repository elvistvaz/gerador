package br.com.icep.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO de response para AvaliacaoSDA.
 */
public record AvaliacaoSDAResponseDTO(
    Integer id,
    @NotNull
    Integer avaliacaoId,
    @NotNull
    Integer municipioId,
    @NotNull
    Integer anoEscolarId,
    @NotNull
    Integer aprendizagemId,
    @NotNull
    Integer nivel,
    @NotNull
    BigDecimal peso,
    @NotNull
    Integer quantidadeAlunos
) {}
