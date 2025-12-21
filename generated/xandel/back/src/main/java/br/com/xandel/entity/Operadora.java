package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Cadastro de operadoras
 */
@Entity
@Table(name = "Operadora", schema = "dbo")
public class Operadora {

    @Id
    @Column(name = "id_Operadora")
    private Integer idOperadora;

    @Column(name = "NomeOperadora", nullable = false, length = 50)
    private String nomeOperadora;

    public Operadora() {
    }

    public Integer getIdOperadora() {
        return idOperadora;
    }

    public void setIdOperadora(Integer idOperadora) {
        this.idOperadora = idOperadora;
    }

    public String getNomeOperadora() {
        return nomeOperadora;
    }

    public void setNomeOperadora(String nomeOperadora) {
        this.nomeOperadora = nomeOperadora;
    }

}
