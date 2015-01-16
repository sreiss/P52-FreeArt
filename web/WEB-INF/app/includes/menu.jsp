<nav class="navbar navbar-primary navbar-static-top">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="${rootUrl}">FreeArt!</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="${rootUrl}/Work">All Art</a></li>
        <li><a href="${rootUrl}/Work?category">Categories</a></li>
        <li><a href="${rootUrl}/Author">Authors</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <c:if test="${not empty sessionScope.currentAuthor}">
          <li><a href="${rootUrl}/Login?action=logout">Logout</a></li>
          <li><a href="${rootUrl}/Account">My Account</a></li>
        </c:if>
        <c:if test="${empty sessionScope.currentAuthor}">
          <li><a href="${rootUrl}/Login?action=login">Login</a></li>
        </c:if>
        <li><a href="${rootUrl}/Cart"><span class="glyphicon glyphicon-shopping-cart"></span> My Cart</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>