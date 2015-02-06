<%-- 
    Document   : ManagePage
    Created on : 6 Φεβ 2015, 2:18:22 μμ
    Author     : Bill
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Articles</title>
    </head>
    <body>

    <center>
        <form action="" > 
            <select name="articles">
                <c:forEach var="article" items="${requestScope['articles']}">
                    <option value="<c:out value='${article}'/>">
                        <c:out value='${article}'/>
                    </option>
                </c:forEach>
            </select>
        </form>
    </center>    
</body>
</html>
