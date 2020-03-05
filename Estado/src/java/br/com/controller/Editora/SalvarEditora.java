/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controller.Editora;

import br.com.controller.Editora.*;
import DAO.EditoraDAO;
import DAO.GenericDAO;
import Models.Editora;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Aluno
 */
@WebServlet(name = "SalvarEditora", urlPatterns = {"/SalvarEditora"})
public class SalvarEditora extends HttpServlet {

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
        
        /*Pega da Tela*/
        String mensagem = null;
        int idEditora = Integer.parseInt(request.getParameter("idEditora"));
        String descricaoEditora = request.getParameter("descricaoEditora");
        String situacaoEditora = request.getParameter("situacaoEditora");        

        /*Manda pra classe*/
        Editora cEditora = new Editora();
        cEditora.setIdEditora(idEditora);
        cEditora.setDescricaoEditora(descricaoEditora);
        cEditora.setSituacaoEditora(situacaoEditora);        
        
        try {
            GenericDAO daoEditora = new EditoraDAO();

            if (cEditora.getIdEditora() == 0 || cEditora.getIdEditora() == null) {
                if (daoEditora.Cadastrar(cEditora)) {
                    mensagem = "Editora cadastrado com sucesso!";
                } else {
                    mensagem = "Problemas ao cadastrar Editora!";
                }
            } else if (daoEditora.Alterar(cEditora)) {
                mensagem = "Editora alterado com sucesso!";
            } else {
                mensagem = "Problema ao alterar Editora!";
            }
            request.setAttribute("Sucesso", mensagem);
            request.getRequestDispatcher("ListarEditora").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("Sucesso", ex.getMessage());
            request.getRequestDispatcher("ListarEditora").forward(request, response);
            ex.printStackTrace();
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
