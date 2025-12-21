package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para PessoaContaRecebimento.
 */
public record PessoaContaRecebimentoResponseDTO(
    Integer idPessoa,
    Integer idCliente,
    Integer idContaCorrente
) {}
