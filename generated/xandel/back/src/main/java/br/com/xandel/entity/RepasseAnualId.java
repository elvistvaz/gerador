package br.com.xandel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Chave primária composta para RepasseAnual.
 */
@Embeddable
public class RepasseAnualId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "Ano", length = 4)
    private String ano;

    @Column(name = "id_Empresa")
    private Integer idEmpresa;

    @Column(name = "id_Pessoa")
    private Integer idPessoa;

    public RepasseAnualId() {
    }

    public RepasseAnualId(String ano, Integer idEmpresa, Integer idPessoa) {
        this.ano = ano;
        this.idEmpresa = idEmpresa;
        this.idPessoa = idPessoa;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
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
        RepasseAnualId that = (RepasseAnualId) o;
        return Objects.equals(ano, that.ano)
            && Objects.equals(idEmpresa, that.idEmpresa)
            && Objects.equals(idPessoa, that.idPessoa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ano, idEmpresa, idPessoa);
    }

    @Override
    public String toString() {
        return "RepasseAnualId{" +
            "ano=" + ano +
            ", idEmpresa=" + idEmpresa +
            ", idPessoa=" + idPessoa +
            '}';
    }
}
