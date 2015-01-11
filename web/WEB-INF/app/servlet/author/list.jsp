<%@ include file="/WEB-INF/app/includes/header.jsp" %>

<c:set var="authors" value="${requestScope.authors}"></c:set>

<div class="container">
  <h1 class="text-center">Authors</h1>
  <%@ include file="/WEB-INF/app/servlet/includes/authorLister.jsp" %>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp" %>