/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.Editora;
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
public class EditoraDAO implements GenericDAO {

    private Connection Connection;

    public EditoraDAO() throws Exception {
        try {
            this.Connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Boolean Cadastrar(Object objeto) {
        
        Editora cEditora = (Editora) objeto;
        Boolean bRetorno = false;

        if (cEditora.getIdEditora() == 0 || cEditora.getIdEditora() == null) {
            bRetorno = this.Inserir(cEditora);
        } else {
            bRetorno = this.Alterar(cEditora);
        }
        return bRetorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {
        
        Editora cEditora = (Editora) objeto;
        PreparedStatement Stmt = null;

        //Prepara comando SQL
        String strSQL = "Insert Into Editora (descricaoEditora, situacaoEditora) Values (?, ?);";

        try {
            Stmt = Connection.prepareStatement(strSQL);

            try {
                Stmt.setString(1, cEditora.getDescricaoEditora());
                Stmt.setString(2, cEditora.getSituacaoEditora());

            } catch (Exception ex) {
                System.out.println("Problemas ao cadastrar Editora! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
            Stmt.execute();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao cadastrar Editora! Erro:" + ex.getMessage());
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
        
        Editora cEditora = (Editora) objeto;
        PreparedStatement Stmt = null;

        /*PreparedStatement = prepara a intrução SQL*/
        String strSQL = " Update Editora set descricaoEditora = ?, situacaoEditora = ? Where IdEditora = ?;";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cEditora.getDescricaoEditora());
            Stmt.setString(2, cEditora.getSituacaoEditora());
            Stmt.setInt(3, cEditora.getIdEditora());

            Stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao alterar Editora! Erro:" + ex.getMessage());
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
        
        Editora cEditora = (Editora) objeto;
        int idEditora = cEditora.getIdEditora();
        String strSQL = "";
        String Situacao = cEditora.getSituacaoEditora();
        PreparedStatement stmt = null;

        //String strSQL = "Delete From Editora Where idEditora = ?";
        
        if (Situacao.equals("A")) {
            strSQL = "Update Editora Set SituacaoEditora = 'I' Where idEditora = ?";
        }else{
            strSQL = "Update Editora Set SituacaoEditora = 'A' Where idEditora = ?";
        };
        
        try {
            stmt = Connection.prepareStatement(strSQL);
            stmt.setInt(1, idEditora);
            stmt.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Problemas ao excluir Editora! Erro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object Carregar(int Numero) {
        
        PreparedStatement Stmt = null;
        ResultSet rs = null;
        Editora cEditora = null;

        String strSQL = "Select E.* From Editora E Where E.IdEditora = ?;";
        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, Numero);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                cEditora = new Editora();
                cEditora.setIdEditora(rs.getInt("IdEditora"));
                cEditora.setDescricaoEditora(rs.getString("descricaoEditora"));
                cEditora.setSituacaoEditora(rs.getString("situacaoEditora"));
            }
            return cEditora;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Editora! Erro: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return cEditora;
    }

    @Override
    public List<Object> Listar() {

        List<Object> listaEditora = new ArrayList<>();
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        String strSQL = "Select E.* From Editora E Order By E.descricaoEditora";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                Editora cEditora = new Editora();
                cEditora.setIdEditora(rs.getInt("IdEditora"));
                cEditora.setDescricaoEditora(rs.getString("DescricaoEditora"));
                cEditora.setSituacaoEditora(rs.getString("situacaoEditora"));
                listaEditora.add(cEditora);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar Editora! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);

            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return listaEditora;
    }

}
