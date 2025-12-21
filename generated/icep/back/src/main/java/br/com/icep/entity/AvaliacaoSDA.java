package br.com.icep.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Avaliação de Situações Didáticas Avaliativas
 */
@Entity
@Table(name = "AvaliacaoSDA", schema = "dbo")
public class AvaliacaoSDA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "avaliacao_id", nullable = false)
    private Integer avaliacaoId;

    @Column(name = "municipio_id", nullable = false)
    private Integer municipioId;

    @Column(name = "ano_escolar_id", nullable = false)
    private Integer anoEscolarId;

    @Column(name = "aprendizagem_id", nullable = false)
    private Integer aprendizagemId;

    @Column(name = "nivel", nullable = false)
    private Integer nivel;

    @Column(name = "peso", nullable = false, precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(name = "quantidade_alunos", nullable = false)
    private Integer quantidadeAlunos;

    public AvaliacaoSDA() {
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

    public Integer getAnoEscolarId() {
        return anoEscolarId;
    }

    public void setAnoEscolarId(Integer anoEscolarId) {
        this.anoEscolarId = anoEscolarId;
    }

    public Integer getAprendizagemId() {
        return aprendizagemId;
    }

    public void setAprendizagemId(Integer aprendizagemId) {
        this.aprendizagemId = aprendizagemId;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public Integer getQuantidadeAlunos() {
        return quantidadeAlunos;
    }

    public void setQuantidadeAlunos(Integer quantidadeAlunos) {
        this.quantidadeAlunos = quantidadeAlunos;
    }

}
