/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controller.Licenciadora;

import br.com.controller.Licenciadora.*;
import DAO.LicenciadoraDAO;
import DAO.GenericDAO;
import Models.Licenciadora;
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
@WebServlet(name = "SalvarLicenciadora", urlPatterns = {"/SalvarLicenciadora"})
public class SalvarLicenciadora extends HttpServlet {

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
        int idLicenciadora = Integer.parseInt(request.getParameter("idLicenciadora"));
        String descricaoLicenciadora = request.getParameter("descricaoLicenciadora");
        String situacaoLicenciadora = request.getParameter("situacaoLicenciadora");        

        /*Manda pra classe*/
        Licenciadora cLicenciadora = new Licenciadora();
        cLicenciadora.setIdLicenciadora(idLicenciadora);
        cLicenciadora.setDescricaoLicenciadora(descricaoLicenciadora);
        cLicenciadora.setSituacaoLicenciadora(situacaoLicenciadora);        
        
        try {
            GenericDAO daoLicenciadora = new LicenciadoraDAO();

            if (cLicenciadora.getIdLicenciadora() == 0 || cLicenciadora.getIdLicenciadora() == null) {
                if (daoLicenciadora.Cadastrar(cLicenciadora)) {
                    mensagem = "Licenciadora cadastrado com sucesso!";
                } else {
                    mensagem = "Problemas ao cadastrar Licenciadora!";
                }
            } else if (daoLicenciadora.Alterar(cLicenciadora)) {
                mensagem = "Licenciadora alterado com sucesso!";
            } else {
                mensagem = "Problema ao alterar Licenciadora!";
            }
            request.setAttribute("Sucesso", mensagem);
            request.getRequestDispatcher("ListarLicenciadora").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("Sucesso", ex.getMessage());
            request.getRequestDispatcher("ListarLicenciadora").forward(request, response);
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
