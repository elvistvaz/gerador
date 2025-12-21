package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para TipoServico.
 */
public record TipoServicoResponseDTO(
    Integer idTipoServico,
    @Size(max = 50)
    String nomeTipoServico
) {}
