<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Courts list</title>
</head>
<body>
    <H1>Listado de pistas</H1>
    <table border="1">
        <thead>
            <tr>
                <th>Id</th>
                <th>Active</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${courtList}" var="court">
                <tr>
                    <td>${court.courtId}</td>
                    <td>${court.active}</td>
                    <td><a href="<c:url value='/delete-court/${court.courtId}' />">delete</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <p><a href="<c:url value='/create-court'/>">Create court</a></p>
    <a href="<c:url value="/home"/>">Home</a>
</body>
</html>