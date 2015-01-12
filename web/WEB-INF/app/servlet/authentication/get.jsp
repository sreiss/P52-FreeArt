<%@ include file="/WEB-INF/app/includes/header.jsp" %>

<div class="container">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
      <c:if test="${not empty requestScope.loginErrorMessage}">
        <div class="alert alert-danger alert-dismissable">
          <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
          <span class="glyphicon glyphicon-warning-sign"></span> ${requestScope.loginErrorMessage}
        </div>
      </c:if>
      <p class="text-center">Login to start uploading your own creations!</p>
      <div class="panel panel-default">
        <div class="panel-body">
          <form method="post" action="${rootUrl}/Authentication">
            <div class="form-group text-center">
              <label>Login</label>
              <input class="form-control text-center" name="login" />
            </div>
            <div class="form-group text-center">
              <label>Password</label>
              <input class="form-control text-center" name="password" />
            </div>
            <p class="text-center">
              No account ?
              <a href="${rootUrl}/Authentication?action=signup">Sign up</a>
            </p>
            <input type="hidden" name="secret" value="1586231455" />
            <input type="submit" class="btn-block btn btn-primary" />
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp" %>