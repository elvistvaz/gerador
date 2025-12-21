package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para Operadora.
 */
public record OperadoraResponseDTO(
    Integer idOperadora,
    @NotNull
    @NotBlank
    @Size(max = 50)
    String nomeOperadora
) {}
