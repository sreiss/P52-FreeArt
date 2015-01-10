<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="rootUrl" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html>
<html>
    <head>
        <title>Free Art<c:if test="${requestScope.pageTitle != ''}"> - <c:out value="${requestScope.pageTitle}" /></c:if></title>
        <meta charset="utf-8" />
        <link rel="stylesheet" href="${rootUrl}/lib/bootstrap/css/bootstrap.min.css" />
        <script src="${rootUrl}/lib/jquery/js/jquery-1.11.2.min.js"></script>
        <script src="${rootUrl}/lib/bootstrap/js/bootstrap.min.js"></script>
    </head>
    <body>
        <%@ include file="/WEB-INF/app/includes/menu.jsp"%>
