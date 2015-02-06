/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bill
 */
public class Verify extends HttpServlet {

    private enum upriviledges {

        user, writer, admin
    };

    private enum uacc_status {

        active, inactive
    };

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

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        Connection activate = null;
        try (PrintWriter out = response.getWriter()) {
            try {
                activate = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library", "root", "nikos");

            } catch (SQLException ex) {
                Logger.getLogger(Verify.class.getName()).log(Level.SEVERE, null, ex);
            }

            String usermail = request.getParameter("param1");
            upriviledges user_priviledge = upriviledges.writer;
            uacc_status status = uacc_status.active;
            String statusString = status.toString();
            String privString = user_priviledge.toString();
            String dbname="";
            int i=0;
            try {
                ResultSet rs = activate.getMetaData().getSchemas();
                while(rs.next()) {
                    dbname=rs.getString(i);
                    i++;
                }
                String updatequery = "UPDATE " + dbname + ".users SET priviledges=?,account_status=?  WHERE email=?";
                PreparedStatement update_st = null;
                update_st = activate.prepareStatement(updatequery);
                update_st.setString(1, privString);
                update_st.setString(2, statusString);
                update_st.setString(3, usermail);
                update_st.executeUpdate();
                update_st.close();
                activate.close();

            } catch (SQLException ex) {
                Logger.getLogger(Verify.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
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
