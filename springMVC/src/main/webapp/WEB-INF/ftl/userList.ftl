<#import "spring.ftl" as spring></import>
<html>
    <head>
        <title><@spring.message "user.userList.title"></@spring.message></title>
    </head>
    <body>
        <table border="1" cellspacing="0" cellpadding="0">
            <thead>
                <tr>
                    <td>用户id</td>
                    <td>用户名</td>
                    <td>手机号</td>
                </tr>
            </thead>
            <#list userList as user>
                <tr>
                    <td><a href="<@spring.url '/user/${user.userId}'></@spring.url>">${user.userId}</a></td>
                    <td>${user.userName}</td>
                    <td>${user.phoneNo}</td>
                </tr>
            </#list>
        </table>
    </body>
</html>