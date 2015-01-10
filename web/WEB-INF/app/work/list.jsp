<%--
  Created by IntelliJ IDEA.
  User: Simon
  Date: 09/01/2015
  Time: 01:45
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="/WEB-INF/includes/header.jsp"%>
<body>
<%@ include file="/WEB-INF/includes/menu.jsp"%>
<div class="container">
  <h1>Works</h1>
  <c:if test="${empty requestScope.works}">
    No works where submitted for the time being!
  </c:if>
  <c:if test="${not empty requestScope.works}">
    <c:forEach items="${requestScope.works}" var="work" varStatus="status">
      <c:if test="status.index%3 == 3">
        <div class="row">
      </c:if>
      <div class="col-md-4">
        <div class="panel panel-default">
          <div class="panel-heading">${work.title}</div>
          <div class="panel-body">
            <div style="max-width: 100%; overflow: hidden;">
            <c:if test="${work.category.name == 'images'}">
              <img src="${pageContext.request.contextPath}/uploads/${work.category.name}/${work.file}" />
            </c:if>
              </div>
            <p>Location : ${$work.location}</p>
            <p>${work.description}</p>
          </div>
        </div>
      </div>
      <c:if test="status.index%3 == 0 || status.last">
          </div>
      </c:if>
    </c:forEach>
  </c:if>
</div>
<%@ include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>
