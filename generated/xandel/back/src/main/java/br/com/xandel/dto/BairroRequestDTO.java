package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para Bairro.
 */
public record BairroRequestDTO(
    Integer idBairro,
    @Size(max = 40)
    String nomeBairro
) {}
