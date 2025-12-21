package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para AmbitoGestao.
 */
public record AmbitoGestaoRequestDTO(
    @NotNull
    @NotBlank
    @Size(max = 100)
    String nome,
    Integer ordem
) {}
