package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Programas de indicação
 */
@Entity
@Table(name = "Indicacao", schema = "dbo")
public class Indicacao {

    @Id
    @Column(name = "id_Indicacao")
    private Integer idIndicacao;

    @Column(name = "de")
    private LocalDateTime de;

    @Column(name = "ate")
    private LocalDateTime ate;

    @Column(name = "IndicacaoMinima")
    private Integer indicacaoMinima;

    @Column(name = "IndicacaoMaxima")
    private Integer indicacaoMaxima;

    @Column(name = "Indice", precision = 5, scale = 2)
    private BigDecimal indice;

    @Column(name = "TetoIndice", precision = 10, scale = 2)
    private BigDecimal tetoIndice;

    @Column(name = "GrupoIndicados")
    private Integer grupoIndicados;

    public Indicacao() {
    }

    public Integer getIdIndicacao() {
        return idIndicacao;
    }

    public void setIdIndicacao(Integer idIndicacao) {
        this.idIndicacao = idIndicacao;
    }

    public LocalDateTime getDe() {
        return de;
    }

    public void setDe(LocalDateTime de) {
        this.de = de;
    }

    public LocalDateTime getAte() {
        return ate;
    }

    public void setAte(LocalDateTime ate) {
        this.ate = ate;
    }

    public Integer getIndicacaoMinima() {
        return indicacaoMinima;
    }

    public void setIndicacaoMinima(Integer indicacaoMinima) {
        this.indicacaoMinima = indicacaoMinima;
    }

    public Integer getIndicacaoMaxima() {
        return indicacaoMaxima;
    }

    public void setIndicacaoMaxima(Integer indicacaoMaxima) {
        this.indicacaoMaxima = indicacaoMaxima;
    }

    public BigDecimal getIndice() {
        return indice;
    }

    public void setIndice(BigDecimal indice) {
        this.indice = indice;
    }

    public BigDecimal getTetoIndice() {
        return tetoIndice;
    }

    public void setTetoIndice(BigDecimal tetoIndice) {
        this.tetoIndice = tetoIndice;
    }

    public Integer getGrupoIndicados() {
        return grupoIndicados;
    }

    public void setGrupoIndicados(Integer grupoIndicados) {
        this.grupoIndicados = grupoIndicados;
    }

}
