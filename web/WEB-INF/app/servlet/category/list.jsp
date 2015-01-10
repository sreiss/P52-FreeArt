<%@ include file="/WEB-INF/app/includes/header.jsp"%>

<div class="container">
  <ol class="breadcrumb">
    <li><a href="${rootUrl}/Work">All Works</a></li>
    <li class="active">Categories</li>
  </ol>
  <h1 class="text-center">Categories</h1>
  <c:if test="${empty requestScope.categories}">
    <p class="text-center">There are no categories!</p>
  </c:if>
  <c:if test="${not empty requestScope.categories}">
    <div class="panel panel-default">
      <table class="table table-striped">
        <thead>
          <tr>
            <td>Name</td>
            <td>Number of elements</td>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${requestScope.categories}" var="category">
            <tr>
              <td><a href="${rootUrl}/Category?id=${category.id}" class="text-capitalize">${fn:toLowerCase(category.name)}</a></td>
              <td>${fn:length(category.works)}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </c:if>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp"%>
