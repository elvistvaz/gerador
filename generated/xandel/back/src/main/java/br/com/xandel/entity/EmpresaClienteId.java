package br.com.xandel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Chave primária composta para EmpresaCliente.
 */
@Embeddable
public class EmpresaClienteId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_Empresa")
    private Integer idEmpresa;

    @Column(name = "id_Cliente")
    private Integer idCliente;

    public EmpresaClienteId() {
    }

    public EmpresaClienteId(Integer idEmpresa, Integer idCliente) {
        this.idEmpresa = idEmpresa;
        this.idCliente = idCliente;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpresaClienteId that = (EmpresaClienteId) o;
        return Objects.equals(idEmpresa, that.idEmpresa)
            && Objects.equals(idCliente, that.idCliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmpresa, idCliente);
    }

    @Override
    public String toString() {
        return "EmpresaClienteId{" +
            "idEmpresa=" + idEmpresa +
            ", idCliente=" + idCliente +
            '}';
    }
}
