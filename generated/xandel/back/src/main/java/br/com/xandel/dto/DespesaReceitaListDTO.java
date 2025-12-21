package br.com.xandel.dto;

import java.math.BigDecimal;

/**
 * DTO de listagem para DespesaReceita.
 */
public record DespesaReceitaListDTO(
    Integer idDespesaReceita,
    String siglaDespesaReceita,
    String nomeDespesaReceita,
    Integer despesa,
    Integer temRateio,
    Integer inativa,
    BigDecimal valor,
    Integer parcelas
) {}
