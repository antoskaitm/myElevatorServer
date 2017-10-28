<%--
  Created by IntelliJ IDEA.
  User: Антон
  Date: 26.10.2017
  Time: 16:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    {
        Integer lastFloor = (Integer) request.getAttribute("lastFloor");
        Integer groundFloor = (Integer) request.getAttribute("groundFloor");
        for (int start = groundFloor; start <= lastFloor; start++) {
            out.println("<input type=\"submit\" value=\"" + start + "\" name=\"floor\">");
        }
    }
%>
