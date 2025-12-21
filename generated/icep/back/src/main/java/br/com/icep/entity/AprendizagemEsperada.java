package br.com.icep.entity;

import jakarta.persistence.*;

/**
 * Aprendizagens esperadas por componente curricular e conceito
 */
@Entity
@Table(name = "AprendizagemEsperada", schema = "dbo")
public class AprendizagemEsperada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "componente_id", nullable = false)
    private Integer componenteId;

    @Column(name = "conceito_aprendido_id", nullable = false)
    private Integer conceitoAprendidoId;

    @Column(name = "descricao", nullable = false, length = 1000)
    private String descricao;

    @Column(name = "codigo", nullable = false, length = 20)
    private String codigo;

    public AprendizagemEsperada() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getComponenteId() {
        return componenteId;
    }

    public void setComponenteId(Integer componenteId) {
        this.componenteId = componenteId;
    }

    public Integer getConceitoAprendidoId() {
        return conceitoAprendidoId;
    }

    public void setConceitoAprendidoId(Integer conceitoAprendidoId) {
        this.conceitoAprendidoId = conceitoAprendidoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
