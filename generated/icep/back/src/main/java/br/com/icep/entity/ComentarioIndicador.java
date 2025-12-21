package br.com.icep.entity;

import jakarta.persistence.*;

/**
 * Comentários sobre indicadores por município e âmbito
 */
@Entity
@Table(name = "ComentarioIndicador", schema = "dbo")
public class ComentarioIndicador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "avaliacao_id", nullable = false)
    private Integer avaliacaoId;

    @Column(name = "municipio_id", nullable = false)
    private Integer municipioId;

    @Column(name = "ambito_gestao_id", nullable = false)
    private Integer ambitoGestaoId;

    @Lob
    @Column(name = "comentario", nullable = false, columnDefinition = "CLOB")
    private String comentario;

    public ComentarioIndicador() {
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

    public Integer getAmbitoGestaoId() {
        return ambitoGestaoId;
    }

    public void setAmbitoGestaoId(Integer ambitoGestaoId) {
        this.ambitoGestaoId = ambitoGestaoId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

}
