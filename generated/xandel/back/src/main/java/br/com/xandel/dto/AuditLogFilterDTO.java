package br.com.xandel.dto;

import java.time.LocalDateTime;

/**
 * DTO de filtro para busca de AuditLog.
 */
public class AuditLogFilterDTO {

    private String entidade;
    private String usuario;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    public AuditLogFilterDTO() {}

    public String getEntidade() { return entidade; }
    public void setEntidade(String entidade) { this.entidade = entidade; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }

    public LocalDateTime getDataFim() { return dataFim; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }
}
