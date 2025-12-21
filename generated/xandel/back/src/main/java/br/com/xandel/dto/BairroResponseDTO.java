package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para Bairro.
 */
public record BairroResponseDTO(
    Integer idBairro,
    @Size(max = 40)
    String nomeBairro
) {}
