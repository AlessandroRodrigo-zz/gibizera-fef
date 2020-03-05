/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.Cidade;
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
public class CidadeDAO implements GenericDAO {

    private Connection Connection;

    public CidadeDAO() throws Exception {
        try {
            this.Connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Boolean Cadastrar(Object objeto) {

        Cidade cCidade = (Cidade) objeto;
        Boolean bRetorno = false;

        if (cCidade.getIdCidade() == 0 || cCidade.getIdCidade() == null) {
            bRetorno = this.Inserir(cCidade);
        } else {
            bRetorno = this.Alterar(cCidade);
        }
        return bRetorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {

        Cidade cCidade = (Cidade) objeto;
        PreparedStatement Stmt = null;

        //Prepara comando SQL
        String strSQL = "Insert Into Cidade (nomeCidade, idEstado) Values (?, ?);";

        try {
            Stmt = Connection.prepareStatement(strSQL);

            try {
                Stmt.setString(1, cCidade.getNomeCidade());
                Stmt.setInt(2, cCidade.getEstado().getIdEstado());

            } catch (Exception ex) {
                System.out.println("Problemas ao cadastrar Cidade! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
            Stmt.execute();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao cadastrar Cidade! Erro:" + ex.getMessage());
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

        Cidade cCidade = (Cidade) objeto;
        PreparedStatement Stmt = null;

        /*PreparedStatement = prepara a intrução SQL*/
        String strSQL = " Update Cidade set nomeCidade = ? Where idEstado = ? And idCidade = ?;";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cCidade.getNomeCidade());
            Stmt.setInt(2, cCidade.getEstado().getIdEstado());
            Stmt.setInt(3, cCidade.getIdCidade());

            Stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao alterar Cidade! Erro:" + ex.getMessage());
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

        Cidade cCidade = (Cidade) objeto;
        PreparedStatement stmt = null;

        String strSQL = "Delete From Cidade Where idEstado = ? and idCidade = ?";

        try {
            stmt = Connection.prepareStatement(strSQL);
            stmt.setInt(1, cCidade.getEstado().getIdEstado());
            stmt.setInt(2, cCidade.getIdCidade());
            stmt.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Problemas ao excluir Cidade! Erro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object Carregar(int Numero) {
        Cidade cCidade = new Cidade();
        return cCidade;
    }

    public Object Carregar(Integer idEstado, Integer idCidade) throws Exception {

        PreparedStatement Stmt = null;
        ResultSet rs = null;
        Cidade cCidade = null;

        String strSQL = "Select * From Cidade Where idEstado = ? and idCidade = ?";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, idEstado);
            Stmt.setInt(2, idCidade);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                cCidade = new Cidade();
                cCidade.setIdCidade(rs.getInt("idCidade"));
                cCidade.setNomeCidade(rs.getString("nomeCidade"));

                EstadoDAO daoEstado = new EstadoDAO();
                cCidade.setEstado((Estado) daoEstado.Carregar(rs.getInt("idEstado")));
            }
            return cCidade;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Cidade! Erro: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return cCidade;
    }

    @Override
    public List<Object> Listar() {

        List<Object> listaCidade = new ArrayList<>();
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        String strSQL = "Select C.idCidade, C.nomeCidade, C.idEstado, E.idEstado, E.nomeEstado, E.siglaEstado "
                        + "From Cidade C, "
                             + "Estado E "
                       + "Where C.idEstado = E.idEstado "
                    + "Group By E.idEstado, E.nomeEstado, E.siglaEstado, C.idEstado, C.idCidade, C.nomeCidade "
                    + "Order By C.nomeCidade";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                Cidade cCidade = new Cidade();
                cCidade.setIdCidade(rs.getInt("idCidade"));
                cCidade.setNomeCidade(rs.getString("nomeCidade"));
                
                Estado cEstado = new Estado();
                cCidade.setEstado(cEstado);
                cCidade.getEstado().setIdEstado(rs.getInt("idEstado"));
                cCidade.getEstado().setNomeEstado(rs.getString("nomeEstado"));
                cCidade.getEstado().setSiglaEstado(rs.getString("siglaEstado"));
                
                listaCidade.add(cCidade);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar Cidade! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);

            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return listaCidade;
    }

    public List<Cidade> Listar(Integer idEstado) {

        List<Cidade> resultado = new ArrayList<>();
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        String strSQL = "Select * From Cidade C Where C.IdEstado = ? Order By NomeCidade";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, idEstado);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                Cidade cCidade = new Cidade();
                cCidade.setIdCidade(rs.getInt("idCidade"));
                cCidade.setNomeCidade(rs.getString("nomeCidade"));

                try {
                    EstadoDAO daoEstado = new EstadoDAO();
                    cCidade.setEstado((Estado) daoEstado.Carregar(rs.getInt("idEstado")));
                } catch (Exception ex) {
                    System.out.println("Problemas ao listar Cidade! Erro: " + ex.getMessage());
                };
                
                resultado.add(cCidade);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar Cidade! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return resultado;
    }

}
