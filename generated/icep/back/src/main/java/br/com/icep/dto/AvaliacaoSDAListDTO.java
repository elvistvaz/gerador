package br.com.icep.dto;

import java.math.BigDecimal;

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
    BigDecimal peso,
    Integer quantidadeAlunos
) {}
