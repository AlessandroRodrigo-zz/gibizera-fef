/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.Personagem;
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
public class PersonagemDAO implements GenericDAO {

    private Connection Connection;

    public PersonagemDAO() throws Exception {
        try {
            this.Connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Boolean Cadastrar(Object objeto) {
        
        Personagem cPersonagem = (Personagem) objeto;
        Boolean bRetorno = false;

        if (cPersonagem.getIdPersonagem() == 0 || cPersonagem.getIdPersonagem() == null) {
            bRetorno = this.Inserir(cPersonagem);
        } else {
            bRetorno = this.Alterar(cPersonagem);
        }
        return bRetorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {
        
        Personagem cPersonagem = (Personagem) objeto;
        PreparedStatement Stmt = null;
        
        //Prepara comando SQL
        String strSQL = "Insert Into Personagem (descricaoPersonagem) Values (?);";

        try {
            Stmt = Connection.prepareStatement(strSQL);

            try {
                Stmt.setString(1, cPersonagem.getDescricaoPersonagem());

            } catch (Exception ex) {
                System.out.println("Problemas ao cadastrar Personagem! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
            Stmt.execute();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao cadastrar Personagem! Erro:" + ex.getMessage());
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
        
        Personagem cPersonagem = (Personagem) objeto;
        PreparedStatement Stmt = null;

        /*PreparedStatement = prepara a intrução SQL*/
        String strSQL = " Update Personagem set descricaoPersonagem = ? Where IdPersonagem = ?;";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cPersonagem.getDescricaoPersonagem());
            Stmt.setInt(2, cPersonagem.getIdPersonagem());

            Stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problemas ao alterar Personagem! Erro:" + ex.getMessage());
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
        
        Personagem cPersonagem = (Personagem) objeto;
        int idPersonagem = cPersonagem.getIdPersonagem();
        PreparedStatement stmt = null;

        String strSQL = "Delete From Personagem Where idPersonagem = ?";       
        
        try {
            stmt = Connection.prepareStatement(strSQL);
            stmt.setInt(1, idPersonagem);
            stmt.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Problemas ao excluir Personagem! Erro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Object Carregar(int Numero) {
        
        PreparedStatement Stmt = null;
        ResultSet rs = null;
        Personagem cPersonagem = null;

        String strSQL = "Select E.* From Personagem E Where E.IdPersonagem = ?;";
        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, Numero);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                cPersonagem = new Personagem();
                cPersonagem.setIdPersonagem(rs.getInt("IdPersonagem"));
                cPersonagem.setDescricaoPersonagem(rs.getString("descricaoPersonagem"));
            }
            return cPersonagem;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Personagem! Erro: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return cPersonagem;
    }

    @Override
    public List<Object> Listar() {

        List<Object> listaPersonagem = new ArrayList<>();
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        String strSQL = "Select E.* From Personagem E Order By E.descricaoPersonagem";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                Personagem cPersonagem = new Personagem();
                cPersonagem.setIdPersonagem(rs.getInt("IdPersonagem"));
                cPersonagem.setDescricaoPersonagem(rs.getString("DescricaoPersonagem"));
                listaPersonagem.add(cPersonagem);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar Personagem! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);

            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return listaPersonagem;
    }

    public int newSequencePersonagem() {
	PreparedStatement Stmt = null;
        ResultSet rs = null;

	Integer idPersonagem = 0;
	String strSQL = "Select Max(idPersonagem) + 1 as idPersonagem From Personagem";

	try{
            Stmt = Connection.prepareStatement(strSQL);
            rs = Stmt.executeQuery();

            while (rs.next()) {
		idPersonagem = rs.getInt("idPersonagem");				
            }
        
            return idPersonagem;
        
	} catch (SQLException ex) {
		System.out.println("Problemas ao buscar ID do Personagem! Erro: " + ex.getMessage());
            ex.printStackTrace();
	} finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro:" + ex.getMessage());
                ex.printStackTrace();
            }
	}
        return idPersonagem ;
    }
    
}
