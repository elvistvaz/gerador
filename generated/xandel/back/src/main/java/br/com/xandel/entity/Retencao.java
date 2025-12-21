package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Faixas de retenção por plano
 */
@Entity
@Table(name = "Retencao", schema = "dbo")
public class Retencao {

    @EmbeddedId
    private RetencaoId id;

    @Column(name = "Reter", nullable = false, precision = 5, scale = 2)
    private BigDecimal reter;

    public Retencao() {
    }

    public RetencaoId getId() {
        return id;
    }

    public void setId(RetencaoId id) {
        this.id = id;
    }

    public BigDecimal getReter() {
        return reter;
    }

    public void setReter(BigDecimal reter) {
        this.reter = reter;
    }

}
