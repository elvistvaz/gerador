package br.com.xandel.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Cadastro de pessoas (sócios/médicos)
 */
@Entity
@Table(name = "Pessoa", schema = "dbo")
public class Pessoa {

    @Id
    @Column(name = "id_Pessoa")
    private Integer idPessoa;

    @Column(name = "Nome", length = 60)
    private String nome;

    @Column(name = "CPF", length = 11)
    private String cpf;

    @Column(name = "id_Conselho")
    private Integer idConselho;

    @Column(name = "NumeroConselho")
    private Integer numeroConselho;

    @Column(name = "Nascimento")
    private LocalDateTime nascimento;

    @Column(name = "RG", length = 14)
    private String rg;

    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "Telefone", length = 9)
    private String telefone;

    @Column(name = "Celular", length = 9)
    private String celular;

    @Column(name = "Endereco", length = 60)
    private String endereco;

    @Column(name = "id_Bairro")
    private Integer idBairro;

    @Column(name = "id_Cidade")
    private Integer idCidade;

    @Column(name = "CEP", length = 8)
    private String cep;

    @Column(name = "id_EstadoCivil")
    private Integer idEstadoCivil;

    @Column(name = "DataAdesao")
    private LocalDateTime dataAdesao;

    @Column(name = "DataInativo")
    private LocalDateTime dataInativo;

    @Column(name = "id_Banco", length = 3)
    private String idBanco;

    @Column(name = "Agencia", length = 5)
    private String agencia;

    @Column(name = "Conta", length = 11)
    private String conta;

    @Column(name = "id_PlanoRetencao")
    private Integer idPlanoRetencao;

    @Column(name = "id_Operadora")
    private Integer idOperadora;

    public Pessoa() {
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getIdConselho() {
        return idConselho;
    }

    public void setIdConselho(Integer idConselho) {
        this.idConselho = idConselho;
    }

    public Integer getNumeroConselho() {
        return numeroConselho;
    }

    public void setNumeroConselho(Integer numeroConselho) {
        this.numeroConselho = numeroConselho;
    }

    public LocalDateTime getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDateTime nascimento) {
        this.nascimento = nascimento;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getIdEstadoCivil() {
        return idEstadoCivil;
    }

    public void setIdEstadoCivil(Integer idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
    }

    public LocalDateTime getDataAdesao() {
        return dataAdesao;
    }

    public void setDataAdesao(LocalDateTime dataAdesao) {
        this.dataAdesao = dataAdesao;
    }

    public LocalDateTime getDataInativo() {
        return dataInativo;
    }

    public void setDataInativo(LocalDateTime dataInativo) {
        this.dataInativo = dataInativo;
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

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public Integer getIdPlanoRetencao() {
        return idPlanoRetencao;
    }

    public void setIdPlanoRetencao(Integer idPlanoRetencao) {
        this.idPlanoRetencao = idPlanoRetencao;
    }

    public Integer getIdOperadora() {
        return idOperadora;
    }

    public void setIdOperadora(Integer idOperadora) {
        this.idOperadora = idOperadora;
    }

}
