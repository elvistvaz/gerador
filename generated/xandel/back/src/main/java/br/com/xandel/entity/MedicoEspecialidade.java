package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Relacionamento médico-especialidade
 */
@Entity
@Table(name = "MedicoEspecialidade", schema = "dbo")
public class MedicoEspecialidade {

    @EmbeddedId
    private MedicoEspecialidadeId id;

    public MedicoEspecialidade() {
    }

    public MedicoEspecialidadeId getId() {
        return id;
    }

    public void setId(MedicoEspecialidadeId id) {
        this.id = id;
    }

}
