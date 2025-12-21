package br.com.icep.entity;

import jakarta.persistence.*;

/**
 * Carga horária de formações por território
 */
@Entity
@Table(name = "CargaHorariaFormacaoTerritorio", schema = "dbo")
public class CargaHorariaFormacaoTerritorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "avaliacao_id", nullable = false)
    private Integer avaliacaoId;

    @Column(name = "territorio_id", nullable = false)
    private Integer territorioId;

    @Column(name = "formacao_territorio_id", nullable = false)
    private Integer formacaoTerritorioId;

    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;

    public CargaHorariaFormacaoTerritorio() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAvaliacaoId() {
        return avaliacaoId;
    }

    public void setAvaliacaoId(Integer avaliacaoId) {
        this.avaliacaoId = avaliacaoId;
    }

    public Integer getTerritorioId() {
        return territorioId;
    }

    public void setTerritorioId(Integer territorioId) {
        this.territorioId = territorioId;
    }

    public Integer getFormacaoTerritorioId() {
        return formacaoTerritorioId;
    }

    public void setFormacaoTerritorioId(Integer formacaoTerritorioId) {
        this.formacaoTerritorioId = formacaoTerritorioId;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

}
