package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Cadastro de especialidades médicas
 */
@Entity
@Table(name = "Especialidade", schema = "dbo")
public class Especialidade {

    @Id
    @Column(name = "id_Especialidade")
    private Integer idEspecialidade;

    @Column(name = "NomeEspecialidade", length = 50)
    private String nomeEspecialidade;

    public Especialidade() {
    }

    public Integer getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Integer idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    public String getNomeEspecialidade() {
        return nomeEspecialidade;
    }

    public void setNomeEspecialidade(String nomeEspecialidade) {
        this.nomeEspecialidade = nomeEspecialidade;
    }

}
