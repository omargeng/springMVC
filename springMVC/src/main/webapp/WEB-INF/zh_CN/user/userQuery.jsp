<%--
  Created by IntelliJ IDEA.
  User: omar09
  Date: 2017/6/13
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>用户注册</title>
</head>
<body>
    <form method="post" action="<c:url value="/user/userQuery.do"/>">
        <table>
            <tr>
                <td>用户名:</td>
                <td><input type="text" name="userId"></td>
            </tr>
        </table>
    </form>
</body>
</html>
