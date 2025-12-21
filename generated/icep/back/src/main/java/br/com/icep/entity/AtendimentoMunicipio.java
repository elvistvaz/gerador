package br.com.icep.entity;

import jakarta.persistence.*;

/**
 * Atendimentos por município e segmento
 */
@Entity
@Table(name = "AtendimentoMunicipio", schema = "dbo")
public class AtendimentoMunicipio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "avaliacao_id", nullable = false)
    private Integer avaliacaoId;

    @Column(name = "municipio_id", nullable = false)
    private Integer municipioId;

    @Column(name = "segmento_atendido_id", nullable = false)
    private Integer segmentoAtendidoId;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    public AtendimentoMunicipio() {
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

    public Integer getSegmentoAtendidoId() {
        return segmentoAtendidoId;
    }

    public void setSegmentoAtendidoId(Integer segmentoAtendidoId) {
        this.segmentoAtendidoId = segmentoAtendidoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
