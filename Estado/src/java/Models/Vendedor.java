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
public class Vendedor extends Pessoa {

    private Integer idVendedor;
    private String observacaoVendedor;
    private String situacaoVendedor;

    public Vendedor() {
    }

    public Vendedor(Integer idVendedor, String observacaoVendedor, String situacaoVendedor, Cidade modelCidade, Integer idPessoa, double cpfCnpjPessoa, String nomePessoa, String enderecoPessoa, double nroEnderecoPessoa, String bairroPessoa, double telefonePessoa, double celularPessoa, String emailPessoa) {
        super(modelCidade, idPessoa, cpfCnpjPessoa, nomePessoa, enderecoPessoa, nroEnderecoPessoa, bairroPessoa, telefonePessoa, celularPessoa, emailPessoa);
        this.idVendedor = idVendedor;
        this.observacaoVendedor = observacaoVendedor;
        this.situacaoVendedor = situacaoVendedor;
    }

    public static Vendedor VendedorVazio() throws ParseException{
        Cidade cCidade = new Cidade();
        Vendedor cVendedor = new Vendedor(0, "", "A", cCidade, 0, 0, "", "", 0, "", 0 , 0, "");
        return cVendedor;
    }
            
    
    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getObservacaoVendedor() {
        return observacaoVendedor;
    }

    public void setObservacaoVendedor(String observacaoVendedor) {
        this.observacaoVendedor = observacaoVendedor;
    }

    public String getSituacaoVendedor() {
        return situacaoVendedor;
    }

    public void setSituacaoVendedor(String situacaoVendedor) {
        this.situacaoVendedor = situacaoVendedor;
    }

}
