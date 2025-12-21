package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para SegmentoAtendido.
 */
public record SegmentoAtendidoResponseDTO(
    Integer id,
    @NotNull
    Integer publicoAlvoId,
    @NotNull
    @NotBlank
    @Size(max = 100)
    String nome,
    Integer ordem
) {}
