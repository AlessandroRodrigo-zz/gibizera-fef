/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

/**
 *
 * @author Flavio Prado
 */
public class Formato {
    
    private Integer idFormato;
    private String descricaoFormato;
    private String situacaoFormato;

    public Formato() {
    }

    public Formato(Integer idFormato, String descricaoFormato, String situacaoFormato) {
        this.idFormato = idFormato;
        this.descricaoFormato = descricaoFormato;
        this.situacaoFormato = situacaoFormato;
    }

    public Integer getIdFormato() {
        return idFormato;
    }

    public void setIdFormato(Integer idFormato) {
        this.idFormato = idFormato;
    }

    public String getDescricaoFormato() {
        return descricaoFormato;
    }

    public void setDescricaoFormato(String descricaoFormato) {
        this.descricaoFormato = descricaoFormato;
    }

    public String getSituacaoFormato() {
        return situacaoFormato;
    }

    public void setSituacaoFormato(String situacaoFormato) {
        this.situacaoFormato = situacaoFormato;
    }
    
    
    
}
