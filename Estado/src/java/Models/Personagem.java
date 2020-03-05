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
public class Personagem {
    
    private Integer idPersonagem;
    private String descricaoPersonagem;

    public Personagem() {
    }

    public Personagem(Integer idPersonagem, String descricaoPersonagem) {
        this.idPersonagem = idPersonagem;
        this.descricaoPersonagem = descricaoPersonagem;
    }

    public Integer getIdPersonagem() {
        return idPersonagem;
    }

    public void setIdPersonagem(Integer idPersonagem) {
        this.idPersonagem = idPersonagem;
    }

    public String getDescricaoPersonagem() {
        return descricaoPersonagem;
    }

    public void setDescricaoPersonagem(String descricaoPersonagem) {
        this.descricaoPersonagem = descricaoPersonagem;
    }
    
    
    
}
