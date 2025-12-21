package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO de request para Cidade.
 */
public record CidadeRequestDTO(
    Integer idCidade,
    @Size(max = 40)
    String nomeCidade,
    @Size(max = 2)
    String uf,
    @Size(max = 2)
    String ddd,
    BigDecimal iss
) {}
