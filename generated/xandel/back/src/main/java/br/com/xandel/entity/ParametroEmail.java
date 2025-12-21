package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Parâmetros de configuração de e-mail
 */
@Entity
@Table(name = "ParametroEmail", schema = "dbo")
public class ParametroEmail {

    @Id
    @Column(name = "id_Parametro")
    private Integer idParametro;

    @Column(name = "AssuntoEmail", length = 100)
    private String assuntoEmail;

    @Column(name = "SMTP", length = 100)
    private String smtp;

    @Column(name = "ContaEmail", length = 100)
    private String contaEmail;

    @Column(name = "SenhaEmail", length = 100)
    private String senhaEmail;

    public ParametroEmail() {
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public String getAssuntoEmail() {
        return assuntoEmail;
    }

    public void setAssuntoEmail(String assuntoEmail) {
        this.assuntoEmail = assuntoEmail;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getContaEmail() {
        return contaEmail;
    }

    public void setContaEmail(String contaEmail) {
        this.contaEmail = contaEmail;
    }

    public String getSenhaEmail() {
        return senhaEmail;
    }

    public void setSenhaEmail(String senhaEmail) {
        this.senhaEmail = senhaEmail;
    }

}
