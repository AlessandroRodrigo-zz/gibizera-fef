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
public class Arco {
    
    private Integer idArco;
    private String descricaoArco;

    public Arco() {
    }  
    
    public Arco(Integer idArco, String descricaoArco) {
        this.idArco = idArco;
        this.descricaoArco = descricaoArco;
    }

    public Integer getIdArco() {
        return idArco;
    }

    public void setIdArco(Integer idArco) {
        this.idArco = idArco;
    }

    public String getDescricaoArco() {
        return descricaoArco;
    }

    public void setDescricaoArco(String descricaoArco) {
        this.descricaoArco = descricaoArco;
    }        
    
}
