package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Contas a pagar e receber
 */
@Entity
@Table(name = "ContasPagarReceber", schema = "dbo")
public class ContasPagarReceber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ContasPagarReceber")
    private Integer idContasPagarReceber;

    @Column(name = "DataLancamento", nullable = false)
    private LocalDateTime dataLancamento;

    @Column(name = "ValorOriginal", nullable = false)
    private BigDecimal valorOriginal;

    @Column(name = "ValorParcela", nullable = false)
    private BigDecimal valorParcela;

    @Column(name = "DataVencimento", nullable = false)
    private LocalDateTime dataVencimento;

    @Column(name = "DataBaixa")
    private LocalDateTime dataBaixa;

    @Column(name = "ValorBaixado")
    private BigDecimal valorBaixado;

    @Column(name = "id_Empresa")
    private Integer idEmpresa;

    @Column(name = "id_Pessoa")
    private Integer idPessoa;

    @Column(name = "MesAnoReferencia", length = 10)
    private String mesAnoReferencia;

    @Column(name = "Historico", length = 100)
    private String historico;

    @Column(name = "id_DespesaReceita")
    private Integer idDespesaReceita;

    public ContasPagarReceber() {
    }

    public Integer getIdContasPagarReceber() {
        return idContasPagarReceber;
    }

    public void setIdContasPagarReceber(Integer idContasPagarReceber) {
        this.idContasPagarReceber = idContasPagarReceber;
    }

    public LocalDateTime getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDateTime dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public BigDecimal getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(BigDecimal valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public BigDecimal getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(BigDecimal valorParcela) {
        this.valorParcela = valorParcela;
    }

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDateTime getDataBaixa() {
        return dataBaixa;
    }

    public void setDataBaixa(LocalDateTime dataBaixa) {
        this.dataBaixa = dataBaixa;
    }

    public BigDecimal getValorBaixado() {
        return valorBaixado;
    }

    public void setValorBaixado(BigDecimal valorBaixado) {
        this.valorBaixado = valorBaixado;
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

    public String getMesAnoReferencia() {
        return mesAnoReferencia;
    }

    public void setMesAnoReferencia(String mesAnoReferencia) {
        this.mesAnoReferencia = mesAnoReferencia;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Integer getIdDespesaReceita() {
        return idDespesaReceita;
    }

    public void setIdDespesaReceita(Integer idDespesaReceita) {
        this.idDespesaReceita = idDespesaReceita;
    }

}
