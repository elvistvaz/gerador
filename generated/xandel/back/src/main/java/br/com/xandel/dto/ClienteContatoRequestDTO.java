package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para ClienteContato.
 */
public record ClienteContatoRequestDTO(
    @NotNull
    Integer idCliente,
    @NotNull
    Integer idTipoContato,
    @NotNull
    @NotBlank
    @Size(max = 100)
    String descricao
) {}
