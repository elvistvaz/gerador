package br.com.icep.dto;

/**
 * DTO de listagem para SegmentoAtendido.
 */
public record SegmentoAtendidoListDTO(
    Integer id,
    Integer publicoAlvoId,
    String nome,
    Integer ordem
) {}
