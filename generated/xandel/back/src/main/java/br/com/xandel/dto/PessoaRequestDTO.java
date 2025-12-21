package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * DTO de request para Pessoa.
 */
public record PessoaRequestDTO(
    Integer idPessoa,
    @Size(max = 60)
    String nome,
    @Size(max = 11)
    String cpf,
    Integer idConselho,
    Integer numeroConselho,
    LocalDateTime nascimento,
    @Size(max = 14)
    String rg,
    @Size(max = 50)
    String email,
    @Size(max = 9)
    String telefone,
    @Size(max = 9)
    String celular,
    @Size(max = 60)
    String endereco,
    Integer idBairro,
    Integer idCidade,
    @Size(max = 8)
    String cep,
    Integer idEstadoCivil,
    LocalDateTime dataAdesao,
    LocalDateTime dataInativo,
    @Size(max = 3)
    String idBanco,
    @Size(max = 5)
    String agencia,
    @Size(max = 11)
    String conta,
    Integer idPlanoRetencao,
    Integer idOperadora
) {}
