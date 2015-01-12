<%@ include file="/WEB-INF/app/includes/header.jsp" %>

<c:set var="errorMessage" value="${requestScope.errorMessage}"></c:set>

<div class="container">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
      <%@ include file="/WEB-INF/app/includes/errorAlert.jsp" %>
      <p class="text-center">Login to start uploading your own creations!</p>
      <div class="panel panel-default">
        <div class="panel-body">
          <form method="post" action="${rootUrl}/Account">
            <div class="form-group text-center">
              <label>Login</label>
              <input type="text" class="form-control text-center" name="login" />
            </div>
            <div class="form-group text-center">
              <label>Password</label>
              <input type="password" class="form-control text-center" name="password" />
            </div>
            <p class="text-center">
              No account ?
              <a href="${rootUrl}/Account?action=signup">Sign up</a>
            </p>
            <input type="hidden" name="action" value="login" />
            <!--<input type="hidden" name="secret" value="1586231455" />-->
            <input type="submit" class="btn-block btn btn-primary" />
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp" %>