/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Flavio Prado
 */
public class ArcoTeste {
    
    private Integer idArco;
    private String descricaoArco;
    
    StringBuilder strSQL = new StringBuilder();
    
    public ArcoTeste() {
    }

    public ArcoTeste(Integer idArco, String descricaoArco) {
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
    
    private String SQLBaseArcoTeste(){
        return SQLBaseArcoTeste(0);
    }
    private String SQLBaseArcoTeste(double pnIdArcoTeste){
        strSQL.delete(0, strSQL.length());
        strSQL.append("Select IdArco, DescricaoArco");
        strSQL.append(" From Arco");
        
        if (pnIdArcoTeste > 0){strSQL.append(" Where IdArco = " + pnIdArcoTeste);} 
        
        return strSQL.toString();
    }
    
    public List<Object> Listar() throws Exception {

        ConnectionFactory Conn = new ConnectionFactory();
        Connection Connection = Conn.getConnection();
        
        List<Object> listaArco = new ArrayList<>();
        PreparedStatement Stmt = null;
        ResultSet rs = null;        

        try {
            Stmt = Connection.prepareStatement(SQLBaseArcoTeste());
            rs = Stmt.executeQuery();

            while (rs.next()) {
                Arco cArco = new Arco();
                cArco.setIdArco(rs.getInt("IdArco"));
                cArco.setDescricaoArco(rs.getString("DescricaoArco"));
                listaArco.add(cArco);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar Arco! Erro: " + ex.getMessage());
            ex.printStackTrace();
            
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);

            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return listaArco;
    }
    
    public Object Carregar(Integer pnIdArcoTeste) throws Exception {
        
        PreparedStatement Stmt = null;
        ResultSet rs = null;
        Arco cArco = null;

        ConnectionFactory Conn = new ConnectionFactory();
        Connection Connection = Conn.getConnection();
        
        try {
            Stmt = Connection.prepareStatement(SQLBaseArcoTeste(pnIdArcoTeste));
            Stmt.setInt(1, pnIdArcoTeste);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                cArco = new Arco();
                cArco.setIdArco(rs.getInt("IdArco"));
                cArco.setDescricaoArco(rs.getString("DescricaoArco"));
            }
            return cArco;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Arco! Erro: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return cArco;
    }
    
}
