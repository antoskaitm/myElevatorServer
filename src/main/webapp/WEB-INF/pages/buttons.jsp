<%--
  Created by IntelliJ IDEA.
  User: Антон
  Date: 26.10.2017
  Time: 16:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach begin="${groundFloor}" end="${lastFloor}" step="1" var="numer">
    <input type="submit" value="${numer}" name="floor">
</c:forEach>
