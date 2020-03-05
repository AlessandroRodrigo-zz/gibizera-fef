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
public class TipoLombada {
    
    private Integer idTipoLombada;
    private String descricaoTipoLombada;
    private String situacaoTipoLombada;

    public TipoLombada() {
    }

    public TipoLombada(Integer idTipoLomabada, String descricaoTipoLombada, String situacaoTipoLombada) {
        this.idTipoLombada = idTipoLomabada;
        this.descricaoTipoLombada = descricaoTipoLombada;
        this.situacaoTipoLombada = situacaoTipoLombada;
    }

    public Integer getIdTipoLombada() {
        return idTipoLombada;
    }

    public void setIdTipoLombada(Integer idTipoLomabada) {
        this.idTipoLombada = idTipoLomabada;
    }

    public String getDescricaoTipoLombada() {
        return descricaoTipoLombada;
    }

    public void setDescricaoTipoLombada(String descricaoTipoLombada) {
        this.descricaoTipoLombada = descricaoTipoLombada;
    }

    public String getSituacaoTipoLombada() {
        return situacaoTipoLombada;
    }

    public void setSituacaoTipoLombada(String situacaoTipoLombada) {
        this.situacaoTipoLombada = situacaoTipoLombada;
    }
    
    
}
