<%@ page import="by.northdakota.Service.TicketService" %>
<%@ page import="by.northdakota.Dto.TicketDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
    <title>Title</title>
  </head>
  <body>
  <h1>Купленные билеты:</h1>
  <ul>
    <c:forEach var="ticket" items="${requestScope.tickets}">
      <li>${ticket.getSeatNo()}</li>
    </c:forEach>

  </ul>
  </body>
</html>
