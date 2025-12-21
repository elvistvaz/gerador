package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para Especialidade.
 */
public record EspecialidadeResponseDTO(
    Integer idEspecialidade,
    @Size(max = 50)
    String nomeEspecialidade
) {}
