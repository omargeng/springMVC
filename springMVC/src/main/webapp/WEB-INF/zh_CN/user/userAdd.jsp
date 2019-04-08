<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: omar
  Date: 2019/3/23
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="website.title"></fmt:message></title>
</head>
<body>
    <form:form commandName="user" action="springFormTest">
        <table>
            <tr>
                <td>用户名</td>
                <td><form:input path="userName"></form:input><form:errors path="userName"></form:errors></td>
            </tr>
            <tr>
                <td>用户id</td>
                <td><form:input path="userId"></form:input><form:errors path="userId"></form:errors></td>
            </tr>
            <tr>
                <td>密码</td>
                <td><form:password path="password"></form:password></td>
            </tr>
            <tr>
                <td>电话号码</td>
                <td><form:input path="phoneNo"></form:input><form:errors path="phoneNo"></form:errors></td>
            </tr>
            <tr>
                <td>性别</td>
                <td><form:radiobuttons path="sex" items="${sexMap}"></form:radiobuttons></td>
            </tr>
            <tr>
                <td>城市</td>
                <td><form:select path="city" items="${cityMap}"></form:select></td>
            </tr>
            <tr>
                <td>喜欢的城市</td>
                <td><form:checkboxes path="favoriteCity" items="${cityMap}"></form:checkboxes></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="提交"/> </td>
            </tr>
        </table>

    </form:form>
</body>
</html>
