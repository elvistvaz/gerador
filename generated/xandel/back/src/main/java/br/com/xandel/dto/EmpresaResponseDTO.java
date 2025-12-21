package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO de response para Empresa.
 */
public record EmpresaResponseDTO(
    Integer idEmpresa,
    @NotNull
    @NotBlank
    @Size(max = 70)
    String nomeEmpresa,
    @Size(max = 30)
    String fantasiaEmpresa,
    @Size(max = 14)
    String cnpj,
    @NotNull
    BigDecimal taxaRetencao,
    Boolean inativa
) {}
