package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Pagamentos para não sócios
 */
@Entity
@Table(name = "PagamentoNaoSocio", schema = "dbo")
public class PagamentoNaoSocio {

    @EmbeddedId
    private PagamentoNaoSocioId id;

    @Column(name = "NF")
    private Integer nf;

    @Column(name = "ValorBruto")
    private BigDecimal valorBruto;

    @Column(name = "Retencao")
    private BigDecimal retencao;

    @Column(name = "ValorLiquido")
    private BigDecimal valorLiquido;

    @Column(name = "ValorTaxa")
    private BigDecimal valorTaxa;

    @Column(name = "ValorRepasse")
    private BigDecimal valorRepasse;

    public PagamentoNaoSocio() {
    }

    public PagamentoNaoSocioId getId() {
        return id;
    }

    public void setId(PagamentoNaoSocioId id) {
        this.id = id;
    }

    public Integer getNf() {
        return nf;
    }

    public void setNf(Integer nf) {
        this.nf = nf;
    }

    public BigDecimal getValorBruto() {
        return valorBruto;
    }

    public void setValorBruto(BigDecimal valorBruto) {
        this.valorBruto = valorBruto;
    }

    public BigDecimal getRetencao() {
        return retencao;
    }

    public void setRetencao(BigDecimal retencao) {
        this.retencao = retencao;
    }

    public BigDecimal getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(BigDecimal valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    public BigDecimal getValorTaxa() {
        return valorTaxa;
    }

    public void setValorTaxa(BigDecimal valorTaxa) {
        this.valorTaxa = valorTaxa;
    }

    public BigDecimal getValorRepasse() {
        return valorRepasse;
    }

    public void setValorRepasse(BigDecimal valorRepasse) {
        this.valorRepasse = valorRepasse;
    }

}
