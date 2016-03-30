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
<h1>Crear un usuario</h1>
    <form:form action="create-user" modelAttribute="user">
        <p>Name:
            <form:input path="username" placeholder="username" required="required" />
            <form:errors path="username" cssClass="error" />
        </p>
        <p>Email:
            <form:input path="email" placeholder="email" required="required" />
        </p>
        <p>Password:
            <form:password path="password" placeholder="password" required="required" showPassword="true" />
        </p>
        <p>Birthdate:
            <form:input path="birthDate" placeholder="dd/MM/yyyy" required="required"/>
        </p>
        <p><input type="submit" value="Crear"></p>
    </form:form>

    <p><a href="<c:url value="/user-list"/>">Volver a lista de usuarios</a></p>
    <p><a href="<c:url value="/home"/>">Home</a></p>
</body>
</html>