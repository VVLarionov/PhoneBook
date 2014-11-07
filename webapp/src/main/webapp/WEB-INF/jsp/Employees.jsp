<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page errorPage="/error"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <%@include file="headcontent.jsp"%>
        <title>Employees</title>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
        <style>
            #resizable { width: 150px; height: 150px; padding: 0.5em; }
            #resizable h3 { text-align: center; margin: 0; }
            .ui-resizable-helper { border: 1px dotted gray; }
        </style>
        <script>
            $(function() {
                $( "#resizable" ).resizable({
                    animate: true
                });
            });
        </script>
    </head>
    <body>
        <%@include file="header.jsp"%>
        <div class="center">
            <table id="resizable" class="ui-widget-content" border="1px" width="100px" >
                <thead>
                <tr>
                    <td>First name</td>
                    <td>Last name</td>
                    <td>Project</td>
                    <td>Department</td>
                    <td>Phones</td>
                    <td>Email</td>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${employees}" var="employee">
                        <tr>
                            <td><c:out value="${employee.firstName}"/></td>
                            <td><c:out value="${employee.lastName}"/></td>
                            <td><c:out value="${employee.project}"/></td>
                            <td>
                                <c:if test="${employee.department != null}">
                                    <c:out value="${employee.department.name}"/>
                                </c:if>
                            </td>
                            <td>
                                <c:forEach items="${employee.phoneList}" var="phone">
                                    <c:out value="${phone.countryCode} ${phone.cityCode} ${phone.phoneNumber}"/>
                                    <form action="/employees/do_edit" method="post">
                                        <input type="hidden" name="action" value="deletePhone">
                                        <input type="hidden" name="phoneId" value="${phone.id}">
                                        <input type="hidden" name="employeeId" value="${employee.id}">
                                        <input type="image" src="${pageContext.request.contextPath}/images/delete.png" height="16px" width="16px" alt="Submit Form">
                                    </form>
                                    <p>
                                </c:forEach>
                            </td>
                            <td><c:out value="${employee.email}"/></td>
                            <td>
                                <form action="/employees/edit" method="get">
                                    <input type="hidden" name="employeeId" value="${employee.id}">
                                    <input type="submit" value="Edit">
                                </form>
                            </td>
                            <td>
                                <form action="/employees/do_edit" method="post">
                                    <input type="hidden" name="employeeId" value="${employee.id}">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="submit" value="Delete">
                                </form>
                            </td>
                            <td>
                                <form action="/employees/add_phone" method="get">
                                    <input type="hidden" name="employeeId" value="${employee.id}">
                                    <input type="submit" value="Add phone">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <p>
                <input type="button" value="Add" onClick="document.location='/employees/edit'">
            </p>
            <form method="POST" action="/employees/load" enctype="multipart/form-data" >
                <input type="submit" value="Upload" name="ignored"/>
                <input type="file" name="file" id="file" />
            </form>
        </div>
        <%@include file="footer.jsp"%>
    </body>
</html>
