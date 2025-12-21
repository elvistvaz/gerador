package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de response para Cliente.
 */
public record ClienteResponseDTO(
    Integer idCliente,
    @NotNull
    @NotBlank
    @Size(max = 70)
    String nomeCliente,
    @Size(max = 40)
    String fantasiaCliente,
    @Size(max = 14)
    String cnpj,
    LocalDateTime dataContrato,
    BigDecimal taxaADM,
    @Size(max = 60)
    String endereco,
    @Size(max = 8)
    String cep,
    Integer idBairro,
    Integer idCidade,
    @Size(max = 30)
    String contato,
    @Size(max = 9)
    String fone1,
    @Size(max = 9)
    String fone2,
    @Size(max = 50)
    String email,
    Integer pessoaJuridica
) {}
