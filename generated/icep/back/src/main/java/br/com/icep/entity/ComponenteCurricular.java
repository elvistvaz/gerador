package br.com.icep.entity;

import jakarta.persistence.*;

/**
 * Componentes curriculares
 */
@Entity
@Table(name = "ComponenteCurricular", schema = "dbo")
public class ComponenteCurricular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "sigla", nullable = false, length = 10)
    private String sigla;

    public ComponenteCurricular() {
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

}
