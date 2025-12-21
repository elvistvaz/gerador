package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para SegmentoAtendido.
 */
public record SegmentoAtendidoRequestDTO(
    @NotNull
    Integer publicoAlvoId,
    @NotNull
    @NotBlank
    @Size(max = 100)
    String nome,
    Integer ordem
) {}
