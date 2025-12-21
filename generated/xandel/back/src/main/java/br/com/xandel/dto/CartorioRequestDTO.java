package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para Cartorio.
 */
public record CartorioRequestDTO(
    Integer idCartorio,
    @Size(max = 60)
    String nomeCartorio,
    @Size(max = 60)
    String endereco,
    Integer idBairro,
    Integer idCidade,
    @Size(max = 9)
    String telefone
) {}
