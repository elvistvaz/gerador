package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para ParametroEmail.
 */
public record ParametroEmailRequestDTO(
    Integer idParametro,
    @Size(max = 100)
    String assuntoEmail,
    @Size(max = 100)
    String smtp,
    @Size(max = 100)
    String contaEmail,
    @Size(max = 100)
    String senhaEmail
) {}
