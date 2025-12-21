package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para EstadoCivil.
 */
public record EstadoCivilResponseDTO(
    Integer idEstadoCivil,
    @Size(max = 30)
    String nomeEstadoCivil
) {}
