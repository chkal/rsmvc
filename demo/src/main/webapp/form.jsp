<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Test</title>
</head>
<body>

<form action="${pageContext.servletContext.contextPath}/resources/form" method="post">

    <c:if test="${msg != null}">
        <ul>
            <li>${msg}</li>
        </ul>
    </c:if>

    <p>
        <label for="name">Name: </label>
        <input id="name" name="name" value="${form.name}" placeholder="Your name" autofocus />
    </p>

    <p>
        <label for="gender">Gender: </label>
        <select id="gender" name="gender">
            <option value="UNKNOWN" ${ form.gender == 'UNKNOWN' ? 'selected' : '' }>- select -</option>
            <option value="MALE" ${ form.gender == 'MALE' ? 'selected' : '' }>Male</option>
            <option value="FEMALE" ${ form.gender == 'FEMALE' ? 'selected' : '' }>Female</option>
        </select>
    </p>

    <p>
        <label for="casual">Casual greeting: </label>
        <input id="casual" name="casual" type="checkbox" ${ form.casual != null ? 'checked' : '' } />
    </p>

    <p>
        <input type="submit" value="Send" />
    </p>

</form>

</body>
</html>
