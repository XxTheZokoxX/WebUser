<%-- 
    Document   : listUsers
    Created on : 01-oct-2019, 16:47:47
    Author     : alumno
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="sinensia.modelo.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="usersList" 
             type="java.util.ArrayList<User>" scope="request">
    <jsp:getProperty property="*" name="usersList"></jsp:getProperty>
</jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users list</title>
    </head>
    <body>
        <h1>Users list</h1>
        <%-- ${} corresponde a EL (Expression Language) de JSTL --%>
        <c:if test="${sessionScope.userLogged != null}">

            <c:forEach items="${usersList}" var="user">
                <form action="users.do" method="post">
                    <table>
                        <tr>
                            <td><input id="id" name="id" 
                                       readonly type="number" size="4" 
                                       value="${user.id}"/></td>
                            <td><input id="email" name="email" 
                                       type="email" size="30" 
                                       maxlength="255"
                                       value="${user.email}"/></td>
                            <td><input id="password" name="password" 
                                       type="password" size="15" 
                                       maxlength="50"
                                       value="${user.password}"/></td>
                            <td><input id="name" name="name" 
                                       type="text" size="15" 
                                       maxlength="50"
                                       value="${user.name}"/></td>
                            <td><input id="age" name="age" 
                                       type="number" size="5" 
                                       maxlength="3" min="1" max="199"
                                       value="${user.age}"/></td>
                            <td><input name="method" type="submit" value="Update"/></td>
                            <td><input name="method" type="submit" value="Delete"/></td>
                        </tr>
                    </table>
                </form>
            </c:forEach>

        </c:if>
 <%--   <c:if test="${sessionScope.userLogged == null}"> --%>
        <% if (session.getAttribute("userLogged") == null) { %>
            You didn't logged.
            <a href="index.jsp">Home</a>
        <% } %>
 <%--   </c:if> --%>
    </body>
</html>
