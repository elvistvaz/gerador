package br.com.icep.dto;

import java.time.LocalDateTime;

/**
 * DTO para listagem de AuditLog.
 */
public class AuditLogListDTO {

    private Long id;
    private String acao;
    private String entidade;
    private String chave;
    private String usuario;
    private LocalDateTime dataHora;

    public AuditLogListDTO() {}

    public AuditLogListDTO(Long id, String acao, String entidade, String chave, String usuario, LocalDateTime dataHora) {
        this.id = id;
        this.acao = acao;
        this.entidade = entidade;
        this.chave = chave;
        this.usuario = usuario;
        this.dataHora = dataHora;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }

    public String getEntidade() { return entidade; }
    public void setEntidade(String entidade) { this.entidade = entidade; }

    public String getChave() { return chave; }
    public void setChave(String chave) { this.chave = chave; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
