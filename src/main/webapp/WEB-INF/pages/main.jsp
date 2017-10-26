<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <body>
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
        <div>
            <h1>call up elevator on floor</h1>
            <form action="/callup" method="post">
                <c:forEach begin="${groundFloor}" end="${lastFloor}" step="1" var="numer">
                    <input type="submit" value="${numer}" name="floor">
                </c:forEach>
            </form>
        </div>

        <div>
            <h1>send elevator on floor</h1>
            <form action="/send" method="post">
                <c:forEach begin="${groundFloor}" end="${lastFloor}" step="1" var="numer">
                    <input type="submit" value="${numer}" name="floor">
                </c:forEach>
            </form>
        </div>
    </body>
</html>
