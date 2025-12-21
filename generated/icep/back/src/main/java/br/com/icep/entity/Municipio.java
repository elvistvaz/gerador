package br.com.icep.entity;

import jakarta.persistence.*;

/**
 * Cadastro de municípios atendidos
 */
@Entity
@Table(name = "Municipio", schema = "dbo")
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "territorio_id", nullable = false)
    private Integer territorioId;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "uf", nullable = false, length = 2)
    private String uf;

    @Column(name = "quantidade_escolas")
    private Integer quantidadeEscolas;

    public Municipio() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTerritorioId() {
        return territorioId;
    }

    public void setTerritorioId(Integer territorioId) {
        this.territorioId = territorioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Integer getQuantidadeEscolas() {
        return quantidadeEscolas;
    }

    public void setQuantidadeEscolas(Integer quantidadeEscolas) {
        this.quantidadeEscolas = quantidadeEscolas;
    }

}
