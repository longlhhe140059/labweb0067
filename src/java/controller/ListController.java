/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DishesDAO;
import dao.DishesTypeDAO;
import dao.impl.DishesDAOImpl;
import dao.impl.DishesTypeDAOImpl;
import entity.Dishes;
import entity.DishesType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author stick
 */
public class ListController extends HttpServlet {

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
        try {
            System.out.println("in list controller");
            response.setContentType("text/html;charset=UTF-8");
            String searchName = request.getParameter("searchName");
            if(searchName == null){
                searchName="";
            }
            String typeStr = request.getParameter("typeDishes");
            int typeId = -1;
            if (typeStr != null && typeStr.length() > 0) {
                typeId = Integer.parseInt(typeStr);
            }
            String pageStr = request.getParameter("page");
            int curPage = 1;
            if (pageStr != null && pageStr.length() > 0) {
                curPage = Integer.parseInt(pageStr);
            }

            DishesDAO dao = new DishesDAOImpl();
            ArrayList<Dishes> dList = dao.searchDishes(5, curPage, searchName, typeId);
            int totalPage = dao.getTotalPage(5, searchName, typeId);
            DishesTypeDAO typeDAO = new DishesTypeDAOImpl();
            ArrayList<DishesType> tList = typeDAO.getAllType();

            request.setAttribute("dList", dList);
            request.setAttribute("tList", tList);
            request.setAttribute("curPage", curPage);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("searchName", searchName);
            request.setAttribute("typeID", typeId);
            
             request.getRequestDispatcher("dishesList.jsp").forward(request, response);
        } catch (Exception e) {
            Logger.getLogger(ListController.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("errorMes", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
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
