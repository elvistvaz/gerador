package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para ConceitoAprendido.
 */
public record ConceitoAprendidoResponseDTO(
    Integer id,
    @NotNull
    @NotBlank
    @Size(max = 100)
    String nome,
    Integer ordem
) {}
