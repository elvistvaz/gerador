package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para Cartorio.
 */
public record CartorioResponseDTO(
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
