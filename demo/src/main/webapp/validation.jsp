<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Test</title>
</head>
<body>

<form action="${pageContext.servletContext.contextPath}/resources/validation" method="post">

    <c:if test="${ not empty errors}">

        <ul style="color: red;">
            <c:forEach var="error" items="${errors}">
                <li>${error.message}</li>
            </c:forEach>
        </ul>
    </c:if>

    <c:if test="${msg != null}">
        <strong>${msg}</strong>
    </c:if>

    <p>
        <label for="name">Name: </label>
        <input id="name" name="name" value="${form.name}" placeholder="Your name" autofocus />
    </p>

    <p>
        <input type="submit" value="Send" />
    </p>

</form>

</body>
</html>
