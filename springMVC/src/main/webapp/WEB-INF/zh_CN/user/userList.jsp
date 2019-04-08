<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: omar
  Date: 2019/3/23
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="website.title"></fmt:message></title>
</head>
<body>
    <fmt:message key="user.userList.title"></fmt:message>
    <table  border="1" cellpadding="0" cellspacing="0">
        <c:forEach items="${userList}" var="user">
            <tr>
                <td>${user.userId}</td>
                <td>${user.userName}</td>
                <td>${user.phoneNo}</td>
                <td><fmt:formatDate value="${user.birthDay}" pattern="yyyy-mm-dd"></fmt:formatDate></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
