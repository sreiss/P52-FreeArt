<%@ include file="/WEB-INF/app/includes/header.jsp" %>

<c:set var="currentAuthor" value="${sessionScope.currentAuthor}"></c:set>
<c:set var="errorMessage" value="${requestScope.errorMessage}"></c:set>
<c:set var="message" value="${requestScope.message}"></c:set>

<div class="container">
  <ol class="breadcrumb">
    <li class="active">Account</li>
  </ol>
  <h1 class="text-center">My Account</h1>
  <%@ include file="/WEB-INF/app/includes/errorAlert.jsp" %>
  <%@ include file="/WEB-INF/app/includes/messageAlert.jsp" %>
  <div class="row">
    <div class="col-md-6">
      <div class="panel panel-default">
      <div class="panel-heading">
        My informations
      </div>
      <div class="panel-body text-center">
        <form method="post" action="${rootUrl}/Account">
          <div class="form-group">
            <label>Email</label>
            <input type="text" class="form-control text-center" name="email" value="${currentAuthor.email}" />
          </div>
          <div class="form-group">
            <label>First Name</label>
            <input type="text" class="form-control text-center" name="firstName" value="${currentAuthor.firstName}" />
          </div>
          <div class="form-group">
            <label>Last Name</label>
            <input type="text" class="form-control text-center" name="name" value="${currentAuthor.name}" />
          </div>
          <input type="hidden" name="action" value="update" />
          <input type="hidden" name="updateType" value="general" />
          <div class="col-md-4 col-md-offset-4">
            <input type="submit" class="btn btn-primary btn-block" value="Update" />
          </div>
        </form>
      </div>
    </div>
  </div>
  <div class="col-md-6">
    <div class="panel panel-default">
      <div class="panel-heading">
        My password
      </div>
      <div class="panel-body text-center">
        <form method="post" action="${rootUrl}/Account">
          <div class="form-group">
            <label>Password</label>
            <input type="password" class="form-control text-center" name="password" />
          </div>
          <div class="form-group">
            <label>Password repeat</label>
            <input type="password" class="form-control text-center" name="passwordRepeat" />
          </div>
          <input type="hidden" name="action" value="update" />
          <input type="hidden" name="updateType" value="password" />
          <div class="col-md-4 col-md-offset-4">
            <input type="submit" class="btn btn-primary btn-block" value="Update" />
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/app/includes/footer.jsp" %>