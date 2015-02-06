
/**
 *
 * @author Bill
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageBean {

    private ArrayList<String> articles;
    private int length;

    public ManageBean() {
        articles = new ArrayList();
        length = 0;
    }

    public void selectArticles() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library", "root", "nikos");
        } catch (SQLException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            String sel = "SELECT Title FROM articles";
            Statement select;
            select = conn.createStatement();
            ResultSet rs = select.executeQuery(sel);
            int i = 0;
            while (rs.next()) {
                articles.add(rs.getString("Title"));
                System.out.println(i + " " + rs.getString("Title"));
                i++;
            }
            rs.close();
            select.close();
            length = articles.size();
        } catch (SQLException ex) {
            Logger.getLogger(ManageBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Object getArticles(int index) {
        return articles.get(index);
    }

    public int getLength() {
        return length;
    }
}
