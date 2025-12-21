package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para PessoaContaRecebimento.
 */
public record PessoaContaRecebimentoRequestDTO(
    Integer idPessoa,
    Integer idCliente,
    Integer idContaCorrente
) {}
