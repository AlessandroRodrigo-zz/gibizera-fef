/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controller.ArcoTeste;

import DAO.ArcoDAO;
import DAO.GenericDAO;
import Database.Database;
import Database.Database.TypeFields;
import Models.Arco;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Flavio Prado
 */
@WebServlet(name = "SalvarArcoTeste", urlPatterns = {"/SalvarArcoTeste"})
public class SalvarArcoTeste extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {        
        Database cDatabase = new Database();
        
        /*Pega da Tela*/
        String mensagem = null;
        Integer idArco = Integer.parseInt(request.getParameter("idArco"));
        String descricaoArco = request.getParameter("descricaoArco");
        
        try {
            //Se não retornou nada da tela Insere
            if (idArco == 0 || idArco == null){
                cDatabase.ClearFields();
                cDatabase.AddFields("idArco", Integer.toString(idArco), TypeFields.INTEGER);
                cDatabase.AddFields("Mensagem", mensagem, TypeFields.VARCHAR);
                cDatabase.AddFields("DescricaoArco", descricaoArco, TypeFields.VARCHAR);
            
                if (cDatabase.InsertDB("Arco")){ mensagem = "Arco cadastrado com sucesso!";}
                else{mensagem = "Problemas ao cadastrar Arco!";}            
            }else{
                cDatabase.ClearFields();
                cDatabase.AddFields("Mensagem", mensagem, TypeFields.VARCHAR);
                cDatabase.AddFields("DescricaoArco", descricaoArco, TypeFields.VARCHAR);
            
                if (cDatabase.UpdateDB("Arco", "Where idArco = " + idArco)){ mensagem = "Arco alterado com sucesso!";}
                else{mensagem = "Problemas ao alterar Arco!";}
            }
            
            request.setAttribute("Sucesso", mensagem);
            request.getRequestDispatcher("ListarArco").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("Sucesso", ex.getMessage());
            request.getRequestDispatcher("ListarArco").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SalvarArcoTeste.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SalvarArcoTeste.class.getName()).log(Level.SEVERE, null, ex);
        }
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
