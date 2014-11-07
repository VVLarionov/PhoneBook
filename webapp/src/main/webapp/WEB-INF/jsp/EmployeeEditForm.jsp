<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="/error"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="headcontent.jsp"%>
    <title>

        <c:choose>
            <c:when test="${param['employeeId'] != null}">
                <c:out value="Edit employee"/>
            </c:when>
            <c:otherwise>
                <c:out value="Add employee"/>
            </c:otherwise>
        </c:choose>
    </title>
</head>
<body>
    <%@include file="header.jsp"%>
    <div class="center">
        <div class="form">
            <c:choose>
                <c:when test="${employee != null}">
                    <form method="post" action="do_edit">
                        <input type="hidden" name="action" value="update"/>
                        <input type="hidden" name="employeeId" value="${employee.id}">
                        <div class="field">
                            <label for="firstName">First name</label>
                            <input id="firstName" name="firstName" value="${employee.firstName}" required="true"/>
                        </div>
                        <div class="field">
                            <label for="lastName">Last name</label>
                            <input id="lastName" name="lastName" value="${employee.lastName}" required="true"/>
                        </div>
                        <div class="field">
                            <label for="project">Project</label>
                            <input id="project" name="project" value="${employee.project}" required="true"/>
                        </div>
                        <div class="field">
                            <label for="department">Department</label>
                            <select id="department" name="departmentId">
                                <option value="${null}" selected></option>
                                <c:forEach items="${departments}" var="department">
                                    <c:choose>
                                        <c:when test="${employee.department != null && employee.department.id == department.id}">
                                            <option value="${department.id}" selected>
                                                <c:out value="${department.name}"/>
                                            </option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${department.id}">
                                                <c:out value="${department.name}"/>
                                            </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="field">
                            <label for="email">Email</label>
                            <input id="email" name="email" value="${employee.email}" required="true"/>
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
                            <label for="firstName">First name</label>
                            <input id="firstName" name="firstName" required="true"/>
                        </div>
                        <div class="field">
                            <label for="lastName">Last name</label>
                            <input id="lastName" name="lastName" required="true"/>
                        </div>
                        <div class="field">
                            <label for="project">Project</label>
                            <input id="project" name="project" required="true"/>
                        </div>
                        <div class="field">
                            <label for="department">Department</label>
                            <select id="department" name="departmentId">
                                <option value="${null}" selected></option>
                                <c:forEach items="${departments}" var="department">
                                    <option value="${department.id}">
                                        <c:out value="${department.name}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="field">
                            <label for="email">Email</label>
                            <input name="email" value="${employee.email}" required="true"/>
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
