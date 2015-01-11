<div class="panel panel-default">
  <table class="table table-striped">
    <thead>
    <tr>
      <th>Name</th>
      <th>Number of elements</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${categories}" var="category">
      <tr>
        <td><a href="${rootUrl}/Work?category&id=${category.id}" class="text-capitalize">${fn:toLowerCase(category.name)}</a></td>
        <td>${fn:length(category.works)}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>