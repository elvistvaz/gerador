package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para PessoaCartorio.
 */
public record PessoaCartorioRequestDTO(
    Integer idPessoa,
    Integer idCartorio
) {}
