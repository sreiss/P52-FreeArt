<%@ include file="/WEB-INF/app/includes/header.jsp"%>

<c:set var="works" value="${requestScope.works}"></c:set>
<c:set var="categories" value="${requestScope.categories}"></c:set>

<div class="container">
  <ol class="breadcrumb">
    <li class="active">Search results for: "${search}"</li>
  </ol>
  <c:if test="${empty works and empty categories}">
    <h3 class="text-center">We're so sorry, but...</h3>
    <p class="text-center">...no match where found for the search: "${search}"</p>
  </c:if>
  <c:if test="${not empty requestScope.works}">
    <h2 class="text-center">Works found:</h2>
    <div class="panel panel-default">
      <table class="table table-striped">
        <thead>
          <tr>
            <th>Name</th>
            <th>Author</th>
            <th>Category</th>
            <th>Creation date</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${works}" var="work">
            <tr>
              <td><a href="${rootUrl}/Work?id=${work.id}">${work.title}</a></td>
              <td><a href="${rootUrl}/Author?id=${work.author.id}">${work.author.firstName} ${work.author.name} (${work.author.login})</a></td>
              <td><a href="${rootUrl}/Work?category&id=${work.category.id}">${work.category.name}</a></td>
              <td><fmt:formatDate value="${work.creationDate}" type="date"></fmt:formatDate></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </c:if>
  <c:if test="${not empty categories}">
    <h2 class="text-center">Categories found:</h2>
    <%@ include file="/WEB-INF/app/servlet/category/categoryLister.jsp" %>
  </c:if>
</div>

<%@include file="/WEB-INF/app/includes/footer.jsp"%>