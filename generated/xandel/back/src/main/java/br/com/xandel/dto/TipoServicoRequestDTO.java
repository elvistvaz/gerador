package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para TipoServico.
 */
public record TipoServicoRequestDTO(
    Integer idTipoServico,
    @Size(max = 50)
    String nomeTipoServico
) {}
