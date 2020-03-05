/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Cidade;
import Models.Pessoa;
import Database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Flavio Prado
 */
public class PessoaDAO {

    private Connection conexao;

    public PessoaDAO() throws Exception {
        try {
            this.conexao = ConnectionFactory.getConnection();
            System.out.println("Conectado com sucesso!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public int Cadastrar(Object objeto) throws ParseException {
        Pessoa cPessoa = (Pessoa) objeto;
        int retorno = 0;

        if (cPessoa.getIdPessoa() == 0) {
            Pessoa objPessoa = this.CarregarCpf(cPessoa.getCpfCnpjPessoa());
            if (objPessoa.getIdPessoa() == 0) {
                retorno = this.Inserir(cPessoa);
            } else {
                retorno = objPessoa.getIdPessoa();
            }
        } else {
            retorno = this.Alterar(cPessoa);
        }

        return retorno;
    }

    public int Inserir(Object objeto) {
        Pessoa cPessoa = (Pessoa) objeto;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer idPessoa = null;
        String sql = "insert into pessoa (cpfCnpjPessoa, nomePessoa, enderecoPessoa, nroEnderecoPessoa, bairroPessoa, telefonePessoa, celularPessoa, emailPessoa, idCidade, idEstado) "
                               + "values (            ?,          ?,              ?,                 ?,            ?,              ?,             ?,           ?,        ?,        ?) returning idPessoa;";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setDouble(1, cPessoa.getCpfCnpjPessoa());
            stmt.setString(2, cPessoa.getNomePessoa());
            stmt.setString(3, cPessoa.getEnderecoPessoa());
            stmt.setDouble(4, cPessoa.getNroEnderecoPessoa());
            stmt.setString(5, cPessoa.getBairroPessoa());
            stmt.setDouble(6, cPessoa.getTelefonePessoa());
            stmt.setDouble(7, cPessoa.getCelularPessoa());
            stmt.setString(8, cPessoa.getEmailPessoa());
            stmt.setInt(9, cPessoa.getModelCidade().getIdCidade());
            stmt.setInt(10, cPessoa.getModelCidade().getEstado().getIdEstado());

            //Executa o comando e já busca o retorno
            rs = stmt.executeQuery();
            
            //Guarda o ID retornado na variável
            while (rs.next()) {
                idPessoa = rs.getInt("idPessoa");
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao cadastrar Pessoa! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(conexao, stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetos de conexão! Erro: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return idPessoa;
    }

    public int Alterar(Object objeto) {
        Pessoa cPessoa = (Pessoa) objeto;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer idPessoa = null;
        String sql = "Update Pessoa Set nomePessoa = ?, enderecoPessoa = ?, nroEnderecoPessoa = ?, bairroPessoa = ?, telefonePessoa = ?, celularPessoa = ?, emailPessoa = ?, idCidade = ?"
                + " Where idPessoa = ? returning idPessoa;";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, cPessoa.getNomePessoa());
            stmt.setString(2, cPessoa.getEnderecoPessoa());
            stmt.setDouble(3, cPessoa.getNroEnderecoPessoa());
            stmt.setString(4, cPessoa.getBairroPessoa());
            stmt.setDouble(5, cPessoa.getTelefonePessoa());
            stmt.setDouble(6, cPessoa.getCelularPessoa());
            stmt.setString(7, cPessoa.getEmailPessoa());
            stmt.setInt(8, cPessoa.getModelCidade().getIdCidade());
            stmt.setInt(9, cPessoa.getIdPessoa());
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                idPessoa = rs.getInt("idPessoa");
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao alterar Pessoa! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(conexao, stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetos de conexão! Erro: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return idPessoa;
    }

    public Pessoa CarregarCpf(Double cpfCnpjPessoa) throws ParseException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Pessoa cPessoa = null;
        String sql = "Select * From Pessoa Where cpfCnpjPessoa = ?;";

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setDouble(1, cpfCnpjPessoa);
            rs = stmt.executeQuery();

            while (rs.next()) {
                cPessoa = this.Carregar(rs.getInt("idPessoa"));
            }

            if (cPessoa == null) {
                Cidade cCidade = new Cidade();
                cPessoa = new Pessoa(cCidade, 0, 0, "", "", 0, "", 0, 0, "");
            }

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Pessoa!"
                    + "Erro:" + ex.getMessage());
        }

        return cPessoa;
    }

    public Pessoa Carregar(int id) {
        int idPessoa = id;
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Pessoa cPessoa = null;
        
        String sql = "Select * From Pessoa Where idPessoa=?";

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPessoa);
            rs = stmt.executeQuery();

            while (rs.next()) {

                Cidade cCidade = null;
                
                try {
                    CidadeDAO daoCidade = new CidadeDAO();
                    int idCidade = rs.getInt("idCidade");
                    cCidade = (Cidade) daoCidade.Carregar(idCidade);
                } catch (Exception ex) {
                    System.out.println("Problemas ao carregar Pessoa! Erro:" + ex.getMessage());
                }

                cPessoa = new Pessoa(cCidade, 
                                    rs.getInt("idPessoa"), 
                                    rs.getDouble("cpfCnpjPessoa"), 
                                    rs.getString("nomePessoa"), 
                                    rs.getString("enderecoPessoa"), 
                                    rs.getDouble("nroEnderecoPessoa"),
                                    rs.getString("bairroPessoa"),
                                    rs.getDouble("telefonePessoa"),
                                    rs.getDouble("celularPessoa"),
                                    rs.getString("emailPessoa"));
            }
            return cPessoa;
        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Pessoa! Erro " + ex.getMessage());
            return null;
        } finally {
            try {
                ConnectionFactory.CloseConnection(conexao, stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro " + ex.getMessage());
            }
        }
    }

}
