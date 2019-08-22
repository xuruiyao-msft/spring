<%--
  Created by IntelliJ IDEA.
  User: yaoxurui
  Date: 2019-08-19
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%String path= request.getContextPath();%>
<link href="<%=path %>/WEB-INF/css/app.css" rel="stylesheet">
<html>
<head>
    <title><s:message code="spittr.welcome"></s:message></title>
</head>
<body>
<h1>Register</h1>
<%--<form method="POST">--%>
    <%--First Name: <input type="text" name="firstName"/> <br/>--%>
    <%--Last Name: <input type="text" name="lastName"/><br/>--%>
    <%--Username: <input type="text" name="username"/><br/>--%>
    <%--Password: <input type="text" name="password"/><br/>--%>
    <%--<input type="submit" value="register">--%>
<%--</form>--%>
<sf:form method="POST" commandName="user">
    <sf:errors path="*" element="div" cssClass="errors"/>
    First Name: <sf:input path="firstName"/> <br/>
    Last Name:  <sf:input path="lastName"/>  <br/>
    Username:   <sf:input path="username"/>  <br/>
    Password:   <sf:password path="password"/> <br/>
    <input type="submit" value="register">
</sf:form>
</body>
</html>
