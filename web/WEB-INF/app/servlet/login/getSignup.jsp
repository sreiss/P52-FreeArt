<%@ include file="/WEB-INF/app/includes/header.jsp" %>

<c:set var="errorMessage" value="${requestScope.errorMessage}"></c:set>

<div class="container">
  <h1 class="text-center">Sign Up to FreeArt!</h1>
  <div class="row">
    <div class="col-md-6 col-md-offset-3">
      <%@ include file="/WEB-INF/app/includes/errorAlert.jsp" %>
      <%@ include file="/WEB-INF/app/includes/messageAlert.jsp" %>
    </div>
  </div>
  <div class="row">
    <div class="col-md-6 col-md-offset-3">
      <div class="panel panel-default">
        <div class="panel-heading">
          My informations
        </div>
        <div class="panel-body text-center">
          <form method="post" action="${rootUrl}/Login">
            <div class="form-group">
              <label>Username</label>
              <p class="text-center">Please note that capital cases will be replaced by lower cases and "-" will take any none alphanumerical character's place.</p>
              <input type="text" class="form-control text-center" name="login" />
            </div>
            <div class="form-group">
              <label>First Name <small>(optionnal)</small></label>
              <input type="text" class="form-control text-center" name="firstName" />
            </div>
            <div class="form-group">
              <label>Last Name <small>(optionnal)</small></label>
              <input type="text" class="form-control text-center" name="name" />
            </div>
            <div class="form-group">
              <label>Email <small>(Optionnal)</small></label>
              <input type="text" class="form-control text-center" name="email" />
            </div>
            <div class="form-group">
              <label>Password</label>
              <input type="password" class="form-control text-center" name="password" />
            </div>
            <div class="form-group">
              <label>Password repeat</label>
              <input type="password" class="form-control text-center" name="passwordRepeat" />
            </div>
            <input type="hidden" name="action" value="signup" />
            <div class="col-md-4 col-md-offset-4">
              <input type="submit" class="btn btn-primary btn-block" value="Sign Up" />
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>

<%@ include file="/WEB-INF/app/includes/footer.jsp" %>