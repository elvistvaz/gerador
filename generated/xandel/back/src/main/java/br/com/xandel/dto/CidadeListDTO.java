package br.com.xandel.dto;

import java.math.BigDecimal;

/**
 * DTO de listagem para Cidade.
 */
public record CidadeListDTO(
    Integer idCidade,
    String nomeCidade,
    String uf,
    String ddd,
    BigDecimal iss
) {}
