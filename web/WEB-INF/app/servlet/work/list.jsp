<%@ include file="/WEB-INF/app/includes/header.jsp"%>

<div class="container">
  <h1 class="text-center">Works</h1>
  <c:if test="${empty requestScope.works}">
    <p class="text-center">No works where submitted for the time being!</p>
  </c:if>
  <c:if test="${not empty requestScope.works}">
    <c:forEach items="${requestScope.works}" var="work" varStatus="status">
      <c:if test="status.index%3 == 3">
        <div class="row">
      </c:if>
      <div class="col-md-4">
        <div class="thumbnail">
          <a href="#">
            <img src="${pageContext.request.contextPath}/thumbnails/${work.category.name}/${work.file}" alt="" />
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
              <a href="#" class="btn btn-default">More details</a>
            </p>
          </div>
        </div>
      </div>
      <c:if test="status.index%3 == 0 || status.last">
          </div>
      </c:if>
    </c:forEach>
  </c:if>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp"%>
