package br.com.xandel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Chave primária composta para PessoaContaRecebimento.
 */
@Embeddable
public class PessoaContaRecebimentoId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_Pessoa")
    private Integer idPessoa;

    @Column(name = "id_Cliente")
    private Integer idCliente;

    @Column(name = "id_ContaCorrente")
    private Integer idContaCorrente;

    public PessoaContaRecebimentoId() {
    }

    public PessoaContaRecebimentoId(Integer idPessoa, Integer idCliente, Integer idContaCorrente) {
        this.idPessoa = idPessoa;
        this.idCliente = idCliente;
        this.idContaCorrente = idContaCorrente;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdContaCorrente() {
        return idContaCorrente;
    }

    public void setIdContaCorrente(Integer idContaCorrente) {
        this.idContaCorrente = idContaCorrente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PessoaContaRecebimentoId that = (PessoaContaRecebimentoId) o;
        return Objects.equals(idPessoa, that.idPessoa)
            && Objects.equals(idCliente, that.idCliente)
            && Objects.equals(idContaCorrente, that.idContaCorrente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPessoa, idCliente, idContaCorrente);
    }

    @Override
    public String toString() {
        return "PessoaContaRecebimentoId{" +
            "idPessoa=" + idPessoa +
            ", idCliente=" + idCliente +
            ", idContaCorrente=" + idContaCorrente +
            '}';
    }
}
