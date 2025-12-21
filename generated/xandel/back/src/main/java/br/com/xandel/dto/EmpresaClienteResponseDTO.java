package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO de response para EmpresaCliente.
 */
public record EmpresaClienteResponseDTO(
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
