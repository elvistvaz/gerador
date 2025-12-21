package br.com.xandel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Chave primária composta para NF.
 */
@Embeddable
public class NFId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_Empresa")
    private Integer idEmpresa;

    @Column(name = "NF")
    private Integer nf;

    public NFId() {
    }

    public NFId(Integer idEmpresa, Integer nf) {
        this.idEmpresa = idEmpresa;
        this.nf = nf;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getNf() {
        return nf;
    }

    public void setNf(Integer nf) {
        this.nf = nf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NFId that = (NFId) o;
        return Objects.equals(idEmpresa, that.idEmpresa)
            && Objects.equals(nf, that.nf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmpresa, nf);
    }

    @Override
    public String toString() {
        return "NFId{" +
            "idEmpresa=" + idEmpresa +
            ", nf=" + nf +
            '}';
    }
}
