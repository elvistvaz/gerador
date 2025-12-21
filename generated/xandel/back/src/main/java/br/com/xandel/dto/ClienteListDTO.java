package br.com.xandel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de listagem para Cliente.
 */
public record ClienteListDTO(
    Integer idCliente,
    String nomeCliente,
    String fantasiaCliente,
    String cnpj,
    LocalDateTime dataContrato,
    BigDecimal taxaADM,
    String contato,
    String email
) {}
