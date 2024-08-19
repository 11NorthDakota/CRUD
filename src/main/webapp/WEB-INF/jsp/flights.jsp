<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Список перелетов:</h1>
    <ul>
        <c:if test="${not empty requestScope.flights}">
            <c:forEach var="flight" items="${requestScope.flights}">
                <li>
                    <a href='/tickets?flightId=%${flight.getId()}'>
                            ${flight.getDescription()}
                    </a>
                </li>
            </c:forEach>
        </c:if>
    </ul>
</body>
</html>
