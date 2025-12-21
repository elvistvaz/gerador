package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Cadastro de cartórios
 */
@Entity
@Table(name = "Cartorio", schema = "dbo")
public class Cartorio {

    @Id
    @Column(name = "id_Cartorio")
    private Integer idCartorio;

    @Column(name = "NomeCartorio", length = 60)
    private String nomeCartorio;

    @Column(name = "Endereco", length = 60)
    private String endereco;

    @Column(name = "id_Bairro")
    private Integer idBairro;

    @Column(name = "id_Cidade")
    private Integer idCidade;

    @Column(name = "Telefone", length = 9)
    private String telefone;

    public Cartorio() {
    }

    public Integer getIdCartorio() {
        return idCartorio;
    }

    public void setIdCartorio(Integer idCartorio) {
        this.idCartorio = idCartorio;
    }

    public String getNomeCartorio() {
        return nomeCartorio;
    }

    public void setNomeCartorio(String nomeCartorio) {
        this.nomeCartorio = nomeCartorio;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getIdBairro() {
        return idBairro;
    }

    public void setIdBairro(Integer idBairro) {
        this.idBairro = idBairro;
    }

    public Integer getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
