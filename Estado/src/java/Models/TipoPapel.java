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
public class TipoPapel {
    
    private Integer idTipoPapel;
    private String descricaoTipoPapel;
    private String situacaoTipoPapel;

    public TipoPapel() {
    }

    public TipoPapel(Integer idTipoPapel, String descricaoTipoPapel, String situacaoTipoPapel) {
        this.idTipoPapel = idTipoPapel;
        this.descricaoTipoPapel = descricaoTipoPapel;
        this.situacaoTipoPapel = situacaoTipoPapel;
    }

    public Integer getIdTipoPapel() {
        return idTipoPapel;
    }

    public void setIdTipoPapel(Integer idTipoPapel) {
        this.idTipoPapel = idTipoPapel;
    }

    public String getDescricaoTipoPapel() {
        return descricaoTipoPapel;
    }

    public void setDescricaoTipoPapel(String descricaoTipoPapel) {
        this.descricaoTipoPapel = descricaoTipoPapel;
    }

    public String getSituacaoTipoPapel() {
        return situacaoTipoPapel;
    }

    public void setSituacaoTipoPapel(String situacaoTipoPapel) {
        this.situacaoTipoPapel = situacaoTipoPapel;
    }
    
    
    
}
