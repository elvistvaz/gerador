package br.com.icep.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para AprendizagemEsperada.
 */
public record AprendizagemEsperadaResponseDTO(
    Integer id,
    @NotNull
    Integer componenteId,
    @NotNull
    Integer conceitoAprendidoId,
    @NotNull
    @NotBlank
    @Size(max = 1000)
    String descricao,
    @NotNull
    @NotBlank
    @Size(max = 20)
    String codigo
) {}
