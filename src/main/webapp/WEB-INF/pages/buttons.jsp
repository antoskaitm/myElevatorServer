<%@ page import="main.servlets.ServerCondition" %><%--
  Created by IntelliJ IDEA.
  User: Антон
  Date: 26.10.2017
  Time: 16:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
        ServerCondition condition = (ServerCondition)request.getAttribute("condition");
        for (int start = condition.getGroundFloor(); start <= condition.getLastFloor(); start++) {
            out.println("<input type=\"submit\" value=\"" + start + "\" name=\"floor\">");
        }
%>
