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
public class Funcao {
    
    private Integer idFuncao;
    private String descricaoFuncao;

    public Funcao() {
    }

    public Funcao(Integer idFuncao, String descricaoFuncao) {
        this.idFuncao = idFuncao;
        this.descricaoFuncao = descricaoFuncao;
    }

    public Integer getIdFuncao() {
        return idFuncao;
    }

    public void setIdFuncao(Integer idFuncao) {
        this.idFuncao = idFuncao;
    }

    public String getDescricaoFuncao() {
        return descricaoFuncao;
    }

    public void setDescricaoFuncao(String descricaoFuncao) {
        this.descricaoFuncao = descricaoFuncao;
    }        
    
}
