package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para AnoEscolar.
 */
public record AnoEscolarResponseDTO(
    Integer id,
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
