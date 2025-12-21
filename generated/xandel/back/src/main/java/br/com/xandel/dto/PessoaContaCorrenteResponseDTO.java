package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para PessoaContaCorrente.
 */
public record PessoaContaCorrenteResponseDTO(
    Integer idContaCorrente,
    Integer idPessoa,
    @Size(max = 3)
    String idBanco,
    @Size(max = 5)
    String agencia,
    @Size(max = 15)
    String contaCorrente,
    @Size(max = 2)
    String dvContaCorrente,
    Boolean ativa,
    Boolean contaPoupanca,
    Boolean contaPadrao,
    @Size(max = 60)
    String nomeDependente,
    @Size(max = 100)
    String pix
) {}
