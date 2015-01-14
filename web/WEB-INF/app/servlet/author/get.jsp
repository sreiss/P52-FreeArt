<%@ include file="/WEB-INF/app/includes/header.jsp" %>

<c:set var="author" value="${requestScope.author}"></c:set>
<c:set var="works" value="${requestScope.works}"></c:set>
<c:set var="login" value="${requestScope.login}"></c:set>

<div class="container">
  <ol class="breadcrumb">
    <li><a href="${rootUrl}/Work">All works</a></li>
    <li><a href="${rootUrl}/Author">Authors</a></li>
    <li class="current">${login}</li>
  </ol>
  <c:if test="${empty author}">
    <p class="text-center">No author with this login where found</p>
  </c:if>
  <c:if test="${empty works}">
    <p class="text-center">This author didn't post any work yet</p>
  </c:if>
  <c:if test="${not empty works}">
    <c:if test="${not empty author}">
      <c:if test="${not empty author.firstName and not empty author.name}">
        <p class="text-center">This is <strong>${author.firstName} ${author.name}</strong>'s work!</p>
      </c:if>
      <c:if test="${not empty author.login and empty author.firstName or empty author.name}">
        <p class="text-center">This is <strong>${author.login}</strong>'s work!</p>
      </c:if>
      <%@ include file="/WEB-INF/app/servlet/includes/workLister.jsp" %>
    </c:if>
  </c:if>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp"%>