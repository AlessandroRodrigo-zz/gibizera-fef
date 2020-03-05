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
public class Licenciadora {
    
    private Integer idLicenciadora;
    private String descricaoLicenciadora;
    private String situacaoLicenciadora;

    public Licenciadora() {
    }

    public Licenciadora(Integer idLicenciadora, String descricaoLicenciadora, String situacaoLicenciadora) {
        this.idLicenciadora = idLicenciadora;
        this.descricaoLicenciadora = descricaoLicenciadora;
        this.situacaoLicenciadora = situacaoLicenciadora;
    }

    public Integer getIdLicenciadora() {
        return idLicenciadora;
    }

    public void setIdLicenciadora(Integer idLicenciadora) {
        this.idLicenciadora = idLicenciadora;
    }

    public String getDescricaoLicenciadora() {
        return descricaoLicenciadora;
    }

    public void setDescricaoLicenciadora(String descricaoLicenciadora) {
        this.descricaoLicenciadora = descricaoLicenciadora;
    }

    public String getSituacaoLicenciadora() {
        return situacaoLicenciadora;
    }

    public void setSituacaoLicenciadora(String situacaoLicenciadora) {
        this.situacaoLicenciadora = situacaoLicenciadora;
    }
    
    
    
}
