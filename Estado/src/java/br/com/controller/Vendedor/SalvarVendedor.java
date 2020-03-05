/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controller.Vendedor;

import DAO.CidadeDAO;
import DAO.GenericDAO;
import DAO.VendedorDAO;
import Models.Cidade;
import Models.Vendedor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Flavio Prado
 */
@WebServlet(name = "SalvarVendedor", urlPatterns = {"/SalvarVendedor"})
public class SalvarVendedor extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //pega dados do formulario
        int idVendedor = Integer.parseInt(request.getParameter("idVendedor"));
        int idPessoa = Integer.parseInt(request.getParameter("idPessoa"));
        int idCidade = Integer.parseInt(request.getParameter("idCidade"));
        int idEstado = Integer.parseInt(request.getParameter("idEstado"));
        
        String observacaoVendedor = request.getParameter("observacaoVendedor");
        String situacaoVendedor = request.getParameter("situacaoVendedor");
        String nomePessoa = request.getParameter("nomePessoa");
        double cpfCnpjPessoa = Double.parseDouble(request.getParameter("cpfCnpjPessoa"));
        String enderecoPessoa = request.getParameter("enderecoPessoa");
        double nroEnderecoPessoa = Double.parseDouble(request.getParameter("nroEnderecoPessoa"));
        String bairroPessoa = request.getParameter("bairroPessoa");
        double telefonePessoa = Double.parseDouble(request.getParameter("telefonePessoa"));
        double celularPessoa = Double.parseDouble(request.getParameter("celularPessoa"));
        String emailPessoa = request.getParameter("emailPessoa");                               
            
        String mensagem = null;
  
        try{
            //busca objeto de cidade
            CidadeDAO daoCidade = new CidadeDAO();
            Cidade cCidade = (Cidade) daoCidade.Carregar(idEstado, idCidade);
            
            //gera objeto de Vendedor
            Vendedor cVendedor = new Vendedor(idVendedor,
                                            observacaoVendedor,
                                            situacaoVendedor,
                                            cCidade,
                                            idPessoa,
                                            cpfCnpjPessoa,
                                            nomePessoa,
                                            enderecoPessoa,
                                            nroEnderecoPessoa,
                                            bairroPessoa,
                                            telefonePessoa,
                                            celularPessoa,
                                            emailPessoa);
            

            GenericDAO daoVendedor = new VendedorDAO();
            
            if (daoVendedor.Cadastrar(cVendedor)){
                mensagem = "Vendedor cadastrada com sucesso!";                
            } else {
                mensagem = "Problemas ao cadastrar Vendedor. Verifique os dados informados e tente novamente!";
            }

            request.setAttribute("Sucesso", mensagem);
            response.sendRedirect("ListarVendedor");

        } catch (Exception ex){
             System.out.println("Problemas no Servlet ao cadastrar Vendedor! Erro: " + ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
