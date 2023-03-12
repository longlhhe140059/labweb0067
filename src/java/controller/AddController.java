/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DishesDAO;
import dao.DishesTypeDAO;
import dao.StatusDAO;
import dao.impl.DishesDAOImpl;
import dao.impl.DishesTypeDAOImpl;
import dao.impl.StatusDAOImpl;
import entity.Dishes;
import entity.DishesType;
import entity.Status;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author stick
 */
@MultipartConfig()
public class AddController extends HttpServlet {

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
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        String modalHtmlComponent = "";
        try {
            modalHtmlComponent += "<div class=\"w-25 detailOrder overflow-auto\">\n"
                    + "                <div class=\"detailHeader\">\n"
                    + "                    <div class=\"header_left\">\n"
                    + "                        <h6>Thêm Món ăn</h6>\n"
                    + "                        <div id=\"runState\" style=\"visibility: hidden;display: inline;\">server controller error</div>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"header_right\">\n"
                    + "                        <button type=\"button\" onclick=\"offDetail1()\"><i class=\"bi bi-x\"></i></button>\n"
                    + "                    </div>\n"
                    + "                    <hr class=\"my-3\">\n"
                    + "                </div>\n"
                    + "\n"
                    + "                <div class=\"detailBody\">\n"
                    + "<form method=\"post\" action=\"AddController\" id=\"formId\" enctype=\"multipart/form-data\">"
                    + "                    <div class=\"choose-image-update\">\n"
                    + "                        <div class=\"component-image-update\">\n"
                    + "                            <input id=\"files-add\" name=\"imageService\" class=\"choose-file-update\" type=\"file\" onchange=\"chooseImageModalUpdate('files-add')\"/>\n"
                    + "                            <label for=\"files-add\">\n"
                    + "                                <img src=\"\" width=\"370\" alt=\"\" id=\"serviceImg\" style=\"\" class=\"image-add-update\"/>\n"
                    + "                                Chọn hoặc đổi ảnh\n"
                    + "                            </label>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"ml-1 mr-1 error\" id=\"errorImg-add\"></div>\n"
                    + "\n"
                    + "                    <div class=\"row ml-1 mr-1 mb-1\">\n"
                    + "                        <label for=\"nameDishes-add\">\n"
                    + "                            <strong>Tên món ăn</strong>\n"
                    + "                        </label>\n"
                    + "                        <input id=\"nameDishes-add\" class=\"form-control\" type=\"text\" placeholder=\"Nhập tên món ăn\" onfocus=\"remove_errorSearch('errorName-add')\"/>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"ml-1 mr-1 error\" id=\"errorName-add\"></div>\n"
                    + "\n"
                    + "                    <div class=\"row ml-1 mr-1 mb-1\">\n"
                    + "                        <label for=\"typeDishes-add\">\n"
                    + "                            <strong>Loại hình</strong>\n"
                    + "                        </label>\n"
                    + "                        <select id=\"typeDishes-add\" class=\"form-control input-field\" type=\"text\" >\n";
            DishesTypeDAO typeDAO = new DishesTypeDAOImpl();
            ArrayList<DishesType> tList = typeDAO.getAllType();
            for (DishesType dishesType : tList) {
                modalHtmlComponent += "<option value=\"" + dishesType.getId() + "\">" + dishesType.getName() + "</option>\n";
            }
            modalHtmlComponent += "</select>\n"
                    + "</div>\n"
                    + "\n"
                    + "                    <div class=\"row ml-1 mr-1 mb-1\">\n"
                    + "                        <label for=\"statusDishes-add\">\n"
                    + "                            <strong>Trạng thái</strong>\n"
                    + "                        </label>\n"
                    + "                        <select id=\"statusDishes-add\" class=\"form-control input-field\" type=\"text\" >\n";
            StatusDAO sDAO = new StatusDAOImpl();
            ArrayList<Status> sList = sDAO.getAllStatus();
            for (Status status : sList) {
                modalHtmlComponent += "<option value=\"" + status.getId() + "\">" + status.getName() + "</option>\n";
            }
            modalHtmlComponent += "</select>\n"
                    + "                    </div>\n"
                    + "\n"
                    + "                    <div class=\"row ml-1 mr-1 mb-1\">\n"
                    + "                        <label for=\"priceDishes-add\">\n"
                    + "                            <strong>Nhập giá món ăn (VND)</strong>\n"
                    + "                        </label>\n"
                    + "                        <input id=\"priceDishes-add\" class=\"form-control\" type=\"number\" placeholder=\"Nhập giá món ăn\" onfocus=\"remove_errorSearch('errorPrice-add')\"/>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"ml-1 mr-1 error\" id=\"errorPrice-add\"></div>\n"
                    + "\n"
                    + "                    <div class=\"row ml-1 mr-1 mb-1\">\n"
                    + "                        <label for=\"note-add\">\n"
                    + "                            <strong>Mô tả</strong>\n"
                    + "                        </label>\n"
                    + "                        <textarea class=\"form-control\" id=\"note-add\" rows=\"3\" placeholder=\"Mô tả dịch vụ\"onfocus=\"remove_errorSearch('errorNote-add')\"></textarea>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"ml-1 mr-1 error\" id=\"errorNote-add\"></div>\n"
                    + "                    <button id=\"buttonSave\" type=\"button\" class=\"btn btn-success ml-1\" style=\"width: 100%;margin-top: 10px;\" onclick=\"SubForm()\">Lưu</button>\n"
                    + "<input type=\"submit\" value=\"ok?\"/>"
                    + "</form>"
                    + "                </div>\n"
                    + "                <div class=\"detailFooter\">\n"
                    + "                    <hr class=\"my-3\">\n"
                    + "                    <button type=\"button\" class=\"btn btn-secondary ml-1\" onclick=\"offDetail()\">Quay lại</button>\n"
                    + "                </div>\n"
                    + "            </div>";
        } catch (Exception e) {
            request.setAttribute("errorMes", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        out.print(modalHtmlComponent);
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
        response.setContentType("text/html; charset=UTF-8");
        System.out.println("in post");
        PrintWriter out = response.getWriter();
        try {
            String name = request.getParameter("name");
            System.out.println("name: "+name+"----------------------------------");
            
            Part part = request.getPart("file");
            String realPart = request.getServletContext().getRealPath("/img");
            System.out.println("real: "+realPart);
//            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            String fileName = part.getSubmittedFileName().toString();
            System.out.println("filenaem: "+fileName);
            if (!Files.exists(Paths.get(realPart))) {
                Files.createDirectories(Paths.get(realPart));
            }
            part.write(realPart + "/" + fileName);
//            String typeStr = request.getParameter("typeId");
//            int typeId = Integer.parseInt(typeStr);
//            String statusStr = request.getParameter("statusId");
//            int statusId = Integer.parseInt(statusStr);
//            String priceStr = request.getParameter("price");
//            int price = Integer.parseInt(priceStr);
//            String note = request.getParameter("note");
//
//            Dishes d = new Dishes();
////            d.setImage(img);
//            d.setName(name);
//            d.setNote(note);
//            d.setPrice(price);
//            Status s = new Status();
//            s.setId(statusId);
//            d.setStatus(s);
//            DishesType dt = new DishesType();
//            dt.setId(typeId);
//            d.setType(dt);
//
//            DishesDAO dDAO = new DishesDAOImpl();
//            dDAO.addDishes(d);
            out.print("<Strong id\"Success\">Thêm thanh công</Strong>");
        } catch (Exception e) {
            Logger.getLogger(AddController.class.getName()).log(Level.SEVERE, null, e);
            out.print("<Strong>error: lỗi server</Strong>");
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
