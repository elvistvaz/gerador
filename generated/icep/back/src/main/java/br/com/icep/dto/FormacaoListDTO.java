package br.com.icep.dto;

/**
 * DTO de listagem para Formacao.
 */
public record FormacaoListDTO(
    Integer id,
    String nome,
    Integer ordem
) {}
