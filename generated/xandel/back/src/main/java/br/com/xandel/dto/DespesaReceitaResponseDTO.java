package br.com.xandel.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO de response para DespesaReceita.
 */
public record DespesaReceitaResponseDTO(
    Integer idDespesaReceita,
    @NotNull
    @NotBlank
    @Size(max = 10)
    String siglaDespesaReceita,
    @NotNull
    @NotBlank
    @Size(max = 60)
    String nomeDespesaReceita,
    @NotNull
    Integer despesa,
    @NotNull
    Integer temRateio,
    @NotNull
    Integer inativa,
    BigDecimal valor,
    Integer parcelas
) {}
