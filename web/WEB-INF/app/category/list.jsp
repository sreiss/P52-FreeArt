<%--
  Created by IntelliJ IDEA.
  User: Simon
  Date: 09/01/2015
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="/WEB-INF/includes/header.jsp"%>
<body>
<%@ include file="/WEB-INF/includes/menu.jsp"%>
<div class="container">
  <h1>Categories</h1>
  <c:if test="${empty requestScope.categories}">
    There are no categories!
  </c:if>
  <c:if test="${not empty requestScope.categorie}">
    <c:forEach items="${requestScope.categories}" var="caegory">
      ${category.name}
    </c:forEach>
  </c:if>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>
