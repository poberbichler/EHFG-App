<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/header.jsp" />

<table class="table table-striped table-bordered">
	<thead>
		<th>image</th>
		<th>name</th>
		<th>description</th>
	</thead>

	<tbody>
		<c:forEach items="${speakers}" var="speaker">
			<tr>
				<td><img src="${speaker.imageUrl}" style="width: 120px; border: 1px solid black"/></td>
				<td>${speaker.fullName}</td>
				<td>${speaker.description}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<jsp:include page="/include/footer.jsp" />