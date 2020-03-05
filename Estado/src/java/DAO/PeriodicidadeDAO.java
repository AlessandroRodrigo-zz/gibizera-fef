/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.Periodicidade;
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
public class PeriodicidadeDAO implements GenericDAO {

    private Connection Connection;

    public PeriodicidadeDAO() throws Exception {
        try {
            this.Connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Boolean Cadastrar(Object objeto) {
        
        Periodicidade cPeriodicidade = (Periodicidade) objeto;
        Boolean bRetorno = false;

        if (cPeriodicidade.getIdPeriodicidade() == 0 || cPeriodicidade.getIdPeriodicidade() == null) {
            bRetorno = this.Inserir(cPeriodicidade);
        } else {
            bRetorno = this.Alterar(cPeriodicidade);
        }
        return bRetorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {
        
        Periodicidade cPeriodicidade = (Periodicidade) objeto;
        PreparedStatement Stmt = null;

        //Prepara comando SQL
        String strSQL = "Insert Into Periodicidade (descricaoPeriodicidade) Values (?);";

        try {
            Stmt = Connection.prepareStatement(strSQL);

            try {
                Stmt.setString(1, cPeriodicidade.getDescricaoPeriodicidade());

            } catch (Exception ex) {
                System.out.println("Problemas ao cadastrar Periodicidade! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
            Stmt.execute();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao cadastrar Periodicidade! Erro:" + ex.getMessage());
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
        
        Periodicidade cPeriodicidade = (Periodicidade) objeto;
        PreparedStatement Stmt = null;

        /*PreparedStatement = prepara a intrução SQL*/
        String strSQL = " Update Periodicidade set descricaoPeriodicidade = ? Where IdPeriodicidade = ?;";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cPeriodicidade.getDescricaoPeriodicidade());
            Stmt.setInt(2, cPeriodicidade.getIdPeriodicidade());

            Stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao alterar Periodicidade! Erro:" + ex.getMessage());
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
        
        Periodicidade cPeriodicidade = (Periodicidade) objeto;
        int idPeriodicidade = cPeriodicidade.getIdPeriodicidade();
        PreparedStatement stmt = null;

        String strSQL = "Delete From Periodicidade Where idPeriodicidade = ?";       
        
        try {
            stmt = Connection.prepareStatement(strSQL);
            stmt.setInt(1, idPeriodicidade);
            stmt.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Problemas ao excluir Periodicidade! Erro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object Carregar(int Numero) {
        
        PreparedStatement Stmt = null;
        ResultSet rs = null;
        Periodicidade cPeriodicidade = null;

        String strSQL = "Select E.* From Periodicidade E Where E.IdPeriodicidade = ?;";
        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, Numero);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                cPeriodicidade = new Periodicidade();
                cPeriodicidade.setIdPeriodicidade(rs.getInt("IdPeriodicidade"));
                cPeriodicidade.setDescricaoPeriodicidade(rs.getString("descricaoPeriodicidade"));
            }
            return cPeriodicidade;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Periodicidade! Erro: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return cPeriodicidade;
    }

    @Override
    public List<Object> Listar() {

        List<Object> listaPeriodicidade = new ArrayList<>();
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        String strSQL = "Select E.* From Periodicidade E Order By E.descricaoPeriodicidade";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                Periodicidade cPeriodicidade = new Periodicidade();
                cPeriodicidade.setIdPeriodicidade(rs.getInt("IdPeriodicidade"));
                cPeriodicidade.setDescricaoPeriodicidade(rs.getString("DescricaoPeriodicidade"));
                listaPeriodicidade.add(cPeriodicidade);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar Periodicidade! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);

            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return listaPeriodicidade;
    }

}
