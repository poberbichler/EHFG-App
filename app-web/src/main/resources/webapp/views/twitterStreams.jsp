<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/include/header.jsp" />

<form action="/mvc/maintenance/twitterstreams/add" method="post" class="form form-horizontal">
	<h4>add stream</h4>
	<div class="form-group">
		<div class="col-sm-4">
			<div class="input-group">
				<span class="input-group-addon">#</span>
				<input id="streamName" class="form-control" name="streamName" />
			</div>
		</div>
		
		<input id="submitInputForm" class="btn btn-primary" type="submit" value="add" />
	</div>
</form>

<c:if test="${not empty streams}">
	<div class="col-sm-3" style="padding-left: 0">
		<h4>active streams <small>(click to delete)</small></h4>
		<div class="list-group">
			<c:forEach items="${streams}" var="stream">
				<a href="twitterstreams/delete/${stream.substring(1)}" class="list-group-item">${stream}</a>
			</c:forEach>
		</div>
	</div>
</c:if>

<jsp:include page="/include/footer.jsp" />