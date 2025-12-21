package br.com.icep.entity;

import jakarta.persistence.*;

/**
 * Segmentos atendidos por público alvo
 */
@Entity
@Table(name = "SegmentoAtendido", schema = "dbo")
public class SegmentoAtendido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "publico_alvo_id", nullable = false)
    private Integer publicoAlvoId;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "ordem")
    private Integer ordem;

    public SegmentoAtendido() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPublicoAlvoId() {
        return publicoAlvoId;
    }

    public void setPublicoAlvoId(Integer publicoAlvoId) {
        this.publicoAlvoId = publicoAlvoId;
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
