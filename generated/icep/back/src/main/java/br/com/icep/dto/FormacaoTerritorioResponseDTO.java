package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para FormacaoTerritorio.
 */
public record FormacaoTerritorioResponseDTO(
    Integer id,
    @NotNull
    @NotBlank
    @Size(max = 150)
    String nome,
    Integer ordem
) {}
