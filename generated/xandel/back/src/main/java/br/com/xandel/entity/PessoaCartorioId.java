package br.com.xandel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Chave primária composta para PessoaCartorio.
 */
@Embeddable
public class PessoaCartorioId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_Pessoa")
    private Integer idPessoa;

    @Column(name = "id_Cartorio")
    private Integer idCartorio;

    public PessoaCartorioId() {
    }

    public PessoaCartorioId(Integer idPessoa, Integer idCartorio) {
        this.idPessoa = idPessoa;
        this.idCartorio = idCartorio;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Integer getIdCartorio() {
        return idCartorio;
    }

    public void setIdCartorio(Integer idCartorio) {
        this.idCartorio = idCartorio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PessoaCartorioId that = (PessoaCartorioId) o;
        return Objects.equals(idPessoa, that.idPessoa)
            && Objects.equals(idCartorio, that.idCartorio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPessoa, idCartorio);
    }

    @Override
    public String toString() {
        return "PessoaCartorioId{" +
            "idPessoa=" + idPessoa +
            ", idCartorio=" + idCartorio +
            '}';
    }
}
