package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Cadastro de clientes
 */
@Entity
@Table(name = "Cliente", schema = "dbo")
public class Cliente {

    @Id
    @Column(name = "id_Cliente")
    private Integer idCliente;

    @Column(name = "NomeCliente", nullable = false, length = 70)
    private String nomeCliente;

    @Column(name = "FantasiaCliente", length = 40)
    private String fantasiaCliente;

    @Column(name = "CNPJ", length = 14)
    private String cnpj;

    @Column(name = "DataContrato")
    private LocalDateTime dataContrato;

    @Column(name = "TaxaADM", precision = 6, scale = 2)
    private BigDecimal taxaADM;

    @Column(name = "Endereco", length = 60)
    private String endereco;

    @Column(name = "CEP", length = 8)
    private String cep;

    @Column(name = "id_Bairro")
    private Integer idBairro;

    @Column(name = "id_Cidade")
    private Integer idCidade;

    @Column(name = "Contato", length = 30)
    private String contato;

    @Column(name = "Fone1", length = 9)
    private String fone1;

    @Column(name = "Fone2", length = 9)
    private String fone2;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "PessoaJuridica")
    private Integer pessoaJuridica;

    public Cliente() {
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getFantasiaCliente() {
        return fantasiaCliente;
    }

    public void setFantasiaCliente(String fantasiaCliente) {
        this.fantasiaCliente = fantasiaCliente;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public LocalDateTime getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(LocalDateTime dataContrato) {
        this.dataContrato = dataContrato;
    }

    public BigDecimal getTaxaADM() {
        return taxaADM;
    }

    public void setTaxaADM(BigDecimal taxaADM) {
        this.taxaADM = taxaADM;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getIdBairro() {
        return idBairro;
    }

    public void setIdBairro(Integer idBairro) {
        this.idBairro = idBairro;
    }

    public Integer getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getFone1() {
        return fone1;
    }

    public void setFone1(String fone1) {
        this.fone1 = fone1;
    }

    public String getFone2() {
        return fone2;
    }

    public void setFone2(String fone2) {
        this.fone2 = fone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(Integer pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

}
