/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.Formato;
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
public class FormatoDAO implements GenericDAO {

    private Connection Connection;

    public FormatoDAO() throws Exception {
        try {
            this.Connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Boolean Cadastrar(Object objeto) {
        
        Formato cFormato = (Formato) objeto;
        Boolean bRetorno = false;

        if (cFormato.getIdFormato() == 0 || cFormato.getIdFormato() == null) {
            bRetorno = this.Inserir(cFormato);
        } else {
            bRetorno = this.Alterar(cFormato);
        }
        return bRetorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {
        
        Formato cFormato = (Formato) objeto;
        PreparedStatement Stmt = null;

        //Prepara comando SQL
        String strSQL = "Insert Into Formato (descricaoFormato, situacaoFormato) Values (?, ?);";

        try {
            Stmt = Connection.prepareStatement(strSQL);

            try {
                Stmt.setString(1, cFormato.getDescricaoFormato());
                Stmt.setString(2, cFormato.getSituacaoFormato());

            } catch (Exception ex) {
                System.out.println("Problemas ao cadastrar Formato! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
            Stmt.execute();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao cadastrar Formato! Erro:" + ex.getMessage());
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
        
        Formato cFormato = (Formato) objeto;
        PreparedStatement Stmt = null;

        /*PreparedStatement = prepara a intrução SQL*/
        String strSQL = " Update Formato set descricaoFormato = ?, situacaoFormato = ? Where IdFormato = ?;";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cFormato.getDescricaoFormato());
            Stmt.setString(2, cFormato.getSituacaoFormato());
            Stmt.setInt(3, cFormato.getIdFormato());

            Stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao alterar Formato! Erro:" + ex.getMessage());
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
        
        Formato cFormato = (Formato) objeto;
        int idFormato = cFormato.getIdFormato();
        String strSQL = "";
        String Situacao = cFormato.getSituacaoFormato();
        PreparedStatement stmt = null;

        //String strSQL = "Delete From Formato Where idFormato = ?";
        
        if (Situacao.equals("A")) {
            strSQL = "Update Formato Set SituacaoFormato = 'I' Where idFormato = ?";
        }else{
            strSQL = "Update Formato Set SituacaoFormato = 'A' Where idFormato = ?";
        };
        
        try {
            stmt = Connection.prepareStatement(strSQL);
            stmt.setInt(1, idFormato);
            stmt.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Problemas ao excluir Formato! Erro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object Carregar(int Numero) {
        
        PreparedStatement Stmt = null;
        ResultSet rs = null;
        Formato cFormato = null;

        String strSQL = "Select E.* From Formato E Where E.IdFormato = ?;";
        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, Numero);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                cFormato = new Formato();
                cFormato.setIdFormato(rs.getInt("IdFormato"));
                cFormato.setDescricaoFormato(rs.getString("descricaoFormato"));
                cFormato.setSituacaoFormato(rs.getString("situacaoFormato"));
            }
            return cFormato;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Formato! Erro: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return cFormato;
    }

    @Override
    public List<Object> Listar() {

        List<Object> listaFormato = new ArrayList<>();
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        String strSQL = "Select E.* From Formato E Order By E.descricaoFormato";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                Formato cFormato = new Formato();
                cFormato.setIdFormato(rs.getInt("IdFormato"));
                cFormato.setDescricaoFormato(rs.getString("DescricaoFormato"));
                cFormato.setSituacaoFormato(rs.getString("situacaoFormato"));
                listaFormato.add(cFormato);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar Formato! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);

            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return listaFormato;
    }

}
