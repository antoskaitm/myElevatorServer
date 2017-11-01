<%@ page import="main.emulator.panel.contract.PageInfo" %>
<%@ page import="main.emulator.panel.contract.ServerInfo" %>
<%@ page import="main.emulator.panel.contract.ElevatorInfo" %>
<%@ page import="main.entities.primitive.Elevator" %>

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
    ElevatorInfo[] elevators = ((PageInfo) request.getAttribute("condition")).getServerInfo().getElevators();
    for(ElevatorInfo info: elevators) {
        for (int start = info.getGroundFloor(); start <= info.getLastFloor(); start++) {
            out.println("<input type=\"submit\" value=\"" + start + "\" name=\"floor\">");
        }
    }
%>
