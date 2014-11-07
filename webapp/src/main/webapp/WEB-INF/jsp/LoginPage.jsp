<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css" />
        <title>Login</title>
    </head>
    <body>
        <div class="form">
            <form action="/" method="post">
                <input type="hidden" name="authorization" value="true">
                <div class="field">
                    <label for="login">Login</label>
                    <input id="login" type="text" name="login"/>
                </div>
                <div class="field">
                    <label for="password">Password</label>
                    <input id="password" type="password" name="password">
                </div>
                <div class="field">
                    <label for="remember">Remember me</label>
                    <input id="remember" type="checkbox" name="remember" value="true">
                </div>
                <div class="submit">
                    <input type="submit" value="Login">
                    <input type="reset" value="Reset">
                </div>
            </form>
        </div>
    </body>
</html>