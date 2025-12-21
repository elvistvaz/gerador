package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Notas fiscais
 */
@Entity
@Table(name = "NF", schema = "dbo")
public class NF {

    @EmbeddedId
    private NFId id;

    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;

    @Column(name = "Emissao", nullable = false)
    private LocalDateTime emissao;

    @Column(name = "Vencimento")
    private LocalDateTime vencimento;

    @Column(name = "Total")
    private BigDecimal total;

    @Column(name = "IRRF")
    private BigDecimal irrf;

    @Column(name = "PIS")
    private BigDecimal pis;

    @Column(name = "Cofins")
    private BigDecimal cofins;

    @Column(name = "CSLL")
    private BigDecimal csll;

    @Column(name = "ISS")
    private BigDecimal iss;

    @Column(name = "Baixa")
    private LocalDateTime baixa;

    @Column(name = "ValorQuitado")
    private BigDecimal valorQuitado;

    @Column(name = "Cancelada")
    private Boolean cancelada;

    @Column(name = "Observacao", length = 50)
    private String observacao;

    public NF() {
    }

    public NFId getId() {
        return id;
    }

    public void setId(NFId id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDateTime getEmissao() {
        return emissao;
    }

    public void setEmissao(LocalDateTime emissao) {
        this.emissao = emissao;
    }

    public LocalDateTime getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDateTime vencimento) {
        this.vencimento = vencimento;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getIrrf() {
        return irrf;
    }

    public void setIrrf(BigDecimal irrf) {
        this.irrf = irrf;
    }

    public BigDecimal getPis() {
        return pis;
    }

    public void setPis(BigDecimal pis) {
        this.pis = pis;
    }

    public BigDecimal getCofins() {
        return cofins;
    }

    public void setCofins(BigDecimal cofins) {
        this.cofins = cofins;
    }

    public BigDecimal getCsll() {
        return csll;
    }

    public void setCsll(BigDecimal csll) {
        this.csll = csll;
    }

    public BigDecimal getIss() {
        return iss;
    }

    public void setIss(BigDecimal iss) {
        this.iss = iss;
    }

    public LocalDateTime getBaixa() {
        return baixa;
    }

    public void setBaixa(LocalDateTime baixa) {
        this.baixa = baixa;
    }

    public BigDecimal getValorQuitado() {
        return valorQuitado;
    }

    public void setValorQuitado(BigDecimal valorQuitado) {
        this.valorQuitado = valorQuitado;
    }

    public Boolean getCancelada() {
        return cancelada;
    }

    public void setCancelada(Boolean cancelada) {
        this.cancelada = cancelada;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
