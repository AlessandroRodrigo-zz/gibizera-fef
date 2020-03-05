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
public class Editora {
    
    private Integer idEditora;
    private String descricaoEditora;
    private String situacaoEditora;

    public Editora() {
    }

    public Editora(Integer idEditora, String descricaoEditora, String situacaoEditora) {
        this.idEditora = idEditora;
        this.descricaoEditora = descricaoEditora;
        this.situacaoEditora = situacaoEditora;
    }

    public Integer getIdEditora() {
        return idEditora;
    }

    public void setIdEditora(Integer idEditora) {
        this.idEditora = idEditora;
    }

    public String getDescricaoEditora() {
        return descricaoEditora;
    }

    public void setDescricaoEditora(String descricaoEditora) {
        this.descricaoEditora = descricaoEditora;
    }

    public String getSituacaoEditora() {
        return situacaoEditora;
    }

    public void setSituacaoEditora(String situacaoEditora) {
        this.situacaoEditora = situacaoEditora;
    }
    
    
    
}
