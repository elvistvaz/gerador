package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Tabela de Imposto de Renda
 */
@Entity
@Table(name = "ImpostoDeRenda", schema = "dbo")
public class ImpostoDeRenda {

    @EmbeddedId
    private ImpostoDeRendaId id;

    @Column(name = "ate")
    private BigDecimal ate;

    @Column(name = "aliquota", precision = 5, scale = 2)
    private BigDecimal aliquota;

    @Column(name = "valordeduzir")
    private BigDecimal valorDeduzir;

    @Column(name = "DeducaoDependente")
    private BigDecimal deducaoDependente;

    public ImpostoDeRenda() {
    }

    public ImpostoDeRendaId getId() {
        return id;
    }

    public void setId(ImpostoDeRendaId id) {
        this.id = id;
    }

    public BigDecimal getAte() {
        return ate;
    }

    public void setAte(BigDecimal ate) {
        this.ate = ate;
    }

    public BigDecimal getAliquota() {
        return aliquota;
    }

    public void setAliquota(BigDecimal aliquota) {
        this.aliquota = aliquota;
    }

    public BigDecimal getValorDeduzir() {
        return valorDeduzir;
    }

    public void setValorDeduzir(BigDecimal valorDeduzir) {
        this.valorDeduzir = valorDeduzir;
    }

    public BigDecimal getDeducaoDependente() {
        return deducaoDependente;
    }

    public void setDeducaoDependente(BigDecimal deducaoDependente) {
        this.deducaoDependente = deducaoDependente;
    }

}
