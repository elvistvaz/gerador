package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de response para Conselho.
 */
public record ConselhoResponseDTO(
    Integer idConselho,
    @Size(max = 50)
    String nomeConselho,
    @Size(max = 12)
    String sigla
) {}
