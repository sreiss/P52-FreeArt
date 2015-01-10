<%--
  Created by IntelliJ IDEA.
  User: Simon
  Date: 09/01/2015
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="/WEB-INF/app/includes/header.jsp"%>
<body>
<%@ include file="/WEB-INF/app/includes/menu.jsp"%>
<div class="container">
  <h1>Categories</h1>
  <c:if test="${empty requestScope.categories}">
    There are no categories!
  </c:if>
  <c:if test="${not empty requestScope.categories}">
    <table class="table table-striped">
      <thead>
        <tr>
          <td>Name</td>
          <td>Number of elements</td>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${requestScope.categories}" var="category">
          <tr>
            <td><a href="${pageContext.request.contextPath}/Work?category=${category.id}">${category.name}</a></td>
            <td>${fn:length(category.works)}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </c:if>
</div>
<%@ include file="/WEB-INF/app/includes/footer.jsp"%>
</body>
</html>
