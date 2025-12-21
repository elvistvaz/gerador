package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Cadastro de bancos
 */
@Entity
@Table(name = "Banco", schema = "dbo")
public class Banco {

    @Id
    @Column(name = "id_Banco", length = 3)
    private String idBanco;

    @Column(name = "NomeBanco", length = 40)
    private String nomeBanco;

    public Banco() {
    }

    public String getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(String idBanco) {
        this.idBanco = idBanco;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

}
