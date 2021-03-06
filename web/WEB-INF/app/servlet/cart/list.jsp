<%@ include file="/WEB-INF/app/includes/header.jsp"%>

<c:set var="message" value="${requestScope.message}"></c:set>

<div class="container">
  <ol class="breadcrumb">
    <li class="active">My Cart</li>
  </ol>
  <h1 class="text-center text-capitalize">My Cart</h1>
  <%@ include file="/WEB-INF/app/includes/messageAlert.jsp" %>
  <%@ include file="/WEB-INF/app/servlet/includes/cartLister.jsp" %>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp"%>
