package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Cadastro de tipos de contato
 */
@Entity
@Table(name = "TipoContato", schema = "dbo")
public class TipoContato {

    @Id
    @Column(name = "id_TipoContato")
    private Integer idTipoContato;

    @Column(name = "NomeTipoContato", length = 50)
    private String nomeTipoContato;

    public TipoContato() {
    }

    public Integer getIdTipoContato() {
        return idTipoContato;
    }

    public void setIdTipoContato(Integer idTipoContato) {
        this.idTipoContato = idTipoContato;
    }

    public String getNomeTipoContato() {
        return nomeTipoContato;
    }

    public void setNomeTipoContato(String nomeTipoContato) {
        this.nomeTipoContato = nomeTipoContato;
    }

}
