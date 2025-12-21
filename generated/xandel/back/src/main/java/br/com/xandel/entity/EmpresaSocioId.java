package br.com.xandel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Chave primária composta para EmpresaSocio.
 */
@Embeddable
public class EmpresaSocioId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_Empresa")
    private Integer idEmpresa;

    @Column(name = "id_Pessoa")
    private Integer idPessoa;

    public EmpresaSocioId() {
    }

    public EmpresaSocioId(Integer idEmpresa, Integer idPessoa) {
        this.idEmpresa = idEmpresa;
        this.idPessoa = idPessoa;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpresaSocioId that = (EmpresaSocioId) o;
        return Objects.equals(idEmpresa, that.idEmpresa)
            && Objects.equals(idPessoa, that.idPessoa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmpresa, idPessoa);
    }

    @Override
    public String toString() {
        return "EmpresaSocioId{" +
            "idEmpresa=" + idEmpresa +
            ", idPessoa=" + idPessoa +
            '}';
    }
}
