package br.com.icep.dto;

public class UsuarioListDTO {

    private Long id;
    private String username;
    private String nome;
    private String email;
    private Boolean ativo;
    private String perfilNome;

    public UsuarioListDTO() {}

    public UsuarioListDTO(Long id, String username, String nome, String email, Boolean ativo, String perfilNome) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.email = email;
        this.ativo = ativo;
        this.perfilNome = perfilNome;
    }

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

    public String getPerfilNome() { return perfilNome; }
    public void setPerfilNome(String perfilNome) { this.perfilNome = perfilNome; }
}
