package br.com.xandel.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade para registro de log de auditoria.
 */
@Entity
@Table(name = "audit_log", schema = "dbo")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "acao", nullable = false, length = 20)
    private String acao;

    @Column(name = "entidade", nullable = false, length = 100)
    private String entidade;

    @Column(name = "chave", nullable = false, length = 255)
    private String chave;

    @Column(name = "usuario", length = 100)
    private String usuario;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Lob
    @Column(name = "dados_anteriores", columnDefinition = "CLOB")
    private String dadosAnteriores;

    public AuditLog() {
    }

    public AuditLog(String acao, String entidade, String chave, String usuario, String dadosAnteriores) {
        this.acao = acao;
        this.entidade = entidade;
        this.chave = chave;
        this.usuario = usuario;
        this.dataHora = LocalDateTime.now();
        this.dadosAnteriores = dadosAnteriores;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getEntidade() {
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDadosAnteriores() {
        return dadosAnteriores;
    }

    public void setDadosAnteriores(String dadosAnteriores) {
        this.dadosAnteriores = dadosAnteriores;
    }
}
