package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Cadastro Brasileiro de Ocupações
 */
@Entity
@Table(name = "CBO", schema = "dbo")
public class CBO {

    @Id
    @Column(name = "id_CBO", length = 6)
    private String idCBO;

    @Column(name = "NomeCBO", nullable = false, length = 100)
    private String nomeCBO;

    public CBO() {
    }

    public String getIdCBO() {
        return idCBO;
    }

    public void setIdCBO(String idCBO) {
        this.idCBO = idCBO;
    }

    public String getNomeCBO() {
        return nomeCBO;
    }

    public void setNomeCBO(String nomeCBO) {
        this.nomeCBO = nomeCBO;
    }

}
