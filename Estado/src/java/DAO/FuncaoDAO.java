/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.Funcao;
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
public class FuncaoDAO implements GenericDAO {

    private Connection Connection;

    public FuncaoDAO() throws Exception {
        try {
            this.Connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Boolean Cadastrar(Object objeto) {
        
        Funcao cFuncao = (Funcao) objeto;
        Boolean bRetorno = false;

        if (cFuncao.getIdFuncao() == 0 || cFuncao.getIdFuncao() == null) {
            bRetorno = this.Inserir(cFuncao);
        } else {
            bRetorno = this.Alterar(cFuncao);
        }
        return bRetorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {
        
        Funcao cFuncao = (Funcao) objeto;
        PreparedStatement Stmt = null;

        //Prepara comando SQL
        String strSQL = "Insert Into Funcao (descricaoFuncao) Values (?);";

        try {
            Stmt = Connection.prepareStatement(strSQL);

            try {
                Stmt.setString(1, cFuncao.getDescricaoFuncao());

            } catch (Exception ex) {
                System.out.println("Problemas ao cadastrar Funcao! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
            Stmt.execute();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao cadastrar Funcao! Erro:" + ex.getMessage());
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
        
        Funcao cFuncao = (Funcao) objeto;
        PreparedStatement Stmt = null;

        /*PreparedStatement = prepara a intrução SQL*/
        String strSQL = " Update Funcao set descricaoFuncao = ? Where IdFuncao = ?;";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cFuncao.getDescricaoFuncao());
            Stmt.setInt(2, cFuncao.getIdFuncao());

            Stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao alterar Funcao! Erro:" + ex.getMessage());
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
        
        Funcao cFuncao = (Funcao) objeto;
        int idFuncao = cFuncao.getIdFuncao();
        PreparedStatement stmt = null;

        String strSQL = "Delete From Funcao Where idFuncao = ?";       
        
        try {
            stmt = Connection.prepareStatement(strSQL);
            stmt.setInt(1, idFuncao);
            stmt.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Problemas ao excluir Funcao! Erro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object Carregar(int Numero) {
        
        PreparedStatement Stmt = null;
        ResultSet rs = null;
        Funcao cFuncao = null;

        String strSQL = "Select E.* From Funcao E Where E.IdFuncao = ?;";
        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, Numero);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                cFuncao = new Funcao();
                cFuncao.setIdFuncao(rs.getInt("IdFuncao"));
                cFuncao.setDescricaoFuncao(rs.getString("descricaoFuncao"));
            }
            return cFuncao;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Funcao! Erro: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return cFuncao;
    }

    @Override
    public List<Object> Listar() {

        List<Object> listaFuncao = new ArrayList<>();
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        String strSQL = "Select E.* From Funcao E Order By E.descricaoFuncao";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                Funcao cFuncao = new Funcao();
                cFuncao.setIdFuncao(rs.getInt("IdFuncao"));
                cFuncao.setDescricaoFuncao(rs.getString("DescricaoFuncao"));
                listaFuncao.add(cFuncao);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar Funcao! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);

            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return listaFuncao;
    }

}
