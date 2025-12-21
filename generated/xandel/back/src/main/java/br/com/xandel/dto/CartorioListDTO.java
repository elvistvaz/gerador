package br.com.xandel.dto;

/**
 * DTO de listagem para Cartorio.
 */
public record CartorioListDTO(
    Integer idCartorio,
    String nomeCartorio,
    String endereco,
    Integer idBairro,
    Integer idCidade,
    String telefone
) {}
