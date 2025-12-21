package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para ConceitoAprendido.
 */
public record ConceitoAprendidoRequestDTO(
    @NotNull
    @NotBlank
    @Size(max = 100)
    String nome,
    Integer ordem
) {}
