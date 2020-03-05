/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.TipoPublicacao;
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
public class TipoPublicacaoDAO implements GenericDAO {

    private Connection Connection;

    public TipoPublicacaoDAO() throws Exception {
        try {
            this.Connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Boolean Cadastrar(Object objeto) {
        
        TipoPublicacao cTipoPublicacao = (TipoPublicacao) objeto;
        Boolean bRetorno = false;

        if (cTipoPublicacao.getIdTipoPublicacao() == 0 || cTipoPublicacao.getIdTipoPublicacao() == null) {
            bRetorno = this.Inserir(cTipoPublicacao);
        } else {
            bRetorno = this.Alterar(cTipoPublicacao);
        }
        return bRetorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {
        
        TipoPublicacao cTipoPublicacao = (TipoPublicacao) objeto;
        PreparedStatement Stmt = null;

        //Prepara comando SQL
        String strSQL = "Insert Into TipoPublicacao (descricaoTipoPublicacao, situacaoTipoPublicacao) Values (?, ?);";

        try {
            Stmt = Connection.prepareStatement(strSQL);

            try {
                Stmt.setString(1, cTipoPublicacao.getDescricaoTipoPublicacao());
                Stmt.setString(2, cTipoPublicacao.getSituacaoTipoPublicacao());

            } catch (Exception ex) {
                System.out.println("Problemas ao cadastrar TipoPublicacao! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
            Stmt.execute();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao cadastrar TipoPublicacao! Erro:" + ex.getMessage());
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
        
        TipoPublicacao cTipoPublicacao = (TipoPublicacao) objeto;
        PreparedStatement Stmt = null;

        /*PreparedStatement = prepara a intrução SQL*/
        String strSQL = " Update TipoPublicacao set descricaoTipoPublicacao = ?, situacaoTipoPublicacao = ? Where IdTipoPublicacao = ?;";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cTipoPublicacao.getDescricaoTipoPublicacao());
            Stmt.setString(2, cTipoPublicacao.getSituacaoTipoPublicacao());
            Stmt.setInt(3, cTipoPublicacao.getIdTipoPublicacao());

            Stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao alterar TipoPublicacao! Erro:" + ex.getMessage());
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
        
        TipoPublicacao cTipoPublicacao = (TipoPublicacao) objeto;
        int idTipoPublicacao = cTipoPublicacao.getIdTipoPublicacao();
        String strSQL = "";
        String Situacao = cTipoPublicacao.getSituacaoTipoPublicacao();
        PreparedStatement stmt = null;

        //String strSQL = "Delete From TipoPublicacao Where idTipoPublicacao = ?";
        
        if (Situacao.equals("A")) {
            strSQL = "Update TipoPublicacao Set SituacaoTipoPublicacao = 'I' Where idTipoPublicacao = ?";
        }else{
            strSQL = "Update TipoPublicacao Set SituacaoTipoPublicacao = 'A' Where idTipoPublicacao = ?";
        };
        
        try {
            stmt = Connection.prepareStatement(strSQL);
            stmt.setInt(1, idTipoPublicacao);
            stmt.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Problemas ao excluir TipoPublicacao! Erro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object Carregar(int Numero) {
        
        PreparedStatement Stmt = null;
        ResultSet rs = null;
        TipoPublicacao cTipoPublicacao = null;

        String strSQL = "Select E.* From TipoPublicacao E Where E.IdTipoPublicacao = ?;";
        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, Numero);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                cTipoPublicacao = new TipoPublicacao();
                cTipoPublicacao.setIdTipoPublicacao(rs.getInt("IdTipoPublicacao"));
                cTipoPublicacao.setDescricaoTipoPublicacao(rs.getString("descricaoTipoPublicacao"));
                cTipoPublicacao.setSituacaoTipoPublicacao(rs.getString("situacaoTipoPublicacao"));
            }
            return cTipoPublicacao;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar TipoPublicacao! Erro: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return cTipoPublicacao;
    }

    @Override
    public List<Object> Listar() {

        List<Object> listaTipoPublicacao = new ArrayList<>();
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        String strSQL = "Select E.* From TipoPublicacao E Order By E.descricaoTipoPublicacao";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                TipoPublicacao cTipoPublicacao = new TipoPublicacao();
                cTipoPublicacao.setIdTipoPublicacao(rs.getInt("IdTipoPublicacao"));
                cTipoPublicacao.setDescricaoTipoPublicacao(rs.getString("DescricaoTipoPublicacao"));
                cTipoPublicacao.setSituacaoTipoPublicacao(rs.getString("situacaoTipoPublicacao"));
                listaTipoPublicacao.add(cTipoPublicacao);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar TipoPublicacao! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);

            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return listaTipoPublicacao;
    }

}
