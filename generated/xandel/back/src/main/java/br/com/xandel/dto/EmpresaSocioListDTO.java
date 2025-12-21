package br.com.xandel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de listagem para EmpresaSocio.
 */
public record EmpresaSocioListDTO(
    Integer idEmpresa,
    Integer idPessoa,
    LocalDateTime dataAdesao,
    Integer numeroQuotas,
    BigDecimal valorQuota,
    LocalDateTime dataSaida,
    Boolean novoModelo
) {}
