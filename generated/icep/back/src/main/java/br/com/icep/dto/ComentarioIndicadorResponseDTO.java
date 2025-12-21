package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para ComentarioIndicador.
 */
public record ComentarioIndicadorResponseDTO(
    Integer id,
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
