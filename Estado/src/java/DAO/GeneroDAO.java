/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.Genero;
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
public class GeneroDAO implements GenericDAO {

    private Connection Connection;

    public GeneroDAO() throws Exception {
        try {
            this.Connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Boolean Cadastrar(Object objeto) {
        
        Genero cGenero = (Genero) objeto;
        Boolean bRetorno = false;

        if (cGenero.getIdGenero() == 0 || cGenero.getIdGenero() == null) {
            bRetorno = this.Inserir(cGenero);
        } else {
            bRetorno = this.Alterar(cGenero);
        }
        return bRetorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {
        
        Genero cGenero = (Genero) objeto;
        PreparedStatement Stmt = null;

        //Prepara comando SQL
        String strSQL = "Insert Into Genero (descricaoGenero, situacaoGenero) Values (?, ?);";

        try {
            Stmt = Connection.prepareStatement(strSQL);

            try {
                Stmt.setString(1, cGenero.getDescricaoGenero());
                Stmt.setString(2, cGenero.getSituacaoGenero());

            } catch (Exception ex) {
                System.out.println("Problemas ao cadastrar Genero! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
            Stmt.execute();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao cadastrar Genero! Erro:" + ex.getMessage());
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
        
        Genero cGenero = (Genero) objeto;
        PreparedStatement Stmt = null;

        /*PreparedStatement = prepara a intrução SQL*/
        String strSQL = " Update Genero set descricaoGenero = ?, situacaoGenero = ? Where IdGenero = ?;";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cGenero.getDescricaoGenero());
            Stmt.setString(2, cGenero.getSituacaoGenero());
            Stmt.setInt(3, cGenero.getIdGenero());

            Stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao alterar Genero! Erro:" + ex.getMessage());
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
        
        Genero cGenero = (Genero) objeto;
        int idGenero = cGenero.getIdGenero();
        String strSQL = "";
        String Situacao = cGenero.getSituacaoGenero();
        PreparedStatement stmt = null;

        //String strSQL = "Delete From Genero Where idGenero = ?";
        
        if (Situacao.equals("A")) {
            strSQL = "Update Genero Set SituacaoGenero = 'I' Where idGenero = ?";
        }else{
            strSQL = "Update Genero Set SituacaoGenero = 'A' Where idGenero = ?";
        };
        
        try {
            stmt = Connection.prepareStatement(strSQL);
            stmt.setInt(1, idGenero);
            stmt.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Problemas ao excluir Genero! Erro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object Carregar(int Numero) {
        
        PreparedStatement Stmt = null;
        ResultSet rs = null;
        Genero cGenero = null;

        String strSQL = "Select E.* From Genero E Where E.IdGenero = ?;";
        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, Numero);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                cGenero = new Genero();
                cGenero.setIdGenero(rs.getInt("IdGenero"));
                cGenero.setDescricaoGenero(rs.getString("descricaoGenero"));
                cGenero.setSituacaoGenero(rs.getString("situacaoGenero"));
            }
            return cGenero;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Genero! Erro: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return cGenero;
    }

    @Override
    public List<Object> Listar() {

        List<Object> listaGenero = new ArrayList<>();
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        String strSQL = "Select E.* From Genero E Order By E.descricaoGenero";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                Genero cGenero = new Genero();
                cGenero.setIdGenero(rs.getInt("IdGenero"));
                cGenero.setDescricaoGenero(rs.getString("DescricaoGenero"));
                cGenero.setSituacaoGenero(rs.getString("situacaoGenero"));
                listaGenero.add(cGenero);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar Genero! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);

            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return listaGenero;
    }

}
