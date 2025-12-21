package br.com.icep.dto;

/**
 * DTO de listagem para FormacaoTerritorio.
 */
public record FormacaoTerritorioListDTO(
    Integer id,
    String nome,
    Integer ordem
) {}
