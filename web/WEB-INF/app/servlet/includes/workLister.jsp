<c:forEach items="${works}" var="work" varStatus="status">
  <c:if test="${status.index%3 == 0}">
    <div class="row">
  </c:if>
  <div class="col-md-4">
    <div class="thumbnail">
      <a href="${rootUrl}/Work?id=${work.id}">
        <img src="${rootUrl}/thumbnails/${work.category.name}/${work.thumbnail}" alt="" />
      </a>
      <div class="caption">
        <h3>${work.title}</h3>
        <div class="panel panel-default">
          <table class="table table-striped">
            <tbody>
            <tr>
              <td>Author :</td>
              <td class="text-right">${work.author.firstName} ${work.author.name} (${work.author.login})</td>
            </tr>
            <tr>
              <td>Creation date :</td>
              <td class="text-right"><fmt:formatDate value="${work.creationDate}" type="date" /></td>
            </tr>
            <tr>
              <td>Location :</td>
              <td class="text-right">${work.location}</td>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="panel panel-default">
          <div class="panel-heading">Description</div>
          <div class="panel-body">
            <p>${work.description}</p>
          </div>
        </div>
        <p>
          <a href="#" class="btn btn-primary">Add to cart</a>
          <a href="${rootUrl}/Work?id=${work.id}" class="btn btn-default">More details</a>
        </p>
      </div>
    </div>
  </div>
  <c:if test="${status.index%3 == 2 or status.last}">
    </div>
  </c:if>
</c:forEach>