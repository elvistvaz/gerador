package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Cadastro de planos de retenção
 */
@Entity
@Table(name = "PlanoRetencao", schema = "dbo")
public class PlanoRetencao {

    @Id
    @Column(name = "id_PlanoRetencao")
    private Integer idPlanoRetencao;

    @Column(name = "NomePlanoRetencao", length = 50)
    private String nomePlanoRetencao;

    @Column(name = "Inativo")
    private Boolean inativo;

    @Column(name = "LiberaDespesas")
    private Boolean liberaDespesas;

    public PlanoRetencao() {
    }

    public Integer getIdPlanoRetencao() {
        return idPlanoRetencao;
    }

    public void setIdPlanoRetencao(Integer idPlanoRetencao) {
        this.idPlanoRetencao = idPlanoRetencao;
    }

    public String getNomePlanoRetencao() {
        return nomePlanoRetencao;
    }

    public void setNomePlanoRetencao(String nomePlanoRetencao) {
        this.nomePlanoRetencao = nomePlanoRetencao;
    }

    public Boolean getInativo() {
        return inativo;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Boolean getLiberaDespesas() {
        return liberaDespesas;
    }

    public void setLiberaDespesas(Boolean liberaDespesas) {
        this.liberaDespesas = liberaDespesas;
    }

}
