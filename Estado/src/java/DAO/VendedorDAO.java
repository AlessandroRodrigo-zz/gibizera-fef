/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.ConnectionFactory;
import Models.Cidade;
import Models.Vendedor;
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
public class VendedorDAO implements GenericDAO {

    private Connection Connection;

    public VendedorDAO() throws Exception {
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
            Vendedor cVendedor = (Vendedor) objeto;

            if (cVendedor.getIdVendedor() == 0 || cVendedor.getIdVendedor() == null) {
                //verifica se já existe pessoa com este CPF cadastrada.
                Integer idVendedor = this.VerificarCpf(cVendedor.getCpfCnpjPessoa());

                if (idVendedor == 0) {
                    //se não encontrou insere
                    retorno = this.Inserir(cVendedor);
                } else {
                    //se encontrou cliente com o cpf altera
                    cVendedor.setIdVendedor(idVendedor);
                    retorno = this.Alterar(cVendedor);
                }
            } else {
                retorno = this.Alterar(cVendedor);
            }

        } catch (Exception ex) {
            System.out.println("Problemas ao incluir Vendedor! Erro " + ex.getMessage());
        }
        return retorno;
    }

    @Override
    public Boolean Inserir(Object objeto) {

        Vendedor cVendedor = (Vendedor) objeto;
        PreparedStatement Stmt = null;
        String sql = "Insert Into Vendedor (idPessoa, observacaoVendedor, situacaoVendedor) values (?, ?, ?)";

        try {
            PessoaDAO daoPessoa = new PessoaDAO();
            int idPessoa = daoPessoa.Inserir(cVendedor);

            Stmt = Connection.prepareStatement(sql);
            Stmt.setInt(1, idPessoa);
            Stmt.setString(2, cVendedor.getObservacaoVendedor());
            Stmt.setString(3, cVendedor.getSituacaoVendedor());
            Stmt.execute();

            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao incluir Vendedor! Erro " + ex.getMessage());
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

        Vendedor cVendedor = (Vendedor) objeto;
        PreparedStatement Stmt = null;

        String strSQL = "Update Vendedor Set observacaoVendedor = ?, SituacaoVendedor = ? Where idVendedor = ?";

        try {
            PessoaDAO daoPessoa = new PessoaDAO();
            daoPessoa.Cadastrar(cVendedor);

            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, cVendedor.getObservacaoVendedor());
            Stmt.setString(2, cVendedor.getSituacaoVendedor());
            Stmt.setInt(3, cVendedor.getIdVendedor());
            Stmt.execute();

            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao alterar Vendedor! Erro: " + ex.getMessage());
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
            //carrega dados de Vendedor
            VendedorDAO daoVendedor = new VendedorDAO();
            Vendedor cVendedor = (Vendedor) daoVendedor.Carregar(id);

            //verifica e troca a situação do cliente
            String situacaoVendedor = "A";

            if (cVendedor.getSituacaoVendedor().equals(situacaoVendedor)) {
                situacaoVendedor = "I";
            } else {
                situacaoVendedor = "A";
            }
            String strSQL = "Update Vendedor set situacaoVendedor = ? Where idVendedor = ?";

            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setString(1, situacaoVendedor);
            Stmt.setInt(2, cVendedor.getIdVendedor());
            Stmt.execute();
            return true;

        } catch (Exception ex) {
            System.out.println("Problemas ao ativar/desativar Vendedor! Erro: " + ex.getMessage());
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

        int idVendedor = Numero;
        PreparedStatement Stmt = null;
        ResultSet rs = null;

        Vendedor cVendedor = null;

        String strSQL = "Select * From Vendedor V, pessoa p Where v.idPessoa = p.idpessoa and v.idVendedor = ?";

        try {
            Stmt = Connection.prepareStatement(strSQL);
            Stmt.setInt(1, idVendedor);
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

                cVendedor = new Vendedor(rs.getInt("idVendedor"),
                        rs.getString("observacaoVendedor"),
                        rs.getString("situacaoVendedor"),
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
            return cVendedor;

        } catch (SQLException ex) {
            System.out.println("Problemas ao carregar Vendedor! Erro " + ex.getMessage());
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

        String strSQL = "Select P.*, V.idVendedor, V.observacaoVendedor, V.situacaoVendedor "
                + "From Vendedor V, Pessoa P "
                + "Where V.idPessoa = P.idPessoa order by P.nomePessoa";
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

                Vendedor cVendedor = new Vendedor(rs.getInt("idVendedor"),
                        rs.getString("observacaoVendedor"),
                        rs.getString("situacaoVendedor"),
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

                resultado.add(cVendedor);
            }
        } catch (SQLException ex) {
            System.out.println("Problemas ao listar Vendedor! Erro " + ex.getMessage());
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
            System.out.println("Problemas ao carregar Vendedor! Erro: " + ex.getMessage());
            return idCliente;
        }
    }
}
