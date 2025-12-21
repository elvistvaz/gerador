package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO de response para Cidade.
 */
public record CidadeResponseDTO(
    Integer idCidade,
    @Size(max = 40)
    String nomeCidade,
    @Size(max = 2)
    String uf,
    @Size(max = 2)
    String ddd,
    BigDecimal iss
) {}
