package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para ClienteFilial.
 */
public record ClienteFilialRequestDTO(
    @NotNull
    Integer idCliente,
    @NotNull
    @NotBlank
    @Size(max = 50)
    String nomeFilial
) {}
