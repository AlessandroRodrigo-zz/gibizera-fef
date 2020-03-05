/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controller.Personagem;

import br.com.controller.Personagem.*;
import br.com.controller.Personagem.*;
import br.com.controller.Personagem.*;
import br.com.controller.Personagem.*;
import DAO.PersonagemDAO;
import DAO.GenericDAO;
import Models.Personagem;
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
@WebServlet(name = "SalvarPersonagem", urlPatterns = {"/SalvarPersonagem"})
public class SalvarPersonagem extends HttpServlet {

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
        int idPersonagem = Integer.parseInt(request.getParameter("idPersonagem"));
        String descricaoPersonagem = request.getParameter("descricaoPersonagem");       

        /*Manda pra classe*/
        Personagem cPersonagem = new Personagem();
        cPersonagem.setIdPersonagem(idPersonagem);
        cPersonagem.setDescricaoPersonagem(descricaoPersonagem);      
        
        try {
            GenericDAO daoPersonagem = new PersonagemDAO();

            if (cPersonagem.getIdPersonagem() == 0 || cPersonagem.getIdPersonagem() == null) {
                if (daoPersonagem.Cadastrar(cPersonagem)) {
                    mensagem = "Personagem cadastrado com sucesso!";
                } else {
                    mensagem = "Problemas ao cadastrar Personagem!";
                }
            } else if (daoPersonagem.Alterar(cPersonagem)) {
                mensagem = "Personagem alterado com sucesso!";
            } else {
                mensagem = "Problema ao alterar Personagem!";
            }
            request.setAttribute("Sucesso", mensagem);
            request.getRequestDispatcher("ListarPersonagem").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("Sucesso", ex.getMessage());
            request.getRequestDispatcher("ListarPersonagem").forward(request, response);
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
