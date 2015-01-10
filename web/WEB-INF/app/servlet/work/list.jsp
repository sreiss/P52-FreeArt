<%@ include file="/WEB-INF/app/includes/header.jsp"%>

<div class="container">
  <h1>Works</h1>
  <c:if test="${empty requestScope.works}">
    No works where submitted for the time being!
  </c:if>
  <c:if test="${not empty requestScope.works}">
    <c:forEach items="${requestScope.works}" var="work" varStatus="status">
      <c:if test="status.index%3 == 3">
        <div class="row">
      </c:if>
      <div class="col-md-4">
        <div class="panel panel-default">
          <div class="panel-heading">${work.title}</div>
          <div class="panel-body">
            <div style="max-width: 100%; overflow: hidden;">
            <c:if test="${work.category.name == 'images'}">
              <img src="${pageContext.request.contextPath}/uploads/${work.category.name}/${work.file}" />
            </c:if>
              </div>
            <p>Location : ${$work.location}</p>
            <p>${work.description}</p>
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
