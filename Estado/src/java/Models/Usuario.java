/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Flavio Prado
 */
public class Usuario extends Pessoa {

    private Integer idUsuario;
    private String observacaoUsuario;
    private String situacaoUsuario;
    private String loginUsuario;
    private String senhaUsuario;

    public Usuario() {
    }

    public static Usuario UsuarioVazio() throws ParseException{
        Cidade cCidade = new Cidade();
        Usuario cUsuario = new Usuario(0, "", "A", "", "", cCidade, 0, 0, "", "", 0, "", 0 , 0, "");
        return cUsuario;
    }    
    
    public Usuario(Integer idUsuario, String observacaoUsuario, String situacaoUsuario, String loginUsuario, String senhaUsuario, Cidade modelCidade, Integer idPessoa, double cpfCnpjPessoa, String nomePessoa, String enderecoPessoa, double nroEnderecoPessoa, String bairroPessoa, double telefonePessoa, double celularPessoa, String emailPessoa) {
        super(modelCidade, idPessoa, cpfCnpjPessoa, nomePessoa, enderecoPessoa, nroEnderecoPessoa, bairroPessoa, telefonePessoa, celularPessoa, emailPessoa);
        this.idUsuario = idUsuario;
        this.observacaoUsuario = observacaoUsuario;
        this.situacaoUsuario = situacaoUsuario;
        this.loginUsuario = loginUsuario;
        this.senhaUsuario = senhaUsuario;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }                
    
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getObservacaoUsuario() {
        return observacaoUsuario;
    }

    public void setObservacaoUsuario(String observacaoUsuario) {
        this.observacaoUsuario = observacaoUsuario;
    }

    public String getSituacaoUsuario() {
        return situacaoUsuario;
    }

    public void setSituacaoUsuario(String situacaoUsuario) {
        this.situacaoUsuario = situacaoUsuario;
    }

}
