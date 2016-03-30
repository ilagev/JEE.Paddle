<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Crear una pista</h1>
    <form:form action="create-court" modelAttribute="court">
        <p>Id:
            <form:input path="courtId" placeholder="courtId" required="required" />
            <form:errors path="courtId" cssClass="error" />
        </p>
        <p><input type="submit" value="Crear"></p>
    </form:form>

    <p><a href="<c:url value="/court-list"/>">Volver a lista de pistas</a></p>
    <p><a href="<c:url value="/home"/>">Home</a></p>
</body>
</html>