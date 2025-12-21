package br.com.icep.entity;

import jakarta.persistence.*;

/**
 * Períodos de avaliação
 */
@Entity
@Table(name = "Avaliacao", schema = "dbo")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "avaliacao", nullable = false, length = 6)
    private String avaliacao;

    public Avaliacao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

}
