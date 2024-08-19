<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: artei
  Date: 18.08.2024
  Time: 12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/registration" method="post">
    <label for="name">Name:
        <input type="text" id="name" name="name">
    </label>
    <br/>
    <label for="birthday">birthday:
        <input type="date" id="birthday" name="birthday">
    </label>
    <br/>
    <label for="email">Email:
        <input type="email" id="email" name="email">
    </label>
    <br/>
    <label for="password">Password:
        <input type="password" id="password" name="password">
    </label>
    <br/>
    <c:forEach var="role" items="${requestScope.roles}">
        <option label="${role}">${role}</option><br/>
    </c:forEach>
    <br/>
    <c:forEach var="gender" items="${requestScope.genders}">
        <input type="radio" name="gender" VALUE="${gender}">${gender}
    </c:forEach>
    <br/>
    <input type="submit" value="send">
</form>
</body>
</html>
