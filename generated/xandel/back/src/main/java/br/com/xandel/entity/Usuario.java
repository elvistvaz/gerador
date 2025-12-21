package br.com.xandel.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Cadastro de usuários do sistema
 */
@Entity
@Table(name = "Usuario", schema = "dbo")
public class Usuario {

    @Id
    @Column(name = "id_Usuario")
    private Integer idUsuario;

    @Column(name = "UsuarioNome", nullable = false, length = 60)
    private String usuarioNome;

    @Column(name = "UsuarioLogin", nullable = false, length = 30, unique = true)
    private String usuarioLogin;

    @Column(name = "UsuarioSenha", nullable = false, length = 50)
    private String usuarioSenha;

    @Column(name = "UsuarioNivelAcesso")
    private Integer usuarioNivelAcesso;

    @Column(name = "UsuarioInativo")
    private Boolean usuarioInativo;

    @Column(name = "UsuarioDataInicio")
    private LocalDateTime usuarioDataInicio;

    public Usuario() {
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getUsuarioSenha() {
        return usuarioSenha;
    }

    public void setUsuarioSenha(String usuarioSenha) {
        this.usuarioSenha = usuarioSenha;
    }

    public Integer getUsuarioNivelAcesso() {
        return usuarioNivelAcesso;
    }

    public void setUsuarioNivelAcesso(Integer usuarioNivelAcesso) {
        this.usuarioNivelAcesso = usuarioNivelAcesso;
    }

    public Boolean getUsuarioInativo() {
        return usuarioInativo;
    }

    public void setUsuarioInativo(Boolean usuarioInativo) {
        this.usuarioInativo = usuarioInativo;
    }

    public LocalDateTime getUsuarioDataInicio() {
        return usuarioDataInicio;
    }

    public void setUsuarioDataInicio(LocalDateTime usuarioDataInicio) {
        this.usuarioDataInicio = usuarioDataInicio;
    }

}
