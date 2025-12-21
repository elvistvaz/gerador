package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Anuidades do CREMEB
 */
@Entity
@Table(name = "AnuidadeCremeb", schema = "dbo")
public class AnuidadeCremeb {

    @Id
    @Column(name = "id_AnuidadeCremeb")
    private Integer idAnuidadeCremeb;

    @Column(name = "AnoExercicio", length = 4)
    private String anoExercicio;

    @Column(name = "DataInicio")
    private LocalDateTime dataInicio;

    @Column(name = "DataFim")
    private LocalDateTime dataFim;

    @Column(name = "ValorTotal")
    private BigDecimal valorTotal;

    @Column(name = "id_Empresa")
    private Integer idEmpresa;

    public AnuidadeCremeb() {
    }

    public Integer getIdAnuidadeCremeb() {
        return idAnuidadeCremeb;
    }

    public void setIdAnuidadeCremeb(Integer idAnuidadeCremeb) {
        this.idAnuidadeCremeb = idAnuidadeCremeb;
    }

    public String getAnoExercicio() {
        return anoExercicio;
    }

    public void setAnoExercicio(String anoExercicio) {
        this.anoExercicio = anoExercicio;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

}
