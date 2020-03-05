/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.TipoPapel;
import Models.TipoPapel;
import Models.TipoPapel;
import Models.TipoPapel;
import Models.TipoPapel;
import Models.TipoPapel;
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
public class TipoPapelDAO implements GenericDAO {

    private Connection Connection;

    public TipoPapelDAO() throws Exception {
        try {
            this.Connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Boolean Cadastrar(Object objeto) {
        
        TipoPapel cTipoPapel = (TipoPapel) objeto;
        Boolean bRetorno = false;

        if (cTipoPapel.getIdTipoPapel() == 0 || cTipoPapel.getIdTipoPapel() == null) {
            bRetorno = this.Inserir(cTipoPapel);
        } else {
            bRetorno = this.Alterar(cTipoPapel);
        }
        return bRetorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {
        
        TipoPapel cTipoPapel = (TipoPapel) objeto;
        PreparedStatement Stmt = null;

        //Prepara comando SQL
        String strSQL = "Insert Into TipoPapel (descricaoTipoPapel, situacaoTipoPapel) Values (?, ?);";

        try {
            Stmt = Connection.prepareStatement(strSQL);

            try {
                Stmt.setString(1, cTipoPapel.getDescricaoTipoPapel());
                Stmt.setString(2, cTipoPapel.getSituacaoTipoPapel());

            } catch (Exception ex) {
                System.out.println("Problemas ao cadastrar TipoPapel! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
            Stmt.execute();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao cadastrar TipoPapel! Erro:" + ex.getMessage());
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
        
        TipoPapel cTipoPapel = (TipoPapel) objeto;
        PreparedStatement Stmt = null;

        /*PreparedStatement = prepara a intrução SQL*/
        String strSQL = " Update TipoPapel set descricaoTipoPapel = ?, situacaoTipoPapel = ? Where IdTipoPapel = ?;";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cTipoPapel.getDescricaoTipoPapel());
            Stmt.setString(2, cTipoPapel.getSituacaoTipoPapel());
            Stmt.setInt(3, cTipoPapel.getIdTipoPapel());

            Stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao alterar TipoPapel! Erro:" + ex.getMessage());
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
        
        TipoPapel cTipoPapel = (TipoPapel) objeto;
        int idTipoPapel = cTipoPapel.getIdTipoPapel();
        String strSQL = "";
        String Situacao = cTipoPapel.getSituacaoTipoPapel();
        PreparedStatement stmt = null;

        //String strSQL = "Delete From TipoPapel Where idTipoPapel = ?";
        
        if (Situacao.equals("A")) {
            strSQL = "Update TipoPapel Set SituacaoTipoPapel = 'I' Where idTipoPapel = ?";
        }else{
            strSQL = "Update TipoPapel Set SituacaoTipoPapel = 'A' Where idTipoPapel = ?";
        };
        
        try {
            stmt = Connection.prepareStatement(strSQL);
            stmt.setInt(1, idTipoPapel);
            stmt.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Problemas ao excluir TipoPapel! Erro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object Carregar(int Numero) {
        
        PreparedStatement Stmt = null;
        ResultSet rs = null;
        TipoPapel cTipoPapel = null;

        String strSQL = "Select E.* From TipoPapel E Where E.IdTipoPapel = ?;";
        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, Numero);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                cTipoPapel = new TipoPapel();
                cTipoPapel.setIdTipoPapel(rs.getInt("IdTipoPapel"));
                cTipoPapel.setDescricaoTipoPapel(rs.getString("descricaoTipoPapel"));
                cTipoPapel.setSituacaoTipoPapel(rs.getString("situacaoTipoPapel"));
            }
            return cTipoPapel;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar TipoPapel! Erro: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return cTipoPapel;
    }

    @Override
    public List<Object> Listar() {

        List<Object> listaTipoPapel = new ArrayList<>();
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        String strSQL = "Select E.* From TipoPapel E Order By E.descricaoTipoPapel";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                TipoPapel cTipoPapel = new TipoPapel();
                cTipoPapel.setIdTipoPapel(rs.getInt("IdTipoPapel"));
                cTipoPapel.setDescricaoTipoPapel(rs.getString("DescricaoTipoPapel"));
                cTipoPapel.setSituacaoTipoPapel(rs.getString("situacaoTipoPapel"));
                listaTipoPapel.add(cTipoPapel);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar TipoPapel! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);

            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return listaTipoPapel;
    }

}
