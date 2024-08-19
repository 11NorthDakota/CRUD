<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <span> CONTENT русский</span>
    <p>Size : ${requestScope.flights.size()}</p>
    <p>Description: ${requestScope.flights[0].getDescription()}</p>
    <p>ID: ${requestScope.flights[0].getId()}</p>
    <p>JSESSIONID: ${cookie.get("JSESSIONID")}</p>
</div>
</body>
</html>
