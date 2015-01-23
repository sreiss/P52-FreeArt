<%@ include file="/WEB-INF/app/includes/header.jsp"%>

<c:set var="category" value="${requestScope.category}"></c:set>

<div class="container">
  <ol class="breadcrumb">
    <li><a href="${rootUrl}/Work">All Works</a></li>
    <li><a href="${rootUrl}/Work?category">Categories</a></li>
    <li class="active text-capitalize">${category.displayName}</li>
  </ol>
  <h1 class="text-center text-capitalize">${category.displayName}</h1>
  <c:if test="${not empty category.works}">
    <c:set var="works" value="${category.works}"></c:set>
    <%@ include file="/WEB-INF/app/servlet/includes/workLister.jsp"%>
  </c:if>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp"%>
