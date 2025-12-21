package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para TipoContato.
 */
public record TipoContatoResponseDTO(
    Integer idTipoContato,
    @Size(max = 50)
    String nomeTipoContato
) {}
