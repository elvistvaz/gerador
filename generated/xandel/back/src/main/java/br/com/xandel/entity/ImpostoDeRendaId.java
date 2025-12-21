package br.com.xandel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Chave primária composta para ImpostoDeRenda.
 */
@Embeddable
public class ImpostoDeRendaId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "Data")
    private LocalDateTime data;

    @Column(name = "de")
    private BigDecimal de;

    public ImpostoDeRendaId() {
    }

    public ImpostoDeRendaId(LocalDateTime data, BigDecimal de) {
        this.data = data;
        this.de = de;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public BigDecimal getDe() {
        return de;
    }

    public void setDe(BigDecimal de) {
        this.de = de;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImpostoDeRendaId that = (ImpostoDeRendaId) o;
        return Objects.equals(data, that.data)
            && Objects.equals(de, that.de);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, de);
    }

    @Override
    public String toString() {
        return "ImpostoDeRendaId{" +
            "data=" + data +
            ", de=" + de +
            '}';
    }
}
