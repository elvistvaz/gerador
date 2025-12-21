package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para Municipio.
 */
public record MunicipioRequestDTO(
    @NotNull
    Integer territorioId,
    @NotNull
    @NotBlank
    @Size(max = 100)
    String nome,
    @NotNull
    @NotBlank
    @Size(max = 2)
    String uf,
    Integer quantidadeEscolas
) {}
