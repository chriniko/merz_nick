<%-- 
    Document   : Writer
    Created on : 4 Φεβ 2015, 9:04:15 μμ
    Author     : Bill
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String username = null;
    username = (String) session.getAttribute("username");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Writer</title>
        <style>
            body {background-color:lightblue}
            box {
                border:1px solid black;
                padding:140px;
                margin:100px;
                background-color: appworkspace; 
            }
        </style>
    </head>
    
    <body>
    <center>
        <br>    
       <h3><div style="color: #000000;">${welcomeMessage}</div></h3>
        <h3>What would you like to do?</h3>
         <br><br><br><br><br><br><br> 
           <form action="UploadProcess" enctype="multipart/form-data" method="POST">
            
            <br>
            <box>Title:<input type="text" name="title"></box>
            <br><br>
            <input type="file" name="article" value="Search">
            <br><br>
            Article type:
                <select name="subject">
                  <option value="Programming">Programming</option>
                  <option value="Architecture">Architecture</option>
                  <option value="Networks">Networks</option>
                  <option value="Hardware">Hardware</option>
                </select> 
            <br><br>
            <input type="submit" value="Upload" > 
        </form>
        <br><br><br>
        
        <h3><div style="color: #000000;">${message}</div></h3> 
        
        <br><br><br>
        <form action="ReviewDoc.jsp">
            <input type="Submit" value="Review quota"> 
        </form>
    </center>
</body>
</html>
