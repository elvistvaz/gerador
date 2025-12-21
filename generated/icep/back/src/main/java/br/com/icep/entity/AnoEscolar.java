package br.com.icep.entity;

import jakarta.persistence.*;

/**
 * Anos escolares
 */
@Entity
@Table(name = "AnoEscolar", schema = "dbo")
public class AnoEscolar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "etapa", nullable = false, length = 30)
    private String etapa;

    @Column(name = "ordem")
    private Integer ordem;

    public AnoEscolar() {
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

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

}
