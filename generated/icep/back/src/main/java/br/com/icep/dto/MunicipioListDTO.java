package br.com.icep.dto;

/**
 * DTO de listagem para Municipio.
 */
public record MunicipioListDTO(
    Integer id,
    Integer territorioId,
    String nome,
    String uf,
    Integer quantidadeEscolas
) {}
