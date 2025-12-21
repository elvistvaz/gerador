package br.com.icep.entity;

import jakarta.persistence.*;

/**
 * Indicadores de acompanhamento
 */
@Entity
@Table(name = "Indicador", schema = "dbo")
public class Indicador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ambito_gestao_id", nullable = false)
    private Integer ambitoGestaoId;

    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;

    @Column(name = "ordem")
    private Integer ordem;

    public Indicador() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmbitoGestaoId() {
        return ambitoGestaoId;
    }

    public void setAmbitoGestaoId(Integer ambitoGestaoId) {
        this.ambitoGestaoId = ambitoGestaoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

}
