<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="sinensia.modelo.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Vista JSTL Operaciones CRUD</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Vista JSTL Operaciones CRUD</h1>
        <ul>
            <li> <a href="register.jsp">Register</a> </li>
            <li> <a href="users.do">Users list</a> </li>
            <li> <a href="restClient.html">Rest Client with AJAX</a> </li>
            <li> <a href="restClient_jq.html">Rest Client with jQuery AJAX</a> </li>
        </ul>
        <form id="formLogin" action="users.do" method="get">

            Email: <input id="email" name="email" 
                       type="email" size="30" 
                       maxlength="255"
                       value="${cookie.email.value}"/><br/>
            Password: <input id="password" name="password" 
                       type="password" size="15" 
                       maxlength="50"
                       value=""/><br/>
            <input name="method" type="submit" value="Login"/>         
        </form>
        <script src='js/md5.min.js'></script>
        <script src='js/encrypt.js'></script>
    </body>
</html>
