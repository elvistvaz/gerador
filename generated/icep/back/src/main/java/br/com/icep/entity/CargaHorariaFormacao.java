package br.com.icep.entity;

import jakarta.persistence.*;

/**
 * Carga horária de formações por município
 */
@Entity
@Table(name = "CargaHorariaFormacao", schema = "dbo")
public class CargaHorariaFormacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "avaliacao_id", nullable = false)
    private Integer avaliacaoId;

    @Column(name = "municipio_id", nullable = false)
    private Integer municipioId;

    @Column(name = "formacao_id", nullable = false)
    private Integer formacaoId;

    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;

    public CargaHorariaFormacao() {
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

    public Integer getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Integer municipioId) {
        this.municipioId = municipioId;
    }

    public Integer getFormacaoId() {
        return formacaoId;
    }

    public void setFormacaoId(Integer formacaoId) {
        this.formacaoId = formacaoId;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

}
