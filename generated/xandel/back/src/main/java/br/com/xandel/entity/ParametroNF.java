package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Parâmetros de Nota Fiscal
 */
@Entity
@Table(name = "ParametroNF", schema = "dbo")
public class ParametroNF {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "Cofins", precision = 5, scale = 2)
    private BigDecimal cofins;

    @Column(name = "Pis", precision = 5, scale = 2)
    private BigDecimal pis;

    @Column(name = "CSLL", precision = 5, scale = 2)
    private BigDecimal csll;

    @Column(name = "IRRF", precision = 5, scale = 2)
    private BigDecimal irrf;

    @Column(name = "TetoImposto", precision = 10, scale = 2)
    private BigDecimal tetoImposto;

    @Column(name = "BasePisCofinsCsll")
    private BigDecimal basePisCofinsCsll;

    public ParametroNF() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCofins() {
        return cofins;
    }

    public void setCofins(BigDecimal cofins) {
        this.cofins = cofins;
    }

    public BigDecimal getPis() {
        return pis;
    }

    public void setPis(BigDecimal pis) {
        this.pis = pis;
    }

    public BigDecimal getCsll() {
        return csll;
    }

    public void setCsll(BigDecimal csll) {
        this.csll = csll;
    }

    public BigDecimal getIrrf() {
        return irrf;
    }

    public void setIrrf(BigDecimal irrf) {
        this.irrf = irrf;
    }

    public BigDecimal getTetoImposto() {
        return tetoImposto;
    }

    public void setTetoImposto(BigDecimal tetoImposto) {
        this.tetoImposto = tetoImposto;
    }

    public BigDecimal getBasePisCofinsCsll() {
        return basePisCofinsCsll;
    }

    public void setBasePisCofinsCsll(BigDecimal basePisCofinsCsll) {
        this.basePisCofinsCsll = basePisCofinsCsll;
    }

}
