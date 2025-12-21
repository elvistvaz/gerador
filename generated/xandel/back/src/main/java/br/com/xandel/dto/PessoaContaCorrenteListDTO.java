package br.com.xandel.dto;

/**
 * DTO de listagem para PessoaContaCorrente.
 */
public record PessoaContaCorrenteListDTO(
    Integer idContaCorrente,
    String idBanco,
    String agencia,
    String contaCorrente,
    String dvContaCorrente,
    Boolean ativa,
    Boolean contaPoupanca,
    Boolean contaPadrao,
    String pix
) {}
