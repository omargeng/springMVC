<%--
  Created by IntelliJ IDEA.
  User: omar09
  Date: 2017/6/13
  Time: 13:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="." uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>用户查询结果</title>
</head>
<body>
    <form:form modelAttribute="user">
        <form:errors path="userId"></form:errors>
        <form:input path="userId"></form:input>
    </form:form>
</body>
</html>
