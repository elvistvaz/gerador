package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para MedicoEspecialidade.
 */
public record MedicoEspecialidadeRequestDTO(
    Integer idPessoa,
    Integer idEspecialidade
) {}
