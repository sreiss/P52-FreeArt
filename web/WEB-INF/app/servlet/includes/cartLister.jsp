<c:set var="works" value="${requestScope.works}"></c:set>

<c:if test="${empty works}">
  <p class="text-center">Your cart is empty!</p>
</c:if>
<c:if test="${not empty works}">
  <div class="panel-default panel">
    <table class="table table-striped">
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
          <td class="vertical-align-middle">${work.title}</td>
          <td class="vertical-align-middle">${work.category.name}</td>
          <td class="vertical-align-middle">${work.author.firstName} ${work.author.name} (${work.author.login})</td>
          <td class="vertical-align-middle"><fmt:formatDate value="${work.creationDate}" type="date"></fmt:formatDate></td>
          <td class="vertical-align-middle">
            <form method="post" action="${rootUrl}/Cart" class="pull-right">
              <input type="hidden" name="action" value="delete" />
              <input type="hidden" name="id" value="${work.id}" />
              <button class="btn btn-danger">Delete</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    <div class="panel-body">
      <div class="row">
        <div class="col-md-4 col-md-offset-4">
          <form method="post" action="${rootUrl}/Cart">
            <input type="hidden" name="action" value="download" />
            <button class="btn btn-success full-width">
              <span class="glyphicon glyphicon-download"></span>
              Download my cart
            </button>
          </form>
        </div>
        <div class="col-md-2 col-md-offset-2 pull-right">
          <form method="post" action="${rootUrl}/Cart">
            <input type="hidden" name="action" value="empty" />
            <button class="btn btn-danger full-width">
              <span class="glyphicon glyphicon-download"></span>
              Empty my cart
            </button>
          </form>
        </div>
      </div>
    </div>
  </div>
</c:if>