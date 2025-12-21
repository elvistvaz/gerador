package br.com.xandel.dto;

/**
 * DTO de listagem para Conselho.
 */
public record ConselhoListDTO(
    Integer idConselho,
    String nomeConselho,
    String sigla
) {}
