<%@ include file="/WEB-INF/app/includes/header.jsp"%>

<c:set var="work" value="${requestScope.work}"></c:set>
<c:set var="imageExtensions" value="${fn:split('jpg,png,gif', ',')}"></c:set>
<c:set var="audioExtensions" value="${fn:split('mp3,ogg,wav', ',')}"></c:set>
<c:set var="fileSplit" value="${fn:split(work.file, '.')}"></c:set>
<c:set var="fileExtension" value="${fileSplit[fn:length(fileSplit) - 1]}"></c:set>

<div class="container">
  <ol class="breadcrumb">
    <li><a href="${rootUrl}/Work">All Works</a></li>
    <li><a href="${rootUrl}/Work?category">Categories</a></li>
    <li><a href="${rootUrl}/Work?category&id=${work.category.id}" class="text-capitalize">${work.category.displayName}</a></li>
    <li class="active">${work.title}</li>
  </ol>
  <h1 class="text-center">${work.title}</h1>
  <c:forEach items="${imageExtensions}" var="imageExtension">
    <c:if test="${fileExtension eq imageExtension}">
      <div class="row">
        <img src="${rootUrl}/uploads/${work.category.name}/${work.file}" alt="${work.title}" class="col-md-12"/>
      </div>
    </c:if>
  </c:forEach>
  <c:forEach items="${audioExtensions}" var="audioExtension">
    <c:if test="${fileExtension eq audioExtension}">
      <div class="well">
        <audio class="full-width" src="${rootUrl}/uploads/${work.category.name}/${work.file}" type="audio/${audioExtension}" controls></audio>
      </div>
    </c:if>
  </c:forEach>
  <div class="panel panel-default">
    <div class="panel-body">
      <div class="col-md-4">
        <div class="panel panel-default">
          <div class="panel-heading">
            Details
          </div>
          <table class="table table-striped panel-body">
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
      </div>
      <div class="col-md-8">
        <h3>Description</h3>
        <div class="text">
          <p>${work.description}</p>
          <p>
            <%@ include file="/WEB-INF/app/servlet/includes/cartButton.jsp"%>
          </p>
        </div>
      </div>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp"%>