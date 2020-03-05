/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.Cidade;
import Models.Usuario;
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
public class UsuarioDAO implements GenericDAO {

    private Connection Connection;

    public UsuarioDAO() throws Exception {
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
            Usuario cUsuario = (Usuario) objeto;

            if (cUsuario.getIdUsuario() == 0 || cUsuario.getIdUsuario() == null) {
                //verifica se já existe pessoa com este CPF cadastrada.
                Integer idUsuario = this.VerificarCpf(cUsuario.getCpfCnpjPessoa());

                if (idUsuario == 0) {
                    //se não encontrou insere
                    retorno = this.Inserir(cUsuario);
                } else {
                    //se encontrou cliente com o cpf altera
                    cUsuario.setIdUsuario(idUsuario);
                    retorno = this.Alterar(cUsuario);
                }
            } else {
                retorno = this.Alterar(cUsuario);
            }

        } catch (Exception ex) {
            System.out.println("Problemas ao incluir Usuario! Erro " + ex.getMessage());
        }
        return retorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {

        Usuario cUsuario = (Usuario) objeto;
        PreparedStatement Stmt = null;
        String sql = "Insert Into Usuario (idPessoa, observacaoUsuario, situacaoUsuario, loginUsuario, senhaUsuario) values (?, ?, ?, ?, ?)";

        try {
            PessoaDAO daoPessoa = new PessoaDAO();
            int idPessoa = daoPessoa.Inserir(cUsuario);

            Stmt = Connection.prepareStatement(sql);
            Stmt.setInt(1, idPessoa);
            Stmt.setString(2, cUsuario.getObservacaoUsuario());
            Stmt.setString(3, cUsuario.getSituacaoUsuario());
            Stmt.setString(4, cUsuario.getLoginUsuario());
            Stmt.setString(5, cUsuario.getSenhaUsuario());
            Stmt.execute();

            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao incluir Usuario! Erro " + ex.getMessage());
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

        Usuario cUsuario = (Usuario) objeto;
        PreparedStatement Stmt = null;

        String strSQL = "Update Usuario Set observacaoUsuario = ?, SituacaoUsuario = ?, loginUsuario = ?, senhaUsuario = ? Where idUsuario = ?";

        try {
            PessoaDAO daoPessoa = new PessoaDAO();
            daoPessoa.Cadastrar(cUsuario);

            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cUsuario.getObservacaoUsuario());
            Stmt.setString(2, cUsuario.getSituacaoUsuario());
            Stmt.setString(3, cUsuario.getLoginUsuario());
            Stmt.setString(4, cUsuario.getSenhaUsuario());
            Stmt.setInt(5, cUsuario.getIdUsuario());
            Stmt.execute();

            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao alterar Usuario! Erro: " + ex.getMessage());
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
            //carrega dados de Usuario
            UsuarioDAO daoUsuario = new UsuarioDAO();
            Usuario cUsuario = (Usuario) daoUsuario.Carregar(id);

            //verifica e troca a situação do cliente
            String situacaoUsuario = "A";

            if (cUsuario.getSituacaoUsuario().equals(situacaoUsuario)) {
                situacaoUsuario = "I";
            } else {
                situacaoUsuario = "A";
            }
            String strSQL = "Update Usuario set situacaoUsuario = ? Where idUsuario = ?";

            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, situacaoUsuario);
            Stmt.setInt(2, cUsuario.getIdUsuario());
            Stmt.execute();
            return true;

        } catch (Exception ex) {
            System.out.println("Problemas ao ativar/desativar Usuario! Erro: " + ex.getMessage());
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

        int idUsuario = Numero;
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        Usuario cUsuario = null;

        String strSQL = "Select * From Usuario V, pessoa p Where v.idPessoa = p.idpessoa and v.idUsuario = ?";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, idUsuario);
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

                cUsuario = new Usuario(rs.getInt("idUsuario"),
                        rs.getString("observacaoUsuario"),
                        rs.getString("situacaoUsuario"),
                        rs.getString("loginUsuario"),
                        rs.getString("senhaUsuario"),
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
            return cUsuario;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Usuario! Erro " + ex.getMessage());
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

        String strSQL = "Select P.*, V.idUsuario, V.observacaoUsuario, V.situacaoUsuario, V.LOGINUSUARIO, V.SENHAUSUARIO "
                + "From Usuario V, Pessoa P "
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

                Usuario cUsuario = new Usuario(rs.getInt("idUsuario"),
                        rs.getString("observacaoUsuario"),
                        rs.getString("situacaoUsuario"),
                        rs.getString("loginUsuario"),
                        rs.getString("senhaUsuario"),
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

                resultado.add(cUsuario);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar Usuario! Erro " + ex.getMessage());
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
            System.out.println("Problemas ao carregar Usuario! Erro: " + ex.getMessage());
            return idCliente;
        }
    }

    public boolean verificarUsuario(String Login, String Senha) {
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        Integer Retorno = 0;

        String strSQL = "Select Count(idUsuario) as Usuario From Usuario Where loginUsuario = ? and senhaUsuario = ?";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, Login);
            Stmt.setString(2, Senha);

            rs = Stmt.executeQuery();

            while (rs.next()) {
                Retorno = rs.getInt("Usuario");
            }
        } catch (Exception Ex) {
            System.out.println("Problemas ao validar Login! Erro: " + Ex.getMessage());
            Ex.printStackTrace();
        } finally {
            try {
                ConnectionFactory.CloseConnection(Connection, Stmt, rs);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar os parâmetos de conexão! Erro: " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        if (Retorno > 0) {
            return true;
        } else {
            return false;
        }
    }
}
