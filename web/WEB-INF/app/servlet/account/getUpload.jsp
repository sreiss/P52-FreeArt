<%@ include file="/WEB-INF/app/includes/header.jsp" %>

<c:set var="errorMessage" value="${requestScope.errorMessage}"></c:set>

<div class="container">
  <ol class="breadcrumb">
    <li><a href="${rootUrl}/Account">Account</a></li>
    <li class="active">Upload</li>
  </ol>
  <h1 class="text-center">Upload a file!</h1>
  <div class="col-md-6 col-md-offset-3">
    <div class="panel panel-default">
      <div class="panel-body">
        <form method="post" action="${rootUrl}/Account?action=upload" enctype="multipart/form-data">
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
            <label>Thumbnail <small>(optional)</small></label>
            <input type="file" class="form-control text-center" name="thumbnail" />
          </div>
          <div class="form-group text-center">
            <label>File</label>
            <input type="file" class="form-control text-center" name="file" />
          </div>
          <input type="hidden" name="action" value="login" />
          <!--<input type="hidden" name="secret" value="1586231455" />-->
          <input type="submit" class="btn-block btn btn-primary" value="Upload!" />
        </form>
      </div>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp" %>