<%@ include file="/WEB-INF/app/includes/header.jsp"%>

<c:set var="work" value="${requestScope.work}"></c:set>

<div class="container">
  <ol class="breadcrumb">
    <li class="active">My Cart</li>
  </ol>
  <h1 class="text-center text-capitalize">My Cart</h1>
  <c:if test="${empty work}">
    <div class="alert alert-danger text-center alert-dismissable">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span>&times;</span></button>
      <span class="glyphicon glyphicon-exclamation-sign"></span>
      This work doesn't exist or is already in your cart!
    </div>
  </c:if>
  <c:if test="${not empty work}">
    <div class="alert alert-success alert-dismissable">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span>&times;</span></button>
      <span class="glyphicon glyphicon-ok text-center"></span>
      ${work.title} was successfully added to your cart!
    </div>
  </c:if>
  <%@ include file="/WEB-INF/app/servlet/cart/cartLister.jsp" %>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp"%>
