package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO de request para EmpresaCliente.
 */
public record EmpresaClienteRequestDTO(
    Integer idEmpresa,
    Integer idCliente,
    BigDecimal taxa,
    @Size(max = 10)
    String processo,
    BigDecimal taxaISS,
    BigDecimal taxaCOFINS,
    BigDecimal taxaPIS,
    BigDecimal taxaCSLL,
    BigDecimal taxaIRRF
) {}
