package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para ComentarioIndicador.
 */
public record ComentarioIndicadorRequestDTO(
    @NotNull
    Integer avaliacaoId,
    @NotNull
    Integer municipioId,
    @NotNull
    Integer ambitoGestaoId,
    @NotNull
    @NotBlank
    String comentario
) {}
