package br.com.icep.dto;

/**
 * DTO de listagem para AprendizagemEsperada.
 */
public record AprendizagemEsperadaListDTO(
    Integer id,
    Integer componenteId,
    Integer conceitoAprendidoId,
    String descricao,
    String codigo
) {}
