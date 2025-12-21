package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Despesas fixas das empresas
 */
@Entity
@Table(name = "EmpresaDespesaFixa", schema = "dbo")
public class EmpresaDespesaFixa {

    @EmbeddedId
    private EmpresaDespesaFixaId id;

    @Column(name = "DataLancamento")
    private LocalDateTime dataLancamento;

    @Column(name = "Parcelas")
    private Integer parcelas;

    @Column(name = "ValorEmpresa")
    private BigDecimal valorEmpresa;

    @Column(name = "Inativa")
    private Integer inativa;

    public EmpresaDespesaFixa() {
    }

    public EmpresaDespesaFixaId getId() {
        return id;
    }

    public void setId(EmpresaDespesaFixaId id) {
        this.id = id;
    }

    public LocalDateTime getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDateTime dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public BigDecimal getValorEmpresa() {
        return valorEmpresa;
    }

    public void setValorEmpresa(BigDecimal valorEmpresa) {
        this.valorEmpresa = valorEmpresa;
    }

    public Integer getInativa() {
        return inativa;
    }

    public void setInativa(Integer inativa) {
        this.inativa = inativa;
    }

}
