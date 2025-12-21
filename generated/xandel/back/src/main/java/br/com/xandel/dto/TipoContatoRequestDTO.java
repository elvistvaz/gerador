package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para TipoContato.
 */
public record TipoContatoRequestDTO(
    Integer idTipoContato,
    @Size(max = 50)
    String nomeTipoContato
) {}
