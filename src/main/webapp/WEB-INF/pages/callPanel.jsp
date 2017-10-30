<%--
  Created by IntelliJ IDEA.
  User: Антон
  Date: 26.10.2017
  Time: 16:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% try { %>
<html>
<body>
<div>
    <%@ include file="/WEB-INF/pages/display.jsp" %>
</div>
<div>
    <h1>call up automate on floor</h1>
    <form action="call" method="post">
        <%@ include file="/WEB-INF/pages/buttons.jsp" %>
    </form>
</div>
</body>
</html>
<% } catch (Throwable ex) {
	System.out.println(ex.getMessage());
    ex.printStackTrace();
    out.println("Error.Sorry!!");
} %>

