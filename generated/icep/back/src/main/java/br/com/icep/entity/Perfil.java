package br.com.icep.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidade que representa um perfil de acesso no sistema.
 */
@Entity
@Table(name = "perfil", schema = "dbo")
public class Perfil {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "perfil_permissao",
        schema = "dbo",
        joinColumns = @JoinColumn(name = "perfil_id")
    )
    @Column(name = "permissao")
    private Set<String> permissoes = new HashSet<>();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public Set<String> getPermissoes() { return permissoes; }
    public void setPermissoes(Set<String> permissoes) { this.permissoes = permissoes; }
}
