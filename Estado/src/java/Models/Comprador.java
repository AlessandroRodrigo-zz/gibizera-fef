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
public class Comprador extends Pessoa {
    
    private Integer idComprador;
    private String observacaoComprador;
    private String situacaoComprador;

    public Comprador() {
    }

    public Comprador(Integer idComprador, String observacaoComprador, String situacaoComprador, Cidade modelCidade, Integer idPessoa, double cpfCnpjPessoa, String nomePessoa, String enderecoPessoa, double nroEnderecoPessoa, String bairroPessoa, double telefonePessoa, double celularPessoa, String emailPessoa) {
        super(modelCidade, idPessoa, cpfCnpjPessoa, nomePessoa, enderecoPessoa, nroEnderecoPessoa, bairroPessoa, telefonePessoa, celularPessoa, emailPessoa);
        this.idComprador = idComprador;
        this.observacaoComprador = observacaoComprador;
        this.situacaoComprador = situacaoComprador;
    }
    
    public static Comprador CompradorVazio() throws ParseException{
        Cidade cCidade = new Cidade();
        Comprador cComprador = new Comprador(0, "", "A", cCidade, 0, 0, "", "", 0, "", 0 , 0, "");
        return cComprador;
    }

    public Integer getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(Integer idComprador) {
        this.idComprador = idComprador;
    }

    public String getObservacaoComprador() {
        return observacaoComprador;
    }

    public void setObservacaoComprador(String observacaoComprador) {
        this.observacaoComprador = observacaoComprador;
    }

    public String getSituacaoComprador() {
        return situacaoComprador;
    }

    public void setSituacaoComprador(String situacaoComprador) {
        this.situacaoComprador = situacaoComprador;
    }
    
}
