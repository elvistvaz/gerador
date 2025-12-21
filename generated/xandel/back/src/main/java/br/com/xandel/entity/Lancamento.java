package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Lançamentos financeiros
 */
@Entity
@Table(name = "Lancamento", schema = "dbo")
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lancamento")
    private Integer idLancamento;

    @Column(name = "Data", nullable = false)
    private LocalDateTime data;

    @Column(name = "id_Empresa", nullable = false)
    private Integer idEmpresa;

    @Column(name = "id_Cliente", nullable = false)
    private Integer idCliente;

    @Column(name = "id_Pessoa", nullable = false)
    private Integer idPessoa;

    @Column(name = "NF")
    private Integer nf;

    @Column(name = "ValorBruto")
    private BigDecimal valorBruto;

    @Column(name = "Despesas")
    private BigDecimal despesas;

    @Column(name = "Retencao")
    private BigDecimal retencao;

    @Column(name = "ValorLiquido")
    private BigDecimal valorLiquido;

    @Column(name = "ValorTaxa")
    private BigDecimal valorTaxa;

    @Column(name = "ValorRepasse")
    private BigDecimal valorRepasse;

    @Column(name = "Baixa")
    private LocalDateTime baixa;

    @Column(name = "MesAno", length = 6)
    private String mesAno;

    @Column(name = "Taxa", precision = 6, scale = 2)
    private BigDecimal taxa;

    @Column(name = "id_TipoServico")
    private Integer idTipoServico;

    public Lancamento() {
    }

    public Integer getIdLancamento() {
        return idLancamento;
    }

    public void setIdLancamento(Integer idLancamento) {
        this.idLancamento = idLancamento;
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

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
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

    public BigDecimal getDespesas() {
        return despesas;
    }

    public void setDespesas(BigDecimal despesas) {
        this.despesas = despesas;
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

    public LocalDateTime getBaixa() {
        return baixa;
    }

    public void setBaixa(LocalDateTime baixa) {
        this.baixa = baixa;
    }

    public String getMesAno() {
        return mesAno;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public BigDecimal getTaxa() {
        return taxa;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }

    public Integer getIdTipoServico() {
        return idTipoServico;
    }

    public void setIdTipoServico(Integer idTipoServico) {
        this.idTipoServico = idTipoServico;
    }

}
