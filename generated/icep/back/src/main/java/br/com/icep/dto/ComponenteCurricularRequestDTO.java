package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para ComponenteCurricular.
 */
public record ComponenteCurricularRequestDTO(
    @NotNull
    @NotBlank
    @Size(max = 100)
    String nome,
    @NotNull
    @NotBlank
    @Size(max = 10)
    String sigla
) {}
