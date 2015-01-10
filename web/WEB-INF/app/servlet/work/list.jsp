<%@ include file="/WEB-INF/app/includes/header.jsp"%>

<div class="container">
  <ol class="breadcrumb">
    <li class="active">All Works</li>
  </ol>
  <h1 class="text-center">All Works</h1>
  <c:if test="${empty requestScope.works}">
    <p class="text-center">No works where submitted for the time being!</p>
  </c:if>
  <c:if test="${not empty requestScope.works}">
    <c:set var="works" value="${requestScope.works}"></c:set>
    <%@ include file="/WEB-INF/app/servlet/includes/workLister.jsp"%>
  </c:if>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp"%>
