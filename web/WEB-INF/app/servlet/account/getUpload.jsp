<%@ include file="/WEB-INF/app/includes/header.jsp" %>

<c:set var="errorMessage" value="${requestScope.errorMessage}"></c:set>
<c:set var="successMessage" value="${requestScope.successMessage}"></c:set>
<c:set var="categories" value="${requestScope.categories}"></c:set>

<div class="container">
  <ol class="breadcrumb">
    <li><a href="${rootUrl}/Account">Account</a></li>
    <li class="active">Upload</li>
  </ol>
  <h1 class="text-center">Upload a file!</h1>
  <div class="col-md-6 col-md-offset-3">
    <%@ include file="/WEB-INF/app/includes/errorAlert.jsp" %>
    <%@ include file="/WEB-INF/app/includes/messageAlert.jsp" %>
    <div class="panel panel-default">
      <div class="panel-body">
        <form method="post" action="${rootUrl}/Account" enctype="multipart/form-data">
          <input type="hidden" name="action" value="upload" />
          <div class="form-group text-center">
            <label>Title</label>
            <input type="text" class="form-control text-center" name="title" />
          </div>
          <div class="form-group text-center">
            <label>Description</label>
            <textarea class="form-control" name="description"></textarea>
          </div>
          <div class="form-group text-center">
            <label>Location <small>(optional)</small></label>
            <input type="text" class="form-control text-center" name="location" />
          </div>
          <div class="form-group text-center">
            <label>Category</label>
            <select name="categoryId" class="form-control">
              <c:forEach items="${categories}" var="category">
                <option value="${category.id}">${category.displayName}</option>
              </c:forEach>
            </select>
          </div>
          <p class="text-center">
            You can also create a new category by checking "create a new category" and giving it's name
          </p>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group text-center">
                <div class="checkbox">
                  <label>
                    <input type="checkbox" name="createCategory" />
                    <strong>Create a new category</strong>
                  </label>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group text-center">
                <label>Category name</label>
                <input type="text" name="categoryName" class="form-control" />
              </div>
            </div>
          </div>
          <div class="form-group text-center">
            <label>Thumbnail <small>(optional)</small></label>
            <input type="file" class="form-control text-center" name="thumbnail" />
          </div>
          <div class="form-group text-center">
            <label>File</label>
            <input type="file" class="form-control text-center" name="file" />
          </div>
          <input type="submit" class="btn-block btn btn-primary" value="Upload!" />
        </form>
      </div>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp" %>