<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="/include/header.jsp" />
<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<th>Name</th>
			<th>Address</th>
			<th>Description</th>
			<th>xValue</th>
			<th>yValue</th>
			<th>Contact</th>
			<th>Website</th>
			<th></th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${points}" var="point">
		<tr>
			<td>${point.name}</td>
			<td>${point.address}</td>
			<td>${point.description}</td>
			<td>${point.xValue}</td>
			<td>${point.yValue}</td>
			<td>${point.contact}</td>
			<td>${point.website}</td>
			<td>
				<a href="edit/${point.id}">edit</a>,
				<a href="delete/${point.id}">delete</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>

<a href="#" id="addPointBtn" class="btn btn-primary">create new point</a>

<sf:form method="post" class="form form-horizontal" id="inputForm" style="display:none">
	<sf:errors path="*" cssClass="alert alert-danger" element="div" />
	<div class="form-group">
		<label for="name" class="col-sm-2 control-label">name</label>
		<div class="col-sm-4">
			<sf:input path="name" id="name" class="form-control" />
		</div>
	</div>
	<div class="form-group">
		<label for="address" class="col-sm-2 control-label">address</label>
		<div class="col-sm-4">
			<sf:input path="address" id="address" class="form-control" />
		</div>
	</div>
	<div class="form-group">
		<label for="description" class="col-sm-2 control-label">description</label>
		<div class="col-sm-4">
			<sf:input path="description" id="description" class="form-control" />
		</div>
	</div>
	<div class="form-group">
		<label for="xValue" class="col-sm-2 control-label">xValue</label>
		<div class="col-sm-4">
			<sf:input path="xValue" id="xValue" class="form-control" />
		</div>
	</div>
	<div class="form-group">
		<label for="yValue" class="col-sm-2 control-label">yValue</label>
		<div class="col-sm-4">
			<sf:input path="yValue" id="yValue" class="form-control" />
		</div>
	</div>
	<div class="form-group">
		<label for="contact" class="col-sm-2 control-label">contact</label>
		<div class="col-sm-4">
			<sf:input path="contact" id="contact" class="form-control" />
		</div>
	</div>
	<div class="form-group">
		<label for="website" class="col-sm-2 control-label">website</label>
		<div class="col-sm-4">
			<sf:input path="website" id="website" class="form-control" />
		</div>
	</div>
	
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<input type="submit" value="submit" class="btn btn-primary" />
		</div>
	</div>
</sf:form>

<jsp:include page="/include/footer.jsp" />

<script type="text/javascript">
	$('#addPointBtn').click('click', function(event) {
		$(this).hide();
		$('#inputForm').show();
	});
</script>