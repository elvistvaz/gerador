package br.com.icep.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

public class UsuarioRequestDTO {

    @NotBlank(message = "Username é obrigatório")
    @Size(min = 3, max = 100, message = "Username deve ter entre 3 e 100 caracteres")
    private String username;

    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String senha;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 200, message = "Nome deve ter no máximo 200 caracteres")
    private String nome;

    @Email(message = "Email inválido")
    private String email;

    private Boolean ativo = true;

    @NotBlank(message = "Perfil é obrigatório")
    private String perfilId;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public String getPerfilId() { return perfilId; }
    public void setPerfilId(String perfilId) { this.perfilId = perfilId; }
}
