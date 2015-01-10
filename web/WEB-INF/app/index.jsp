<%@ include file="/WEB-INF/app/includes/header.jsp"%>

    <div class="container">
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <h1 class="text-center">
                  Welcome to FreeArt!
                </h1>
                <form method="get" action="${pageContext.request.contextPath}/Search">
                    <div class="form-group">
                        <div class="text-center">
                            <label for="search">Find some art!</label>
                        </div>
                        <input class="form-control text-center" id="search" type="text" name="search" placeholder="Type a keyword" />
                    </div>
                    <button type="submit" class="btn btn-primary center-block">Search</button>
                </form>
            </div>
        </div>
    </div>

<%@ include file="/WEB-INF/app/includes/footer.jsp"%>