package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Clientes vinculados à empresa
 */
@Entity
@Table(name = "EmpresaCliente", schema = "dbo")
public class EmpresaCliente {

    @EmbeddedId
    private EmpresaClienteId id;

    @Column(name = "Taxa", precision = 6, scale = 2)
    private BigDecimal taxa;

    @Column(name = "Processo", length = 10)
    private String processo;

    @Column(name = "TaxaISS", precision = 5, scale = 2)
    private BigDecimal taxaISS;

    @Column(name = "TaxaCOFINS", precision = 5, scale = 2)
    private BigDecimal taxaCOFINS;

    @Column(name = "TaxaPIS", precision = 5, scale = 2)
    private BigDecimal taxaPIS;

    @Column(name = "TaxaCSLL", precision = 5, scale = 2)
    private BigDecimal taxaCSLL;

    @Column(name = "TaxaIRRF", precision = 5, scale = 2)
    private BigDecimal taxaIRRF;

    public EmpresaCliente() {
    }

    public EmpresaClienteId getId() {
        return id;
    }

    public void setId(EmpresaClienteId id) {
        this.id = id;
    }

    public BigDecimal getTaxa() {
        return taxa;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public BigDecimal getTaxaISS() {
        return taxaISS;
    }

    public void setTaxaISS(BigDecimal taxaISS) {
        this.taxaISS = taxaISS;
    }

    public BigDecimal getTaxaCOFINS() {
        return taxaCOFINS;
    }

    public void setTaxaCOFINS(BigDecimal taxaCOFINS) {
        this.taxaCOFINS = taxaCOFINS;
    }

    public BigDecimal getTaxaPIS() {
        return taxaPIS;
    }

    public void setTaxaPIS(BigDecimal taxaPIS) {
        this.taxaPIS = taxaPIS;
    }

    public BigDecimal getTaxaCSLL() {
        return taxaCSLL;
    }

    public void setTaxaCSLL(BigDecimal taxaCSLL) {
        this.taxaCSLL = taxaCSLL;
    }

    public BigDecimal getTaxaIRRF() {
        return taxaIRRF;
    }

    public void setTaxaIRRF(BigDecimal taxaIRRF) {
        this.taxaIRRF = taxaIRRF;
    }

}
