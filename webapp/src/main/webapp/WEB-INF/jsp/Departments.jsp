<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page errorPage="/error"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
    <head>
        <%@include file="headcontent.jsp"%>
        <title>Departments</title>
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
            <table id="resizable" class="ui-widget-content" border="1px" width="100px">
                <thead>
                <tr>
                    <td>Department name</td>
                    <td>Manager</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${departments}" var="department">
                    <tr>
                        <td><c:out value="${department.name}"/></td>
                        <td><c:out value="${department.manager.firstName} ${department.manager.lastName}"/></td>
                        <td>
                            <form action="departments/edit" method="post">
                                <input type="hidden" name="departmentId" value="${department.id}">
                                <input type="submit" value="Edit">
                            </form>
                        </td>
                        <td>
                            <form action="departments/do_edit" method="post">
                                <input type="hidden" name="departmentId" value="${department.id}">
                                <input type="hidden" name="action" value="delete">
                                <input type="submit" value="Delete">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <input type="button" value="Add" onClick="document.location='departments/edit'">
        </div>
        <%@include file="footer.jsp"%>
    </body>
</html>
