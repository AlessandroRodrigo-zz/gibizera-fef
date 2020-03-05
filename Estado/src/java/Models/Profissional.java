/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.text.ParseException;

/**
 *
 * @author Flavio Prado
 */
public class Profissional extends Pessoa{
    
    private Integer idProfissional;
    private String observacaoProfissional;
    private String situacaoProfissional;

    public Profissional() {
    }

    public Profissional(Integer idProfissional, String observacaoProfissional, String situacaoProfissional, Cidade modelCidade, Integer idPessoa, double cpfCnpjPessoa, String nomePessoa, String enderecoPessoa, double nroEnderecoPessoa, String bairroPessoa, double telefonePessoa, double celularPessoa, String emailPessoa) {
        super(modelCidade, idPessoa, cpfCnpjPessoa, nomePessoa, enderecoPessoa, nroEnderecoPessoa, bairroPessoa, telefonePessoa, celularPessoa, emailPessoa);
        this.idProfissional = idProfissional;
        this.observacaoProfissional = observacaoProfissional;
        this.situacaoProfissional = situacaoProfissional;
    }
    
    public static Profissional ProfissionalVazio() throws ParseException{
        Cidade cCidade = new Cidade();
        Profissional cProfissional = new Profissional(0, "", "A", cCidade, 0, 0, "", "", 0, "", 0 , 0, "");
        return cProfissional;
    }

    public Integer getIdProfissional() {
        return idProfissional;
    }

    public void setIdProfissional(Integer idProfissional) {
        this.idProfissional = idProfissional;
    }

    public String getObservacaoProfissional() {
        return observacaoProfissional;
    }

    public void setObservacaoProfissional(String observacaoProfissional) {
        this.observacaoProfissional = observacaoProfissional;
    }

    public String getSituacaoProfissional() {
        return situacaoProfissional;
    }

    public void setSituacaoProfissional(String situacaoProfissional) {
        this.situacaoProfissional = situacaoProfissional;
    }
    
}
