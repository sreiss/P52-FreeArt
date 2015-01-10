<%@ include file="/WEB-INF/app/includes/header.jsp"%>

<div class="container">
  <h1 class="text-danger"><%= request.getAttribute("javax.servlet.error.status_code")%>!</h1>
  <p class="text-danger"><%= request.getAttribute("javax.servlet.error.message")%></p>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp"%>
