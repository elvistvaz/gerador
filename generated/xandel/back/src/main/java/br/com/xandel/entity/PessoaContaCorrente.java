package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Contas correntes da pessoa
 */
@Entity
@Table(name = "PessoaContaCorrente", schema = "dbo")
public class PessoaContaCorrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ContaCorrente")
    private Integer idContaCorrente;

    @Column(name = "id_Pessoa")
    private Integer idPessoa;

    @Column(name = "id_Banco", length = 3)
    private String idBanco;

    @Column(name = "Agencia", length = 5)
    private String agencia;

    @Column(name = "ContaCorrente", length = 15)
    private String contaCorrente;

    @Column(name = "dvContaCorrente", length = 2)
    private String dvContaCorrente;

    @Column(name = "Ativa")
    private Boolean ativa;

    @Column(name = "ContaPoupanca")
    private Boolean contaPoupanca;

    @Column(name = "ContaPadrao")
    private Boolean contaPadrao;

    @Column(name = "NomeDependente", length = 60)
    private String nomeDependente;

    @Column(name = "PIX", length = 100)
    private String pix;

    public PessoaContaCorrente() {
    }

    public Integer getIdContaCorrente() {
        return idContaCorrente;
    }

    public void setIdContaCorrente(Integer idContaCorrente) {
        this.idContaCorrente = idContaCorrente;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(String idBanco) {
        this.idBanco = idBanco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(String contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public String getDvContaCorrente() {
        return dvContaCorrente;
    }

    public void setDvContaCorrente(String dvContaCorrente) {
        this.dvContaCorrente = dvContaCorrente;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public Boolean getContaPoupanca() {
        return contaPoupanca;
    }

    public void setContaPoupanca(Boolean contaPoupanca) {
        this.contaPoupanca = contaPoupanca;
    }

    public Boolean getContaPadrao() {
        return contaPadrao;
    }

    public void setContaPadrao(Boolean contaPadrao) {
        this.contaPadrao = contaPadrao;
    }

    public String getNomeDependente() {
        return nomeDependente;
    }

    public void setNomeDependente(String nomeDependente) {
        this.nomeDependente = nomeDependente;
    }

    public String getPix() {
        return pix;
    }

    public void setPix(String pix) {
        this.pix = pix;
    }

}
