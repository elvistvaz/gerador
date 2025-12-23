package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para AvaliacaoSDA.
 */
public record AvaliacaoSDARequestDTO(
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
    Integer quantidadeAlunos
) {}
