package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para ClienteContato.
 */
public record ClienteContatoResponseDTO(
    Integer idClienteContato,
    @NotNull
    Integer idCliente,
    @NotNull
    Integer idTipoContato,
    @NotNull
    @NotBlank
    @Size(max = 100)
    String descricao
) {}
