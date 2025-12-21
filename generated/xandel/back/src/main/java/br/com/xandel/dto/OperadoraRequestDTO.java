package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para Operadora.
 */
public record OperadoraRequestDTO(
    Integer idOperadora,
    @NotNull
    @NotBlank
    @Size(max = 50)
    String nomeOperadora
) {}
