package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Cadastro de empresas
 */
@Entity
@Table(name = "Empresa", schema = "dbo")
public class Empresa {

    @Id
    @Column(name = "id_Empresa")
    private Integer idEmpresa;

    @Column(name = "NomeEmpresa", nullable = false, length = 70)
    private String nomeEmpresa;

    @Column(name = "FantasiaEmpresa", length = 30)
    private String fantasiaEmpresa;

    @Column(name = "CNPJ", length = 14)
    private String cnpj;

    @Column(name = "TaxaRetencao", nullable = false, precision = 18, scale = 2)
    private BigDecimal taxaRetencao;

    @Column(name = "Inativa")
    private Boolean inativa;

    public Empresa() {
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getFantasiaEmpresa() {
        return fantasiaEmpresa;
    }

    public void setFantasiaEmpresa(String fantasiaEmpresa) {
        this.fantasiaEmpresa = fantasiaEmpresa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public BigDecimal getTaxaRetencao() {
        return taxaRetencao;
    }

    public void setTaxaRetencao(BigDecimal taxaRetencao) {
        this.taxaRetencao = taxaRetencao;
    }

    public Boolean getInativa() {
        return inativa;
    }

    public void setInativa(Boolean inativa) {
        this.inativa = inativa;
    }

}
