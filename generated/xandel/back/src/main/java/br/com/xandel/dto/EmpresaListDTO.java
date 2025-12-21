package br.com.xandel.dto;

import java.math.BigDecimal;

/**
 * DTO de listagem para Empresa.
 */
public record EmpresaListDTO(
    Integer idEmpresa,
    String nomeEmpresa,
    String fantasiaEmpresa,
    String cnpj,
    BigDecimal taxaRetencao,
    Boolean inativa
) {}
