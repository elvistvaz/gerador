package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para AnoEscolar.
 */
public record AnoEscolarRequestDTO(
    @NotNull
    @NotBlank
    @Size(max = 50)
    String nome,
    @NotNull
    @NotBlank
    @Size(max = 30)
    String etapa,
    Integer ordem
) {}
