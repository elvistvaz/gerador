package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de response para EmpresaSocio.
 */
public record EmpresaSocioResponseDTO(
    Integer idEmpresa,
    Integer idPessoa,
    LocalDateTime dataAdesao,
    Integer numeroQuotas,
    BigDecimal valorQuota,
    LocalDateTime dataSaida,
    BigDecimal valorCremeb,
    @NotNull
    Boolean novoModelo,
    BigDecimal taxaConsulta,
    BigDecimal taxaProcedimento
) {}
