package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para FormacaoTerritorio.
 */
public record FormacaoTerritorioRequestDTO(
    @NotNull
    @NotBlank
    @Size(max = 150)
    String nome,
    Integer ordem
) {}
