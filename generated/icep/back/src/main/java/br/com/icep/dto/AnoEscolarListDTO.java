package br.com.icep.dto;

/**
 * DTO de listagem para AnoEscolar.
 */
public record AnoEscolarListDTO(
    Integer id,
    String nome,
    String etapa,
    Integer ordem
) {}
