package br.com.icep.dto;

/**
 * DTO de listagem para ComentarioIndicador.
 */
public record ComentarioIndicadorListDTO(
    Integer id,
    Integer avaliacaoId,
    Integer municipioId,
    Integer ambitoGestaoId,
    String comentario
) {}
