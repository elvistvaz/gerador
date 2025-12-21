package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para MedicoEspecialidade.
 */
public record MedicoEspecialidadeResponseDTO(
    Integer idPessoa,
    Integer idEspecialidade
) {}
