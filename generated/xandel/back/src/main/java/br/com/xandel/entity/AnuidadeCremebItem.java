package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Itens da anuidade CREMEB
 */
@Entity
@Table(name = "AnuidadeCremebItem", schema = "dbo")
public class AnuidadeCremebItem {

    @Id
    @Column(name = "id_AnuidadeCremebItem")
    private Integer idAnuidadeCremebItem;

    @Column(name = "id_AnuidadeCremeb")
    private Integer idAnuidadeCremeb;

    @Column(name = "id_Pessoa")
    private Integer idPessoa;

    @Column(name = "DataLancamento")
    private LocalDateTime dataLancamento;

    @Column(name = "ValorIndividual")
    private BigDecimal valorIndividual;

    @Column(name = "DataPagamento", length = 10)
    private String dataPagamento;

    @Column(name = "id_Lancamento")
    private Integer idLancamento;

    public AnuidadeCremebItem() {
    }

    public Integer getIdAnuidadeCremebItem() {
        return idAnuidadeCremebItem;
    }

    public void setIdAnuidadeCremebItem(Integer idAnuidadeCremebItem) {
        this.idAnuidadeCremebItem = idAnuidadeCremebItem;
    }

    public Integer getIdAnuidadeCremeb() {
        return idAnuidadeCremeb;
    }

    public void setIdAnuidadeCremeb(Integer idAnuidadeCremeb) {
        this.idAnuidadeCremeb = idAnuidadeCremeb;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public LocalDateTime getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDateTime dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public BigDecimal getValorIndividual() {
        return valorIndividual;
    }

    public void setValorIndividual(BigDecimal valorIndividual) {
        this.valorIndividual = valorIndividual;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Integer getIdLancamento() {
        return idLancamento;
    }

    public void setIdLancamento(Integer idLancamento) {
        this.idLancamento = idLancamento;
    }

}
