package br.com.icep.dto;

import java.time.LocalDateTime;

public class UsuarioResponseDTO {

    private Long id;
    private String username;
    private String nome;
    private String email;
    private Boolean ativo;
    private String perfilId;
    private String perfilNome;
    private LocalDateTime dataCriacao;
    private LocalDateTime ultimoAcesso;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public String getPerfilId() { return perfilId; }
    public void setPerfilId(String perfilId) { this.perfilId = perfilId; }

    public String getPerfilNome() { return perfilNome; }
    public void setPerfilNome(String perfilNome) { this.perfilNome = perfilNome; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getUltimoAcesso() { return ultimoAcesso; }
    public void setUltimoAcesso(LocalDateTime ultimoAcesso) { this.ultimoAcesso = ultimoAcesso; }
}
