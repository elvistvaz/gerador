package br.com.icep.dto;

/**
 * DTO de listagem para Territorio.
 */
public record TerritorioListDTO(
    Integer id,
    String nome,
    String descricao
) {}
