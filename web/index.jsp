<%-- 
    Document   : index
    Created on : 4 Φεβ 2015, 7:16:02 μμ
    Author     : Bill
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Online library</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            body {background-color:lightblue}
        </style>
    </head>
    <body>
    <center>
        <form action="LoginProcess" method="Post">
            <br><br><br><br><br><br>
            <div style="color: #FF0000;">${authenticationError}</div>
            Username:<input type="text" name="Username" value="">
            <br>
            Password:<input type="password" name="Password" value="">
            <br><br>
            <input type="submit" value="Login">
        </form> 
        <h4>Not a member yet?Click here to <a href="SignUp.jsp">sign up</a></h4>
    </center> 
</body>
</html>
