<%@ include file="/WEB-INF/app/includes/header.jsp"%>

<c:set var="deleted" value="${requestScope.deleted}"></c:set>

<div class="container">
  <ol class="breadcrumb">
    <li class="active">My Cart</li>
  </ol>
  <h1 class="text-center text-capitalize">My Cart</h1>
  <c:if test="${not deleted}">
    <div class="alert alert-danger text-center alert-dismissable">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span>&times;</span></button>
      <span class="glyphicon glyphicon-exclamation-sign"></span>
      Unable to delete the item. Was it in your cart in the first place ?
    </div>
  </c:if>
  <c:if test="${deleted}">
    <div class="alert alert-success alert-dismissable">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span>&times;</span></button>
      <span class="glyphicon glyphicon-ok text-center"></span>
      The item was successfully deleted
    </div>
  </c:if>
  <%@ include file="/WEB-INF/app/servlet/cart/cartLister.jsp" %>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp"%>
