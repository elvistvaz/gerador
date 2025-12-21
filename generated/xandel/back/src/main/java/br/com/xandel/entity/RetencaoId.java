package br.com.xandel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Chave primária composta para Retencao.
 */
@Embeddable
public class RetencaoId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_PlanoRetencao")
    private Integer idPlanoRetencao;

    @Column(name = "Ate")
    private BigDecimal ate;

    public RetencaoId() {
    }

    public RetencaoId(Integer idPlanoRetencao, BigDecimal ate) {
        this.idPlanoRetencao = idPlanoRetencao;
        this.ate = ate;
    }

    public Integer getIdPlanoRetencao() {
        return idPlanoRetencao;
    }

    public void setIdPlanoRetencao(Integer idPlanoRetencao) {
        this.idPlanoRetencao = idPlanoRetencao;
    }

    public BigDecimal getAte() {
        return ate;
    }

    public void setAte(BigDecimal ate) {
        this.ate = ate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetencaoId that = (RetencaoId) o;
        return Objects.equals(idPlanoRetencao, that.idPlanoRetencao)
            && Objects.equals(ate, that.ate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPlanoRetencao, ate);
    }

    @Override
    public String toString() {
        return "RetencaoId{" +
            "idPlanoRetencao=" + idPlanoRetencao +
            ", ate=" + ate +
            '}';
    }
}
