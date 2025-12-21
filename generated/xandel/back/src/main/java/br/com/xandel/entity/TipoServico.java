package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Cadastro de tipos de serviço
 */
@Entity
@Table(name = "TipoServico", schema = "dbo")
public class TipoServico {

    @Id
    @Column(name = "id_TipoServico")
    private Integer idTipoServico;

    @Column(name = "NomeTipoServico", length = 50)
    private String nomeTipoServico;

    public TipoServico() {
    }

    public Integer getIdTipoServico() {
        return idTipoServico;
    }

    public void setIdTipoServico(Integer idTipoServico) {
        this.idTipoServico = idTipoServico;
    }

    public String getNomeTipoServico() {
        return nomeTipoServico;
    }

    public void setNomeTipoServico(String nomeTipoServico) {
        this.nomeTipoServico = nomeTipoServico;
    }

}
