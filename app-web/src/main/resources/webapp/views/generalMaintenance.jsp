<%@page isELIgnored="false"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/include/header.jsp" />

<sf:form method="post" modelAttribute="config" class="form form-horizontal">
	<sf:errors path="*" cssClass="alert alert-danger" element="div" />
	<div class="form-group">
		<label for="hashtag" class="col-sm-2 control-label">hashtag</label>
		<div class="col-sm-4">
			<sf:input path="hashtag" id="hashtag" class="form-control" />
		</div>
	</div>

	<div class="form-group">
		<label for="numberOfTweets" class="col-sm-2 control-label">number of shown tweets</label>
		<div class="col-sm-4">
			<sf:input path="numberOfTweets" id="numberOfTweets" class="form-control" />
		</div>
	</div>
	
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<button id="submitInputForm" class="btn btn-primary" type="submit">save</button>
		</div>
	</div>
</sf:form>

<jsp:include page="/include/footer.jsp" />