/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Aluno
 */
public class TipoCapa {
    
    private Integer idTipoCapa;
    private String descricaoTipoCapa;
    private String situacaoTipoCapa;

    public TipoCapa() {
    }

    public TipoCapa(Integer idTipoCapa, String descricaoTipoCapa, String situacaoTipoCapa) {
        this.idTipoCapa = idTipoCapa;
        this.descricaoTipoCapa = descricaoTipoCapa;
        this.situacaoTipoCapa = situacaoTipoCapa;
    }        
    
    public String getSituacaoTipoCapa() {
        return situacaoTipoCapa;
    }

    public void setSituacaoTipoCapa(String situacaoTipoCapa) {
        this.situacaoTipoCapa = situacaoTipoCapa;
    }

    public Integer getIdTipoCapa() {
        return idTipoCapa;
    }

    public void setIdTipoCapa(Integer idTipoCapa) {
        this.idTipoCapa = idTipoCapa;
    }

    public String getDescricaoTipoCapa() {
        return descricaoTipoCapa;
    }

    public void setDescricaoTipoCapa(String descricaoTipoCapa) {
        this.descricaoTipoCapa = descricaoTipoCapa;
    }        
    
}
