package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Adiantamentos de valores
 */
@Entity
@Table(name = "Adiantamento", schema = "dbo")
public class Adiantamento {

    @Id
    @Column(name = "id_Adiantamento")
    private Integer idAdiantamento;

    @Column(name = "Data", nullable = false)
    private LocalDateTime data;

    @Column(name = "id_Empresa", nullable = false)
    private Integer idEmpresa;

    @Column(name = "id_Pessoa", nullable = false)
    private Integer idPessoa;

    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;

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

    @Column(name = "id_lancamento", nullable = false)
    private Integer idLancamento;

    @Column(name = "Pago")
    private Boolean pago;

    public Adiantamento() {
    }

    public Integer getIdAdiantamento() {
        return idAdiantamento;
    }

    public void setIdAdiantamento(Integer idAdiantamento) {
        this.idAdiantamento = idAdiantamento;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
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

    public Integer getIdLancamento() {
        return idLancamento;
    }

    public void setIdLancamento(Integer idLancamento) {
        this.idLancamento = idLancamento;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

}
