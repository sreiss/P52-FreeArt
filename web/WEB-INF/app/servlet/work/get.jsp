<%@ include file="/WEB-INF/app/includes/header.jsp"%>

<c:set var="work" value="${requestScope.work}"></c:set>
<c:set var="imageExtensions" value="${fn:split('jpg,png,gif', ',')}"></c:set>
<c:set var="audioExtensions" value="${fn:split('mp3,ogg,wav', ',')}"></c:set>
<c:set var="fileSplit" value="${fn:split(work.file, '.')}"></c:set>
<c:set var="fileExtension" value="${fileSplit[fn:length(fileSplit) - 1]}"></c:set>

<div class="container">
  <ol class="breadcrumb">
    <li><a href="${rootUrl}/Work">All Works</a></li>
    <li><a href="${rootUrl}/Category">Categories</a></li>
    <li><a href="${rootUrl}/Category?id=${work.category.id}" class="text-capitalize">${fn:toLowerCase(work.category.name)}</a></li>
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
      <div class="embed-responsive embed-responsive-16by9m">
        <div class="embed-responsive-item">
          <audio src="${rootUrl}/uploads/${work.category.name}/${work.file}"></audio>
        </div>
      </div>
    </c:if>
  </c:forEach>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp"%>