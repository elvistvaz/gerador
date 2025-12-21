package br.com.icep.entity;

import jakarta.persistence.*;

/**
 * Conceitos de aprendizagem
 */
@Entity
@Table(name = "ConceitoAprendido", schema = "dbo")
public class ConceitoAprendido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "ordem")
    private Integer ordem;

    public ConceitoAprendido() {
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
