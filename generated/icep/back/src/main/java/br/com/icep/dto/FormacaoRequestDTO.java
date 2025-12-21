package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para Formacao.
 */
public record FormacaoRequestDTO(
    @NotNull
    @NotBlank
    @Size(max = 100)
    String nome,
    Integer ordem
) {}
