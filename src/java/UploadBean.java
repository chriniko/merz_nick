/**
 *
 * @author Bill
 */
import java.io.InputStream;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UploadBean {
    private String subject;
    private InputStream article;
    private String username;
    private String title;
    
    
    public UploadBean(){
      subject=null;
      article=null;
      username=null;
      title=null;
    }
    
    public void setSubject(String subject){
      this.subject=subject; 
    }
    public void setArticle(InputStream article){
      this.article=article;
    }
    public void setUsername(String username){
       this.username=username;
    }
    public void setTitle(String title)
    {
      this.title=title;
    }
    
    public void uploadArticle(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
             System.out.println("Where is your MySQL JDBC Driver?");
             e.printStackTrace();
             return;
        }
        try {
            Connection upconn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library", "root", "nikos");
            String insert="INSERT INTO articles (Title,article,category,writer) VALUES (?,?,?,?)";
            PreparedStatement insert_st=upconn.prepareStatement(insert);
            insert_st.setString(1,title);
            insert_st.setBlob(2, article);
            insert_st.setString(3, subject);
            insert_st.setString(4, username);
            insert_st.executeUpdate();
            insert_st.close();
            upconn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(UploadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
      
    }
}
