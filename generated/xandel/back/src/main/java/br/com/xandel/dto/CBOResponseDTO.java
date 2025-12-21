package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para CBO.
 */
public record CBOResponseDTO(
    @Size(max = 6)
    String idCBO,
    @NotNull
    @NotBlank
    @Size(max = 100)
    String nomeCBO
) {}
