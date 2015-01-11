<%@ include file="/WEB-INF/app/includes/header.jsp"%>

<c:set var="categories" value="${requestScope.categories}"></c:set>

<div class="container">
  <ol class="breadcrumb">
    <li><a href="${rootUrl}/Work">All Works</a></li>
    <li class="active">Categories</li>
  </ol>
  <h1 class="text-center">Categories</h1>
  <c:if test="${empty categories}">
    <p class="text-center">There are no categories!</p>
  </c:if>
  <c:if test="${not empty categories}">
    <%@ include file="/WEB-INF/app/servlet/category/categoryLister.jsp" %>
  </c:if>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp"%>
