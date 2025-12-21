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
    @Size(max = 6)
    String agencia,
    @Size(max = 20)
    String contaCorrente,
    @Size(max = 2)
    String dvContaCorrente,
    Boolean ativa,
    Boolean contaPoupanca,
    @Size(max = 100)
    String nomeDependente,
    Boolean contaPadrao,
    @Size(max = 100)
    String pix
) {}
