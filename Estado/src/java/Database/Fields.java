/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Database.Database.TypeFields;

/**
 *
 * @author Flavio Prado
 */
public class Fields {
    
    public String Nome;    
    public String Valor;
    public TypeFields Tipo;

    public Fields() {
    }

    public Fields(String Nome, String Valor, TypeFields Tipo) {
        this.Nome = Nome;
        this.Valor = Valor;
        this.Tipo = Tipo;
    }       
}
