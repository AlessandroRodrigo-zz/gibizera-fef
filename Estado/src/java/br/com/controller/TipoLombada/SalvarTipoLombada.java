/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controller.TipoLombada;

import DAO.GenericDAO;
import DAO.TipoLombadaDAO;
import Models.TipoLombada;
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
@WebServlet(name = "SalvarTipoLombada", urlPatterns = {"/SalvarTipoLombada"})
public class SalvarTipoLombada extends HttpServlet {

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
        int idTipoLombada = Integer.parseInt(request.getParameter("idTipoLombada"));
        String descricaoTipoLombada = request.getParameter("descricaoTipoLombada");
        String situacaoTipoLombada = request.getParameter("situacaoTipoLombada");        

        /*Manda pra classe*/
        TipoLombada cTipoLombada = new TipoLombada();
        cTipoLombada.setIdTipoLombada(idTipoLombada);
        cTipoLombada.setDescricaoTipoLombada(descricaoTipoLombada);
        cTipoLombada.setSituacaoTipoLombada(situacaoTipoLombada);        
        
        try {
            GenericDAO daoTipoLombada = new TipoLombadaDAO();

            if (cTipoLombada.getIdTipoLombada() == 0 || cTipoLombada.getIdTipoLombada() == null) {
                if (daoTipoLombada.Cadastrar(cTipoLombada)) {
                    mensagem = "TipoLombada cadastrado com sucesso!";
                } else {
                    mensagem = "Problemas ao cadastrar TipoLombada!";
                }
            } else if (daoTipoLombada.Alterar(cTipoLombada)) {
                mensagem = "TipoLombada alterado com sucesso!";
            } else {
                mensagem = "Problema ao alterar TipoLombada!";
            }
            request.setAttribute("Sucesso", mensagem);
            request.getRequestDispatcher("ListarTipoLombada").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("Sucesso", ex.getMessage());
            request.getRequestDispatcher("ListarTipoLombada").forward(request, response);
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
