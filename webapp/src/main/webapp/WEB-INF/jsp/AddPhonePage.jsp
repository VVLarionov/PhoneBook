<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="/error"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="headcontent.jsp"%>
    <title>Add phone</title>
</head>
<body>
    <%@include file="header.jsp"%>
    <div class="center">
        <form action="do_edit" method="post">
            <input type="hidden" name="employeeId" value="${param['employeeId']}">
            <input type="hidden" name="action" value="addPhone">
            <p>Country code:<input type="text" name="countryCode" required="true">
            <p>City code:<input type="text" name="cityCode" required="true">
            <p>Phone number:<input type="text" name="phoneNumber" required="true">
            <p>Type:
                <select name="isMobile">
                    <option value="true">Mobile</option>
                    <option value="false">Home</option>
                </select>
            <p><input type="submit" name="Add">
        </form>
    </div>
    <%@include file="footer.jsp"%>
</body>
</html>

