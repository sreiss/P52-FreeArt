<c:if test="${not empty message}">
  <div class="alert alert-success alert-dismissable">
    <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
    <span class="glyphicon glyphicon-warning-sign"></span>
    ${message}
  </div>
</c:if>