/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.Licenciadora;
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
public class LicenciadoraDAO implements GenericDAO {

    private Connection Connection;

    public LicenciadoraDAO() throws Exception {
        try {
            this.Connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Boolean Cadastrar(Object objeto) {
        
        Licenciadora cLicenciadora = (Licenciadora) objeto;
        Boolean bRetorno = false;

        if (cLicenciadora.getIdLicenciadora() == 0 || cLicenciadora.getIdLicenciadora() == null) {
            bRetorno = this.Inserir(cLicenciadora);
        } else {
            bRetorno = this.Alterar(cLicenciadora);
        }
        return bRetorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {
        
        Licenciadora cLicenciadora = (Licenciadora) objeto;
        PreparedStatement Stmt = null;

        //Prepara comando SQL
        String strSQL = "Insert Into Licenciadora (descricaoLicenciadora, situacaoLicenciadora) Values (?, ?);";

        try {
            Stmt = Connection.prepareStatement(strSQL);

            try {
                Stmt.setString(1, cLicenciadora.getDescricaoLicenciadora());
                Stmt.setString(2, cLicenciadora.getSituacaoLicenciadora());

            } catch (Exception ex) {
                System.out.println("Problemas ao cadastrar Licenciadora! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
            Stmt.execute();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao cadastrar Licenciadora! Erro:" + ex.getMessage());
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
        
        Licenciadora cLicenciadora = (Licenciadora) objeto;
        PreparedStatement Stmt = null;

        /*PreparedStatement = prepara a intrução SQL*/
        String strSQL = " Update Licenciadora set descricaoLicenciadora = ?, situacaoLicenciadora = ? Where IdLicenciadora = ?;";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cLicenciadora.getDescricaoLicenciadora());
            Stmt.setString(2, cLicenciadora.getSituacaoLicenciadora());
            Stmt.setInt(3, cLicenciadora.getIdLicenciadora());

            Stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao alterar Licenciadora! Erro:" + ex.getMessage());
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
        
        Licenciadora cLicenciadora = (Licenciadora) objeto;
        int idLicenciadora = cLicenciadora.getIdLicenciadora();
        String strSQL = "";
        String Situacao = cLicenciadora.getSituacaoLicenciadora();
        PreparedStatement stmt = null;

        //String strSQL = "Delete From Licenciadora Where idLicenciadora = ?";
        
        if (Situacao.equals("A")) {
            strSQL = "Update Licenciadora Set SituacaoLicenciadora = 'I' Where idLicenciadora = ?";
        }else{
            strSQL = "Update Licenciadora Set SituacaoLicenciadora = 'A' Where idLicenciadora = ?";
        };
        
        try {
            stmt = Connection.prepareStatement(strSQL);
            stmt.setInt(1, idLicenciadora);
            stmt.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Problemas ao excluir Licenciadora! Erro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object Carregar(int Numero) {
        
        PreparedStatement Stmt = null;
        ResultSet rs = null;
        Licenciadora cLicenciadora = null;

        String strSQL = "Select E.* From Licenciadora E Where E.IdLicenciadora = ?;";
        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, Numero);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                cLicenciadora = new Licenciadora();
                cLicenciadora.setIdLicenciadora(rs.getInt("IdLicenciadora"));
                cLicenciadora.setDescricaoLicenciadora(rs.getString("descricaoLicenciadora"));
                cLicenciadora.setSituacaoLicenciadora(rs.getString("situacaoLicenciadora"));
            }
            return cLicenciadora;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Licenciadora! Erro: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return cLicenciadora;
    }

    @Override
    public List<Object> Listar() {

        List<Object> listaLicenciadora = new ArrayList<>();
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        String strSQL = "Select E.* From Licenciadora E Order By E.descricaoLicenciadora";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                Licenciadora cLicenciadora = new Licenciadora();
                cLicenciadora.setIdLicenciadora(rs.getInt("IdLicenciadora"));
                cLicenciadora.setDescricaoLicenciadora(rs.getString("DescricaoLicenciadora"));
                cLicenciadora.setSituacaoLicenciadora(rs.getString("situacaoLicenciadora"));
                listaLicenciadora.add(cLicenciadora);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar Licenciadora! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);

            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return listaLicenciadora;
    }

}
