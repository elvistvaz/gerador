package br.com.icep.entity;

import jakarta.persistence.*;

/**
 * Avaliação de indicadores por município
 */
@Entity
@Table(name = "AvaliacaoIndicador", schema = "dbo")
public class AvaliacaoIndicador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "avaliacao_id", nullable = false)
    private Integer avaliacaoId;

    @Column(name = "municipio_id", nullable = false)
    private Integer municipioId;

    @Column(name = "indicador_id", nullable = false)
    private Integer indicadorId;

    @Column(name = "valor")
    private Integer valor;

    public AvaliacaoIndicador() {
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

    public Integer getIndicadorId() {
        return indicadorId;
    }

    public void setIndicadorId(Integer indicadorId) {
        this.indicadorId = indicadorId;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

}
