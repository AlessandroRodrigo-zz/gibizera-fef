/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controller.TipoCapa;

import DAO.GenericDAO;
import DAO.TipoCapaDAO;
import Models.TipoCapa;
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
@WebServlet(name = "SalvarTipoCapa", urlPatterns = {"/SalvarTipoCapa"})
public class SalvarTipoCapa extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        /*Pega da Tela*/
        String mensagem = null;
        int idTipoCapa = Integer.parseInt(request.getParameter("idTipoCapa"));
        String descricaoTipoCapa = request.getParameter("descricaoTipoCapa");
        String situacaoTipoCapa = request.getParameter("situacaoTipoCapa");        

        /*Manda pra classe*/
        TipoCapa cTipoCapa = new TipoCapa();
        cTipoCapa.setIdTipoCapa(idTipoCapa);
        cTipoCapa.setDescricaoTipoCapa(descricaoTipoCapa);
        cTipoCapa.setSituacaoTipoCapa(situacaoTipoCapa);        
        
        try {
            GenericDAO daoTipoCapa = new TipoCapaDAO();

            if (cTipoCapa.getIdTipoCapa() == 0 || cTipoCapa.getIdTipoCapa() == null) {
                if (daoTipoCapa.Cadastrar(cTipoCapa)) {
                    mensagem = "TipoCapa cadastrado com sucesso!";
                } else {
                    mensagem = "Problemas ao cadastrar TipoCapa!";
                }
            } else if (daoTipoCapa.Alterar(cTipoCapa)) {
                mensagem = "TipoCapa alterado com sucesso!";
            } else {
                mensagem = "Problema ao alterar TipoCapa!";
            }
            request.setAttribute("Sucesso", mensagem);
            request.getRequestDispatcher("ListarTipoCapa").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("Sucesso", ex.getMessage());
            request.getRequestDispatcher("ListarTipoCapa").forward(request, response);
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
