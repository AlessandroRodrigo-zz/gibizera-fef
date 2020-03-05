/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.Estado;
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
public class EstadoDAO implements GenericDAO {

    private Connection Connection;

    public EstadoDAO() throws Exception {
        try {
            this.Connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Boolean Cadastrar(Object objeto) {

        Estado cEstado = (Estado) objeto;
        Boolean bRetorno = false;

        if (cEstado.getIdEstado() == 0 || cEstado.getIdEstado() == null) {
            bRetorno = this.Inserir(cEstado);
        } else {
            bRetorno = this.Alterar(cEstado);
        }
        return bRetorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {
        Estado cEstado = (Estado) objeto;
        PreparedStatement Stmt = null;

        //Prepara comando SQL
        String strSQL = "Insert Into Estado (nomeEstado, siglaEstado) Values (?, ?);";

        try {
            Stmt = Connection.prepareStatement(strSQL);

            try {
                Stmt.setString(1, cEstado.getNomeEstado());
                Stmt.setString(2, cEstado.getSiglaEstado());

            } catch (Exception ex) {
                System.out.println("Problemas ao cadastrar Estado! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
            Stmt.execute();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao cadastrar Estado! Erro:" + ex.getMessage());
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

        Estado cEstado = (Estado) objeto;
        PreparedStatement Stmt = null;

        /*PreparedStatement = prepara a intrução SQL*/
        String strSQL = " Update Estado set nomeEstado = ?, siglaEstado = ? Where IdEstado = ?;";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cEstado.getNomeEstado());
            Stmt.setString(2, cEstado.getSiglaEstado());
            Stmt.setInt(3, cEstado.getIdEstado());

            Stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao alterar Estado! Erro:" + ex.getMessage());
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

        Estado cEstado = (Estado) objeto;
        int idEstado = cEstado.getIdEstado();
        PreparedStatement stmt = null;

        String strSQL = "Delete From Estado Where idEstado = ?";

        try {
            stmt = Connection.prepareStatement(strSQL);
            stmt.setInt(1, idEstado);
            stmt.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Problemas ao excluir Estado! Erro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object Carregar(int Numero) {

        PreparedStatement Stmt = null;
        ResultSet rs = null;
        Estado cEstado = null;

        String strSQL = "Select E.* From Estado E Where E.IdEstado = ?;";
        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, Numero);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                cEstado = new Estado();
                cEstado.setIdEstado(rs.getInt("IdEstado"));
                cEstado.setNomeEstado(rs.getString("nomeEstado"));
                cEstado.setSiglaEstado(rs.getString("siglaEstado"));
            }
            return cEstado;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Estado! Erro: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return cEstado;
    }

    @Override
    public List<Object> Listar() {

        List<Object> listaEstado = new ArrayList<>();
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        String strSQL = "Select E.* From Estado E Order By E.nomeEstado";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                Estado cEstado = new Estado();
                cEstado.setIdEstado(rs.getInt("IdEstado"));
                cEstado.setNomeEstado(rs.getString("nomeEstado"));
                cEstado.setSiglaEstado(rs.getString("siglaEstado"));
                listaEstado.add(cEstado);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar Estado! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);

            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return listaEstado;
    }

}
