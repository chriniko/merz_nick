import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Authenticator;
import javax.servlet.ServletContext;

/**
 * j
 *
 * @author Bill
 */
public class Registration extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private enum priviledges {

        user, writer, admin
    };

    private enum acc_status {

        active, inactive
    };

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username, password, email;

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("Where is your MySQL JDBC Driver?");
                e.printStackTrace();
                return;
            }

            Connection conn = null;
            try {
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library", "root", "nikos");
            } catch (SQLException ex) {
                Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            }

            username = request.getParameter("user");
            password = request.getParameter("pass");
            email = request.getParameter("mail");

            if (username.equals("") || username.equals(null) || password.equals("") || password.equals(null) || email.equals("") || email.equals(null)) {
                request.setAttribute("errorMessage", "Error,one or more empty fields detected.");
                request.getRequestDispatcher("/SignUp.jsp").forward(request, response);
                return;
            }

            Boolean mail_exists = false, username_exists = false;
            try {
                String checkquery;
                checkquery = "SELECT username,email FROM users WHERE username=" + "'" + username + "'" + " " + "OR email=" + "'" + email + "'";
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library", "root", "");
                Statement select;
                select = conn.createStatement();
                ResultSet exists = select.executeQuery(checkquery);
                String user;
                String mail;
                while (exists.next()) {
                    user = exists.getString("username");
                    mail = exists.getString("email");

                    if (user.equals(username)) {
                        username_exists = true;
                    }
                    if (mail.equals(email)) {
                        mail_exists = true;
                    }

                }
                select.close();
            } catch (SQLException ex) {
                Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (mail_exists == true && username_exists == true) {
                request.setAttribute("errorMessage", "Please choose a different username and email address.");
                request.getRequestDispatcher("/SignUp.jsp").forward(request, response);
                return;
            }

            if (mail_exists == true) {
                request.setAttribute("errorMessage", "Please choose a different email address.");
                request.getRequestDispatcher("/SignUp.jsp").forward(request, response);
                return;
            }
            if (username_exists == true) {
                request.setAttribute("errorMessage", "Please choose a different username.");
                request.getRequestDispatcher("/SignUp.jsp").forward(request, response);
                return;
            }

            String insert = "INSERT INTO users (username,password,email,priviledges,account_status) VALUES (?,?,?,?,?)";

            priviledges user_priviledge = priviledges.user;
            acc_status status = acc_status.inactive;

            String statusString = status.toString();
            String privString = user_priviledge.toString();
            try {
                try (PreparedStatement insert_st = conn.prepareStatement(insert)) {
                    insert_st.setString(1, username);
                    insert_st.setString(2, password);
                    insert_st.setString(3, email);
                    insert_st.setString(4, privString);
                    insert_st.setString(5, statusString);
                    insert_st.executeUpdate();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            }

            URL verification = new URL("http", "localhost", 8080, "/TheLibrary/Verify");

            try {

                String to = email;
                String from = "progrjob63@gmail.com";
                final String musername = "progrjob63";
                final String mpassword = "19634298vegu5";
                String host = "smtp.gmail.com";

                Properties pr = null;
                if (pr == null) {
                    pr = System.getProperties();
                }

                pr.put("mail.smtp.auth", "true");
                pr.put("mail.smtp.starttls.enable", "true");
                pr.put("mail.smtp.port", "587");
                pr.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                pr.setProperty("mail.smtp.host", host);

                Session session = Session.getInstance(pr,
                        new javax.mail.Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(musername, mpassword);
                            }
                        });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

                message.setSubject("Please verify registration to the online library.");

                message.setContent("You have made an attempt to register to our library.\n"
                        + "If this is not your doing, ignore and delete this message.\n"
                        + "Otherwise please click on the link to authenticate your registration\n\n"
                        + "<a href=" + verification.toString() + "?param1=" + to + ">Complete your registration</a></h4>", "text/html");

                Transport.send(message, message.getAllRecipients());

            } catch (MessagingException ex) {
                Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            }

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Verify</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<br><br><br><br><br><br>");
            out.println("<h4>In order to fully activate your account you must click on the link sent to you in your email.</h4>");
            out.println("<h4><a href=index.jsp>Return to Log in.</a>" + "</h4>");
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");

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
