<%@ include file="/WEB-INF/app/includes/header.jsp"%>

<div class="container">
  <h1>Category</h1>
  <c:if test="${not empty requestScope.categories}">
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
          <td><a href="${pageContext.request.}/Work?category=${category.id}">${category.name}</a></td>
          <td>${fn:length(category.works)}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:if>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp"%>
