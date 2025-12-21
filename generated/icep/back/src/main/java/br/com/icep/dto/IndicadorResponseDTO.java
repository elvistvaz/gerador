package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para Indicador.
 */
public record IndicadorResponseDTO(
    Integer id,
    @NotNull
    Integer ambitoGestaoId,
    @NotNull
    @NotBlank
    @Size(max = 500)
    String descricao,
    Integer ordem
) {}
