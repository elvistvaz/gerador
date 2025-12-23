package br.com.icep.dto;

/**
 * DTO de listagem para AvaliacaoSDA.
 */
public record AvaliacaoSDAListDTO(
    Integer id,
    Integer avaliacaoId,
    Integer municipioId,
    Integer anoEscolarId,
    Integer aprendizagemId,
    Integer nivel,
    Integer quantidadeAlunos
) {}
