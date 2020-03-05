/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.Cidade;
import Models.Comprador;
import Models.Pessoa;
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
public class CompradorDAO implements GenericDAO {

    private Connection Connection;

    public CompradorDAO() throws Exception {
        try {
            this.Connection = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso!");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public Boolean Cadastrar(Object objeto) {
        Boolean retorno = false;
        
        try {
            Comprador cComprador = (Comprador) objeto;

            if (cComprador.getIdComprador() == 0 || cComprador.getIdComprador() == null) {
                //verifica se já existe pessoa com este CPF cadastrada.
                Integer idComprador = this.VerificarCpf(cComprador.getCpfCnpjPessoa());

                if (idComprador == 0) {
                    //se não encontrou insere
                    retorno = this.Inserir(cComprador);
                } else {
                    //se encontrou cliente com o cpf altera
                    cComprador.setIdComprador(idComprador);
                    retorno = this.Alterar(cComprador);
                }
            } else {
                retorno = this.Alterar(cComprador);
            }

        } catch (Exception ex) {
            System.out.println("Problemas ao incluir Comprador! Erro " + ex.getMessage());
        }
        return retorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {

        Comprador cComprador = (Comprador) objeto;
        PreparedStatement Stmt = null;
        String sql = "Insert Into Comprador (idPessoa, observacaoComprador, situacaoComprador) values (?, ?, ?)";

        try {
            PessoaDAO daoPessoa = new PessoaDAO();
            int idPessoa = daoPessoa.Inserir(cComprador);

            Stmt = Connection.prepareStatement(sql);
            Stmt.setInt(1, idPessoa);
            Stmt.setString(2, cComprador.getObservacaoComprador());
            Stmt.setString(3, cComprador.getSituacaoComprador());
            Stmt.execute();

            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao incluir Comprador! Erro " + ex.getMessage());
            return false;

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro: " + ex.getMessage());
            }
        }
    }

    @Override
    public Boolean Alterar(Object objeto) {

        Comprador cComprador = (Comprador) objeto;
        PreparedStatement Stmt = null;

        String strSQL = "Update Comprador Set observacaoComprador = ?, SituacaoComprador = ? Where idComprador = ?";

        try {
            PessoaDAO daoPessoa = new PessoaDAO();
            daoPessoa.Cadastrar(cComprador);

            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cComprador.getObservacaoComprador());
            Stmt.setString(2, cComprador.getSituacaoComprador());
            Stmt.setInt(3, cComprador.getIdComprador());
            Stmt.execute();

            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao alterar Comprador! Erro: " + ex.getMessage());
            return false;

        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro " + ex.getMessage());
            }
        }
    }

    @Override
    public Boolean Excluir(Object object) {
        return true;
    }

    public Boolean Excluir(Integer id) {
        PreparedStatement Stmt = null;

        try {
            //carrega dados de Comprador
            CompradorDAO daoComprador = new CompradorDAO();
            Comprador cComprador = (Comprador) daoComprador.Carregar(id);

            //verifica e troca a situação do cliente
            String situacaoComprador = "A";

            if (cComprador.getSituacaoComprador().equals(situacaoComprador)) {
                situacaoComprador = "I";
            } else {
                situacaoComprador = "A";
            }
            String strSQL = "Update Comprador set situacaoComprador = ? Where idComprador = ?";

            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, situacaoComprador);
            Stmt.setInt(2, cComprador.getIdComprador());
            Stmt.execute();
            return true;

        } catch (Exception ex) {
            System.out.println("Problemas ao ativar/desativar Comprador! Erro: " + ex.getMessage());
            return false;
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar parâmetros de conexão! Erro: " + ex.getMessage());
            }
        }
    }

    @Override
    public Object Carregar(int Numero) {

        int idComprador = Numero;
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        Comprador cComprador = null;

        String strSQL = "Select * From Comprador V, pessoa p Where v.idPessoa = p.idpessoa and v.idComprador = ?";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, idComprador);
            rs = Stmt.executeQuery();

            while (rs.next()) {
                //Busca a cidade
                Cidade cCidade = null;
                try {
                    CidadeDAO daoCidade = new CidadeDAO();
                    cCidade = (Cidade) daoCidade.Carregar(rs.getInt("idEstado"), rs.getInt("idCidade"));

                } catch (Exception ex) {
                    System.out.println("Problemas ao carregar Cidade!"
                            + "Erro:" + ex.getMessage());
                }

                cComprador = new Comprador(rs.getInt("idComprador"),
                        rs.getString("observacaoComprador"),
                        rs.getString("situacaoComprador"),
                        cCidade,
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
            return cComprador;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Comprador! Erro " + ex.getMessage());
            return null;
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro " + ex.getMessage());
            }
        }
    }

    @Override
    public List<Object> Listar() {

        List<Object> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String strSQL = "Select P.*, V.idComprador, V.observacaoComprador, V.situacaoComprador "
                + "From Comprador V, Pessoa P "
                + "Where V.idPessoa = P.idPessoa Order by P.nomePessoa";
        try {
            stmt = Connection.prepareStatement(strSQL);
            rs = stmt.executeQuery();

            while (rs.next()) {

                //busca cidade
                Cidade cCidade = null;
                try {
                    CidadeDAO daoCidade = new CidadeDAO();
                    cCidade = (Cidade) daoCidade.Carregar(rs.getInt("idCidade"));
                } catch (Exception ex) {
                    System.out.println("Problemas ao carregar usuario! Erro:" + ex.getMessage());
                }

                Comprador cComprador = new Comprador(rs.getInt("idComprador"),
                        rs.getString("observacaoComprador"),
                        rs.getString("situacaoComprador"),
                        cCidade,
                        rs.getInt("idPessoa"),
                        rs.getDouble("cpfCnpjPessoa"),
                        rs.getString("nomePessoa"),
                        rs.getString("enderecoPessoa"),
                        rs.getDouble("nroEnderecoPessoa"),
                        rs.getString("bairroPessoa"),
                        rs.getDouble("telefonePessoa"),
                        rs.getDouble("celularPessoa"),
                        rs.getString("emailPessoa"));

                resultado.add(cComprador);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar Comprador! Erro " + ex.getMessage());
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar parâmetros de econexão! Erro: " + ex.getMessage());
            }
        }
        return resultado;
    }

    public int VerificarCpf(Double cpf) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        int idCliente = 0;

        String strSQL = "Select v.* from vendedor v, pessoa p "
                + "where v.idpessoa = p.idPessoa and p.cpf = ?;";

        try {
            stmt = Connection.prepareStatement(strSQL);
            stmt.setDouble(1, cpf);
            rs = stmt.executeQuery();

            while (rs.next()) {
                idCliente = rs.getInt("idCliente");
            }
            return idCliente;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Comprador! Erro: " + ex.getMessage());
            return idCliente;
        }
    }
}
