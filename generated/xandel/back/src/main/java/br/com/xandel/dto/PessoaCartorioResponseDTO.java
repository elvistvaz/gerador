package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para PessoaCartorio.
 */
public record PessoaCartorioResponseDTO(
    Integer idPessoa,
    Integer idCartorio
) {}
