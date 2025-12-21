package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Cadastro de estados civis
 */
@Entity
@Table(name = "EstadoCivil", schema = "dbo")
public class EstadoCivil {

    @Id
    @Column(name = "id_EstadoCivil")
    private Integer idEstadoCivil;

    @Column(name = "NomeEstadoCivil", length = 30)
    private String nomeEstadoCivil;

    public EstadoCivil() {
    }

    public Integer getIdEstadoCivil() {
        return idEstadoCivil;
    }

    public void setIdEstadoCivil(Integer idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
    }

    public String getNomeEstadoCivil() {
        return nomeEstadoCivil;
    }

    public void setNomeEstadoCivil(String nomeEstadoCivil) {
        this.nomeEstadoCivil = nomeEstadoCivil;
    }

}
