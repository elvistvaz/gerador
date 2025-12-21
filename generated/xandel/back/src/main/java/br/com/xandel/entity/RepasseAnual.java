package br.com.xandel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Repasse anual consolidado
 */
@Entity
@Table(name = "RepasseAnual", schema = "dbo")
public class RepasseAnual {

    @EmbeddedId
    private RepasseAnualId id;

    @Column(name = "JanBruto")
    private BigDecimal janBruto;

    @Column(name = "JanTaxa")
    private BigDecimal janTaxa;

    @Column(name = "FevBruto")
    private BigDecimal fevBruto;

    @Column(name = "FevTaxa")
    private BigDecimal fevTaxa;

    @Column(name = "MarBruto")
    private BigDecimal marBruto;

    @Column(name = "MarTaxa")
    private BigDecimal marTaxa;

    @Column(name = "AbrBruto")
    private BigDecimal abrBruto;

    @Column(name = "AbrTaxa")
    private BigDecimal abrTaxa;

    @Column(name = "MaiBruto")
    private BigDecimal maiBruto;

    @Column(name = "MaiTaxa")
    private BigDecimal maiTaxa;

    @Column(name = "JunBruto")
    private BigDecimal junBruto;

    @Column(name = "JunTaxa")
    private BigDecimal junTaxa;

    @Column(name = "JulBruto")
    private BigDecimal julBruto;

    @Column(name = "JulTaxa")
    private BigDecimal julTaxa;

    @Column(name = "AgoBruto")
    private BigDecimal agoBruto;

    @Column(name = "AgoTaxa")
    private BigDecimal agoTaxa;

    @Column(name = "SetBruto")
    private BigDecimal setBruto;

    @Column(name = "SetTaxa")
    private BigDecimal setTaxa;

    @Column(name = "OutBruto")
    private BigDecimal outBruto;

    @Column(name = "OutTaxa")
    private BigDecimal outTaxa;

    @Column(name = "NovBruto")
    private BigDecimal novBruto;

    @Column(name = "NovTaxa")
    private BigDecimal novTaxa;

    @Column(name = "DezBruto")
    private BigDecimal dezBruto;

    @Column(name = "DezTaxa")
    private BigDecimal dezTaxa;

    public RepasseAnual() {
    }

    public RepasseAnualId getId() {
        return id;
    }

    public void setId(RepasseAnualId id) {
        this.id = id;
    }

    public BigDecimal getJanBruto() {
        return janBruto;
    }

    public void setJanBruto(BigDecimal janBruto) {
        this.janBruto = janBruto;
    }

    public BigDecimal getJanTaxa() {
        return janTaxa;
    }

    public void setJanTaxa(BigDecimal janTaxa) {
        this.janTaxa = janTaxa;
    }

    public BigDecimal getFevBruto() {
        return fevBruto;
    }

    public void setFevBruto(BigDecimal fevBruto) {
        this.fevBruto = fevBruto;
    }

    public BigDecimal getFevTaxa() {
        return fevTaxa;
    }

    public void setFevTaxa(BigDecimal fevTaxa) {
        this.fevTaxa = fevTaxa;
    }

    public BigDecimal getMarBruto() {
        return marBruto;
    }

    public void setMarBruto(BigDecimal marBruto) {
        this.marBruto = marBruto;
    }

    public BigDecimal getMarTaxa() {
        return marTaxa;
    }

    public void setMarTaxa(BigDecimal marTaxa) {
        this.marTaxa = marTaxa;
    }

    public BigDecimal getAbrBruto() {
        return abrBruto;
    }

    public void setAbrBruto(BigDecimal abrBruto) {
        this.abrBruto = abrBruto;
    }

    public BigDecimal getAbrTaxa() {
        return abrTaxa;
    }

    public void setAbrTaxa(BigDecimal abrTaxa) {
        this.abrTaxa = abrTaxa;
    }

    public BigDecimal getMaiBruto() {
        return maiBruto;
    }

    public void setMaiBruto(BigDecimal maiBruto) {
        this.maiBruto = maiBruto;
    }

    public BigDecimal getMaiTaxa() {
        return maiTaxa;
    }

    public void setMaiTaxa(BigDecimal maiTaxa) {
        this.maiTaxa = maiTaxa;
    }

    public BigDecimal getJunBruto() {
        return junBruto;
    }

    public void setJunBruto(BigDecimal junBruto) {
        this.junBruto = junBruto;
    }

    public BigDecimal getJunTaxa() {
        return junTaxa;
    }

    public void setJunTaxa(BigDecimal junTaxa) {
        this.junTaxa = junTaxa;
    }

    public BigDecimal getJulBruto() {
        return julBruto;
    }

    public void setJulBruto(BigDecimal julBruto) {
        this.julBruto = julBruto;
    }

    public BigDecimal getJulTaxa() {
        return julTaxa;
    }

    public void setJulTaxa(BigDecimal julTaxa) {
        this.julTaxa = julTaxa;
    }

    public BigDecimal getAgoBruto() {
        return agoBruto;
    }

    public void setAgoBruto(BigDecimal agoBruto) {
        this.agoBruto = agoBruto;
    }

    public BigDecimal getAgoTaxa() {
        return agoTaxa;
    }

    public void setAgoTaxa(BigDecimal agoTaxa) {
        this.agoTaxa = agoTaxa;
    }

    public BigDecimal getSetBruto() {
        return setBruto;
    }

    public void setSetBruto(BigDecimal setBruto) {
        this.setBruto = setBruto;
    }

    public BigDecimal getSetTaxa() {
        return setTaxa;
    }

    public void setSetTaxa(BigDecimal setTaxa) {
        this.setTaxa = setTaxa;
    }

    public BigDecimal getOutBruto() {
        return outBruto;
    }

    public void setOutBruto(BigDecimal outBruto) {
        this.outBruto = outBruto;
    }

    public BigDecimal getOutTaxa() {
        return outTaxa;
    }

    public void setOutTaxa(BigDecimal outTaxa) {
        this.outTaxa = outTaxa;
    }

    public BigDecimal getNovBruto() {
        return novBruto;
    }

    public void setNovBruto(BigDecimal novBruto) {
        this.novBruto = novBruto;
    }

    public BigDecimal getNovTaxa() {
        return novTaxa;
    }

    public void setNovTaxa(BigDecimal novTaxa) {
        this.novTaxa = novTaxa;
    }

    public BigDecimal getDezBruto() {
        return dezBruto;
    }

    public void setDezBruto(BigDecimal dezBruto) {
        this.dezBruto = dezBruto;
    }

    public BigDecimal getDezTaxa() {
        return dezTaxa;
    }

    public void setDezTaxa(BigDecimal dezTaxa) {
        this.dezTaxa = dezTaxa;
    }

}
