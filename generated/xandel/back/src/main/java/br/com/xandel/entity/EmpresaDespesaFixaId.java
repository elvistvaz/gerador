package br.com.xandel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Chave primária composta para EmpresaDespesaFixa.
 */
@Embeddable
public class EmpresaDespesaFixaId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_Empresa")
    private Integer idEmpresa;

    @Column(name = "id_DespesaReceita")
    private Integer idDespesaReceita;

    public EmpresaDespesaFixaId() {
    }

    public EmpresaDespesaFixaId(Integer idEmpresa, Integer idDespesaReceita) {
        this.idEmpresa = idEmpresa;
        this.idDespesaReceita = idDespesaReceita;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdDespesaReceita() {
        return idDespesaReceita;
    }

    public void setIdDespesaReceita(Integer idDespesaReceita) {
        this.idDespesaReceita = idDespesaReceita;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpresaDespesaFixaId that = (EmpresaDespesaFixaId) o;
        return Objects.equals(idEmpresa, that.idEmpresa)
            && Objects.equals(idDespesaReceita, that.idDespesaReceita);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmpresa, idDespesaReceita);
    }

    @Override
    public String toString() {
        return "EmpresaDespesaFixaId{" +
            "idEmpresa=" + idEmpresa +
            ", idDespesaReceita=" + idDespesaReceita +
            '}';
    }
}
