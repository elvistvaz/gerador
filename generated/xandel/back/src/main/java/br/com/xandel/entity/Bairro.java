package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Cadastro de bairros
 */
@Entity
@Table(name = "Bairro", schema = "dbo")
public class Bairro {

    @Id
    @Column(name = "id_Bairro")
    private Integer idBairro;

    @Column(name = "NomeBairro", length = 40)
    private String nomeBairro;

    public Bairro() {
    }

    public Integer getIdBairro() {
        return idBairro;
    }

    public void setIdBairro(Integer idBairro) {
        this.idBairro = idBairro;
    }

    public String getNomeBairro() {
        return nomeBairro;
    }

    public void setNomeBairro(String nomeBairro) {
        this.nomeBairro = nomeBairro;
    }

}
