<%@ include file="/WEB-INF/app/includes/header.jsp"%>

<div class="container">
  <c:if test="${empty works and empty categories}">
    No match where found for the search: ${search}
  </c:if>
  <c:if test="${not empty requestScope.works}">
    <h2>Works found:</h2>
    <c:forEach items="${requestScope.works}" var="work">
      ${work.title}
    </c:forEach>
  </c:if>
  <c:if test="${not empty requestScope.categories}">
    <h2>Categories found:</h2>
    <c:forEach items="${requestScope.categories}" var="category">
      ${category.name}
    </c:forEach>
  </c:if>
</div>

<%@include file="/WEB-INF/app/includes/footer.jsp"%>