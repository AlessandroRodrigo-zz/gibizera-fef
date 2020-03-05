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
public class Genero {
    
    private Integer idGenero;
    private String descricaoGenero;
    private String situacaoGenero;
       
    public Genero() {
    }
    
    public Genero(Integer idGenero, String descricaoGenero, String situacaoGenero) {
        this.idGenero = idGenero;
        this.descricaoGenero = descricaoGenero;
        this.situacaoGenero = situacaoGenero;
    }

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

    public String getDescricaoGenero() {
        return descricaoGenero;
    }

    public void setDescricaoGenero(String descricaoGenero) {
        this.descricaoGenero = descricaoGenero;
    }

    public String getSituacaoGenero() {
        return situacaoGenero;
    }

    public void setSituacaoGenero(String situacaoGenero) {
        this.situacaoGenero = situacaoGenero;
    }

    
    
}
