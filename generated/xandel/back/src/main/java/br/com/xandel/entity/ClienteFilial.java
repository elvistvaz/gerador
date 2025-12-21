package br.com.xandel.entity;

import jakarta.persistence.*;

/**
 * Filiais do cliente
 */
@Entity
@Table(name = "ClienteFilial", schema = "dbo")
public class ClienteFilial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ClienteFilial")
    private Integer idClienteFilial;

    @Column(name = "id_Cliente", nullable = false)
    private Integer idCliente;

    @Column(name = "NomeFilial", nullable = false, length = 100)
    private String nomeFilial;

    public ClienteFilial() {
    }

    public Integer getIdClienteFilial() {
        return idClienteFilial;
    }

    public void setIdClienteFilial(Integer idClienteFilial) {
        this.idClienteFilial = idClienteFilial;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeFilial() {
        return nomeFilial;
    }

    public void setNomeFilial(String nomeFilial) {
        this.nomeFilial = nomeFilial;
    }

}
