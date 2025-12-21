package br.com.xandel.dto;

/**
 * DTO de listagem para ParametroEmail.
 */
public record ParametroEmailListDTO(
    Integer idParametro,
    String assuntoEmail,
    String smtp,
    String contaEmail
) {}
