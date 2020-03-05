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
public class Pessoa {
    
    private Cidade modelCidade;
    
    private Integer idPessoa;
    private double cpfCnpjPessoa;
    private String nomePessoa;
    private String enderecoPessoa;
    private double nroEnderecoPessoa;
    private String bairroPessoa;
    private double telefonePessoa;
    private double celularPessoa;
    private String emailPessoa;

    public Pessoa() {
    }

    public Pessoa(Cidade modelCidade, Integer idPessoa, double cpfCnpjPessoa, String nomePessoa, String enderecoPessoa, double nroEnderecoPessoa, String bairroPessoa, double telefonePessoa, double celularPessoa, String emailPessoa) {
        this.modelCidade = modelCidade;
        this.idPessoa = idPessoa;
        this.cpfCnpjPessoa = cpfCnpjPessoa;
        this.nomePessoa = nomePessoa;
        this.enderecoPessoa = enderecoPessoa;
        this.nroEnderecoPessoa = nroEnderecoPessoa;
        this.bairroPessoa = bairroPessoa;
        this.telefonePessoa = telefonePessoa;
        this.celularPessoa = celularPessoa;
        this.emailPessoa = emailPessoa;
    }

    public Cidade getModelCidade() {
        return modelCidade;
    }

    public void setModelCidade(Cidade modelCidade) {
        this.modelCidade = modelCidade;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }

    public double getCpfCnpjPessoa() {
        return cpfCnpjPessoa;
    }

    public void setCpfCnpjPessoa(double cpfCnpjPessoa) {
        this.cpfCnpjPessoa = cpfCnpjPessoa;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getEnderecoPessoa() {
        return enderecoPessoa;
    }

    public void setEnderecoPessoa(String enderecoPessoa) {
        this.enderecoPessoa = enderecoPessoa;
    }

    public double getNroEnderecoPessoa() {
        return nroEnderecoPessoa;
    }

    public void setNroEnderecoPessoa(double nroEnderecoPessoa) {
        this.nroEnderecoPessoa = nroEnderecoPessoa;
    }

    public String getBairroPessoa() {
        return bairroPessoa;
    }

    public void setBairroPessoa(String bairroPessoa) {
        this.bairroPessoa = bairroPessoa;
    }

    public double getTelefonePessoa() {
        return telefonePessoa;
    }

    public void setTelefonePessoa(double telefonePessoa) {
        this.telefonePessoa = telefonePessoa;
    }

    public double getCelularPessoa() {
        return celularPessoa;
    }

    public void setCelularPessoa(double celularPessoa) {
        this.celularPessoa = celularPessoa;
    }

    public String getEmailPessoa() {
        return emailPessoa;
    }

    public void setEmailPessoa(String emailPessoa) {
        this.emailPessoa = emailPessoa;
    }
    
    
    
}
