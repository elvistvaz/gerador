package br.com.icep.entity;

import jakarta.persistence.*;

/**
 * Tipos de formação por território
 */
@Entity
@Table(name = "FormacaoTerritorio", schema = "dbo")
public class FormacaoTerritorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "ordem")
    private Integer ordem;

    public FormacaoTerritorio() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

}
