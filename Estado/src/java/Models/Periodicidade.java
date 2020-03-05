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
public class Periodicidade {

    private Integer idPeriodicidade;
    private String descricaoPeriodicidade;
    private String situacaoPeriodicidade;

    public Periodicidade() {
    }

    public Periodicidade(Integer idPeriodicidade, String descricaoPeriodicidade, String situacaoPeriodicidade) {
        this.idPeriodicidade = idPeriodicidade;
        this.descricaoPeriodicidade = descricaoPeriodicidade;
        this.situacaoPeriodicidade = situacaoPeriodicidade;
    }

    public Integer getIdPeriodicidade() {
        return idPeriodicidade;
    }

    public void setIdPeriodicidade(Integer idPeriodicidade) {
        this.idPeriodicidade = idPeriodicidade;
    }

    public String getDescricaoPeriodicidade() {
        return descricaoPeriodicidade;
    }

    public void setDescricaoPeriodicidade(String descricaoPeriodicidade) {
        this.descricaoPeriodicidade = descricaoPeriodicidade;
    }

    public String getSituacaoPeriodicidade() {
        return situacaoPeriodicidade;
    }

    public void setSituacaoPeriodicidade(String situacaoPeriodicidade) {
        this.situacaoPeriodicidade = situacaoPeriodicidade;
    }

    
}
