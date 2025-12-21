package br.com.xandel.dto;

import java.time.LocalDateTime;

/**
 * DTO de listagem para Pessoa.
 */
public record PessoaListDTO(
    Integer idPessoa,
    String nome,
    String cpf,
    Integer idConselho,
    Integer numeroConselho,
    LocalDateTime nascimento,
    String email,
    String celular,
    LocalDateTime dataAdesao,
    LocalDateTime dataInativo
) {}
