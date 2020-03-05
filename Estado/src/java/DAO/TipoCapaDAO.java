/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.TipoCapa;
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
public class TipoCapaDAO implements GenericDAO {

    private Connection Connection;

    public TipoCapaDAO() throws Exception {
        try {
            this.Connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Boolean Cadastrar(Object objeto) {
        
        TipoCapa cTipoCapa = (TipoCapa) objeto;
        Boolean bRetorno = false;

        if (cTipoCapa.getIdTipoCapa() == 0 || cTipoCapa.getIdTipoCapa() == null) {
            bRetorno = this.Inserir(cTipoCapa);
        } else {
            bRetorno = this.Alterar(cTipoCapa);
        }
        return bRetorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {
        
        TipoCapa cTipoCapa = (TipoCapa) objeto;
        PreparedStatement Stmt = null;

        //Prepara comando SQL
        String strSQL = "Insert Into TipoCapa (descricaoTipoCapa, situacaoTipoCapa) Values (?, ?);";

        try {
            Stmt = Connection.prepareStatement(strSQL);

            try {
                Stmt.setString(1, cTipoCapa.getDescricaoTipoCapa());
                Stmt.setString(2, cTipoCapa.getSituacaoTipoCapa());

            } catch (Exception ex) {
                System.out.println("Problemas ao cadastrar TipoCapa! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
            Stmt.execute();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao cadastrar TipoCapa! Erro:" + ex.getMessage());
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
        
        TipoCapa cTipoCapa = (TipoCapa) objeto;
        PreparedStatement Stmt = null;

        /*PreparedStatement = prepara a intrução SQL*/
        String strSQL = " Update TipoCapa set descricaoTipoCapa = ?, situacaoTipoCapa = ? Where IdTipoCapa = ?;";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cTipoCapa.getDescricaoTipoCapa());
            Stmt.setString(2, cTipoCapa.getSituacaoTipoCapa());
            Stmt.setInt(3, cTipoCapa.getIdTipoCapa());

            Stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao alterar TipoCapa! Erro:" + ex.getMessage());
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
        
        TipoCapa cTipoCapa = (TipoCapa) objeto;
        int idTipoCapa = cTipoCapa.getIdTipoCapa();
        String strSQL = "";
        String Situacao = cTipoCapa.getSituacaoTipoCapa();
        PreparedStatement stmt = null;

        //String strSQL = "Delete From TipoCapa Where idTipoCapa = ?";
        
        if (Situacao.equals("A")) {
            strSQL = "Update TipoCapa Set SituacaoTipoCapa = 'I' Where idTipoCapa = ?";
        }else{
            strSQL = "Update TipoCapa Set SituacaoTipoCapa = 'A' Where idTipoCapa = ?";
        };
        
        try {
            stmt = Connection.prepareStatement(strSQL);
            stmt.setInt(1, idTipoCapa);
            stmt.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Problemas ao excluir TipoCapa! Erro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object Carregar(int Numero) {
        
        PreparedStatement Stmt = null;
        ResultSet rs = null;
        TipoCapa cTipoCapa = null;

        String strSQL = "Select E.* From TipoCapa E Where E.IdTipoCapa = ?;";
        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, Numero);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                cTipoCapa = new TipoCapa();
                cTipoCapa.setIdTipoCapa(rs.getInt("IdTipoCapa"));
                cTipoCapa.setDescricaoTipoCapa(rs.getString("descricaoTipoCapa"));
                cTipoCapa.setSituacaoTipoCapa(rs.getString("situacaoTipoCapa"));
            }
            return cTipoCapa;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar TipoCapa! Erro: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return cTipoCapa;
    }

    @Override
    public List<Object> Listar() {

        List<Object> listaTipoCapa = new ArrayList<>();
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        String strSQL = "Select E.* From TipoCapa E Order By E.descricaoTipoCapa";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                TipoCapa cTipoCapa = new TipoCapa();
                cTipoCapa.setIdTipoCapa(rs.getInt("IdTipoCapa"));
                cTipoCapa.setDescricaoTipoCapa(rs.getString("DescricaoTipoCapa"));
                cTipoCapa.setSituacaoTipoCapa(rs.getString("situacaoTipoCapa"));
                listaTipoCapa.add(cTipoCapa);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar TipoCapa! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);

            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return listaTipoCapa;
    }

}
