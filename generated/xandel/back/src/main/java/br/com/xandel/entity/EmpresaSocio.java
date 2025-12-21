package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Sócios da empresa
 */
@Entity
@Table(name = "EmpresaSocio", schema = "dbo")
public class EmpresaSocio {

    @EmbeddedId
    private EmpresaSocioId id;

    @Column(name = "DataAdesao")
    private LocalDateTime dataAdesao;

    @Column(name = "NumeroQuotas")
    private Integer numeroQuotas;

    @Column(name = "ValorQuota")
    private BigDecimal valorQuota;

    @Column(name = "DataSaida")
    private LocalDateTime dataSaida;

    @Column(name = "ValorCremeb")
    private BigDecimal valorCremeb;

    @Column(name = "NovoModelo", nullable = false)
    private Boolean novoModelo;

    @Column(name = "TaxaConsulta", precision = 5, scale = 2)
    private BigDecimal taxaConsulta;

    @Column(name = "TaxaProcedimento", precision = 5, scale = 2)
    private BigDecimal taxaProcedimento;

    public EmpresaSocio() {
    }

    public EmpresaSocioId getId() {
        return id;
    }

    public void setId(EmpresaSocioId id) {
        this.id = id;
    }

    public LocalDateTime getDataAdesao() {
        return dataAdesao;
    }

    public void setDataAdesao(LocalDateTime dataAdesao) {
        this.dataAdesao = dataAdesao;
    }

    public Integer getNumeroQuotas() {
        return numeroQuotas;
    }

    public void setNumeroQuotas(Integer numeroQuotas) {
        this.numeroQuotas = numeroQuotas;
    }

    public BigDecimal getValorQuota() {
        return valorQuota;
    }

    public void setValorQuota(BigDecimal valorQuota) {
        this.valorQuota = valorQuota;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    public BigDecimal getValorCremeb() {
        return valorCremeb;
    }

    public void setValorCremeb(BigDecimal valorCremeb) {
        this.valorCremeb = valorCremeb;
    }

    public Boolean getNovoModelo() {
        return novoModelo;
    }

    public void setNovoModelo(Boolean novoModelo) {
        this.novoModelo = novoModelo;
    }

    public BigDecimal getTaxaConsulta() {
        return taxaConsulta;
    }

    public void setTaxaConsulta(BigDecimal taxaConsulta) {
        this.taxaConsulta = taxaConsulta;
    }

    public BigDecimal getTaxaProcedimento() {
        return taxaProcedimento;
    }

    public void setTaxaProcedimento(BigDecimal taxaProcedimento) {
        this.taxaProcedimento = taxaProcedimento;
    }

}
