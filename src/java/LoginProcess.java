import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Bill
 */
public class LoginProcess extends HttpServlet {

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
        String username = request.getParameter("Username");
        String password = request.getParameter("Password");
        boolean authenticated = false;
        String priviledge = null;
        LoginBean logb = new LoginBean();

        logb.setUsername(username);
        logb.setPassword(password);
        logb.authentication();
        authenticated = logb.getAuthenticated();
        priviledge= logb.getPriviledge();

        if (authenticated == true) {
            if (priviledge.equals("administrator")) 
            {
              HttpSession session=request.getSession();
              session.setAttribute("username", username);
              request.setAttribute("welcomeMessage", "Welcome "+username+".");
              request.getRequestDispatcher("/Administrator.jsp").forward(request, response);
            } 
            else if (priviledge.equals("writer")) 
            {
               HttpSession session=request.getSession();
               session.setAttribute("username", username);
               request.setAttribute("welcomeMessage", "Welcome "+username+".");
               request.getRequestDispatcher("/Writer.jsp").forward(request, response);
            } 
            else if (priviledge.equals("user")) 
            {
               request.setAttribute("welcomeMessage", "Welcome "+username+".");
               request.getRequestDispatcher("/User.jsp").forward(request, response);
            }
        } 
        else 
        {
            request.setAttribute("authenticationError", "Error!Wrong username or password.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
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
