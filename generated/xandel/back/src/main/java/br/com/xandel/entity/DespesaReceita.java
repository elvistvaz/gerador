package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Cadastro de despesas e receitas
 */
@Entity
@Table(name = "DespesaReceita", schema = "dbo")
public class DespesaReceita {

    @Id
    @Column(name = "id_DespesaReceita")
    private Integer idDespesaReceita;

    @Column(name = "SiglaDespesaReceita", nullable = false, length = 10)
    private String siglaDespesaReceita;

    @Column(name = "NomeDespesaReceita", nullable = false, length = 60)
    private String nomeDespesaReceita;

    @Column(name = "Despesa", nullable = false)
    private Integer despesa;

    @Column(name = "TemRateio", nullable = false)
    private Integer temRateio;

    @Column(name = "Inativa", nullable = false)
    private Integer inativa;

    @Column(name = "Valor")
    private BigDecimal valor;

    @Column(name = "Parcelas")
    private Integer parcelas;

    public DespesaReceita() {
    }

    public Integer getIdDespesaReceita() {
        return idDespesaReceita;
    }

    public void setIdDespesaReceita(Integer idDespesaReceita) {
        this.idDespesaReceita = idDespesaReceita;
    }

    public String getSiglaDespesaReceita() {
        return siglaDespesaReceita;
    }

    public void setSiglaDespesaReceita(String siglaDespesaReceita) {
        this.siglaDespesaReceita = siglaDespesaReceita;
    }

    public String getNomeDespesaReceita() {
        return nomeDespesaReceita;
    }

    public void setNomeDespesaReceita(String nomeDespesaReceita) {
        this.nomeDespesaReceita = nomeDespesaReceita;
    }

    public Integer getDespesa() {
        return despesa;
    }

    public void setDespesa(Integer despesa) {
        this.despesa = despesa;
    }

    public Integer getTemRateio() {
        return temRateio;
    }

    public void setTemRateio(Integer temRateio) {
        this.temRateio = temRateio;
    }

    public Integer getInativa() {
        return inativa;
    }

    public void setInativa(Integer inativa) {
        this.inativa = inativa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

}
