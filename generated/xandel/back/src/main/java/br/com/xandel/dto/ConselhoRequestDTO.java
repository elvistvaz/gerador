package br.com.xandel.dto;

import jakarta.validation.constraints.*;

/**
 * DTO de request para Conselho.
 */
public record ConselhoRequestDTO(
    Integer idConselho,
    @Size(max = 50)
    String nomeConselho,
    @Size(max = 12)
    String sigla
) {}
