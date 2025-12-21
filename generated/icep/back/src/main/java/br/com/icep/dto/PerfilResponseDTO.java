package br.com.icep.dto;

import java.util.Set;

public class PerfilResponseDTO {

    private String id;
    private String nome;
    private String descricao;
    private Boolean ativo;
    private Set<String> permissoes;

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
