package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para ClienteFilial.
 */
public record ClienteFilialResponseDTO(
    Integer idClienteFilial,
    @NotNull
    Integer idCliente,
    @NotNull
    @NotBlank
    @Size(max = 50)
    String nomeFilial
) {}
