<%--
  Created by IntelliJ IDEA.
  User: Антон
  Date: 26.10.2017
  Time: 16:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <h2>${errorMessage}</h2>
</div>
<div>
    <h1>current floor</h1>
    <h2>${currentFloor}</h2>
    <h2>person condition ${personCondition}</h2>
    <h2>person id ${id}</h2>
    <form action="/getCurrentFloor" method="post">
        <input type="hidden" name="page" value="${page}">
        <input type="submit" value="currentFloor">
    </form>
</div>