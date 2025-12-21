package br.com.icep.dto;

/**
 * DTO de listagem para ConceitoAprendido.
 */
public record ConceitoAprendidoListDTO(
    Integer id,
    String nome,
    Integer ordem
) {}
