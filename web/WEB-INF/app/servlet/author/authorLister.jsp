<c:if test="${empty authors}">
  <p class="text-center">There are no authors on FreeArt! for the time being!</p>
</c:if>
<c:if test="${not empty authors}">
  <div class="panel-default panel">
    <table class="table table-striped">
      <thead>
      <tr>
        <th>Login</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th></th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${authors}" var="author">
        <tr>
          <td class="vertical-align-middle">${author.login}</td>
          <td class="vertical-align-middle">${author.firstName}</td>
          <td class="vertical-align-middle">${author.name}</td>
          <td class="vertical-align-middle text-right">
            <a href="${rootUrl}/Work?author&id=${author.id}" class="btn btn-primary">Browse his work</a>
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
            <button class="btn btn-success full-width">Download my cart</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</c:if>