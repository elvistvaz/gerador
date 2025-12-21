package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Contatos do cliente
 */
@Entity
@Table(name = "ClienteContato", schema = "dbo")
public class ClienteContato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ClienteContato")
    private Integer idClienteContato;

    @Column(name = "id_Cliente", nullable = false)
    private Integer idCliente;

    @Column(name = "id_TipoContato", nullable = false)
    private Integer idTipoContato;

    @Column(name = "Descricao", nullable = false, length = 100)
    private String descricao;

    public ClienteContato() {
    }

    public Integer getIdClienteContato() {
        return idClienteContato;
    }

    public void setIdClienteContato(Integer idClienteContato) {
        this.idClienteContato = idClienteContato;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdTipoContato() {
        return idTipoContato;
    }

    public void setIdTipoContato(Integer idTipoContato) {
        this.idTipoContato = idTipoContato;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
