package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Contas de recebimento por pessoa e cliente
 */
@Entity
@Table(name = "PessoaContaRecebimento", schema = "dbo")
public class PessoaContaRecebimento {

    @EmbeddedId
    private PessoaContaRecebimentoId id;

    public PessoaContaRecebimento() {
    }

    public PessoaContaRecebimentoId getId() {
        return id;
    }

    public void setId(PessoaContaRecebimentoId id) {
        this.id = id;
    }

}
