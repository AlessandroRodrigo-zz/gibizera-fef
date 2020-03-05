/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.Cidade;
import Models.Profissional;
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
public class ProfissionalDAO implements GenericDAO {

    private Connection Connection;

    public ProfissionalDAO() throws Exception {
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
            Profissional cProfissional = (Profissional) objeto;

            if (cProfissional.getIdProfissional() == 0 || cProfissional.getIdProfissional() == null) {
                //verifica se já existe pessoa com este CPF cadastrada.
                Integer idProfissional = this.VerificarCpf(cProfissional.getCpfCnpjPessoa());

                if (idProfissional == 0) {
                    //se não encontrou insere
                    retorno = this.Inserir(cProfissional);
                } else {
                    //se encontrou cliente com o cpf altera
                    cProfissional.setIdProfissional(idProfissional);
                    retorno = this.Alterar(cProfissional);
                }
            } else {
                retorno = this.Alterar(cProfissional);
            }

        } catch (Exception ex) {
            System.out.println("Problemas ao incluir Profissional! Erro " + ex.getMessage());
        }
        return retorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {

        Profissional cProfissional = (Profissional) objeto;
        PreparedStatement Stmt = null;
        String sql = "Insert Into Profissional (idPessoa, observacaoProfissional, situacaoProfissional) values (?, ?, ?)";

        try {
            PessoaDAO daoPessoa = new PessoaDAO();
            int idPessoa = daoPessoa.Inserir(cProfissional);

            Stmt = Connection.prepareStatement(sql);
            Stmt.setInt(1, idPessoa);
            Stmt.setString(2, cProfissional.getObservacaoProfissional());
            Stmt.setString(3, cProfissional.getSituacaoProfissional());
            Stmt.execute();

            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao incluir Profissional! Erro " + ex.getMessage());
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

        Profissional cProfissional = (Profissional) objeto;
        PreparedStatement Stmt = null;

        String strSQL = "Update Profissional Set observacaoProfissional = ?, SituacaoProfissional = ? Where idProfissional = ?";

        try {
            PessoaDAO daoPessoa = new PessoaDAO();
            daoPessoa.Cadastrar(cProfissional);

            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cProfissional.getObservacaoProfissional());
            Stmt.setString(2, cProfissional.getSituacaoProfissional());
            Stmt.setInt(3, cProfissional.getIdProfissional());
            Stmt.execute();

            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao alterar Profissional! Erro: " + ex.getMessage());
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
            //carrega dados de Profissional
            ProfissionalDAO daoProfissional = new ProfissionalDAO();
            Profissional cProfissional = (Profissional) daoProfissional.Carregar(id);

            //verifica e troca a situação do cliente
            String situacaoProfissional = "A";

            if (cProfissional.getSituacaoProfissional().equals(situacaoProfissional)) {
                situacaoProfissional = "I";
            } else {
                situacaoProfissional = "A";
            }
            String strSQL = "Update Profissional set situacaoProfissional = ? Where idProfissional = ?";

            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, situacaoProfissional);
            Stmt.setInt(2, cProfissional.getIdProfissional());
            Stmt.execute();
            return true;

        } catch (Exception ex) {
            System.out.println("Problemas ao ativar/desativar Profissional! Erro: " + ex.getMessage());
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

        int idProfissional = Numero;
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        Profissional cProfissional = null;

        String strSQL = "Select * From Profissional V, pessoa p Where v.idPessoa = p.idpessoa and v.idProfissional = ?";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, idProfissional);
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

                cProfissional = new Profissional(rs.getInt("idProfissional"),
                        rs.getString("observacaoProfissional"),
                        rs.getString("situacaoProfissional"),
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
            return cProfissional;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Profissional! Erro " + ex.getMessage());
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

        String strSQL = "Select P.*, V.idProfissional, V.observacaoProfissional, V.situacaoProfissional "
                + "From Profissional V, Pessoa P "
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

                Profissional cProfissional = new Profissional(rs.getInt("idProfissional"),
                        rs.getString("observacaoProfissional"),
                        rs.getString("situacaoProfissional"),
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

                resultado.add(cProfissional);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar Profissional! Erro " + ex.getMessage());
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
            System.out.println("Problemas ao carregar Profissional! Erro: " + ex.getMessage());
            return idCliente;
        }
    }
}
