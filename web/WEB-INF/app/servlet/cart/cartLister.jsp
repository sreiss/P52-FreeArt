<c:set var="works" value="${requestScope.works}"></c:set>

<c:if test="${empty works}">
  <p class="text-center">Your cart is empty!</p>
</c:if>
<c:if test="${not empty works}">
  <table class="table table-striped panel panel-default">
    <thead>
    <tr>
      <th>Name</th>
      <th>Category</th>
      <th>Author</th>
      <th>Creation date</th>
      <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${works}" var="work">
      <tr>
        <td>${work.title}</td>
        <td>${work.category.name}</td>
        <td>${work.author.firstName} ${work.author.name} (${work.author.login})</td>
        <td><fmt:formatDate value="${work.creationDate}" type="date"></fmt:formatDate></td>
        <td>
          <form method="post" action="${rootUrl}/Cart">
            <input type="hidden" name="action" value="delete" />
            <input type="hidden" name="id" value="${work.id}" />
            <button class="btn btn-danger">Delete</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</c:if>