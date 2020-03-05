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
public class TipoPublicacao {

    private Integer idTipoPublicacao;
    private String descricaoTipoPublicacao;
    private String situacaoTipoPublicacao;

    public TipoPublicacao() {
    }

    public TipoPublicacao(Integer idTipoPublicacao, String descricaoTipoPublicacao, String situacaoTipoPublicacao) {
        this.idTipoPublicacao = idTipoPublicacao;
        this.descricaoTipoPublicacao = descricaoTipoPublicacao;
        this.situacaoTipoPublicacao = situacaoTipoPublicacao;
    }

    public Integer getIdTipoPublicacao() {
        return idTipoPublicacao;
    }

    public void setIdTipoPublicacao(Integer idTipoPublicacao) {
        this.idTipoPublicacao = idTipoPublicacao;
    }

    public String getDescricaoTipoPublicacao() {
        return descricaoTipoPublicacao;
    }

    public void setDescricaoTipoPublicacao(String descricaoTipoPublicacao) {
        this.descricaoTipoPublicacao = descricaoTipoPublicacao;
    }

    public String getSituacaoTipoPublicacao() {
        return situacaoTipoPublicacao;
    }

    public void setSituacaoTipoPublicacao(String situacaoTipoPublicacao) {
        this.situacaoTipoPublicacao = situacaoTipoPublicacao;
    }
    
    

}
