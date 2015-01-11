<%@ include file="/WEB-INF/app/includes/header.jsp"%>

<c:set var="referer" value="${pageContext.request.getHeader('referer')}"></c:set>

<div class="container text-center">
  <h1 class="text-danger"><%= request.getAttribute("javax.servlet.error.status_code")%>!</h1>
  <p class="text-danger"><%= request.getAttribute("javax.servlet.error.message")%></p>
  <c:if test="${not empty referer}">
    <p class="text-center">
      <a href="${referer}">Back to previous page</a>
    </p>
  </c:if>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp"%>
