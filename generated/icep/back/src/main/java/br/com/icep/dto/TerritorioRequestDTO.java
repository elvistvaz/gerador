package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para Territorio.
 */
public record TerritorioRequestDTO(
    @NotNull
    @NotBlank
    @Size(max = 100)
    String nome,
    @Size(max = 255)
    String descricao
) {}
