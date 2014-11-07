<%@ page import="com.getjavajobs.larionov.phonebook.service.EmployeeService" %>
<%@ page import="com.getjavajobs.larionov.phonebook.service.DepartmentService" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="/error"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <%@include file="headcontent.jsp"%>
        <title>
            <c:choose>
                <c:when test="${param['departmentId'] != null}">
                    <c:out value="Edit department"/>
                </c:when>
                <c:otherwise>
                    <c:out value="Add department"/>
                </c:otherwise>
            </c:choose>
        </title>
    </head>
    <body>
    <%@include file="header.jsp"%>
    <div class="center">
        <div class="form">
            <c:choose>
                <c:when test="${department != null}">
                    <form method="post" action="do_edit">
                        <input type="hidden" name="action" value="update"/>
                        <input type="hidden" name="departmentId" value="${department.id}">
                        <div class="field">
                            <label for="name">Name</label>
                            <input id="name" name="name" value="${department.name}" required="true"/>
                        </div>
                        <div class="field">
                            <label for="manager">Manager</label>
                            <select id="manager" name="managerId">
                                <c:forEach items="${employees}" var="employee">
                                    <c:choose>
                                        <c:when test="${employee.id == department.id}">
                                            <option value="${employee.id}" selected>
                                                <c:out value="${employee.firstName} ${employee.lastName}"/>
                                            </option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${employee.id}" selected>
                                                <c:out value="${employee.firstName} ${employee.lastName}"/>
                                            </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="submit">
                            <input type="submit" value="Update"/>
                            <input type="reset" value="Reset"/>
                        </div>
                    </form>

                </c:when>
                <c:otherwise>
                    <form method="post" action="do_edit">
                        <input type="hidden" name="action" value="add"/>
                        <div class="field">
                            <label for="name">Name</label>
                            <input id="name" name="name" required="true"/>
                        </div>
                        <div class="field">
                            <label for="manager">Manager</label>
                            <select id="manager" name="managerId">
                                <c:forEach items="${employees}" var="employee">
                                    <option value="${employee.id}">
                                        <c:out value="${employee.firstName} ${employee.lastName}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="submit">
                            <input type="submit" value="Add"/>
                            <input type="reset" value="Reset"/>
                        </div>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <%@include file="footer.jsp"%>
    </body>
</html>