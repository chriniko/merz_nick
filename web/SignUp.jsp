<%-- 
    Document   : SignUp
    Created on : 24 Δεκ 2014, 8:26:42 μμ
    Author     : Bill
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign up</title>
        <style>
            body {background-color:lightblue}
        </style>
    </head>
    
    <body>
     <br><br><br>      
    <center>
        <form action="Registration" method="post">
            <div style="color: #FF0000;">${errorMessage}</div>
            Username:<input type="text" name="user" value="">
            <br><br>
            Password:<input type="password" name="pass" value="">
            <br><br>
            Email:   <input type="text" name="mail" value="">
            <br><br>
            <input type="submit" value="Sign up">
            
        </form>
    </center>
</body>
</html>
