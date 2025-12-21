package br.com.xandel.dto;

/**
 * DTO de listagem para PessoaContaRecebimento.
 */
public record PessoaContaRecebimentoListDTO(
    Integer idPessoa,
    Integer idCliente,
    Integer idContaCorrente
) {}
