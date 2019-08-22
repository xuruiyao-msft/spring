<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: yaoxurui
  Date: 2019-08-17
  Time: 08:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>spring in action</title>
</head>
<h1>Welcome to Spittr</h1>
<a href="<c:url value="/spittles" />" > Spittles </a> |
<a href="<c:url value="/spittles/register" />" > Register </a>
</body>
</html>
