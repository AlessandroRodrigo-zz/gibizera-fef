/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controller.Profissional;

import DAO.CidadeDAO;
import DAO.ProfissionalDAO;
import DAO.EstadoDAO;
import DAO.GenericDAO;
import Models.Cidade;
import Models.Profissional;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Flavio Prado
 */
@WebServlet(name = "CarregarProfissional", urlPatterns = {"/CarregarProfissional"})
public class CarregarProfissional extends HttpServlet {

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
        
        int idProfissional = Integer.parseInt(request.getParameter("idProfissional"));
        String mensagem = null;
        
        try{
            //carrega dados cliente
            GenericDAO oProfissionalDAO = new ProfissionalDAO();
            Profissional oProfissional = (Profissional) oProfissionalDAO.Carregar(idProfissional);
            request.setAttribute("Profissional", oProfissional );
            
            //Gera lista de estado
            GenericDAO oEstadoDAO = new EstadoDAO();
            request.setAttribute("Estados", oEstadoDAO.Listar());
            
            //Gera lista de cidade
            CidadeDAO oCidadeDAO = new CidadeDAO();
            List<Cidade> lstCidades = oCidadeDAO.Listar(oProfissional.getModelCidade().getEstado().getIdEstado());
            request.setAttribute("Cidades", lstCidades );
            
            //dispacha objeto de lombada para a pagina jsp
            request.getRequestDispatcher("/Cadastros/Profissional/CadastrarProfissional.jsp").forward(request, response);
        }
        catch(Exception ex){System.out.println("Problemas no Servlet ao Novo cliente! Erro: " + ex.getMessage());
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
