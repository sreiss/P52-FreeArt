<c:if test="${not empty errorMessage}">
  <div class="alert alert-danger alert-dismissable">
    <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
    <span class="glyphicon glyphicon-warning-sign"></span>
    ${errorMessage}
  </div>
</c:if>