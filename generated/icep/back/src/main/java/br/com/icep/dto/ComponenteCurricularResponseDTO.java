package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para ComponenteCurricular.
 */
public record ComponenteCurricularResponseDTO(
    Integer id,
    @NotNull
    @NotBlank
    @Size(max = 100)
    String nome,
    @NotNull
    @NotBlank
    @Size(max = 10)
    String sigla
) {}
