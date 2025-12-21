package br.com.xandel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Chave primária composta para PagamentoNaoSocio.
 */
@Embeddable
public class PagamentoNaoSocioId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "Data")
    private LocalDateTime data;

    @Column(name = "id_Empresa")
    private Integer idEmpresa;

    @Column(name = "id_Cliente")
    private Integer idCliente;

    @Column(name = "id_PessoaNaoSocio")
    private Integer idPessoaNaoSocio;

    public PagamentoNaoSocioId() {
    }

    public PagamentoNaoSocioId(LocalDateTime data, Integer idEmpresa, Integer idCliente, Integer idPessoaNaoSocio) {
        this.data = data;
        this.idEmpresa = idEmpresa;
        this.idCliente = idCliente;
        this.idPessoaNaoSocio = idPessoaNaoSocio;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
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

    public Integer getIdPessoaNaoSocio() {
        return idPessoaNaoSocio;
    }

    public void setIdPessoaNaoSocio(Integer idPessoaNaoSocio) {
        this.idPessoaNaoSocio = idPessoaNaoSocio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagamentoNaoSocioId that = (PagamentoNaoSocioId) o;
        return Objects.equals(data, that.data)
            && Objects.equals(idEmpresa, that.idEmpresa)
            && Objects.equals(idCliente, that.idCliente)
            && Objects.equals(idPessoaNaoSocio, that.idPessoaNaoSocio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, idEmpresa, idCliente, idPessoaNaoSocio);
    }

    @Override
    public String toString() {
        return "PagamentoNaoSocioId{" +
            "data=" + data +
            ", idEmpresa=" + idEmpresa +
            ", idCliente=" + idCliente +
            ", idPessoaNaoSocio=" + idPessoaNaoSocio +
            '}';
    }
}
