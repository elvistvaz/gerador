package br.com.xandel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Chave primária composta para MedicoEspecialidade.
 */
@Embeddable
public class MedicoEspecialidadeId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_Pessoa")
    private Integer idPessoa;

    @Column(name = "id_Especialidade")
    private Integer idEspecialidade;

    public MedicoEspecialidadeId() {
    }

    public MedicoEspecialidadeId(Integer idPessoa, Integer idEspecialidade) {
        this.idPessoa = idPessoa;
        this.idEspecialidade = idEspecialidade;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Integer getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Integer idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicoEspecialidadeId that = (MedicoEspecialidadeId) o;
        return Objects.equals(idPessoa, that.idPessoa)
            && Objects.equals(idEspecialidade, that.idEspecialidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPessoa, idEspecialidade);
    }

    @Override
    public String toString() {
        return "MedicoEspecialidadeId{" +
            "idPessoa=" + idPessoa +
            ", idEspecialidade=" + idEspecialidade +
            '}';
    }
}
