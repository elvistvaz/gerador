package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para Territorio.
 */
public record TerritorioResponseDTO(
    Integer id,
    @NotNull
    @NotBlank
    @Size(max = 100)
    String nome,
    @Size(max = 255)
    String descricao
) {}
