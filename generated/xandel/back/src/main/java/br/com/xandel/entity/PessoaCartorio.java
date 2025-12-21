package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Relação pessoa-cartório
 */
@Entity
@Table(name = "PessoaCartorio", schema = "dbo")
public class PessoaCartorio {

    @EmbeddedId
    private PessoaCartorioId id;

    public PessoaCartorio() {
    }

    public PessoaCartorioId getId() {
        return id;
    }

    public void setId(PessoaCartorioId id) {
        this.id = id;
    }

}
