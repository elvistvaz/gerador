package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para Banco.
 */
public record BancoRequestDTO(
    @Size(max = 3)
    String idBanco,
    @Size(max = 40)
    String nomeBanco
) {}
