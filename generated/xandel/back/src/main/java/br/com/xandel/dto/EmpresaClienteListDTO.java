package br.com.xandel.dto;

import java.math.BigDecimal;

/**
 * DTO de listagem para EmpresaCliente.
 */
public record EmpresaClienteListDTO(
    Integer idEmpresa,
    Integer idCliente,
    BigDecimal taxa,
    String processo,
    BigDecimal taxaISS,
    BigDecimal taxaCOFINS,
    BigDecimal taxaPIS,
    BigDecimal taxaCSLL,
    BigDecimal taxaIRRF
) {}
