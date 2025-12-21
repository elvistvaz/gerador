package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Cadastro de cidades
 */
@Entity
@Table(name = "Cidade", schema = "dbo")
public class Cidade {

    @Id
    @Column(name = "id_Cidade")
    private Integer idCidade;

    @Column(name = "NomeCidade", length = 40)
    private String nomeCidade;

    @Column(name = "UF", length = 2)
    private String uf;

    @Column(name = "DDD", length = 2)
    private String ddd;

    @Column(name = "ISS", precision = 5, scale = 2)
    private BigDecimal iss;

    public Cidade() {
    }

    public Integer getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public BigDecimal getIss() {
        return iss;
    }

    public void setIss(BigDecimal iss) {
        this.iss = iss;
    }

}
