package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para AmbitoGestao.
 */
public record AmbitoGestaoResponseDTO(
    Integer id,
    @NotNull
    @NotBlank
    @Size(max = 100)
    String nome,
    Integer ordem
) {}
