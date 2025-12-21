package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Cadastro de conselhos profissionais
 */
@Entity
@Table(name = "Conselho", schema = "dbo")
public class Conselho {

    @Id
    @Column(name = "id_Conselho")
    private Integer idConselho;

    @Column(name = "NomeConselho", length = 50)
    private String nomeConselho;

    @Column(name = "Sigla", length = 12)
    private String sigla;

    public Conselho() {
    }

    public Integer getIdConselho() {
        return idConselho;
    }

    public void setIdConselho(Integer idConselho) {
        this.idConselho = idConselho;
    }

    public String getNomeConselho() {
        return nomeConselho;
    }

    public void setNomeConselho(String nomeConselho) {
        this.nomeConselho = nomeConselho;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

}
