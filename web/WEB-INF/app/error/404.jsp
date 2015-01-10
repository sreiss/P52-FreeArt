<%--
  Created by IntelliJ IDEA.
  User: Andesite
  Date: 1/10/2015
  Time: 5:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="/WEB-INF/app/includes/header.jsp"%>
<body>
<%@ include file="/WEB-INF/app/includes/menu.jsp"%>
<div class="container">
  <h1 class="text-danger"><%= request.getAttribute("javax.servlet.error.status_code")%>!</h1>
  <p class="text-danger"><%= request.getAttribute("javax.servlet.error.message")%></p>
</div>
<%@ include file="/WEB-INF/app/includes/footer.jsp"%>
</body>
</html>