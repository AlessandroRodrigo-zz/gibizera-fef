/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.TipoLombada;
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
public class TipoLombadaDAO implements GenericDAO {

    private Connection Connection;

    public TipoLombadaDAO() throws Exception {
        try {
            this.Connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Boolean Cadastrar(Object objeto) {
        TipoLombada cTipoLombada = (TipoLombada) objeto;
        Boolean bRetorno = false;

        if (cTipoLombada.getIdTipoLombada() == 0 || cTipoLombada.getIdTipoLombada() == null) {
            bRetorno = this.Inserir(cTipoLombada);
        } else {
            bRetorno = this.Alterar(cTipoLombada);
        }
        return bRetorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {
        
        TipoLombada cTipoLombada = (TipoLombada) objeto;
        PreparedStatement Stmt = null;

        //Prepara comando SQL
        String strSQL = "Insert Into TipoLombada (descricaoTipoLombada, situacaoTipoLombada) Values (?, ?);";

        try {
            Stmt = Connection.prepareStatement(strSQL);

            try {
                Stmt.setString(1, cTipoLombada.getDescricaoTipoLombada());
                Stmt.setString(2, cTipoLombada.getSituacaoTipoLombada());

            } catch (Exception ex) {
                System.out.println("Problemas ao cadastrar TipoLombada! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
            Stmt.execute();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao cadastrar TipoLombada! Erro:" + ex.getMessage());
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
        
        TipoLombada cTipoLombada = (TipoLombada) objeto;
        PreparedStatement Stmt = null;

        /*PreparedStatement = prepara a intrução SQL*/
        String strSQL = " Update TipoLombada set descricaoTipoLombada = ?, situacaoTipoLombada = ? Where IdTipoLombada = ?;";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cTipoLombada.getDescricaoTipoLombada());
            Stmt.setString(2, cTipoLombada.getSituacaoTipoLombada());
            Stmt.setInt(3, cTipoLombada.getIdTipoLombada());

            Stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao alterar TipoLombada! Erro:" + ex.getMessage());
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
        
        TipoLombada cTipoLombada = (TipoLombada) objeto;
        int idTipoLombada = cTipoLombada.getIdTipoLombada();
        PreparedStatement stmt = null;

        String strSQL = "Delete From TipoLombada Where idTipoLombada = ?";

        try {
            stmt = Connection.prepareStatement(strSQL);
            stmt.setInt(1, idTipoLombada);
            stmt.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Problemas ao excluir TipoLombada! Erro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object Carregar(int Numero) {
        
        PreparedStatement Stmt = null;
        ResultSet rs = null;
        TipoLombada cTipoLombada = null;

        String strSQL = "Select E.* From TipoLombada E Where E.IdTipoLombada = ?;";
        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, Numero);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                cTipoLombada = new TipoLombada();
                cTipoLombada.setIdTipoLombada(rs.getInt("IdTipoLombada"));
                cTipoLombada.setDescricaoTipoLombada(rs.getString("descricaoTipoLombada"));
                cTipoLombada.setSituacaoTipoLombada(rs.getString("situacaoTipoLombada"));
            }
            return cTipoLombada;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar TipoLombada! Erro: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return cTipoLombada;
    }

    @Override
    public List<Object> Listar() {
        
        List<Object> listaTipoLombada = new ArrayList<>();
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        String strSQL = "Select E.* From TipoLombada E Order By E.DescricaoTipoLombada";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                TipoLombada cTipoLombada = new TipoLombada();
                cTipoLombada.setIdTipoLombada(rs.getInt("IdTipoLombada"));
                cTipoLombada.setDescricaoTipoLombada(rs.getString("DescricaoTipoLombada"));
                cTipoLombada.setSituacaoTipoLombada(rs.getString("SituacaoTipoLombada"));
                listaTipoLombada.add(cTipoLombada);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar TipoLombada! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);

            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return listaTipoLombada;
    }

}
