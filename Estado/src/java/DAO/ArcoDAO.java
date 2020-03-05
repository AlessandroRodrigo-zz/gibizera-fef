/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.Arco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aluno
 */
public class ArcoDAO implements GenericDAO {

    private Connection Connection;

    public ArcoDAO() throws Exception {
        try {
            this.Connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Boolean Cadastrar(Object objeto) {
        
        Arco cArco = (Arco) objeto;
        Boolean bRetorno = false;

        if (cArco.getIdArco() == 0 || cArco.getIdArco() == null) {
            bRetorno = this.Inserir(cArco);
        } else {
            bRetorno = this.Alterar(cArco);
        }
        return bRetorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {
        
        Arco cArco = (Arco) objeto;
        PreparedStatement Stmt = null;

        //Prepara comando SQL
        String strSQL = "Insert Into Arco (descricaoArco) Values (?);";

        try {
            Stmt = Connection.prepareStatement(strSQL);

            try {
                Stmt.setString(1, cArco.getDescricaoArco());

            } catch (Exception ex) {
                System.out.println("Problemas ao cadastrar Arco! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
            Stmt.execute();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao cadastrar Arco! Erro:" + ex.getMessage());
            ex.printStackTrace();
            return false;

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }

        }
    }

    @Override
    public Boolean Alterar(Object objeto) {
        
        Arco cArco = (Arco) objeto;
        PreparedStatement Stmt = null;

        /*PreparedStatement = prepara a intrução SQL*/
        String strSQL = " Update Arco set descricaoArco = ? Where IdArco = ?;";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cArco.getDescricaoArco());
            Stmt.setInt(2, cArco.getIdArco());

            Stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao alterar Arco! Erro:" + ex.getMessage());
            ex.printStackTrace();
            return false;

        } finally {

            try {
                ConnectionFactory.CloseConnection(Connection, Stmt);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    @Override
    public Boolean Excluir(Object objeto) {
        
        Arco cArco = (Arco) objeto;
        int idArco = cArco.getIdArco();
        PreparedStatement stmt = null;

        String strSQL = "Delete From Arco Where idArco = ?";       
        
        try {
            stmt = Connection.prepareStatement(strSQL);
            stmt.setInt(1, idArco);
            stmt.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Problemas ao excluir Arco! Erro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object Carregar(int Numero) {
        
        PreparedStatement Stmt = null;
        ResultSet rs = null;
        Arco cArco = null;

        String strSQL = "Select E.* From Arco E Where E.IdArco = ?;";
        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, Numero);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                cArco = new Arco();
                cArco.setIdArco(rs.getInt("IdArco"));
                cArco.setDescricaoArco(rs.getString("descricaoArco"));
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

    @Override
    public List<Object> Listar() {

        List<Object> listaArco = new ArrayList<>();
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        String strSQL = "Select E.* From Arco E Order By E.descricaoArco";

        try {
            Stmt = Connection.prepareStatement(strSQL);
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

}
