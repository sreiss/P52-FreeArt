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
            <a href="${rootUrl}/Work?author&login=${author.login}">Browse his work</a>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</c:if>