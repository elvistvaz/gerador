package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para EstadoCivil.
 */
public record EstadoCivilRequestDTO(
    Integer idEstadoCivil,
    @Size(max = 30)
    String nomeEstadoCivil
) {}
