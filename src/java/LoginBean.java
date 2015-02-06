/**
 *
 * @author Bill
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginBean {
    private String username;
    private String password;
    private boolean authenticated;
    private String priviledge;
    
    public LoginBean()
    {
      username=null;
      password=null;
      authenticated=false;
    }
    
    public void setUsername(String username)
    {
      this.username=username;
    }
    
    public void setPassword(String password)
    {
      this.password=password;
    }
    
    public void authentication()
    {
       try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
             System.out.println("Where is your MySQL JDBC Driver?");
             e.printStackTrace();
             return;
        }
        
        try {
            boolean uok=false,pok=false;
            Connection logconn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library", "root", "nikos");
            Statement sel;
            sel=logconn.createStatement();
            String LoginQuery="SELECT username,password,priviledges FROM users WHERE username="+"'"+username+"'"+" "+"AND password="+"'"+password+"'";
            ResultSet rs=sel.executeQuery(LoginQuery);
            while(rs.next())
            {
              if(rs.getString("username").equals(username))
                uok=true;
              if(rs.getString("password").equals(password))
                pok=true;
              this.priviledge = rs.getString("priviledges");              
            }
            
            if(uok==true && pok==true)
               authenticated=true;
            else 
               authenticated=false;
        
            
        } catch (SQLException ex) {
            Logger.getLogger(LoginProcess.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    
    }
    
    public boolean getAuthenticated()
    {
      return authenticated;
    }
    
    public String getPriviledge()
    {
      return priviledge;
    }

   
    
}
