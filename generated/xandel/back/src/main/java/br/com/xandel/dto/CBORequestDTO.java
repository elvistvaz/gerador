package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para CBO.
 */
public record CBORequestDTO(
    @Size(max = 6)
    String idCBO,
    @NotNull
    @NotBlank
    @Size(max = 100)
    String nomeCBO
) {}
