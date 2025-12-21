package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para Especialidade.
 */
public record EspecialidadeRequestDTO(
    Integer idEspecialidade,
    @Size(max = 50)
    String nomeEspecialidade
) {}
