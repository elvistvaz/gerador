package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para Banco.
 */
public record BancoResponseDTO(
    @Size(max = 3)
    String idBanco,
    @Size(max = 40)
    String nomeBanco
) {}
